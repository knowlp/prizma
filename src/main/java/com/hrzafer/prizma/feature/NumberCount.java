package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.feature.value.FeatureValue;
import com.hrzafer.prizma.feature.value.IntegerValue;
import com.hrzafer.prizma.preprocessing.Analyzer;
import com.hrzafer.prizma.util.STR;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Works only for integer numbers so far.
 *
 * @author harun
 */
public class NumberCount extends Feature {

    public NumberCount(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, name, weight, description, parameters, analyzer);
    }

    @Override
    public List<FeatureValue> extract(String data) {
        String source = data.replaceAll(STR.CLEAN_REGEX, " ");
        Scanner s = new Scanner(source);
        Integer numberCount = 0;
        while (s.hasNext()) {
            if (s.hasNextInt()) {
                numberCount++;
            }
            s.next();
        }
        FeatureValue value = new IntegerValue(numberCount);
        return Collections.singletonList(value);
    }

}
