package com.rtarita.yourbuild.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GpuModel implements Model {
    public static final String EAN = "EAN";
    public static final String BASE_CLOCK = "BASE_CLOCK";
    public static final String BOOST_CLOCK = "BOOST_CLOCK";
    public static final String VRAM = "VRAM";

    private final String ean;
    private final float baseClock;
    private final float boostClock;
    private final int vram;

    private final Map<String, Object> primaryKey = new HashMap<>(1);
    private final Map<List<String>, List<Object>> foreignKeys = new HashMap<>(1);
    private final Map<String, Object> insertStatementParameters = new HashMap<>(4);

    public GpuModel(String ean, float baseClock, float boostClock, int vram) {
        this.ean = ean;
        this.baseClock = baseClock;
        this.boostClock = boostClock;
        this.vram = vram;

        primaryKey.put(EAN, ean);

        foreignKeys.put(List.of(EAN), List.of(ean));

        insertStatementParameters.put(EAN, ean);
        insertStatementParameters.put(BASE_CLOCK, baseClock);
        insertStatementParameters.put(BOOST_CLOCK, boostClock);
        insertStatementParameters.put(VRAM, vram);
    }

    public String getEan() {
        return ean;
    }

    public float getBaseClock() {
        return baseClock;
    }

    public float getBoostClock() {
        return boostClock;
    }

    public int getVram() {
        return vram;
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
        if (!(o instanceof GpuModel gpuModel)) return false;
        return Float.compare(gpuModel.baseClock, baseClock) == 0 && Float.compare(gpuModel.boostClock, boostClock) == 0 && vram == gpuModel.vram && ean.equals(gpuModel.ean);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ean, baseClock, boostClock, vram);
    }

    @Override
    public String toString() {
        return "GpuModel{" +
            "ean='" + ean + '\'' +
            ", baseClock=" + baseClock +
            ", boostClock=" + boostClock +
            ", vram=" + vram +
            '}';
    }
}
