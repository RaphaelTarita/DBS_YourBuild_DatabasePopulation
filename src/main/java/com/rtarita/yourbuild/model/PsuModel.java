package com.rtarita.yourbuild.model;

import com.rtarita.yourbuild.model.enums.PsuEfficiencyRating;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PsuModel implements Model {
    public static final String EAN = "EAN";
    public static final String WATTAGE = "WATTAGE";
    public static final String EFFICIENCY_RATING = "EFFICIENCY_RATING";

    private final String ean;
    private final int wattage;
    private final PsuEfficiencyRating efficiencyRating;

    private final Map<String, Object> primaryKey = new HashMap<>(1);
    private final Map<List<String>, List<Object>> foreignKeys = new HashMap<>(1);
    private final Map<String, Object> insertStatementParameters = new HashMap<>(3);

    public PsuModel(String ean, int wattage, PsuEfficiencyRating efficiencyRating) {
        this.ean = ean;
        this.wattage = wattage;
        this.efficiencyRating = efficiencyRating;

        primaryKey.put(EAN, ean);

        foreignKeys.put(List.of(EAN), List.of(ean));

        insertStatementParameters.put(EAN, ean);
        insertStatementParameters.put(WATTAGE, wattage);
        insertStatementParameters.put(EFFICIENCY_RATING, efficiencyRating.getCode());
    }

    public String getEan() {
        return ean;
    }

    public int getWattage() {
        return wattage;
    }

    public PsuEfficiencyRating getEfficiencyRating() {
        return efficiencyRating;
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
        if (!(o instanceof PsuModel psuModel)) return false;
        return wattage == psuModel.wattage && ean.equals(psuModel.ean) && efficiencyRating == psuModel.efficiencyRating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ean, wattage, efficiencyRating);
    }

    @Override
    public String toString() {
        return "PsuModel{" +
            "ean='" + ean + '\'' +
            ", wattage=" + wattage +
            ", efficiencyRating=" + efficiencyRating +
            '}';
    }
}
