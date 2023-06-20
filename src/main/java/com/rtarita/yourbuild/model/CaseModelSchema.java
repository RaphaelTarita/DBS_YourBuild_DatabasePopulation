package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;
import com.rtarita.yourbuild.util.Constants;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CaseModelSchema implements ModelSchema<CaseModel> {
    private static final List<String> allColumnNames = List.of(
        CaseModel.EAN,
        CaseModel.CASE_FORM_FACTOR
    );

    private static final List<String> insertableColumnNames = allColumnNames;
    private static final List<String> nonDefaultColumnNames = allColumnNames;
    private static final Set<String> primaryKeyColumns = Set.of(CaseModel.EAN);
    private static final Map<List<String>, Reference> foreignKeyColumns =
        Map.of(List.of(CaseModel.EAN), new Reference(
            PartModelSchema.class,
            List.of(PartModel.EAN)
        ));
    private static final Set<String> columnsOmittedByGeneration = Set.of(CaseModel.EAN);

    @Override
    public String tableName() {
        return "CASE";
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
    public CaseModel generate(Faker faker) {
        return new CaseModel(
            "",
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
