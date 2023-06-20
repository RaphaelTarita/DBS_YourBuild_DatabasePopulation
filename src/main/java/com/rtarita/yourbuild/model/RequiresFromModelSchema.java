package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RequiresFromModelSchema implements ModelSchema<RequiresFromModel> {
    private static final List<String> allColumnNames = List.of(
        RequiresFromModel.TRACKING_NUMBER,
        RequiresFromModel.BUILD_ID,
        RequiresFromModel.VENDOR_ID,
        RequiresFromModel.EAN
    );

    private static final List<String> insertableColumnNames = allColumnNames;
    private static final List<String> nonDefaultColumnNames = allColumnNames;

    private static final Set<String> primaryKeyColumns = Set.of(
        RequiresFromModel.TRACKING_NUMBER,
        RequiresFromModel.BUILD_ID,
        RequiresFromModel.VENDOR_ID,
        RequiresFromModel.EAN
    );

    private static final Map<List<String>, Reference> foreignKeyColumns = Map.of(
        List.of(RequiresFromModel.TRACKING_NUMBER), new Reference(ClientOrderModelSchema.class, List.of(ClientOrderModel.TRACKING_NUMBER)),
        List.of(RequiresFromModel.TRACKING_NUMBER, RequiresFromModel.BUILD_ID), new Reference(BuildModelSchema.class, List.of(BuildModel.TRACKING_NUMBER, BuildModel.BUILD_ID)),
        List.of(RequiresFromModel.VENDOR_ID), new Reference(VendorModelSchema.class, List.of(VendorModel.VENDOR_ID)),
        List.of(RequiresFromModel.EAN), new Reference(PartModelSchema.class, List.of(PartModel.EAN))
    );
    private static final Set<String> columnsOmittedByGeneration = Set.of(RequiresFromModel.TRACKING_NUMBER, RequiresFromModel.BUILD_ID, RequiresFromModel.VENDOR_ID, RequiresFromModel.EAN);

    @Override
    public String tableName() {
        return "REQUIRES_FROM";
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
    public RequiresFromModel generate(Faker faker) {
        return new RequiresFromModel(
            0,
            "",
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
