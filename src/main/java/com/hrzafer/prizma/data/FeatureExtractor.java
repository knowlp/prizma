package com.hrzafer.prizma.data;

import com.hrzafer.prizma.feature.Feature;
import com.hrzafer.prizma.feature.FeatureValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 14.01.2014
 * Time: 08:16
 * To change this template use File | Settings | File Templates.
 */
public class FeatureExtractor {
    public static List<FeatureValue> extract(Document document, List<Feature> features) {
        List<FeatureValue> vector = new ArrayList<>();
        for (Feature feature : features) {
            List<FeatureValue> values = feature.extract(document);
            vector.addAll(values);
        }
        return vector;
    }
}
