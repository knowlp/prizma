package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.data.Document;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 13.03.2014
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */
public class TitledFileDocumentSourceStrategy implements FileDocumentSourceStrategy{

    @Override
    public void extractData(Map<String, String> data, String content) {
        int i = content.indexOf(System.lineSeparator());
        if(i>0){
            String title = content.substring(0, i);
            data.put("title", title);
        }
        else{
            data.put("title", "");
        }
        String rest = content.substring(i+1);
        data.put(Document.CONTENT_FIELDNAME, rest);
    }
}
