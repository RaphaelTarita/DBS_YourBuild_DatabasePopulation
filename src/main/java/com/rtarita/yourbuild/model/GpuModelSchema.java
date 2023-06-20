package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GpuModelSchema implements ModelSchema<GpuModel> {
    private static final List<String> allColumnNames = List.of(
        GpuModel.EAN,
        GpuModel.BASE_CLOCK,
        GpuModel.BOOST_CLOCK,
        GpuModel.VRAM
    );

    private static final List<String> insertableColumnNames = allColumnNames;
    private static final List<String> nonDefaultColumnNames = allColumnNames;
    private static final Set<String> primaryKeyColumns = Set.of(GpuModel.EAN);
    private static final Map<List<String>, Reference> foreignKeys =
        Map.of(List.of(GpuModel.EAN), new Reference(
            PartModelSchema.class,
            List.of(PartModel.EAN)
        ));
    private static final Set<String> columnsOmittedByGeneration = Set.of(GpuModel.EAN);

    @Override
    public String tableName() {
        return "GPU";
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
    public GpuModel generate(Faker faker) {
        float baseClock = (float) faker.number().randomDouble(5, 1, 3);
        return new GpuModel(
            "",
            baseClock,
            (float) faker.number().randomDouble(5, (int) baseClock + 1, 5),
            faker.random().nextInt(1, 64)
        );
    }

    @Override
    public Set<String> primaryKeyColumns() {
        return primaryKeyColumns;
    }

    @Override
    public Map<List<String>, Reference> foreignKeyColumns() {
        return foreignKeys;
    }
}
