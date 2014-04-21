package com.hrzafer.prizma.termselection;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 11.04.2014
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */
public class MutualInfo implements Measurement{

    @Override
    public double measure(int a, int b, int c, int d) {
        int N = a + b + c + d;
        return a/(double)(a+b) * Math.log(a*N/((a+b)*(a+c)));
    }
}
