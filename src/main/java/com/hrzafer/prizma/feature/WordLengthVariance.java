package com.hrzafer.prizma.feature;

import com.google.common.primitives.Ints;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.feature.value.FeatureValue;
import com.hrzafer.prizma.feature.value.FeatureValueFactory;
import com.hrzafer.prizma.preprocessing.Analyzer;
import com.hrzafer.prizma.util.MAT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author hrzafer
 */
public class WordLengthVariance extends Feature {

    public WordLengthVariance(String type, String field, String name, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, field, name, description, parameters, analyzer);
    }

    @Override
    public List<FeatureValue> extract(List<String> tokens) {
        int wordCount = 0;
        int totalWordLen = 0;
        List<Integer> lenghts = new ArrayList();

        for (String token : tokens) {
            int tokenLength = token.length();
            totalWordLen += tokenLength;
            wordCount++;
            lenghts.add(new Integer(tokenLength));
        }

        if (wordCount < 1) {
            return unitList(FeatureValueFactory.create(Double.NaN));
            //return Double.toString(Double.NaN);
        }

        double mean = (double) totalWordLen / wordCount;
        int[] values = Ints.toArray(lenghts);
        double variance = MAT.variance(values, mean);
        return unitList(FeatureValueFactory.create(variance));
    }
}
