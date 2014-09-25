package com.hrzafer.prizma.data.io;

import au.com.bytecode.opencsv.CSVWriter;
import com.hrzafer.prizma.data.Dataset;
import com.hrzafer.prizma.data.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes a dataset to a CSV file
 */
public class CSVDatasetWriter extends DatasetWriter {
    @Override
    public void write(Dataset dataset, String path) {
        CSVWriter writer;

        try {
            writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
            List<Document> documents = dataset.getAllDocuments();
            List<String[]> data = new ArrayList<>();
            List<String> headers = dataset.getFieldNames();
            data.add(headers.toArray(new String[headers.size()]));
            for (Document document : documents) {
                data.add(document.getData());
            }
            writer.writeAll(data);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
