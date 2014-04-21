package com.hrzafer.prizma;

import com.hrzafer.prizma.data.Dataset;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.data.FeatureReader;
import com.hrzafer.prizma.data.io.*;
import com.hrzafer.prizma.feature.Feature;
import com.hrzafer.prizma.feature.value.DocumentVectors;
import com.hrzafer.prizma.util.IO;

import java.util.List;

/**

 */
public class TestMain {
    public static void main(String[] args) {



        String inputPath = "C:\\Users\\hrzafer\\Desktop\\workspace\\Prizma\\code\\prizma\\dataset\\test_dataset";
        String outputPath = "C:\\Users\\hrzafer\\Desktop\\workspace\\Prizma\\code\\prizma\\dataset\\test_dataset.csv";

        DirToCSVConverter.convertDataset(inputPath, outputPath);
//        DatasetReader reader = new CSVDatasetReader(inputPath);
//        List<Feature> features = FeatureReader.read("experiment/features.xml");
//        Dataset dataset = reader.read();
//        List<Document> instances = dataset.getAllInstances();
//        DocumentVectors relation = new DocumentVectors("test_dataset", features, instances);
//        String arff = NewArffCreator.create(relation);
//        IO.write("arff/test_dataset_csv.arff", arff);


//        DatasetWriter writer = new CSVDatasetWriter();
//        writer.write(dataset, outputPath);



    }
}
