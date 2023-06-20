package com.rtarita.yourbuild.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CpuCoolerModel implements Model {
    public static final String EAN = "EAN";
    public static final String SOCKET_TYPE_ID = "SOCKET_TYPE_ID";

    private final String ean;
    private final String socketTypeId;

    private final Map<String, Object> primaryKey = new HashMap<>(1);
    private final Map<List<String>, List<Object>> foreignKeys = new HashMap<>(2);
    private final Map<String, Object> insertStatementParameters = new HashMap<>(2);

    public CpuCoolerModel(String ean, String socketTypeId) {
        this.ean = ean;
        this.socketTypeId = socketTypeId;

        primaryKey.put(EAN, ean);

        foreignKeys.put(List.of(EAN), List.of(ean));
        foreignKeys.put(List.of(SOCKET_TYPE_ID), List.of(socketTypeId));

        insertStatementParameters.put(EAN, ean);
        insertStatementParameters.put(SOCKET_TYPE_ID, socketTypeId);
    }

    public String getEan() {
        return ean;
    }

    public String getSocketTypeId() {
        return socketTypeId;
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
        if (!(o instanceof CpuCoolerModel that)) return false;
        return ean.equals(that.ean)
            && socketTypeId.equals(that.socketTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ean, socketTypeId);
    }

    @Override
    public String toString() {
        return "CpuCoolerModel{" +
            "ean='" + ean + '\'' +
            ", socketTypeId='" + socketTypeId + '\'' +
            '}';
    }
}
