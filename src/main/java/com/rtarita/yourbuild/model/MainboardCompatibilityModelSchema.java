package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainboardCompatibilityModelSchema implements ModelSchema<MainboardCompatibilityModel> {
    private static final List<String> allColumnNames = List.of(
        MainboardCompatibilityModel.EAN,
        MainboardCompatibilityModel.FORM_FACTOR_ID
    );

    private static final List<String> insertableColumnNames = allColumnNames;
    private static final List<String> nonDefaultColumnNames = allColumnNames;
    private static final Set<String> primaryKeyColumns = Set.of(MainboardCompatibilityModel.EAN, MainboardCompatibilityModel.FORM_FACTOR_ID);
    private static final Map<List<String>, Reference> foreignKeyColumns = Map.of(
        List.of(MainboardCompatibilityModel.EAN), new Reference(CaseModelSchema.class, List.of(CaseModel.EAN)),
        List.of(MainboardCompatibilityModel.FORM_FACTOR_ID), new Reference(MainboardFormFactorModelSchema.class, List.of(MainboardFormFactorModel.ID))
    );
    private static final Set<String> columnsOmittedByGeneration = Set.of(MainboardCompatibilityModel.EAN, MainboardCompatibilityModel.FORM_FACTOR_ID);

    @Override
    public String tableName() {
        return "MAINBOARD_COMPATIBILITY";
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
    public MainboardCompatibilityModel generate(Faker faker) {
        return new MainboardCompatibilityModel(
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
