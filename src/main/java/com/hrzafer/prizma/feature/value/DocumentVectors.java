package com.hrzafer.prizma.feature.value;

import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.feature.Feature;
import com.hrzafer.prizma.feature.TermVector;

import java.util.*;

/**
 *
 */
public class DocumentVectors {

    private String name;
    private List<Feature> features;
    private List<List<FeatureValue>> vectors;
    private Set<String> categoryNames;

    public DocumentVectors(String name, List<Feature> features) {
        this.name = name;
        this.features = features;
        vectors = new ArrayList<>();
        categoryNames = new TreeSet<>();
    }

    public DocumentVectors(String name, List<Feature> features, List<Document> documents) {
        this.name = name;
        this.features = features;
        for (Feature feature : features) {
            if (feature.getType().equals("TermVector")) {
                ((TermVector) feature).buildTermDictionary(documents);
            }
        }
        vectors = new ArrayList<>();
        categoryNames = new TreeSet<>();
        add(documents);
    }

    public void add(Document document){
        List<FeatureValue> vector = extract(document);
        String actualCategory = document.getActualCategory();
        categoryNames.add(actualCategory);
        vector.add(new NominalValue(actualCategory));
        vectors.add(vector);
    }

    public void add(List<Document> documents){
        int i = 0;
        for (Document document : documents) {
            add(document);
            if (i % 200 == 0) {
                System.out.println(i + " documents' vector is extracted");
            }
            i++;
        }
    }

    public List<FeatureValue> vector(int i){
        return vectors.get(i);
    }

    private List<FeatureValue> extract(Document document) {
        List<FeatureValue> vector = new ArrayList<>();
        for (Feature feature : features) {
            List<FeatureValue> values = feature.extract(document);
            vector.addAll(values);
        }
        return vector;
    }

    public List<List<FeatureValue>> getVectors() {
        return Collections.unmodifiableList(vectors);
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public String[] getCategoryNames() {
        return categoryNames.toArray(new String[categoryNames.size()]);
    }

    public String getName() {
        return name;
    }
}
