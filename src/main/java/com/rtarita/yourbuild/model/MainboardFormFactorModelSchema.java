package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;
import com.rtarita.yourbuild.util.Constants;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainboardFormFactorModelSchema implements ModelSchema<MainboardFormFactorModel> {
    private static final List<String> allColumnNames = List.of(
        MainboardFormFactorModel.ID,
        MainboardFormFactorModel.NAME
    );

    private static final List<String> insertableColumnNames = allColumnNames;
    private static final List<String> nonDefaultColumnNames = allColumnNames;
    private static final Set<String> primaryKeyColumns = Set.of(MainboardFormFactorModel.ID);
    private static final Map<List<String>, Reference> foreignKeyColumns = Collections.emptyMap();
    private static final Set<String> columnsOmittedByGeneration = Collections.emptySet();

    @Override
    public String tableName() {
        return "MAINBOARD_FORM_FACTOR";
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
    public MainboardFormFactorModel generate(Faker faker) {
        return new MainboardFormFactorModel(
            faker.bothify(Constants.ID_PATTERN),
            faker.bothify(Constants.FORM_FACTOR_PATTERN, true)
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
