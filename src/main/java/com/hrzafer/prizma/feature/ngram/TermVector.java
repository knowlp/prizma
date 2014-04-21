package com.hrzafer.prizma.feature.ngram;

import com.hrzafer.prizma.feature.value.BinaryValue;
import com.hrzafer.prizma.feature.value.FeatureValue;
import com.hrzafer.prizma.feature.value.FeatureValueFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 15.04.2014
 * Time: 10:54
 * To change this template use File | Settings | File Templates.
 */
public class TermVector {
//    private List<FeatureValue> vector;
//
//    public TermVector(List<String> tokens, boolean binary, NGramExtractor extractor) {
//        List<String> nGrams = extractor.extractTermList(tokens);
//        int[] vectors = compareAndGetVector(nGrams);
//        if (binary){
//            vector = toBinaryValues(vectors);
//        }
//        vector = toIntegerValues(vectors);
//    }
//
////    private int[] compareAndGetVector(List<String> grams) {
////        int[] vector = new int[terms.size()];
////        int i = 0;
////        for (String s : terms.keySet()) {
////            for (String nGram : grams) {
////                if (s.equals(nGram)) {
////                    vector[i]++;
////                }
////            }
////            i++;
////        }
////        return vector;
////    }
////
//
//    private List<FeatureValue> toIntegerValues(int[] vector){
//        List<FeatureValue> values = new ArrayList<>();
//        for (int i : vector) {
//            values.add(FeatureValueFactory.create(i));
//        }
//        return values;
//    }
//
//    private List<FeatureValue> toBinaryValues(int[] vector){
//        List<FeatureValue> values = new ArrayList<>();
//        for (int i : vector) {
//            values.add(new BinaryValue(i));
//        }
//        return values;
//    }

}
