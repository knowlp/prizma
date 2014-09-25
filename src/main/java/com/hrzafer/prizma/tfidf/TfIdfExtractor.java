package com.hrzafer.prizma.tfidf;

import com.hrzafer.prizma.data.*;
import com.hrzafer.prizma.data.io.CSVDatasetReader;
import com.hrzafer.prizma.data.io.DirectoryDatasetReader;
import com.hrzafer.prizma.data.io.DatasetReader;
import com.hrzafer.prizma.feature.Feature;
import com.hrzafer.prizma.feature.NGramTerms;
import com.hrzafer.prizma.util.IO;
import com.hrzafer.prizma.util.Timer;

import java.util.*;
/**
 * Dataset'te belirtilen sınıf için TF-IDF değerlerini listeler Menü'de araçlar
 * gibi bir sekmeye eklenebilir.
 * @author hrzafer
 */
public class TfIdfExtractor {

    public static final int NumberOfTopTerms = 200;
    public static final double Treshold = 0.0002; //tf-idf eşik değeri
    public static final boolean TresholdBased = false;
    public static final boolean oneToManyMode = true;
    private static final double LLRTreshold = 90.0;
    private static final boolean bigram = false;
    public static final String dataSetPathDir = "dataset\\kategori_10arSayfa.csv";
    public static final String dataSetPathCsv = "data/iyi_kotu.csv";
    public static final String TopTermsFileName = "topTermsLexicon.txt";

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.start();
        extract();
        timer.stop();
        System.out.println("Total Time: " + timer.getElapsedSeconds());
    }

    public static void extract() {
        List<Feature> features = FeatureReader.read("experiment/features_tfidf.xml");
        NGramTerms nGramTerms = (NGramTerms) features.get(0);

        //DatasetReader reader = new CSVDatasetReader(dataSetPathCsv);
        DatasetReader reader = new CSVDatasetReader(dataSetPathDir);

        Timer timer = new Timer();
        timer.start();
        Dataset dataset = reader.read();
        timer.stop();
        System.out.println("Dataset reading time: " + timer.getElapsedSeconds());

        List<String> klassNames = dataset.getKlassNames();
        List<CategoryOld> categories = new ArrayList<>();

        timer.start();
        for (String klassName : klassNames) {
            CategoryOld cat = new CategoryOld(dataset.getDocumentsOf(klassName, 100), nGramTerms, klassName);
            categories.add(cat);
        }

        timer.stop();
        System.out.println("Ngrams time: " + timer.getElapsedSeconds());

        Set<TfIdf> top_terms = new TreeSet<>();

        for (CategoryOld category : categories) {
            List<TfIdf> tfIdfs = category.getTfidfsByKlassFrequency();
            //List<TfIdf> tfIdfs = category.getTfidfsByInstanceFrequency();
            System.out.println(category.getTfidfsByKlassFrequency().size());
            for (TfIdf tfIdf : tfIdfs) {
                double idf = 0;
                int catsContainingTheTerm=0;
                for (CategoryOld cat : categories) {
                    if (cat.contains(tfIdf.getTerm().getKey())) {
                        catsContainingTheTerm++;
                    }
                }
                idf = categories.size() / catsContainingTheTerm;
                tfIdf.setIdf(idf);
            }

            Collections.sort(tfIdfs);

            if (bigram){
                eliminateByLLR(tfIdfs);
            }

            if (TresholdBased) {
                AddTermsAboveTreshold(top_terms, tfIdfs, Treshold);
            } else {
                AddTopTerms(top_terms, tfIdfs, NumberOfTopTerms);
            }

            if (oneToManyMode){
                tfidfsToFile(tfIdfs.subList(0, NumberOfTopTerms), category.getId());
            }
            else {
                tfidfsToFile(tfIdfs, category.getId());
            }
        }
        TopTermsToFile(top_terms, TopTermsFileName);
    }

    public static void eliminateByLLR(List<TfIdf> classTerms){
        Iterator<TfIdf> iterator = classTerms.iterator();
        while(iterator.hasNext()){
            TfIdf tfIdf = iterator.next();
            if (tfIdf.getLlr()<LLRTreshold){
                iterator.remove();
            }
        }
    }

    public static void AddTermsAboveTreshold(Set<TfIdf> top_terms, List<TfIdf> classTerms, double treshold) {
        for (TfIdf tfIdf : classTerms) {
            if (!top_terms.contains(tfIdf) && tfIdf.getTfidf() > treshold) {
                top_terms.add(tfIdf);
            }
        }
    }

    public static void AddTopTerms(Set<TfIdf> top_terms, List<TfIdf> classTerms, int n) {
        int i = 0;
        int count = 0;
        while (count < n) {
            if (i >= classTerms.size()) {
                throw new RuntimeException("N değeri dökümandaki terim sayısından fazla :" + classTerms.size());
            }
            if (!top_terms.contains(classTerms.get(i))) {
                top_terms.add(classTerms.get(i));
                count++;
            } else {
               // System.out.println(classTerms.get(i));
            }
            i++;
        }
    }

    public static void TopTermsToFile(Set<TfIdf> tfIdfs, String filename) {
        StringBuilder sb = new StringBuilder();
        for (TfIdf tfIdf : tfIdfs) {
            sb.append(tfIdf.getTerm().getKey()).append("\n");
        }
        IO.write("experiment/" + filename , sb.toString());
    }

    public static void tfidfsToFile(List<TfIdf> tfIdfs, String filename) {
        StringBuilder sb = new StringBuilder();
        for (TfIdf tfIdf : tfIdfs) {

            if (oneToManyMode){
                sb.append(tfIdf.getTerm().getKey()).append("\n");
            }
            else {
                sb.append(tfIdf).append("\n");
            }
        }
        IO.write("experiment/" + filename + "Lexicon", sb.toString());
    }

}
