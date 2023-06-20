package com.rtarita.yourbuild.generator;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.DatabaseHandle;
import com.rtarita.yourbuild.database.Reference;
import com.rtarita.yourbuild.model.ModelSchema;
import com.rtarita.yourbuild.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabasePopulationEngine {
    private static final Logger log = LoggerFactory.getLogger(DatabasePopulationEngine.class);
    private final DatabaseHandle handle;
    private final List<GenerationRequest<?, ?>> requests;
    private final Map<Class<?>, RecordCache> generationCache = new HashMap<>();

    private static <T> Map<T, Set<T>> reverseGraph(Map<T, Set<T>> graph) {
        Map<T, Set<T>> reversed = new HashMap<>(graph.size());
        for (Map.Entry<T, Set<T>> entry : graph.entrySet()) {
            reversed.putIfAbsent(entry.getKey(), Collections.emptySet());
            for (T neighbor : entry.getValue()) {
                reversed.merge(
                    neighbor,
                    Set.of(entry.getKey()),
                    (oldVal, newVal) -> {
                        Set<T> newNeighbors = new HashSet<>(oldVal);
                        newNeighbors.add(entry.getKey());
                        return newNeighbors;
                    });
            }
        }
        return reversed;
    }

    private static <T> List<T> topologicalSort(Map<T, Set<T>> graph) {
        Map<T, Integer> inDegree = new HashMap<>(graph.size());
        for (T vertex : graph.keySet()) {
            inDegree.putIfAbsent(vertex, 0);
            for (T neighbor : graph.get(vertex)) {
                inDegree.merge(neighbor, 1, (oldVal, newVal) -> oldVal + 1);
            }
        }
        Queue<T> queue = new ArrayDeque<>();
        for (Map.Entry<T, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }
        List<T> result = new ArrayList<>(graph.size());
        while (!queue.isEmpty()) {
            T vertex = queue.remove();
            result.add(vertex);
            for (T neighbor : graph.get(vertex)) {
                inDegree.computeIfPresent(neighbor, (v, deg) -> deg - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }
        return result;
    }

    public DatabasePopulationEngine(DatabaseHandle handle, List<GenerationRequest<?, ?>> requests) {
        log.info("--> Step 1: initializing");
        this.handle = handle;
        this.requests = requests;
        for (GenerationRequest<?, ?> req : requests) {
            generationCache.put(req.getModelSchema().getClass(), new RecordCache(req.getGenerationCount(), req.getModelSchema()));
        }
    }

    private Map<String, Object> translateForeignKey(
        Map<String, Object> foreignRecord,
        Map.Entry<List<String>, Reference> foreignKey) {
        List<String> ownColumnNames = foreignKey.getKey();
        List<String> foreignColumnNames = foreignKey.getValue().targetColumns();
        Map<String, Object> result = new HashMap<>(foreignRecord.size());
        for (Map.Entry<String, Object> entry : foreignRecord.entrySet()) {
            int idx = foreignColumnNames.indexOf(entry.getKey());
            if (idx >= 0) {
                result.put(
                    ownColumnNames.get(foreignColumnNames.indexOf(entry.getKey())),
                    entry.getValue()
                );
            }
        }
        return result;
    }

    private void generateSingle(GenerationRequest<?, ?> request) {
        ModelSchema<?> ms = request.getModelSchema();
        Faker faker = request.getFaker();
        Map<String, Object> insertParams = ms.generate(faker).insertStatementParameters();
        Set<String> omitted = new HashSet<>(ms.columnsOmittedByGeneration());
        List<Map.Entry<List<String>, Reference>> fkOrder = ms.foreignKeyColumns()
            .entrySet()
            .stream()
            .sorted((fk1, fk2) -> fk2.getKey().size() - fk1.getKey().size())
            .toList();

        for (Map.Entry<List<String>, Reference> fk : fkOrder) {
            if (fk.getValue().target().equals(ms.getClass())) continue;
            RecordCache cache = generationCache.get(fk.getValue().target());
            Map<String, Object> foreign = translateForeignKey(
                Collections.disjoint(ms.primaryKeyColumns(), fk.getKey())
                    ? cache.getRandom(faker.random())
                    : cache.getNextUnused(),
                fk
            );
            foreign.keySet().retainAll(omitted);
            insertParams.putAll(foreign);
            fk.getKey().forEach(omitted::remove);
        }
        for (String omittedColumn : omitted) {
            insertParams.remove(omittedColumn);
        }
        generationCache.get(ms.getClass()).addRecord(insertParams);
    }

    private void resetAllCaches() {
        for (RecordCache cache : generationCache.values()) {
            cache.reset();
        }
    }

    private Set<GenerationRequest<?, ?>> determineAutogenerated(Collection<GenerationRequest<?, ?>> requests) {
        return requests.stream()
            .filter(req -> {
                    Set<String> foreignKeys = req.getModelSchema()
                        .foreignKeyColumns()
                        .keySet()
                        .stream()
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());
                    return !foreignKeys.containsAll(req.getModelSchema().columnsOmittedByGeneration());
                }
            )
            .collect(Collectors.toSet());
    }

    private Map<GenerationRequest<?, ?>, Map<List<String>, Reference>> determineRecursive(Collection<GenerationRequest<?, ?>> requests) {
        return requests.stream()
            .filter(req -> req.getModelSchema()
                .foreignKeyColumns()
                .values()
                .stream()
                .anyMatch(fkRef -> fkRef.target().equals(req.getModelSchema().getClass()))
            )
            .collect(Collectors.toMap(
                Function.identity(),
                req -> req.getModelSchema().foreignKeyColumns()
            ));
    }

    private void prepareInsertStatements(Collection<GenerationRequest<?, ?>> requests) {
        for (GenerationRequest<?, ?> req : requests) {
            String tableName = req.getModelSchema().tableName();
            List<String> columns = req.getModelSchema().insertableColumnNames();
            handle.prepareDMLStatement(
                tableName,
                "INSERT INTO "
                    + tableName
                    + " ("
                    + String.join(", ", columns)
                    + ") VALUES ("
                    + String.join(", ", Collections.nCopies(columns.size(), "?"))
                    + ")"
            );
        }
    }

    private void prepareUpdateStatements(Map<GenerationRequest<?, ?>, Map<List<String>, Reference>> requests) {
        for (Map.Entry<GenerationRequest<?, ?>, Map<List<String>, Reference>> req : requests.entrySet()) {
            String tableName = req.getKey().getModelSchema().tableName();
            for (Map.Entry<List<String>, Reference> fk : req.getValue().entrySet()) {
                handle.prepareDMLStatement(
                    tableName + "_UPDATE",
                    "UPDATE "
                        + tableName
                        + " SET "
                        + fk.getKey()
                        .stream()
                        .map(col -> col + " = ?")
                        .collect(Collectors.joining(", "))
                        + " WHERE "
                        + fk.getValue()
                        .targetColumns()
                        .stream()
                        .map(col -> col + " = ?")
                        .collect(Collectors.joining(" AND "))
                );
            }
        }
    }


    private void prepareSelectStatements(Collection<GenerationRequest<?, ?>> requests) {
        for (GenerationRequest<?, ?> req : requests) {
            Set<String> autogeneratedColumns = new HashSet<>(req.getModelSchema().allColumnNames());
            req.getModelSchema().insertableColumnNames().forEach(autogeneratedColumns::remove);
            String tableName = req.getModelSchema().tableName();
            handle.prepareSQLStatement(
                tableName,
                "SELECT "
                    + String.join(", ", autogeneratedColumns)
                    + " FROM "
                    + tableName
            );
        }
    }

    private void logGenerationOrder(List<GenerationRequest<?, ?>> order) {
        int boxSize = 70;
        String border = String.join("", Collections.nCopies(boxSize, "-"));
        log.info(border);
        log.info(StringUtil.padWith("| > GENERATION ORDER ", boxSize - 1, '<') + '|');
        log.info(StringUtil.padWith("|", boxSize - 1, ' ') + '|');
        for (GenerationRequest<?, ?> req : order) {
            log.info(
                StringUtil.padWith(
                    "| " + req.getModelSchema().tableName() + " (" + req.getGenerationCount() + " rows)",
                    boxSize - 1,
                    ' '
                ) + '|'
            );
        }
        log.info(border);
    }

    private void insertAll(ModelSchema<?> ms) {
        handle.transactional(h -> {
            for (Map<String, Object> record : generationCache.get(ms.getClass())) {
                h.executeDML(
                    ms.tableName(),
                    ms.insertableColumnNames()
                        .stream()
                        .map(record::get)
                        .collect(Collectors.toList())
                );
            }
        });
    }

    private void fetchAll(ModelSchema<?> ms) {
        List<Map<String, Object>> cache = generationCache.get(ms.getClass());
        List<Map<String, Object>> result = handle.executeSQLAndUnwind(ms.tableName());
        if (cache.size() != result.size()) {
            log.warn("Expected " + cache.size() + " records in database but got " + result.size());
        }
        int counter = 0;
        for (Map<String, Object> record : result) {
            if (counter >= cache.size()) break;
            cache.get(counter++).putAll(record);
        }
    }

    private void updateRecursiveRelation(GenerationRequest<?, ?> request, List<String> columns) {
        String dmlStatementKey = request.getModelSchema().tableName() + "_UPDATE";
        RecordCache cache = generationCache.get(request.getModelSchema().getClass());
        for (Map<String, Object> record : cache) {
            Map<String, Object> other = cache.getRandom(request.getFaker().random());
            Stream<Object> newParams = columns.stream().map(other::get);
            Stream<Object> recordIdentifier = columns.stream().map(record::get);
            handle.executeDML(
                dmlStatementKey,
                Stream.concat(newParams, recordIdentifier).toList()
            );
        }
    }

    public void populate() {
        log.info("--> Step 2: calculating generation order...");
        Map<GenerationRequest<?, ?>, Set<GenerationRequest<?, ?>>> generatorGraph = new HashMap<>(requests.size());
        for (GenerationRequest<?, ?> req : requests) {
            Set<Class<? extends ModelSchema<?>>> foreignKeys = req.getModelSchema()
                .foreignKeyColumns()
                .values()
                .stream()
                .map(Reference::target)
                .collect(Collectors.toSet());
            generatorGraph.put(
                req,
                requests.stream()
                    .filter(r -> !r.getModelSchema().getClass().equals(req.getModelSchema().getClass())
                        && foreignKeys.contains(r.getModelSchema().getClass()))
                    .collect(Collectors.toSet())

            );
        }
        List<GenerationRequest<?, ?>> generationOrder = topologicalSort(reverseGraph(generatorGraph));
        logGenerationOrder(generationOrder);
        log.info("--> Step 3: calculating autogeneration-dependent tables...");
        Set<GenerationRequest<?, ?>> requireSQL = determineAutogenerated(generationOrder);
        log.info(
            "The following tables autogenerate some of their columns: "
                + requireSQL.stream()
                .map(req -> req.getModelSchema().tableName())
                .collect(Collectors.joining(", "))
        );
        log.info("--> Step 4: calculating tables with recursive relations...");
        Map<GenerationRequest<?, ?>, Map<List<String>, Reference>> recursive = determineRecursive(generationOrder);
        log.info(
            "The following tables have recursive relations: "
                + recursive.keySet()
                .stream()
                .map(req -> req.getModelSchema().tableName())
                .collect(Collectors.joining(", "))
        );
        log.info("--> Step 5: generating DML...");
        prepareInsertStatements(generationOrder);
        prepareUpdateStatements(recursive);
        log.info("--> Step 6: generating SQL...");
        prepareSelectStatements(requireSQL);
        log.info("--> Step 7: Generating records and populating database... ");
        int count = 1;
        for (GenerationRequest<?, ?> request : generationOrder) {
            log.info(count++ + ". " + request.getModelSchema().tableName());
            log.info("# Generating " + request.getGenerationCount() + " records...");
            for (int i = 0; i < request.getGenerationCount(); ++i) {
                generateSingle(request);
            }
            resetAllCaches();
            log.info("# inserting all records...");
            insertAll(request.getModelSchema());
            if (requireSQL.contains(request)) {
                log.info("# fetching autogenerated columns...");
                fetchAll(request.getModelSchema());
            }
            if (recursive.containsKey(request)) {
                log.info("# connecting recursive relations...");
                Map<List<String>, Reference> relations = recursive.get(request);
                for (Map.Entry<List<String>, Reference> relation : relations.entrySet()) {
                    updateRecursiveRelation(request, relation.getValue().targetColumns());
                }
            }
        }
        log.info("--> Step 8: clearing cache...");
        int totalInserted = generationCache.values()
            .stream()
            .map(RecordCache::size)
            .reduce(0, Integer::sum);
        generationCache.clear();
        log.info("=== DONE. Inserted " + totalInserted + " records into the database ===");
    }
}
