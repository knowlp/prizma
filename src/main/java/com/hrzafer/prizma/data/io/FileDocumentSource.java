package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.util.IO;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * To create a Document from a file given its path
 */

//todo: UTF_8 meselesini çöz!
public class FileDocumentSource extends DocumentSource {
    private final String _encoding = IO.UTF_8;
    private final boolean createId;
    private final FileDocumentSourceStrategy strategy;
    private String path;

    public FileDocumentSource(String path, FileDocumentSourceStrategy strategy, boolean createId) {
        this.path = path;
        this.createId = createId;
        this.strategy = strategy;
    }

    @Override
    protected Map<String, String> readData() {
        String content = IO.read(path, _encoding);
        Map<String, String> data = new LinkedHashMap<>();
        if (createId) {
            data.put(Document.ID_FIELDNAME, getFileName(path));
        }
        strategy.extractData(data, content);
        return data;
    }

    private String getFileName(String path) {

        int index = path.lastIndexOf('\\', path.lastIndexOf('\\') - 1);
        //int index = path.lastIndexOf('\\');
        return path.substring(index + 1);
    }


}
