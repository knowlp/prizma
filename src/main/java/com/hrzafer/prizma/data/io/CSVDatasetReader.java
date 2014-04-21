package com.hrzafer.prizma.data.io;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import com.hrzafer.prizma.data.DocumentCategory;
import com.hrzafer.prizma.data.DocumentCategoryFromDocuments;
import com.hrzafer.prizma.data.Dataset;
import com.hrzafer.prizma.data.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Reads the dataset from a CSV file
 */
public class CSVDatasetReader extends DatasetReader {

    private final String csvPath;

    public CSVDatasetReader(String csvPath) {
        this.csvPath = csvPath;
    }

    /**
     * Assigns category label to each document, creates categories and returns the data
     */
    @Override
    public Dataset read() {
        List<Document> documents = readInstances();
        List<Document> instancesOfCategory = new ArrayList<>();
        List<DocumentCategory> categories = new ArrayList<>();
        Collections.sort(documents);
        String categoryName = documents.get(0).getActualCategory();
        for (Document document : documents) {
            if (categoryName.equals(document.getActualCategory())) {
                instancesOfCategory.add(document);
            } else {
                categories.add(new DocumentCategoryFromDocuments(instancesOfCategory, categoryName));
                instancesOfCategory = new ArrayList<>();
                instancesOfCategory.add(document);
                categoryName = document.getActualCategory();
            }
        }
        categories.add(new DocumentCategoryFromDocuments(instancesOfCategory, categoryName));
        return new Dataset(categories);
    }

    /**
     * Reads all instances from CSV
     */
    private List<Document> readInstances() {
        List<Document> documents = new ArrayList<>();
        List<String[]> data;
        try {

            CSVReader csvReader = new CSVReader(
                    new InputStreamReader(new FileInputStream(csvPath), "UTF-8"),
                    CSVParser.DEFAULT_SEPARATOR,
                    CSVParser.DEFAULT_QUOTE_CHARACTER,
                    '\0');

            data = csvReader.readAll();
            String[] headers = data.get(0);
            for (int i = 1; i < data.size(); i++) {
                Map<String, String> map = new LinkedHashMap<>();
                String[] row = data.get(i);
                for (int j = 0; j < headers.length - 1; j++) {
                    map.put(headers[j], row[j]);
                }
                String categoryName = row[headers.length - 1];
                documents.add(new Document(new DocumentFromMap(map), categoryName));
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return documents;
    }

}
