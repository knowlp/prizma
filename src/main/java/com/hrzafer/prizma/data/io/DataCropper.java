package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.Config;
import com.hrzafer.prizma.util.STR;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 13.03.2014
 * Time: 16:37
 * To change this template use File | Settings | File Templates.
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
                System.out.println(croppedCount + " cropped");
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
