package com.rtarita.yourbuild.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainboardModel implements Model {
    public static final String EAN = "EAN";
    public static final String FORM_FACTOR_ID = "FORM_FACTOR_ID";
    public static final String SOCKET_TYPE_ID = "SOCKET_TYPE_ID";
    public static final String RAM_SLOTS = "RAM_SLOTS";

    private final String ean;
    private final String formFactorId;
    private final String socketTypeId;
    private final int ramSlots;

    private final Map<String, Object> primaryKey = new HashMap<>(1);
    private final Map<List<String>, List<Object>> foreignKeys = new HashMap<>(3);
    private final Map<String, Object> insertStatementParameters = new HashMap<>(4);

    public MainboardModel(String ean, String formFactorId, String socketTypeId, int ramSlots) {
        this.ean = ean;
        this.formFactorId = formFactorId;
        this.socketTypeId = socketTypeId;
        this.ramSlots = ramSlots;

        primaryKey.put(EAN, ean);

        foreignKeys.put(List.of(EAN), List.of(ean));
        foreignKeys.put(List.of(FORM_FACTOR_ID), List.of(formFactorId));
        foreignKeys.put(List.of(SOCKET_TYPE_ID), List.of(socketTypeId));

        insertStatementParameters.put(EAN, ean);
        insertStatementParameters.put(FORM_FACTOR_ID, formFactorId);
        insertStatementParameters.put(SOCKET_TYPE_ID, socketTypeId);
        insertStatementParameters.put(RAM_SLOTS, ramSlots);
    }

    public String getEan() {
        return ean;
    }

    public String getFormFactorId() {
        return formFactorId;
    }

    public String getSocketTypeId() {
        return socketTypeId;
    }

    public int getRamSlots() {
        return ramSlots;
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
        if (!(o instanceof MainboardModel that)) return false;
        return ramSlots == that.ramSlots
            && ean.equals(that.ean)
            && formFactorId.equals(that.formFactorId)
            && socketTypeId.equals(that.socketTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ean, formFactorId, socketTypeId, ramSlots);
    }

    @Override
    public String toString() {
        return "MainboardModel{" +
            "ean='" + ean + '\'' +
            ", formFactorId='" + formFactorId + '\'' +
            ", socketTypeId='" + socketTypeId + '\'' +
            ", ramSlots=" + ramSlots +
            '}';
    }
}
