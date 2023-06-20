package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProducesModelSchema implements ModelSchema<ProducesModel> {
    private static final List<String> allColumnNames = List.of(
        ProducesModel.MANUFACTURER_ID,
        ProducesModel.EAN
    );

    private static final List<String> insertableColumnNames = allColumnNames;
    private static final List<String> nonDefaultColumnNames = allColumnNames;
    private static final Set<String> primaryKeyColumns = Set.of(ProducesModel.MANUFACTURER_ID, ProducesModel.EAN);
    private static final Map<List<String>, Reference> foreignKeyColumns = Map.of(
        List.of(ProducesModel.MANUFACTURER_ID), new Reference(ManufacturerModelSchema.class, List.of(ManufacturerModel.MANUFACTURER_ID)),
        List.of(ProducesModel.EAN), new Reference(PartModelSchema.class, List.of(PartModel.EAN))
    );
    private static final Set<String> columnsOmittedByGeneration = Set.of(ProducesModel.MANUFACTURER_ID, ProducesModel.EAN);

    @Override
    public String tableName() {
        return "PRODUCES";
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
    public ProducesModel generate(Faker faker) {
        return new ProducesModel(
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
