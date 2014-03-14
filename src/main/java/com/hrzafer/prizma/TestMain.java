package com.hrzafer.prizma;

import com.hrzafer.prizma.data.Dataset;
import com.hrzafer.prizma.data.io.*;

import java.nio.charset.StandardCharsets;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 13.03.2014
 * Time: 16:01
 * To change this template use File | Settings | File Templates.
 */
public class TestMain {
    public static void main(String[] args) {

        String inputPath = "dataset/sample_dataset_sub";
        String outputPath = "dataset/sample_dataset_10.csv";

        DatasetReader reader = new DirectoryDatasetReader(inputPath).percentage(10).shuffled(true);

        Dataset dataset = reader.read();
        DatasetWriter writer = new CSVDatasetWriter();
        writer.write(dataset, outputPath);
    }
}
