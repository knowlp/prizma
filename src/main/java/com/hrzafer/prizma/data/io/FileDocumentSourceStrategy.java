package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.data.Document;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 13.03.2014
 * Time: 16:42
 * To change this template use File | Settings | File Templates.
 */
public interface FileDocumentSourceStrategy {
    public void extractData(Map<String, String> data, String content);
}
