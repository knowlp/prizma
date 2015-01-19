package com.hrzafer.prizma;

import com.hrzafer.prizma.data.Dataset;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.data.FeatureReader;
import com.hrzafer.prizma.data.io.*;
import com.hrzafer.prizma.feature.Feature;
import com.hrzafer.prizma.feature.ngram.NGramExtractor;
import com.hrzafer.prizma.feature.ngram.NGramSize;
import com.hrzafer.prizma.feature.ngram.PrizmaNGramExtractor;
import com.hrzafer.prizma.feature.value.DocumentVectors;
import com.hrzafer.prizma.preprocessing.Analyzer;
import com.hrzafer.prizma.util.IO;
import com.hrzafer.prizma.util.Jaccard;
import com.hrzafer.prizma.util.WeightedJaccard;

import java.util.*;

/**

 */
public class TestMain {
    public static Map<String, Set<String>> map = new HashMap<>();

//    static {
//        int count = 100;
//        map.put("catBusiness", new HashSet<>(IO.readLines("experiment/catBusinessLexicon")));
//        map.put("catEducation", new HashSet<>(IO.readLines("experiment/catEducationLexicon")));
//        map.put("catGame", new HashSet<>(IO.readLines("experiment/catGameLexicon")));
//        map.put("catHealth", new HashSet<>(IO.readLines("experiment/catHealthLexicon")));
//        map.put("catMagazine", new HashSet<>(IO.readLines("experiment/catMagazineLexicon")));
//        map.put("catNews", new HashSet<>(IO.readLines("experiment/catNewsLexicon")));
//        map.put("catPolitics", new HashSet<>(IO.readLines("experiment/catPoliticsLexicon")));
//        map.put("catReligion", new HashSet<>(IO.readLines("experiment/catReligionLexicon")));
//        map.put("catShopping", new HashSet<>(IO.readLines("experiment/catShoppingLexicon")));
//        map.put("catSport", new HashSet<>(IO.readLines("experiment/catSportsLexicon")));
//        map.put("catTech", new HashSet<>(IO.readLines("experiment/catTechLexicon")));
//    }

    public static void main(String[] args) {

        //convertToJSON();
        test();

    }

    public static void test() {

        String inputPath = "C:\\Users\\hrzafer\\Desktop\\workspace\\Prizma\\code\\prizma\\dataset\\train_9x100.json";
        //DatasetReader reader = new DirectoryDatasetReader.Builder(inputPath).build();

        List<Feature> features = FeatureReader.read("experiment/exp1.xml");


        ArffProperties properties = new ArffProperties(inputPath, features, "test", 100);
        OldArffCreator.create(properties, "arff/train_9x100_json.arff");

        //DatasetReader reader = new JSONDatasetReader(inputPath);
        //Dataset dataset = reader.read();
        //List<Document> instances = dataset.getAllDocuments();
        //DocumentVectors vectors = new DocumentVectors("test_dataset", features, instances);
        //String arff = ArffCreator.create(vectors);todo: neden çalışmıyor ?


    }

    public static void convertToJSON() {
        String inputPath = "C:\\Users\\hrzafer\\Desktop\\workspace\\Prizma\\code\\prizma\\dataset\\train_9x100";
        String outputPath = "C:\\Users\\hrzafer\\Desktop\\workspace\\Prizma\\code\\prizma\\dataset\\train_9x100.json";
        DatasetReader reader = new DirectoryDatasetReader.Builder(inputPath).build();
        JSONDatasetWriter writer = new JSONDatasetWriter();
        writer.write(reader.read(),outputPath);
    }

    public static void convertToCSV() {
        String inputPath = "C:\\Users\\hrzafer\\Desktop\\workspace\\Prizma\\code\\prizma-old\\prizma_data_sets\\dataset_authorship";
        String outputPath = "C:\\Users\\hrzafer\\Desktop\\workspace\\Prizma\\code\\prizma\\dataset\\dataset_authorship.csv";
        DirToCSVConverter.convertDataset(inputPath, outputPath);
    }

    private static void convertDirToXML(){
        String inputPath = "C:\\Users\\hrzafer\\Desktop\\workspace\\Prizma\\veri\\train_9x100";
        String outputPath = "C:\\Users\\hrzafer\\Desktop\\solr-4.9.0\\example\\exampledocs\\train_9x100.xml";
        DatasetReader reader = new DirectoryDatasetReader.
                Builder(inputPath).createId(true).strategy(new TitledFileDocumentSourceStrategy()).build();
        Dataset dataset = reader.read();
        XmlDatasetWriter writer = new XmlDatasetWriter();
        writer.write(dataset, outputPath);

    }


    //Bazı terimler diğerlerine göre daha ağırlıklı! Burada bu ağırlık kullanılmadığından başarım %87'den %75'e düştü
    //Bu ağırlığı nasıl kullanabiliriz?
    //TF-IDF
    //
    public static void JaccardSimTest() {

        Analyzer analyzer = Globals.getAnalyzer("global");
        DatasetReader reader = new CSVDatasetReader("dataset\\kategori_valid_11x600.csv");
        Dataset dataset = reader.read();
        List<Document> documents = dataset.getAllDocuments();
        for (Document document : documents) {
            List<String> tokens = analyzer.analyze(document.getFieldData("content"));
            NGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.UNIGRAM);
            Set<String> s2 = extractor.extractTermSet(tokens);
            String predicted = "";
            double max = 0;
            for (String cat : map.keySet()) {
                Set<String> s1 = map.get(cat);
                double sim = Jaccard.Similarity(s1, s2);
                if (max < sim) {
                    max = sim;
                    predicted = cat;
                }
            }
            document.setPredictedCategory(predicted);
        }
        System.out.println(dataset.evaluate());
    }
}
