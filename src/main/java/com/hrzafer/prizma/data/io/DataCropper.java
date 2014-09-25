package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.Config;
import com.hrzafer.prizma.util.STR;

import java.util.Map;

/**
    If a field of a document (usually the content) is too long, this class shortens the field by splitting it to n parts and
    reads the first k bytes of each part and merges them. Thus the document size is shortened to n*k=maxSampleLength.
 */
public class DataCropper {
    private static int maxLen = Config.getAsInt("maxSampleLength");
    private static int parts = Config.getAsInt("subSamplePartCount");

    private static int croppedCount = 0;

    public static Map<String, String> crop(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (isTooLong(entry.getValue())) {
                data.put(entry.getKey(), crop(entry.getValue()));
                croppedCount++;
            }
        }
        return data;
    }

    private static String crop(String str) {
        return STR.getSubString(str, maxLen, parts);
    }

    private static boolean isTooLong(String str) {
        return str.length() > maxLen;
    }
}
