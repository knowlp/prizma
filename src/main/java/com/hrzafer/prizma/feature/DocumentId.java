package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.data.Document;
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
public class DocumentId extends Feature {

    private final static Map<String, String> parameters = new LinkedHashMap<>();
    static { parameters.put("field", "id");}

    public DocumentId(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, name, weight, description, parameters, analyzer);
    }

    public DocumentId(){
        super("DocumentId", parameters, null);
    }

    @Override
    public List<FeatureValue> extract(String data) {
        List<FeatureValue> featureValues = new ArrayList<>();
        featureValues.add(new NominalValue("% " + data));
        return featureValues;
    }
}
