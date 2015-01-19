package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.data.io.DataCropper;

import java.util.Map;

/**
 * A document
 */
public abstract class DocumentSource {

    /**
     * MaxLen'den daha uzun veriyi kırpar
     */
    //todo: Get cropTooLongData parameter from properties file
    public static boolean cropTooLongData = true;

    public Map<String, String> getData() {
        Map<String, String> data = readData();
        if (cropTooLongData) {
            return DataCropper.crop(data);
        }
        return data;
    }

    protected abstract Map<String, String> readData();
}
