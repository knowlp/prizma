package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.preprocessing.Analyzer;

import java.util.Map;

/**
 *
 * @author hrzafer
 */
public class PunctuationColonRatio extends PunctuationRatio {

    public PunctuationColonRatio(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, name, weight, description, parameters, analyzer);
        punctuation = COLON;
    }



}
