package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.data.Document;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 13.03.2014
 * Time: 16:46
 * To change this template use File | Settings | File Templates.
 */
public class DefaultFileDocumentSourceStrategy implements FileDocumentSourceStrategy{

    @Override
    public void extractData(Map<String, String> data, String content) {
        data.put(Document.CONTENT_FIELDNAME, content);
    }
}
