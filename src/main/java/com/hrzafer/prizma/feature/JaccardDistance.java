package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.feature.ngram.NGramSize;
import com.hrzafer.prizma.preprocessing.Analyzer;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 28.04.2014
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
public class JaccardDistance extends NGramTerms{

    protected JaccardDistance(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer, NGramSize size) {
        super(type, name, weight, description, parameters, analyzer, size);
    }
}
