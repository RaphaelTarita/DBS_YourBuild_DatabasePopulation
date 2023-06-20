package com.rtarita.yourbuild.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CpuModel implements Model {
    public static final String EAN = "EAN";
    public static final String CORE_COUNT = "CORE_COUNT";
    public static final String THREAD_COUNT = "THREAD_COUNT";
    public static final String BASE_CLOCK = "BASE_CLOCK";
    public static final String BOOST_CLOCK = "BOOST_CLOCK";
    public static final String SOCKET_TYPE_ID = "SOCKET_TYPE_ID";

    private final String ean;
    private final int coreCount;
    private final int threadCount;
    private final float baseClock;
    private final float boostClock;
    private final String socketTypeId;

    private final Map<String, Object> primaryKey = new HashMap<>(1);
    private final Map<List<String>, List<Object>> foreignKeys = new HashMap<>(2);
    private final Map<String, Object> insertStatementParameters = new HashMap<>(6);

    public CpuModel(String ean, int coreCount, int threadCount, float baseClock, float boostClock, String socketTypeId) {
        this.ean = ean;
        this.coreCount = coreCount;
        this.threadCount = threadCount;
        this.baseClock = baseClock;
        this.boostClock = boostClock;
        this.socketTypeId = socketTypeId;

        primaryKey.put(EAN, ean);

        foreignKeys.put(List.of(EAN), List.of(ean));
        foreignKeys.put(List.of(SOCKET_TYPE_ID), List.of(socketTypeId));

        insertStatementParameters.put(EAN, ean);
        insertStatementParameters.put(CORE_COUNT, coreCount);
        insertStatementParameters.put(THREAD_COUNT, threadCount);
        insertStatementParameters.put(BASE_CLOCK, baseClock);
        insertStatementParameters.put(BOOST_CLOCK, boostClock);
        insertStatementParameters.put(SOCKET_TYPE_ID, socketTypeId);
    }

    public String getEan() {
        return ean;
    }

    public int getCoreCount() {
        return coreCount;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public float getBaseClock() {
        return baseClock;
    }

    public float getBoostClock() {
        return boostClock;
    }

    public String getSocketTypeId() {
        return socketTypeId;
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
        if (!(o instanceof CpuModel cpuModel)) return false;
        return coreCount == cpuModel.coreCount
            && threadCount == cpuModel.threadCount
            && Float.compare(cpuModel.baseClock, baseClock) == 0
            && Float.compare(cpuModel.boostClock, boostClock) == 0
            && ean.equals(cpuModel.ean)
            && socketTypeId.equals(cpuModel.socketTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ean, coreCount, threadCount, baseClock, boostClock, socketTypeId);
    }

    @Override
    public String toString() {
        return "CpuModel{" +
            "ean='" + ean + '\'' +
            ", coreCount=" + coreCount +
            ", threadCount=" + threadCount +
            ", baseClock=" + baseClock +
            ", boostClock=" + boostClock +
            ", socketTypeId='" + socketTypeId + '\'' +
            '}';
    }
}
