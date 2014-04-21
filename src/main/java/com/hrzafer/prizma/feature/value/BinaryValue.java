package com.hrzafer.prizma.feature.value;

public class BinaryValue extends FeatureValue {

    private boolean value;

    public BinaryValue(boolean value) {
        this.value = value;
    }

    public BinaryValue(int value) {
        this.value = value > 0;
    }


    @Override
    public String toString() {
        return value ? "1" : "0";
    }

    @Override
    public FeatureValueType getType() {
        return FeatureValueType.BINARY;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
