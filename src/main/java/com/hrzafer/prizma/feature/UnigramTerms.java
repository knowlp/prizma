/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.feature.ngram.NGramSize;
import com.hrzafer.prizma.preprocessing.Analyzer;

import java.util.Map;

/**
 * @author hrzafer
 */
public class UnigramTerms extends NGramTerms {

    public UnigramTerms(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, name, weight, description, parameters, analyzer, NGramSize.UNIGRAM);
    }

    public UnigramTerms(Map<String, String> parameters, Analyzer analyzer ){
        super(parameters, analyzer, NGramSize.UNIGRAM);
    }
}
