package com.hrzafer.prizma.feature;

import com.google.common.primitives.Ints;
import com.hrzafer.prizma.data.Document;
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

    public WordLengthVariance(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, name, weight, description, parameters, analyzer);
    }

    @Override
    public List<FeatureValue> extract(Document document) {
        String data = getFieldData(document);
        String source = data.replaceAll("[\\*-\\:\\\"\\.,'\\(\\);?!]", "");
        Scanner s = new Scanner(source);
        int wordCount = 0;
        int totalWordLen = 0;
        List<Integer> lenghts = new ArrayList();

        while (s.hasNext()) {
            String token = s.next();
            int tokenLength = token.length();
            totalWordLen += tokenLength;
            wordCount++;
            lenghts.add(new Integer(tokenLength));
        }
        s.close();

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
