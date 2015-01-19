package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.feature.value.FeatureValue;
import com.hrzafer.prizma.feature.value.NominalValue;
import com.hrzafer.prizma.preprocessing.Analyzer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class DocId extends SingleTokenFeature {

    private final static Map<String, String> parameters = new LinkedHashMap<>();
    static { parameters.put("field", "id");}

    public DocId(String type, String field, String name, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, field, name, description, parameters, analyzer);
    }

    public DocId(){
        super("DocId", "id", parameters, null);
    }

    @Override
    public List<FeatureValue> extract(String data) {
        List<FeatureValue> featureValues = new ArrayList<>();
        featureValues.add(new NominalValue("% " + data));
        return featureValues;
    }
}
