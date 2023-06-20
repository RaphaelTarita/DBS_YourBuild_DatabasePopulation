package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;
import com.rtarita.yourbuild.model.enums.PartPerformanceLabel;
import com.rtarita.yourbuild.util.StringUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PartModelSchema implements ModelSchema<PartModel> {
    private static final Map<String, Integer> fullNameRegistry = new HashMap<>();
    private static final List<String> allColumnNames = List.of(
        PartModel.EAN,
        PartModel.FULL_NAME,
        PartModel.DESCRIPTION,
        PartModel.PRICE,
        PartModel.MSRP,
        PartModel.PERFORMANCE_LABEL
    );

    private static final List<String> insertableColumnNames = allColumnNames;

    private static final List<String> nonDefaultColumnNames = List.of(
        PartModel.EAN,
        PartModel.FULL_NAME,
        PartModel.PRICE,
        PartModel.MSRP
    );

    private static final Set<String> primaryKeyColumns = Set.of(PartModel.EAN);
    private static final Map<List<String>, Reference> foreignKeyColumns = Collections.emptyMap();
    private static final Set<String> columnsOmittedByGeneration = Collections.emptySet();

    private static final List<Character> allowedChars = Arrays.stream(PartPerformanceLabel.values())
        .map(PartPerformanceLabel::getCode)
        .toList();

    @Override
    public String tableName() {
        return "PART";
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
    public PartModel generate(Faker faker) {
        return new PartModel(
            faker.code().ean13(),
            StringUtil.uniqueAndTruncated(fullNameRegistry, faker.commerce().productName(), 100),
            faker.lorem().fixedString(faker.random().nextInt(1, 1000)),
            faker.random().nextInt(0, 500_000),
            faker.random().nextInt(0, 500_000),
            PartPerformanceLabel.fromCode(allowedChars.get(faker.random().nextInt(0, allowedChars.size() - 1)))
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
