package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;
import com.rtarita.yourbuild.util.Constants;
import com.rtarita.yourbuild.util.StringUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ManufacturerModelSchema implements ModelSchema<ManufacturerModel> {
    private static final Map<String, Integer> nameRegistry = new HashMap<>();
    private static final List<String> allColumnNames = List.of(
        ManufacturerModel.MANUFACTURER_ID,
        ManufacturerModel.NAME,
        ManufacturerModel.HOMEPAGE
    );

    private static final List<String> insertableColumnNames = allColumnNames;

    private static final List<String> nonDefaultColumnNames = List.of(
        ManufacturerModel.MANUFACTURER_ID,
        ManufacturerModel.NAME
    );

    private static final Set<String> primaryKeyColumns = Set.of(ManufacturerModel.MANUFACTURER_ID);
    private static final Map<List<String>, Reference> foreignKeyColumns = Collections.emptyMap();
    private static final Set<String> columnsOmittedByGeneration = Collections.emptySet();

    @Override
    public String tableName() {
        return "MANUFACTURER";
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
    public ManufacturerModel generate(Faker faker) {
        return new ManufacturerModel(
            faker.bothify(Constants.ID_PATTERN),
            StringUtil.uniqueAndTruncated(nameRegistry, faker.company().name(), 50),
            StringUtil.truncate(faker.company().url(), 100)
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
