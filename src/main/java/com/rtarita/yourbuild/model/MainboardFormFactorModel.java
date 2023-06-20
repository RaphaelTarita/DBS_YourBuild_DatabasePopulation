package com.rtarita.yourbuild.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainboardFormFactorModel implements Model {
    public static final String ID = "ID";
    public static final String NAME = "NAME";

    private final String id;
    private final String name;

    private final Map<String, Object> primaryKey = new HashMap<>(1);
    private final Map<List<String>, List<Object>> foreignKeys = Collections.emptyMap();
    private final Map<String, Object> insertStatementParameters = new HashMap<>(2);

    public MainboardFormFactorModel(String id, String name) {
        this.id = id;
        this.name = name;

        primaryKey.put(ID, id);

        insertStatementParameters.put(ID, id);
        insertStatementParameters.put(NAME, name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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
        if (!(o instanceof MainboardFormFactorModel that)) return false;
        return id.equals(that.id)
            && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "MainboardFormFactorModel{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
