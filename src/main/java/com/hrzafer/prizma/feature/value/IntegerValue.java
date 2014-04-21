package com.hrzafer.prizma.feature.value;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 17.03.2014
 * Time: 10:57
 * To change this template use File | Settings | File Templates.
 */
public class IntegerValue extends FeatureValue{
    private int value;

    public IntegerValue(int value) {
        this.value = value;
    }

    public void increment(){
        value++;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public FeatureValueType getType() {
        return FeatureValueType.INTEGER;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
