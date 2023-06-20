package com.rtarita.yourbuild.model;

import com.rtarita.yourbuild.model.enums.PermanentStorageType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PermanentStorageModel implements Model {
    public static final String EAN = "EAN";
    public static final String TYPE = "TYPE";
    public static final String STORAGE_SIZE = "STORAGE_SIZE";

    private final String ean;
    private final PermanentStorageType type;
    private final int storageSize;

    private final Map<String, Object> primaryKey = new HashMap<>(1);
    private final Map<List<String>, List<Object>> foreignKeys = new HashMap<>(1);
    private final Map<String, Object> insertStatementParameters = new HashMap<>(3);

    public PermanentStorageModel(String ean, PermanentStorageType type, int storageSize) {
        this.ean = ean;
        this.type = type;
        this.storageSize = storageSize;

        primaryKey.put(EAN, ean);

        foreignKeys.put(List.of(EAN), List.of(ean));

        insertStatementParameters.put(EAN, ean);
        insertStatementParameters.put(TYPE, type.getCode());
        insertStatementParameters.put(STORAGE_SIZE, storageSize);
    }

    public String getEan() {
        return ean;
    }

    public PermanentStorageType getType() {
        return type;
    }

    public int getStorageSize() {
        return storageSize;
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
        if (!(o instanceof PermanentStorageModel that)) return false;
        return storageSize == that.storageSize
            && ean.equals(that.ean)
            && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ean, type, storageSize);
    }

    @Override
    public String toString() {
        return "PermanentStorageModel{" +
            "ean='" + ean + '\'' +
            ", type=" + type +
            ", storageSize=" + storageSize +
            '}';
    }
}
