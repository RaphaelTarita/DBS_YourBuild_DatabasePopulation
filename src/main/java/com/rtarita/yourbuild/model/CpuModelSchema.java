package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CpuModelSchema implements ModelSchema<CpuModel> {
    private static final List<String> allColumnNames = List.of(
        CpuModel.EAN,
        CpuModel.CORE_COUNT,
        CpuModel.THREAD_COUNT,
        CpuModel.BASE_CLOCK,
        CpuModel.BOOST_CLOCK,
        CpuModel.SOCKET_TYPE_ID
    );

    private static final List<String> insertableColumnNames = allColumnNames;
    private static final List<String> nonDefaultColumnNames = allColumnNames;
    private static final Set<String> primaryKeyColumns = Set.of(CpuModel.EAN);
    private static final Map<List<String>, Reference> foreignKeyColumns = Map.of(
        List.of(CpuModel.EAN), new Reference(PartModelSchema.class, List.of(PartModel.EAN)),
        List.of(CpuModel.SOCKET_TYPE_ID), new Reference(CpuSocketTypeModelSchema.class, List.of(CpuSocketTypeModel.ID))
    );
    private static final Set<String> columnsOmittedByGeneration = Set.of(CpuModel.EAN, CpuModel.SOCKET_TYPE_ID);

    @Override
    public String tableName() {
        return "CPU";
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
    public CpuModel generate(Faker faker) {
        int coreCount = faker.random().nextInt(1, 64);
        float baseClock = (float) faker.number().randomDouble(5, 1, 5);
        return new CpuModel(
            "",
            coreCount,
            faker.random().nextInt(coreCount, coreCount * 2),
            baseClock,
            (float) faker.number().randomDouble(5, (int) baseClock + 1, 8),
            ""
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
