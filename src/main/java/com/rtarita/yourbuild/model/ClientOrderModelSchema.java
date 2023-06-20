package com.rtarita.yourbuild.model;

import com.github.javafaker.Faker;
import com.rtarita.yourbuild.database.Reference;
import com.rtarita.yourbuild.model.enums.ClientOrderStatus;
import com.rtarita.yourbuild.util.StringUtil;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ClientOrderModelSchema implements ModelSchema<ClientOrderModel> {
    private static final List<String> allColumnNames = List.of(
        ClientOrderModel.TRACKING_NUMBER,
        ClientOrderModel.STATUS,
        ClientOrderModel.TARGET_ADDRESS,
        ClientOrderModel.PRICE,
        ClientOrderModel.ADMISSION_DATE,
        ClientOrderModel.ORDERED_BY
    );

    private static final List<String> insertableColumnNames = List.of(
        ClientOrderModel.STATUS,
        ClientOrderModel.TARGET_ADDRESS,
        ClientOrderModel.PRICE,
        ClientOrderModel.ADMISSION_DATE,
        ClientOrderModel.ORDERED_BY
    );

    private static final List<String> nonDefaultColumNames = List.of(
        ClientOrderModel.STATUS,
        ClientOrderModel.TARGET_ADDRESS,
        ClientOrderModel.PRICE,
        ClientOrderModel.ORDERED_BY
    );

    private static final Set<String> primaryKeyColumns = Set.of(ClientOrderModel.TRACKING_NUMBER);

    private static final Map<List<String>, Reference> foreignKeyColumns =
        Map.of(List.of(ClientOrderModel.ORDERED_BY), new Reference(
            ClientModelSchema.class,
            List.of(ClientModel.CLIENT_ID)
        ));
    private static final Set<String> columnsOmittedByGeneration = Set.of(ClientOrderModel.TRACKING_NUMBER, ClientOrderModel.ORDERED_BY);

    @Override
    public String tableName() {
        return "CLIENT_ORDER";
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
        return nonDefaultColumNames;
    }

    @Override
    public Set<String> columnsOmittedByGeneration() {
        return columnsOmittedByGeneration;
    }

    @Override
    public ClientOrderModel generate(Faker faker) {
        return new ClientOrderModel(
            0,
            ClientOrderStatus.fromCode(faker.random().nextInt(0, 7)),
            StringUtil.truncate(faker.address().fullAddress(), 100),
            faker.random().nextInt(0, 2_000_000),
            new Timestamp(faker.date().past(10 * 365, TimeUnit.DAYS).getTime()),
            0
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
