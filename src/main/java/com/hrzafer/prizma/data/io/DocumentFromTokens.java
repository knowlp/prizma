package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.util.STR;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * To change this template use File | Settings | File Templates.
 */
public class DocumentFromTokens extends DocumentSource {

    private List<String> tokens;
    private String id;

    public DocumentFromTokens(List<String> tokens) {
        this.tokens = tokens;
        this.id = "";
    }

    public DocumentFromTokens(List<String> tokens, String id) {
        this.tokens = tokens;
        this.id = id;
    }

    @Override
    protected Map<String, String> readData() {
        Map<String, String> data = new HashMap<>();
        String content = STR.TokensToString(tokens);
        data.put(Document.CONTENT_FIELDNAME, content);
        if (id!=null && !id.isEmpty()){
            data.put(Document.ID_FIELDNAME, id);
        }
        return data;
    }


}
