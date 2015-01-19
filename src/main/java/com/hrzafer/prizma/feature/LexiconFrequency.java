package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.Resources;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.feature.value.BinaryValue;
import com.hrzafer.prizma.feature.value.FeatureValue;
import com.hrzafer.prizma.preprocessing.Analyzer;

import java.util.*;


public class LexiconFrequency extends SingleTokenFeature{

    private Set<String> lexicon;
    private static final String PARAM_LEXICONENCODED = "lexiconEncoded";
    private boolean lexiconEncoded;

    public LexiconFrequency(String type, String field, String name, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, field, name, description, parameters, analyzer);
        parseAndSetIsLexiconEncoded();
        lexicon = new HashSet<>(Resources.getLines(parameters.get("lexiconFilePath"), lexiconEncoded));
    }

    private void parseAndSetIsLexiconEncoded() {
        String value = parameters.get(PARAM_LEXICONENCODED);
        lexiconEncoded = Boolean.parseBoolean(value);
    }

    @Override
    public List<FeatureValue> extract(String data) {
        List<String> tokens = analyzer.analyze(data);
        int count=0;
        for (String token : tokens) {
            if (lexicon.contains(token)){
                count++;
            }
        }
        FeatureValue value = new BinaryValue(count > 1 ? true : false);
        return Collections.singletonList(value);
    }
}
