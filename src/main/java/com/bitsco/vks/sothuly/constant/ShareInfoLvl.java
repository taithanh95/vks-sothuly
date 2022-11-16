package com.bitsco.vks.sothuly.constant;

public enum ShareInfoLvl {
    INTERNAL(0), // everyone in same unit is able to see and edit the record
    PROTECTED(1), // everyone in same unit is able to see and edit the record
    PRIVATE(2); // only the create can see and edit the record

    private final int value;

    ShareInfoLvl(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
