package com.rtarita.yourbuild.model;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ManufacturerModel implements Model {
    public static final String MANUFACTURER_ID = "MANUFACTURER_ID";
    public static final String NAME = "NAME";
    public static final String HOMEPAGE = "HOMEPAGE";

    private final String manufacturerId;
    private final String name;

    @Nullable
    private final String homepage;

    private final Map<String, Object> primaryKey = new HashMap<>(1);
    private final Map<List<String>, List<Object>> foreignKeys = Collections.emptyMap();
    private final Map<String, Object> insertStatementParameters = new HashMap<>(3);

    public ManufacturerModel(String manufacturerId, String name, @Nullable String homepage) {
        this.manufacturerId = manufacturerId;
        this.name = name;
        this.homepage = homepage;

        primaryKey.put(MANUFACTURER_ID, manufacturerId);

        insertStatementParameters.put(MANUFACTURER_ID, manufacturerId);
        insertStatementParameters.put(NAME, name);
        insertStatementParameters.put(HOMEPAGE, homepage);
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getHomepage() {
        return homepage;
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
        if (!(o instanceof ManufacturerModel that)) return false;
        return manufacturerId.equals(that.manufacturerId)
            && name.equals(that.name)
            && Objects.equals(homepage, that.homepage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manufacturerId, name, homepage);
    }

    @Override
    public String toString() {
        return "ManufacturerModel{" +
            "manufacturerId='" + manufacturerId + '\'' +
            ", name='" + name + '\'' +
            ", homepage='" + homepage + '\'' +
            '}';
    }
}
