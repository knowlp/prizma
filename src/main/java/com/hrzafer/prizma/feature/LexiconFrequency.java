package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.Resources;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.preprocessing.Analyzer;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class LexiconFrequency extends Feature{

    private Set<String> lexicon;
    private static final String PARAM_LEXICONENCODED = "lexiconEncoded";
    private boolean lexiconEncoded;

    //sınıf sayısı boyutunda bir vektör gerekiyor
    public LexiconFrequency(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, name, weight, description, parameters, analyzer);
        parseAndSetIsLexiconEncoded();
        lexicon = new HashSet<>(Resources.getLines(parameters.get("lexiconFilePath"), lexiconEncoded));
    }

    private void parseAndSetIsLexiconEncoded() {
        String value = parameters.get(PARAM_LEXICONENCODED);
        lexiconEncoded = Boolean.parseBoolean(value);
    }

    @Override
    public String extract(Document document) {
        String data = getFieldData(document);
        List<String> tokens = analyzer.analyze(data);

        int count=0;
        for (String token : tokens) {
            if (lexicon.contains(token)){
                count++;
            }
        }
        if (count>1){
            return Integer.toString(1);
        }
        return Integer.toString(0);
    }
}
