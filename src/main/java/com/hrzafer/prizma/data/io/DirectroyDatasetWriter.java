package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.data.Dataset;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.util.IO;

import java.io.File;
import java.util.List;

public class DirectroyDatasetWriter extends DatasetWriter {

    @Override
    public void write(Dataset dataset, String path) {

        File dir = new File(path);
        if (dir.exists() && !dir.isDirectory()) {
            throw new RuntimeException("Prizma: " + path + " is not a directory !");
        }

        if (!dir.exists()) {
            dir.mkdir();
        }

        List<String> names = dataset.getKlassNames();
        for (String name : names) {
            File subDir = new File(path + "/" + name);
            if (!subDir.exists()) {
                subDir.mkdir();
            }
            List<Document> documents = dataset.getDocumentsOf(name, 100);
            for (Document document : documents) {
                IO.write(subDir.getPath() + "/"+ document.getFieldData("id"), document.getFieldData("content"));
            }
        }

    }
}
