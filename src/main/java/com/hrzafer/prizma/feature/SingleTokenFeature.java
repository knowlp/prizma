package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.feature.value.FeatureValue;
import com.hrzafer.prizma.preprocessing.Analyzer;

import java.util.List;
import java.util.Map;

/**
 * Created by hrzafer on 30.12.2014.
 */
public abstract class SingleTokenFeature extends Feature {

    public SingleTokenFeature(String type, String field, String name, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, field, name, description, parameters, analyzer);
    }

    public SingleTokenFeature(String type, String field, Map<String, String> parameters, Analyzer analyzer) {
        super(type, field, parameters, analyzer);
    }

    @Override
    public List<FeatureValue> extract(List<String> tokens) {
        return extract(tokens.get(0));
    }

    public abstract List<FeatureValue> extract(String text);

}
