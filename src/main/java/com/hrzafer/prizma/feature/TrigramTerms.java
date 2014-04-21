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
public class TrigramTerms extends NGramTerms {

    public TrigramTerms(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, name, weight, description, parameters, analyzer, NGramSize.TRIGRAM);

    }


}
