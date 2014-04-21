package com.hrzafer.prizma.feature.value;

import com.hrzafer.prizma.util.STR;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 17.03.2014
 * Time: 10:57
 * To change this template use File | Settings | File Templates.
 */
public class DoubleValue  extends FeatureValue{

    private double value;

    public DoubleValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return STR.formatDouble(value, "#.##");
    }

    @Override
    public FeatureValueType getType() {
        return FeatureValueType.DOUBLE;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
