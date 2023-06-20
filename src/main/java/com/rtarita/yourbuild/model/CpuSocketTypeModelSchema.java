package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;
import com.rtarita.yourbuild.util.Constants;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CpuSocketTypeModelSchema implements ModelSchema<CpuSocketTypeModel> {
    private static final List<String> allColumnNames = List.of(
        CpuSocketTypeModel.ID,
        CpuSocketTypeModel.NAME
    );

    private static final List<String> insertableColumnNames = allColumnNames;
    private static final List<String> nonDefaultColumnNames = allColumnNames;

    private static final Set<String> primaryKeyColumns = Set.of(CpuSocketTypeModel.ID);
    private static final Map<List<String>, Reference> foreignKeyColumns = Collections.emptyMap();
    private static final Set<String> columnsOmittedByGeneration = Collections.emptySet();

    @Override
    public String tableName() {
        return "CPU_SOCKET_TYPE";
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
    public CpuSocketTypeModel generate(Faker faker) {
        return new CpuSocketTypeModel(
            faker.bothify(Constants.ID_PATTERN),
            faker.bothify(Constants.SOCKET_TYPE_PATTERN, true)
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
