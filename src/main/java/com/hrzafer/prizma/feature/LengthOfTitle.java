package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.preprocessing.Analyzer;

import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Ziynet Nesibe DAYIOGLU 
 * ziynetnesibe@gmail.com www.ziynetnesibe.com <br/>
 *
 * This class contains an feature which counts the length of the title
 */
public class LengthOfTitle extends Feature {

    public LengthOfTitle(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, name, weight, description, parameters, analyzer);
    }

    @Override
    public String extract(Document document) {
        String data = getFieldData(document);
        int letterCount = 0;
        String titleOfText = getTitleOfText(data);
        if (titleOfText != null) {
            letterCount = titleOfText.length();
        }
        return Integer.toString(letterCount);
    }

    public String getTitleOfText(String source) {
        Scanner scanner = new Scanner(source);
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        scanner.close();
        return null;
    }
}