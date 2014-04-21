package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.feature.value.FeatureValue;
import com.hrzafer.prizma.gui.Frame;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.preprocessing.Analyzer;
import com.hrzafer.prizma.util.GUI;
import com.hrzafer.prizma.util.STR;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
    protected final String name;
    protected final int weight;
    protected final String description;
    protected final Map<String, String> parameters;
    protected final Analyzer analyzer;

    public Feature(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        this.type = type.indexOf(".") > 0 ? trimPackageName(type) : type;
        this.name = name;
        this.weight = weight;
        this.description = description;
        this.parameters = parameters;
        this.analyzer = analyzer;
    }

    protected Feature(String type, Map<String, String> parameters, Analyzer analyzer) {
        this.type = type;
        this.name = "";
        this.parameters = parameters;
        this.analyzer = analyzer;
        this.weight = 1;
        this.description = "";
    }

    public List<FeatureValue> extract(Document document){
        String data = getFieldData(document);
        return extract(data);
    }

    public abstract List<FeatureValue> extract(String data);

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

    protected String getFieldData(Document document){
        return document.getFieldData(parameters.get("field"));
    }

    public String getDeclarationForArff() {
        return "@attribute " + type + "_" + parameters.get("field") + " " + "numeric" + "\n";
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

    public String getTitle() {
        return description;
    }

    public String getType() {
        return type;
    }

    @Override
    public int compareTo(Feature t) {
        return name.compareTo(t.getName());
    }

    public static Feature getInstance(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        String packagePath = "com.hrzafer.prizma.feature";
        try {
            Class<?> c = Class.forName(packagePath + "." + type);
            Constructor<?> cons = c.getConstructor(String.class, String.class, int.class, String.class, Map.class, Analyzer.class);
            return (Feature) cons.newInstance(type, name, weight, description, parameters, analyzer);
        } catch (ClassNotFoundException ex) {
            GUI.messageBox("Prizma Error: Feature class " + STR.addDoubleQuote(type)
                    + " can not be found in the package " + STR.addDoubleQuote(packagePath), "Missing Feature");
            return null;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


}
