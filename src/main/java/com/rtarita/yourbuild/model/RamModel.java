package com.rtarita.yourbuild.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RamModel implements Model {
    public static final String EAN = "EAN";
    public static final String STORAGE_SIZE = "STORAGE_SIZE";
    public static final String MEMORY_CLOCK_SPEED = "MEMORY_CLOCK_SPEED";

    private final String ean;
    private final int storageSize;
    private final int memoryClockSpeed;

    private final Map<String, Object> primaryKey = new HashMap<>(1);
    private final Map<List<String>, List<Object>> foreignKeys = new HashMap<>(1);
    private final Map<String, Object> insertStatementParameters = new HashMap<>(3);

    public RamModel(String ean, int storageSize, int memoryClockSpeed) {
        this.ean = ean;
        this.storageSize = storageSize;
        this.memoryClockSpeed = memoryClockSpeed;

        primaryKey.put(EAN, ean);

        foreignKeys.put(List.of(EAN), List.of(ean));

        insertStatementParameters.put(EAN, ean);
        insertStatementParameters.put(STORAGE_SIZE, storageSize);
        insertStatementParameters.put(MEMORY_CLOCK_SPEED, memoryClockSpeed);
    }

    public String getEan() {
        return ean;
    }

    public int getStorageSize() {
        return storageSize;
    }

    public int getMemoryClockSpeed() {
        return memoryClockSpeed;
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
        if (!(o instanceof RamModel ramModel)) return false;
        return storageSize == ramModel.storageSize
            && memoryClockSpeed == ramModel.memoryClockSpeed
            && ean.equals(ramModel.ean);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ean, storageSize, memoryClockSpeed);
    }

    @Override
    public String toString() {
        return "RamModel{" +
            "ean='" + ean + '\'' +
            ", storageSize=" + storageSize +
            ", memoryClockSpeed=" + memoryClockSpeed +
            '}';
    }
}
