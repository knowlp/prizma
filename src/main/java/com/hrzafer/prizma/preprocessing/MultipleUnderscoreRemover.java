package com.hrzafer.prizma.preprocessing;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 30.12.2013
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class MultipleUnderscoreRemover extends Filter {

    @Override
    protected boolean eval(String token) {
        return token.indexOf("__") > -1;
    }
}
