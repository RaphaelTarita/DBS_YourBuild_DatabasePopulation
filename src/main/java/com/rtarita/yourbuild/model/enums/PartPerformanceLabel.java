package com.rtarita.yourbuild.model.enums;

public enum PartPerformanceLabel {
    X('X', "no performance label given"),
    F('F', "worst performance"),
    D('D', "D-class performance"),
    C('C', "C-class performance"),
    B('B', "B-class performance"),
    A('A', "A-class performance"),
    S('S', "S-class performance");

    private final char code;
    private final String meaning;

    PartPerformanceLabel(char code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public char getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    @Override
    public String toString() {
        return meaning;
    }

    public static PartPerformanceLabel fromCode(char code) {
        char codeUpper = Character.toUpperCase(code);
        for (PartPerformanceLabel label : values()) {
            if (label.code == codeUpper) {
                return label;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
