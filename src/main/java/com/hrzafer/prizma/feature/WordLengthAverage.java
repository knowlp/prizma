package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.preprocessing.Analyzer;

import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author hrzafer
 */
public class WordLengthAverage extends Feature {

    public WordLengthAverage(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, name, weight, description, parameters, analyzer);
    }

    @Override
    public String extract(Document document) {
        String data = getFieldData(document);
        String source = data.replaceAll("[\\*-\\:\\\"\\.,'\\(\\);?!]", "");
        Scanner s = new Scanner(source);
        int wordCount = 0;
        int totalWordLen = 0;
        while (s.hasNext()) {
            String token = s.next();
            totalWordLen += token.length();
            wordCount++;
        }
        s.close();
        double mean = (double) totalWordLen / wordCount;
        return String.format(Locale.US, "%.2f", mean);
    }


}
