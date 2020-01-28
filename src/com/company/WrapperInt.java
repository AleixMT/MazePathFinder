package com.company;

public class WrapperInt {
    private int value;

    public WrapperInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return
                "value: " + value;
    }
}
