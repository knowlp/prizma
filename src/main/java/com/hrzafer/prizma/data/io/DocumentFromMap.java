package com.hrzafer.prizma.data.io;

import java.util.Map;

/**
 * To create a Document directly from a Map object
 */
public class DocumentFromMap extends DocumentSource {

    Map<String, String> data;

    public DocumentFromMap(Map<String, String> data) {
        this.data = data;
    }

    @Override
    protected Map<String, String> readData() {
        return data;
    }
}
