package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.data.Dataset;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.data.FeatureReader;
import com.hrzafer.prizma.feature.Feature;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 21.04.2014
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
public class DirToCSVConverter {


    public static void convertDataset(String dirPath, String csvPath, boolean cropLongDocuments){

    }

    public static void convertDataset(String dirPath, String csvPath){
        DocumentSource.cropTooLongData = false;
        DatasetReader reader = new DirectoryDatasetReader.Builder(dirPath).build();
        Dataset dataset = reader.read();
        CSVDatasetWriter writer = new CSVDatasetWriter();
        writer.write(dataset, csvPath);
    }
}
