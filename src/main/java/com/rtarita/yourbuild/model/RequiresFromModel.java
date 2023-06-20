package com.rtarita.yourbuild.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RequiresFromModel implements Model {
    public static final String TRACKING_NUMBER = "TRACKING_NUMBER";
    public static final String BUILD_ID = "BUILD_ID";
    public static final String VENDOR_ID = "VENDOR_ID";
    public static final String EAN = "EAN";

    private final int trackingNumber;
    private final String buildId;
    private final String vendorId;
    private final String ean;

    private final Map<String, Object> primaryKey = new HashMap<>(4);
    private final Map<List<String>, List<Object>> foreignKeys = new HashMap<>(4);
    private final Map<String, Object> insertStatementParameters = new HashMap<>(4);

    public RequiresFromModel(int trackingNumber, String buildId, String vendorId, String ean) {
        this.trackingNumber = trackingNumber;
        this.buildId = buildId;
        this.vendorId = vendorId;
        this.ean = ean;

        primaryKey.put(TRACKING_NUMBER, trackingNumber);
        primaryKey.put(BUILD_ID, buildId);
        primaryKey.put(VENDOR_ID, vendorId);
        primaryKey.put(EAN, ean);

        foreignKeys.put(List.of(TRACKING_NUMBER), List.of(trackingNumber));
        foreignKeys.put(List.of(TRACKING_NUMBER, BUILD_ID), List.of(trackingNumber, buildId));
        foreignKeys.put(List.of(VENDOR_ID), List.of(vendorId));
        foreignKeys.put(List.of(EAN), List.of(ean));

        insertStatementParameters.put(TRACKING_NUMBER, trackingNumber);
        insertStatementParameters.put(BUILD_ID, buildId);
        insertStatementParameters.put(VENDOR_ID, vendorId);
        insertStatementParameters.put(EAN, ean);
    }

    public int getTrackingNumber() {
        return trackingNumber;
    }

    public String getBuildId() {
        return buildId;
    }

    public String getVendorId() {
        return vendorId;
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
        if (!(o instanceof RequiresFromModel that)) return false;
        return trackingNumber == that.trackingNumber
            && buildId.equals(that.buildId)
            && vendorId.equals(that.vendorId)
            && ean.equals(that.ean);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingNumber, buildId, vendorId, ean);
    }

    @Override
    public String toString() {
        return "RequiresFromModel{" +
            "trackingNumber=" + trackingNumber +
            ", buildId='" + buildId + '\'' +
            ", vendorId='" + vendorId + '\'' +
            ", ean='" + ean + '\'' +
            '}';
    }
}
