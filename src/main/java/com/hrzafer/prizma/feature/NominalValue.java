package com.hrzafer.prizma.feature;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 17.03.2014
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
public class NominalValue extends FeatureValue {
    private String value;

    public NominalValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public ValueType getType() {
        return ValueType.NOMINAL;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
