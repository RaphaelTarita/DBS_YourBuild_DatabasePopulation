package com.rtarita.yourbuild.model.enums;

public enum PermanentStorageType {
    HDD(0, "HDD"),
    SSD(1, "SSD"),
    OTHER(2, "other");
    private final int code;
    private final String meaning;

    PermanentStorageType(int code, String meaning) {
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

    public static PermanentStorageType fromCode(int code) {
        for (PermanentStorageType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
