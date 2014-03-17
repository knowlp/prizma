package com.hrzafer.prizma.feature;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 17.03.2014
 * Time: 10:54
 * To change this template use File | Settings | File Templates.
 */
public abstract class FeatureValue {

    @Override
    public abstract String toString();

    public abstract ValueType getType();

    public abstract Object getValue();

}
