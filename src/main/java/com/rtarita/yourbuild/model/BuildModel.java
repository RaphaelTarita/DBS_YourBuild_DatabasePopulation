package com.rtarita.yourbuild.model;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BuildModel implements Model {
    public static final String TRACKING_NUMBER = "TRACKING_NUMBER";
    public static final String BUILD_ID = "BUILD_ID";
    public static final String PRICE = "PRICE";
    public static final String PERFORMANCE_RATING = "PERFORMANCE_RATING";

    private final int trackingNumber;
    private final String buildId;
    private final int price;
    @Nullable
    private final Float performanceRating;

    private final Map<String, Object> primaryKey = new HashMap<>(2);
    private final Map<List<String>, List<Object>> foreignKeys = new HashMap<>(1);
    private final Map<String, Object> insertStatementParameters = new HashMap<>(4);

    public BuildModel(int trackingNumber, String buildId, int price, @Nullable Float performanceRating) {
        this.trackingNumber = trackingNumber;
        this.buildId = buildId;
        this.price = price;
        this.performanceRating = performanceRating;

        primaryKey.put(TRACKING_NUMBER, trackingNumber);
        primaryKey.put(BUILD_ID, buildId);

        foreignKeys.put(List.of(TRACKING_NUMBER), List.of(trackingNumber));

        insertStatementParameters.put(TRACKING_NUMBER, trackingNumber);
        insertStatementParameters.put(BUILD_ID, buildId);
        insertStatementParameters.put(PRICE, price);
        insertStatementParameters.put(PERFORMANCE_RATING, performanceRating);
    }

    public int getTrackingNumber() {
        return trackingNumber;
    }

    public String getBuildId() {
        return buildId;
    }

    public int getPrice() {
        return price;
    }

    @Nullable
    public Float getPerformanceRating() {
        return performanceRating;
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
        if (!(o instanceof BuildModel that)) return false;
        return trackingNumber == that.trackingNumber
            && price == that.price
            && buildId.equals(that.buildId)
            && Objects.equals(performanceRating, that.performanceRating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingNumber, buildId, price, performanceRating);
    }

    @Override
    public String toString() {
        return "BuildModel{" +
            "trackingNUmber=" + trackingNumber +
            ", buildId='" + buildId + '\'' +
            ", price=" + price +
            ", performanceRating=" + performanceRating +
            '}';
    }
}
