package com.rtarita.yourbuild.model;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VendorModel implements Model {
    public static final String VENDOR_ID = "VENDOR_ID";
    public static final String NAME = "NAME";
    public static final String HOMEPAGE = "HOMEPAGE";

    private final String vendorId;
    private final String name;
    @Nullable
    private final String homepage;

    private final Map<String, Object> primaryKey = new HashMap<>(1);
    private final Map<List<String>, List<Object>> foreignKeys = Collections.emptyMap();
    private final Map<String, Object> insertStatementParameters = new HashMap<>(3);

    public VendorModel(String vendorId, String name, @Nullable String homepage) {
        this.vendorId = vendorId;
        this.name = name;
        this.homepage = homepage;

        primaryKey.put(VENDOR_ID, vendorId);

        insertStatementParameters.put(VENDOR_ID, vendorId);
        insertStatementParameters.put(NAME, name);
        insertStatementParameters.put(HOMEPAGE, homepage);
    }

    public String getVendorId() {
        return vendorId;
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
        if (!(o instanceof VendorModel that)) return false;
        return vendorId.equals(that.vendorId)
            && name.equals(that.name)
            && Objects.equals(homepage, that.homepage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendorId, name, homepage);
    }

    @Override
    public String toString() {
        return "VendorModel{" +
            "vendorId='" + vendorId + '\'' +
            ", name='" + name + '\'' +
            ", homepage='" + homepage + '\'' +
            '}';
    }
}
