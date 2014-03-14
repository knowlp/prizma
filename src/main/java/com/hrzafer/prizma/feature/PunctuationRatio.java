package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.preprocessing.Analyzer;
import com.hrzafer.prizma.util.STR;

import java.util.Map;

/**
 *
 * @author test
 */
public abstract class PunctuationRatio extends Feature {

    protected final char ASTERIKS = '*';
    protected final char COLON = ':';
    protected final char SEMI_COLON = ';';
    protected final char COMMA = ',';
    protected final char DASH = '-';
    protected final char DOUBLE_QUOTE = '"';
    protected final char EXCLAMATION = '!';
    protected final char QUESTION_MARK = '?';
    private PunctuationDataExtractor extractor;

    protected PunctuationRatio(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, name, weight, description, parameters, analyzer);
    }

    @Override
    public abstract String extract(Document document);

    protected String extractRatioAsFormattedString(String source, char ch) {
        extractor = new PunctuationDataExtractor(source, ch);
        double ratio = getPunctuationRatio();
        return STR.formatDouble(ratio, "#.###");
    }

    private double getPunctuationRatio() {
        int punctuationCount = extractor.getPunctuationCount();
        int totalPunctuationCount = extractor.getTotalPunctuationCount();
        double ratio = ((double) punctuationCount / totalPunctuationCount);
        return ratio;
    }

    protected String extractCountAsFormattedString(String source, char ch) {
        extractor = new PunctuationDataExtractor(source, ch);
        int count = extractor.getPunctuationCount();
        return Integer.toString(count);
    }
}
