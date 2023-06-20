package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;
import com.rtarita.yourbuild.model.enums.PsuEfficiencyRating;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PsuModelSchema implements ModelSchema<PsuModel> {
    private static final List<String> allColumns = List.of(
        PsuModel.EAN,
        PsuModel.WATTAGE,
        PsuModel.EFFICIENCY_RATING
    );

    private static final List<String> insertableColumns = allColumns;
    private static final List<String> nonDefaultColumns = allColumns;

    private static final Set<String> primaryKeyColumns = Set.of(PsuModel.EAN);
    private static final Map<List<String>, Reference> foreignKeyColumns =
        Map.of(List.of(PsuModel.EAN), new Reference(
            PartModelSchema.class,
            List.of(PartModel.EAN)
        ));
    private static final Set<String> columnsOmittedByGeneration = Set.of(PsuModel.EAN);

    @Override
    public String tableName() {
        return "PSU";
    }

    @Override
    public List<String> allColumnNames() {
        return allColumns;
    }

    @Override
    public List<String> insertableColumnNames() {
        return insertableColumns;
    }

    @Override
    public List<String> nonDefaultColumnNames() {
        return nonDefaultColumns;
    }

    @Override
    public Set<String> columnsOmittedByGeneration() {
        return columnsOmittedByGeneration;
    }

    @Override
    public PsuModel generate(Faker faker) {
        return new PsuModel(
            "",
            faker.random().nextInt(1, 2500),
            PsuEfficiencyRating.fromCode(faker.random().nextInt(0, 5))
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
