package com.rtarita.yourbuild.model;

import com.rtarita.yourbuild.model.enums.PartPerformanceLabel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PartModel implements Model {
    public static final String EAN = "EAN";
    public static final String FULL_NAME = "FULL_NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String PRICE = "PRICE";
    public static final String MSRP = "MSRP";
    public static final String PERFORMANCE_LABEL = "PERFORMANCE_LABEL";

    private final String ean;
    private final String fullName;
    private final String description;
    private final int price;
    private final int msrp;
    private final PartPerformanceLabel performanceLabel;

    private final Map<String, Object> primaryKey = new HashMap<>(1);
    private final Map<List<String>, List<Object>> foreignKeys = Collections.emptyMap();
    private final Map<String, Object> insertStatementParameters = new HashMap<>(6);

    public PartModel(String ean, String fullName, String description, int price, int msrp, PartPerformanceLabel performanceLabel) {
        this.ean = ean;
        this.fullName = fullName;
        this.description = description;
        this.price = price;
        this.msrp = msrp;
        this.performanceLabel = performanceLabel;

        primaryKey.put(EAN, ean);

        insertStatementParameters.put(EAN, ean);
        insertStatementParameters.put(FULL_NAME, fullName);
        insertStatementParameters.put(DESCRIPTION, description);
        insertStatementParameters.put(PRICE, price);
        insertStatementParameters.put(MSRP, msrp);
        insertStatementParameters.put(PERFORMANCE_LABEL, performanceLabel.getCode());
    }

    public String getEan() {
        return ean;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getMsrp() {
        return msrp;
    }

    public PartPerformanceLabel getPerformanceLabel() {
        return performanceLabel;
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
        if (!(o instanceof PartModel partModel)) return false;
        return price == partModel.price
            && msrp == partModel.msrp
            && ean.equals(partModel.ean)
            && fullName.equals(partModel.fullName)
            && description.equals(partModel.description)
            && performanceLabel == partModel.performanceLabel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ean, fullName, description, price, msrp, performanceLabel);
    }

    @Override
    public String toString() {
        return "PartModel{" +
            "ean='" + ean + '\'' +
            ", fullName='" + fullName + '\'' +
            ", description='" + description + '\'' +
            ", price=" + price +
            ", msrp=" + msrp +
            ", performanceLabel=" + performanceLabel +
            '}';
    }
}
