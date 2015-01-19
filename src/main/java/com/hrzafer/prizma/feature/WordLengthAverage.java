package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.feature.value.FeatureValue;
import com.hrzafer.prizma.feature.value.FeatureValueFactory;
import com.hrzafer.prizma.preprocessing.Analyzer;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author hrzafer
 */
public class WordLengthAverage extends Feature {

    public WordLengthAverage(String type, String field, String name, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, field, name, description, parameters, analyzer);
    }

    @Override
    public List<FeatureValue> extract(List<String> tokens) {
        int wordCount = 0;
        int totalWordLen = 0;
        for (String token : tokens) {
            totalWordLen += token.length();
            wordCount++;
        }
        double mean = (double) totalWordLen / wordCount;
        FeatureValue value = FeatureValueFactory.create(mean);
        return unitList(value);
    }
}
