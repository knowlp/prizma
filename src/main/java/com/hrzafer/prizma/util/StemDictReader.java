package com.hrzafer.prizma.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Reads a stem dictionary into a Map(string,string)
 * Used by all dictionary based Stemmers
 */
public class StemDictReader {
    public static Map<String, String> read(String resourcePath) {
        System.out.println("deneme");
        InputStream is = StemDictReader.class.getResourceAsStream(resourcePath);
        Scanner scanner = new Scanner(is);
        Map<String, String> map = new HashMap<String, String>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.charAt(0) != '#') {
                try {
                    String[] columns = line.split("\t");
                    if (map.put(columns[0], columns[1]) != null) {
                        System.out.println(columns[0]);//Shows duplicates
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("HATA:" + line);
                }

            }
        }
        return map;
    }
}
