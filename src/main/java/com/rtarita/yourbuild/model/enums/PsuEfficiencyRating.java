package com.rtarita.yourbuild.model.enums;

public enum PsuEfficiencyRating {
    EIGHTYPLUS(0, "80+"),
    EIGHTYPLUS_BRONZE(1, "80+ Bronze"),
    EIGHTYPLUS_SILVER(2, "80+ Silver"),
    EIGHTYPLUS_GOLD(3, "80+ Gold"),
    EIGHTYPLUS_PLATINUM(4, "80+ Platinum"),
    EIGHTYPLUS_TITANIUM(5, "80+ Titanium");
    private final int code;
    private final String meaning;

    PsuEfficiencyRating(int code, String meaning) {
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

    public static PsuEfficiencyRating fromCode(int code) {
        for (PsuEfficiencyRating rating : values()) {
            if (rating.code == code) {
                return rating;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
