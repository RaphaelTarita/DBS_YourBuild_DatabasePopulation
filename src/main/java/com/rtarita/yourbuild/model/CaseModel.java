package com.rtarita.yourbuild.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CaseModel implements Model {
    public static final String EAN = "EAN";
    public static final String CASE_FORM_FACTOR = "CASE_FORM_FACTOR";

    private final String ean;
    private final String caseFormFactor;

    private final Map<String, Object> primaryKey = new HashMap<>(1);
    private final Map<List<String>, List<Object>> foreignKeys = new HashMap<>(1);
    private final Map<String, Object> insertStatementParameters = new HashMap<>(2);

    public CaseModel(String ean, String caseFormFactor) {
        this.ean = ean;
        this.caseFormFactor = caseFormFactor;

        primaryKey.put(EAN, ean);

        foreignKeys.put(List.of(EAN), List.of(ean));

        insertStatementParameters.put(EAN, ean);
        insertStatementParameters.put(CASE_FORM_FACTOR, caseFormFactor);
    }

    public String getEan() {
        return ean;
    }

    public String getCaseFormFactor() {
        return caseFormFactor;
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
        if (!(o instanceof CaseModel caseModel)) return false;
        return ean.equals(caseModel.ean)
            && caseFormFactor.equals(caseModel.caseFormFactor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ean, caseFormFactor);
    }

    @Override
    public String toString() {
        return "CaseModel{" +
            "ean='" + ean + '\'' +
            ", caseFormFactor='" + caseFormFactor + '\'' +
            '}';
    }
}
