package com.rtarita.yourbuild.model.enums;

public enum ClientOrderStatus {
    ORDER_RECEIVED(0, "Order received"),
    ORDER_ACCEPTED(1, "Order accepted"),
    ACQUIRING_PARTS(2, "Acquiring parts"),
    PARTS_IN_SHIPMENT(3, "Parts in shipment"),
    IN_ASSEMBLY(4, "In assembly"),
    PREPARING_FOR_SHIPMENT(5, "Preparing for shipment"),
    SHIPPING(6, "Shipping"),
    RECEIVED_BY_CUSTOMER(7, "Received by customer");
    private final int code;
    private final String meaning;

    ClientOrderStatus(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public int getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    @Override
    public String toString() {
        return meaning;
    }

    public static ClientOrderStatus fromCode(int code) {
        for (ClientOrderStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
