package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainboardModelSchema implements ModelSchema<MainboardModel> {
    private static final List<String> allColumnNames = List.of(
        MainboardModel.EAN,
        MainboardModel.FORM_FACTOR_ID,
        MainboardModel.SOCKET_TYPE_ID,
        MainboardModel.RAM_SLOTS
    );

    private static final List<String> insertableColumnNames = allColumnNames;
    private static final List<String> nonDefaultColumnNames = allColumnNames;
    private static final Set<String> primaryKeyColumns = Set.of(MainboardModel.EAN);
    private static final Map<List<String>, Reference> foreignKeyColumnNames = Map.of(
        List.of(MainboardModel.EAN), new Reference(PartModelSchema.class, List.of(PartModel.EAN)),
        List.of(MainboardModel.FORM_FACTOR_ID), new Reference(MainboardFormFactorModelSchema.class, List.of(MainboardFormFactorModel.ID)),
        List.of(MainboardModel.SOCKET_TYPE_ID), new Reference(CpuSocketTypeModelSchema.class, List.of(CpuSocketTypeModel.ID))
    );
    private static final Set<String> columnsOmittedByGeneration = Set.of(MainboardModel.EAN, MainboardModel.FORM_FACTOR_ID, MainboardModel.SOCKET_TYPE_ID);

    @Override
    public String tableName() {
        return "MAINBOARD";
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
    public MainboardModel generate(Faker faker) {
        return new MainboardModel(
            "",
            "",
            "",
            faker.random().nextInt(1, 8)
        );
    }

    @Override
    public Set<String> primaryKeyColumns() {
        return primaryKeyColumns;
    }

    @Override
    public Map<List<String>, Reference> foreignKeyColumns() {
        return foreignKeyColumnNames;
    }
}
