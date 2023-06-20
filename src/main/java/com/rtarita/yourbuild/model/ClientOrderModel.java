package com.rtarita.yourbuild.model;

import com.rtarita.yourbuild.model.enums.ClientOrderStatus;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ClientOrderModel implements Model {
    public static final String TRACKING_NUMBER = "TRACKING_NUMBER";
    public static final String STATUS = "STATUS";
    public static final String TARGET_ADDRESS = "TARGET_ADDRESS";
    public static final String PRICE = "PRICE";
    public static final String ADMISSION_DATE = "ADMISSION_DATE";
    public static final String ORDERED_BY = "ORDERED_BY";

    private final int trackingNumber;
    private final ClientOrderStatus status;
    private final String targetAddress;
    private final int price;
    private final Timestamp admissionDate;
    private final int orderedBy;

    private final Map<String, Object> primaryKey = new HashMap<>(1);
    private final Map<List<String>, List<Object>> foreignKeys = new HashMap<>(1);
    private final Map<String, Object> insertStatementParameters = new HashMap<>(6);

    public ClientOrderModel(int trackingNumber, ClientOrderStatus status, String targetAddress, int price, Timestamp admissionDate, int orderedBy) {
        this.trackingNumber = trackingNumber;
        this.status = status;
        this.targetAddress = targetAddress;
        this.price = price;
        this.admissionDate = admissionDate;
        this.orderedBy = orderedBy;

        primaryKey.put(TRACKING_NUMBER, trackingNumber);

        foreignKeys.put(List.of(ORDERED_BY), List.of(orderedBy));

        insertStatementParameters.put(TRACKING_NUMBER, trackingNumber);
        insertStatementParameters.put(STATUS, status.getCode());
        insertStatementParameters.put(TARGET_ADDRESS, targetAddress);
        insertStatementParameters.put(PRICE, price);
        insertStatementParameters.put(ADMISSION_DATE, admissionDate);
        insertStatementParameters.put(ORDERED_BY, orderedBy);
    }

    public int getTrackingNumber() {
        return trackingNumber;
    }

    public ClientOrderStatus getStatus() {
        return status;
    }

    public String getTargetAddress() {
        return targetAddress;
    }

    public int getPrice() {
        return price;
    }

    public Timestamp getAdmissionDate() {
        return admissionDate;
    }

    public int getOrderedBy() {
        return orderedBy;
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
        if (!(o instanceof ClientOrderModel that)) return false;
        return trackingNumber == that.trackingNumber
            && price == that.price
            && orderedBy == that.orderedBy
            && status == that.status
            && targetAddress.equals(that.targetAddress)
            && admissionDate.equals(that.admissionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingNumber, status, targetAddress, price, admissionDate, orderedBy);
    }

    @Override
    public String toString() {
        return "ClientOrderModel{" +
            "trackingNumber=" + trackingNumber +
            ", status=" + status +
            ", targetAddress='" + targetAddress + '\'' +
            ", price=" + price +
            ", admissionDate=" + admissionDate +
            ", orderedBy=" + orderedBy +
            '}';
    }
}
