package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CpuCoolerModelSchema implements ModelSchema<CpuCoolerModel> {
    private static final List<String> allColumnNames = List.of(
        CpuCoolerModel.EAN,
        CpuCoolerModel.SOCKET_TYPE_ID
    );

    private static final List<String> insertableColumnNames = allColumnNames;
    private static final List<String> nonDefaultColumnNames = allColumnNames;
    private static final Set<String> primaryKeyColumns = Set.of(CpuCoolerModel.EAN);
    private static final Map<List<String>, Reference> foreignKeyColumns = Map.of(
        List.of(CpuCoolerModel.EAN), new Reference(PartModelSchema.class, List.of(PartModel.EAN)),
        List.of(CpuCoolerModel.SOCKET_TYPE_ID), new Reference(CpuSocketTypeModelSchema.class, List.of(CpuSocketTypeModel.ID))
    );
    private static final Set<String> columnsOmittedByGeneration = Set.of(CpuCoolerModel.EAN, CpuCoolerModel.SOCKET_TYPE_ID);

    @Override
    public String tableName() {
        return "CPU_COOLER";
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
    public CpuCoolerModel generate(Faker faker) {
        return new CpuCoolerModel(
            "",
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
