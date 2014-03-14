package com.hrzafer.prizma.data;

import com.hrzafer.prizma.feature.Feature;

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
    public static List<String> extract(Document document, List<Feature> features) {
        List<String> values = new ArrayList<>();
        for (Feature feature : features) {
            String value = feature.extract(document);
            values.add(value);
        }
        return values;
    }
}
