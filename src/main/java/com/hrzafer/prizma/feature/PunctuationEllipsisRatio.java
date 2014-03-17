package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.preprocessing.Analyzer;
import com.hrzafer.prizma.util.STR;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author hrzafer
 */
public class PunctuationEllipsisRatio extends Feature {

    public PunctuationEllipsisRatio(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, name, weight, description, parameters, analyzer);
    }

    @Override
    public List<FeatureValue> extract(Document document) {
        String data = getFieldData(document);
        PunctuationDataExtractor extractor = new PunctuationDataExtractor(data, '.');
        int totalPunctuationCount = extractor.getTotalPunctuationCount();
        int ellipsisCount = 0;
        Pattern pattern = Pattern.compile("\\.\\.\\.");
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            ellipsisCount++;
        }
        totalPunctuationCount -= ellipsisCount * 2;
        double ratio = (double) ellipsisCount / totalPunctuationCount;
        FeatureValue value = FeatureValueFactory.create(ratio);
        return unitList(value);
    }
}
