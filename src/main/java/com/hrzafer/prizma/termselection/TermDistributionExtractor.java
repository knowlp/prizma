package com.hrzafer.prizma.termselection;

import com.hrzafer.prizma.data.Dataset;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.data.FeatureReader;
import com.hrzafer.prizma.data.io.DatasetReader;
import com.hrzafer.prizma.data.io.DirectoryDatasetReader;
import com.hrzafer.prizma.feature.Feature;
import com.hrzafer.prizma.feature.TermVector;
import com.hrzafer.prizma.feature.ngram.TermDictionary;

import java.util.*;

/**
 * Tüm categoriler için terim dağılımlarını çıkarır
 */

public class TermDistributionExtractor {

    public static List<Category> extract(Dataset dataset, TermVector termVector) {
        List<String> klassNames = dataset.getKlassNames();
        Map<String, TermDictionary> categoryDictionaries = new TreeMap<>();
        for (String klassName : klassNames) {
            List<Document> documents = dataset.getDocumentsOf(klassName);
            TermDictionary terms = termVector.buildNewTermDictionary(documents);
            categoryDictionaries.put(klassName, terms);
        }

        List<Category> categories = new ArrayList<>();

        for (Map.Entry<String, TermDictionary> entry : categoryDictionaries.entrySet()) {
            String categoryName = entry.getKey();
            TermDictionary termDictionary = entry.getValue();
            Set<String> terms = termDictionary.getTerms();
            List<Term> categoryTerms = new ArrayList<>();
            int documentCount = termDictionary.getDocumentCount();
            for (String term : terms) {
                int a = termDictionary.getContainingDocumentNumber(term);
                int b = documentCount - a;
                int c = 0;
                int d = 0;
                for (Map.Entry<String, TermDictionary> tempEntry : categoryDictionaries.entrySet()) {
                    String otherCategoryName = tempEntry.getKey();
                    if (categoryName.equals(otherCategoryName)) {
                        continue;
                    }
                    TermDictionary tempDictionary = tempEntry.getValue();
                    c += tempDictionary.getContainingDocumentNumber(term);
                    d += tempDictionary.getDocumentCount();
                }
                d = d - c;
                categoryTerms.add(new Term(term, new TermDistribution(a, b, c, d)));
            }
            categories.add(new Category(categoryName, categoryTerms));
        }
        return categories;
    }

    public static void main(String[] args) {
        DatasetReader reader = new DirectoryDatasetReader.Builder("dataset/test_dataset").build();
        Dataset dataset = reader.read();
        List<Feature> features = FeatureReader.read("experiment/features_tfidf.xml");
        TermVector termVector = (TermVector) features.get(0);
        List<Category> categories = TermDistributionExtractor.extract(dataset, termVector);
        for (Category category : categories) {
            System.out.println(category);
        }
    }
}
