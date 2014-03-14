package com.hrzafer.prizma;

import com.hrzafer.prizma.preprocessing.Analyzer;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 03.03.2014
 * Time: 09:38
 * To change this template use File | Settings | File Templates.
 */
public class Globals {
    private static Map<String, Analyzer> analyzers = GlobalsReader.read("globals.xml");;

    public static Analyzer getAnalyzer(String name) {
        return analyzers.get(name);
    }
}
