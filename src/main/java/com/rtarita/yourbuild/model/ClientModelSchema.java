package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;
import com.rtarita.yourbuild.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClientModelSchema implements ModelSchema<ClientModel> {
    private static final Map<String, Integer> displayNameRegistry = new HashMap<>();
    private static final List<String> allColumnNames = List.of(
        ClientModel.CLIENT_ID,
        ClientModel.FULL_NAME,
        ClientModel.DISPLAY_NAME,
        ClientModel.VOUCHERS,
        ClientModel.INVITED_BY
    );

    private static final List<String> insertableColumnNames = List.of(
        ClientModel.FULL_NAME,
        ClientModel.DISPLAY_NAME,
        ClientModel.VOUCHERS,
        ClientModel.INVITED_BY
    );

    private static final List<String> nonDefaultColumnNames = List.of(
        ClientModel.FULL_NAME,
        ClientModel.DISPLAY_NAME
    );

    private static final Set<String> primaryKeyColumns = Set.of(ClientModel.CLIENT_ID);

    private static final Map<List<String>, Reference> foreignKeyColumns =
        Map.of(List.of(ClientModel.INVITED_BY), new Reference(
            ClientModelSchema.class,
            List.of(ClientModel.CLIENT_ID)
        ));
    private static final Set<String> columnsOmittedByGeneration = Set.of(ClientModel.CLIENT_ID, ClientModel.INVITED_BY);

    @Override
    public String tableName() {
        return "CLIENT";
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
    public ClientModel generate(Faker faker) {
        return new ClientModel(
            0,
            StringUtil.truncate(faker.name().fullName(), 100),
            StringUtil.uniqueAndTruncated(displayNameRegistry, faker.funnyName().name(), 20),
            faker.random().nextInt(0, 10),
            null
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
