package com.rtarita.yourbuild.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainboardCompatibilityModel implements Model {
    public static final String EAN = "EAN";
    public static final String FORM_FACTOR_ID = "FORM_FACTOR_ID";

    private final String ean;
    private final String formFactorId;

    private final Map<String, Object> primaryKey = new HashMap<>(1);
    private final Map<List<String>, List<Object>> foreignKeys = new HashMap<>(2);
    private final Map<String, Object> insertStatementParameters = new HashMap<>(2);

    public MainboardCompatibilityModel(String ean, String formFactorId) {
        this.ean = ean;
        this.formFactorId = formFactorId;

        primaryKey.put(EAN, ean);

        foreignKeys.put(List.of(EAN), List.of(ean));
        foreignKeys.put(List.of(FORM_FACTOR_ID), List.of(formFactorId));

        insertStatementParameters.put(EAN, ean);
        insertStatementParameters.put(FORM_FACTOR_ID, formFactorId);
    }

    public String getEan() {
        return ean;
    }

    public String getFormFactorId() {
        return formFactorId;
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
        if (!(o instanceof MainboardCompatibilityModel that)) return false;
        return ean.equals(that.ean)
            && formFactorId.equals(that.formFactorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ean, formFactorId);
    }

    @Override
    public String toString() {
        return "MainboardCompatibilityModel{" +
            "ean='" + ean + '\'' +
            ", formFactorId='" + formFactorId + '\'' +
            '}';
    }
}
