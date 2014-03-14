package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.data.Document;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class DocumentFromString extends DocumentSource {

    private String content;
    private String id;

    public DocumentFromString(String content) {
        this.content = content;
        this.id = "";
    }

    public DocumentFromString(String content, String id) {
        this.content = content;
        this.id = id;
    }

    @Override
    protected Map<String, String> readData() {
        Map<String, String> data = new HashMap<>();
        data.put(Document.CONTENT_FIELDNAME, content);
        if (!id.isEmpty()){
            data.put(Document.ID_FIELDNAME, id);
        }
        return data;
    }
}
