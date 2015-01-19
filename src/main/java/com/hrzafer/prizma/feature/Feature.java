package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.feature.value.FeatureValue;
import com.hrzafer.prizma.gui.Frame;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.preprocessing.Analyzer;
import com.hrzafer.prizma.util.GUI;
import com.hrzafer.prizma.util.STR;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is the base class for attributes to be extracted from documents.
 */

public abstract class Feature implements Comparable<Feature> {

    protected final String type;
    protected final String field;
    protected final String name;
    protected final String description;
    protected final Map<String, String> parameters;
    protected final Analyzer analyzer;

    public Feature(String type,
                   String field,
                   String name,
                   String description,
                   Map<String, String> parameters,
                   Analyzer analyzer) {
        this.type = type.indexOf(".") > 0 ? trimPackageName(type) : type;
        this.field = field;
        this.name = name;
        this.description = description;
        this.parameters = parameters;
        this.analyzer = analyzer;
    }

    protected Feature(String type,
                      String field,
                      Map<String, String> parameters,
                      Analyzer analyzer) {
        this.type = type;
        this.field = field;
        this.name = "";
        this.parameters = parameters;
        this.analyzer = analyzer;
        this.description = "";
    }

    /***
     * Extracts feature values from the specified field of the document
     * @param document
     * @return
     */
    public List<FeatureValue> extract(Document document){
        String data = getFieldData(document);
        List<String> tokens;
        if (analyzer!=null){
             tokens = analyzer.analyze(data);
        }
        else {
            tokens = new ArrayList<>();
            tokens.add(data);
        }

        return extract(tokens);
    }

    public abstract List<FeatureValue> extract(List<String> tokens);


    protected String getFieldData(Document document){
        return document.getFieldData(field);
    }

    public String getDeclarationForArff() {
        return "@attribute " + type + "_" + field + " " + "numeric" + "\n";
    }

    protected List<FeatureValue> unitList(FeatureValue value){
        return Collections.singletonList(value);
    }

    @Override
    public String toString() {
        return name;
    }

    private String trimPackageName(String name) {
        int i = name.lastIndexOf(".");
        return name.substring(i + 1);
    }

    public String getName() {
        return name;
    }

    public String getDescription() { return description; }

    public String getType() {
        return type;
    }

    @Override
    public int compareTo(Feature t) {
        return name.compareTo(t.getName());
    }

}
