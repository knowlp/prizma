package com.hrzafer.prizma.termselection;

import com.hrzafer.prizma.data.Dataset;
import com.hrzafer.prizma.data.FeatureReader;
import com.hrzafer.prizma.data.io.DatasetReader;
import com.hrzafer.prizma.data.io.DirectoryDatasetReader;
import com.hrzafer.prizma.feature.Feature;
import com.hrzafer.prizma.feature.NGramTerms;
import com.hrzafer.prizma.util.IO;
import com.hrzafer.prizma.util.Timer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 11.04.2014
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */
public class TermSelector {
    public static final int NumberOfTopTerms = 200;
    public static final double Treshold = 0.0002;
    public static final boolean TresholdBased = false;
    public static final boolean oneToManyMode = false;
    public static final Measurement MEASUREMENT = new TfIdfMeasurement();


    public static void main(String[] args){
        List<Feature> features = FeatureReader.read("experiment/features_tfidf.xml");
        NGramTerms nGramTerms = (NGramTerms) features.get(0);

        //DatasetReader reader = new CSVDatasetReader(dataSetPathCsv);
        DatasetReader reader = new DirectoryDatasetReader("dataset/train_9x100");

        Timer timer = new Timer();
        timer.start();
        Dataset dataset = reader.read();
        timer.stop();
        System.out.println("Dataset reading time: " + timer.getElapsedSeconds());

        List<Category> categories = TermDistributionExtractor.extract(dataset, nGramTerms);
        Set<Term> termSet = selectTerms(categories);
        TopTermsToFile(termSet, "topTermsLexicon.txt");

    }

    public static Set<Term> selectTerms(List<Category> categories){
        Set<Term> topTerms = new HashSet<>();
        for (Category category : categories) {
            AddTopTerms(topTerms, category.getTerms(), NumberOfTopTerms);
            tfidfsToFile(category.getTerms(), "new_" + category.getName());
        }
        return topTerms;
    }

    public static void AddTopTerms(Set<Term> topTerms, List<Term> categoryTerms, int n) {
        int i = 0;
        int count = 0;
        while (count < n) {
            if (i >= categoryTerms.size()) {
                throw new RuntimeException("Kategoride yeteri kadar terim yok:" + "n:" + n + "size:" + categoryTerms.size());
            }
            if (!topTerms.contains(categoryTerms.get(i))) {
                topTerms.add(categoryTerms.get(i));
                count++;
            } else {
                System.out.println(categoryTerms.get(i));
            }
            i++;
        }
    }

    public static void TopTermsToFile(Set<Term> termSet, String filename) {
        StringBuilder sb = new StringBuilder();
        for (Term term : termSet) {
            sb.append(term.getTerm()).append("\n");
        }
        IO.write("experiment/" + filename, sb.toString());
    }

    public static void tfidfsToFile(List<Term> terms, String filename) {
        StringBuilder sb = new StringBuilder();
        for (Term term : terms) {
            if (oneToManyMode){
                sb.append(term.getTerm()).append("\n");
            }
            else {
                sb.append(term.toString()).append("\n");
            }
        }
        IO.write("experiment/" + filename + "Lexicon", sb.toString());
    }
}
