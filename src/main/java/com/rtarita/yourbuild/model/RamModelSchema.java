package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RamModelSchema implements ModelSchema<RamModel> {
    private static final List<String> allColumnNames = List.of(
        RamModel.EAN,
        RamModel.STORAGE_SIZE,
        RamModel.MEMORY_CLOCK_SPEED
    );

    private static final List<String> insertableColumnNames = allColumnNames;
    private static final List<String> nonDefaultColumnNames = allColumnNames;
    private static final Set<String> primaryKeyColumns = Set.of(RamModel.EAN);
    private static final Map<List<String>, Reference> foreignKeyColumns =
        Map.of(List.of(RamModel.EAN), new Reference(
            PartModelSchema.class,
            List.of(PartModel.EAN)
        ));
    private static final Set<String> columnsOmittedByGeneration = Set.of(RamModel.EAN);

    @Override
    public String tableName() {
        return "RAM";
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
    public RamModel generate(Faker faker) {
        return new RamModel(
            "",
            faker.random().nextInt(1, 64),
            faker.random().nextInt(1000, 10_000)
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
