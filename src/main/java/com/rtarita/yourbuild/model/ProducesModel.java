package com.rtarita.yourbuild.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProducesModel implements Model {
    public static final String MANUFACTURER_ID = "MANUFACTURER_ID";
    public static final String EAN = "EAN";

    private final String manufacturerId;
    private final String ean;

    private final Map<String, Object> primaryKey = new HashMap<>(2);
    private final Map<List<String>, List<Object>> foreignKeys = new HashMap<>(2);
    private final Map<String, Object> insertStatementParameters = new HashMap<>(2);

    public ProducesModel(String manufacturerId, String ean) {
        this.manufacturerId = manufacturerId;
        this.ean = ean;

        primaryKey.put(MANUFACTURER_ID, manufacturerId);
        primaryKey.put(EAN, ean);

        foreignKeys.put(List.of(MANUFACTURER_ID), List.of(manufacturerId));
        foreignKeys.put(List.of(EAN), List.of(ean));

        insertStatementParameters.put(MANUFACTURER_ID, manufacturerId);
        insertStatementParameters.put(EAN, ean);
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public String getEan() {
        return ean;
    }

    @Override
    public Map<String, Object> primaryKey() {
        return primaryKey;
    }

    @Override
    public Map<List<String>, List<Object>> foreignKeys() {
        return foreignKeys;
    }

    @Override
    public Map<String, Object> insertStatementParameters() {
        return new HashMap<>(insertStatementParameters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProducesModel that)) return false;
        return manufacturerId.equals(that.manufacturerId)
            && ean.equals(that.ean);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manufacturerId, ean);
    }

    @Override
    public String toString() {
        return "ProducesModel{" +
            "manufacturerId='" + manufacturerId + '\'' +
            ", ean='" + ean + '\'' +
            '}';
    }
}
