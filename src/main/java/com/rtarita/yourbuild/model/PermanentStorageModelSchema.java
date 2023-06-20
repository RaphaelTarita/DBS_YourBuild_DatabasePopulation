package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;
import com.rtarita.yourbuild.model.enums.PermanentStorageType;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PermanentStorageModelSchema implements ModelSchema<PermanentStorageModel> {
    private static final List<String> allColumnNames = List.of(
        PermanentStorageModel.EAN,
        PermanentStorageModel.TYPE,
        PermanentStorageModel.STORAGE_SIZE
    );

    private static final List<String> insertableColumnNames = allColumnNames;
    private static final List<String> nonDefaultColumnNames = allColumnNames;
    private static final Set<String> primaryKeyColumns = Set.of(PermanentStorageModel.EAN);
    private static final Map<List<String>, Reference> foreignKeyColumns =
        Map.of(List.of(PermanentStorageModel.EAN), new Reference(
            PartModelSchema.class,
            List.of(PartModel.EAN)
        ));
    private static final Set<String> columnsOmittedByGeneration = Set.of(PermanentStorageModel.EAN);

    @Override
    public String tableName() {
        return "PERMANENT_STORAGE";
    }

    @Override
    public List<String> allColumnNames() {
        return allColumnNames;
    }

    @Override
    public List<String> insertableColumnNames() {
        return insertableColumnNames;
    }

    @Override
    public List<String> nonDefaultColumnNames() {
        return nonDefaultColumnNames;
    }

    @Override
    public Set<String> columnsOmittedByGeneration() {
        return columnsOmittedByGeneration;
    }

    @Override
    public PermanentStorageModel generate(Faker faker) {
        return new PermanentStorageModel(
            "",
            PermanentStorageType.fromCode(faker.random().nextInt(0, 2)),
            faker.random().nextInt(1, 8192)
        );
    }

    @Override
    public Set<String> primaryKeyColumns() {
        return primaryKeyColumns;
    }

    @Override
    public Map<List<String>, Reference> foreignKeyColumns() {
        return foreignKeyColumns;
    }
}
