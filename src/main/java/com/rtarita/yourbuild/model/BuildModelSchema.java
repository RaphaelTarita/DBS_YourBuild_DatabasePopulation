package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;
import com.rtarita.yourbuild.util.Constants;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BuildModelSchema implements ModelSchema<BuildModel> {
    private static final List<String> allColumnNames = List.of(
        BuildModel.TRACKING_NUMBER,
        BuildModel.BUILD_ID,
        BuildModel.PRICE,
        BuildModel.PERFORMANCE_RATING
    );

    private static final List<String> insertableColumnNames = List.of(
        BuildModel.TRACKING_NUMBER,
        BuildModel.BUILD_ID,
        BuildModel.PRICE,
        BuildModel.PERFORMANCE_RATING
    );

    private static final List<String> nonDefaultColumnNames = insertableColumnNames;

    private static final Set<String> primaryKeyColumns = Set.of(BuildModel.TRACKING_NUMBER, BuildModel.BUILD_ID);
    private static final Map<List<String>, Reference> foreignKeyColumns =
        Map.of(List.of(BuildModel.TRACKING_NUMBER), new Reference(
            ClientOrderModelSchema.class,
            List.of(ClientOrderModel.TRACKING_NUMBER)
        ));
    private static final Set<String> columnsOmittedByGeneration = Set.of(BuildModel.TRACKING_NUMBER);

    @Override
    public String tableName() {
        return "BUILD";
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
    public BuildModel generate(Faker faker) {
        return new BuildModel(
            0,
            faker.bothify(Constants.ID_PATTERN),
            faker.random().nextInt(0, 1_000_00),
            faker.random().nextInt(0, 20) > 15
                ? null
                : (float) faker.number().randomDouble(5, 0, 10)
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
