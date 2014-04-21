package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.data.Document;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class DocumentFromString extends DocumentSource {

    private String id;
    private String content;


    public DocumentFromString(String content) {
        this.id = "";
        this.content = content;
    }

    public DocumentFromString(String id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    protected Map<String, String> readData() {
        Map<String, String> data = new HashMap<>();
        data.put(Document.CONTENT_FIELDNAME, content);
        if (!id.isEmpty()) {
            data.put(Document.ID_FIELDNAME, id);
        }
        return data;
    }
}
