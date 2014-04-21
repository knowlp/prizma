package com.hrzafer.prizma.feature.value;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 17.03.2014
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
public class FeatureValueFactory {
    public static FeatureValue create(Object value){
        if (value instanceof Double){
            return new DoubleValue((double) value);
        }
        if (value instanceof Boolean){
            return new BinaryValue((boolean) value);
        }
        if (value instanceof Integer){
            return new IntegerValue((int)value);
        }
        if (value instanceof String){
            return new NominalValue((String)value);
        }
        throw new IllegalArgumentException("Value Type not found!");
    }
}
