package com.hrzafer.prizma.feature.ngram;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 17.01.2014
 * Time: 10:38
 * To change this template use File | Settings | File Templates.
 */
public class NGramFrequency {
    private int byKlass = 1; // sınıftaki tüm numunelerde toplam geçme sayısı
    private int byInstance = 1; //kaç farklı numunede geçiyor

    public int getByKlass() {
        return byKlass;
    }

    public int getByInstance() {
        return byInstance;
    }

    public int incrementByInstance(){
        byInstance++;
        return byInstance;
    }

    public int incrementByKlass(){
        byKlass++;
        return byKlass;
    }

    @Override
    public String toString() {
        return byInstance + "/" + byKlass;
    }
}
