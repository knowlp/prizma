package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.preprocessing.Analyzer;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hrzafer
 */
public class PunctuationAsteriksRatio extends PunctuationRatio {

    public PunctuationAsteriksRatio(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, name, weight, description, parameters, analyzer);
        punctuation = ASTERIKS;
    }
}
