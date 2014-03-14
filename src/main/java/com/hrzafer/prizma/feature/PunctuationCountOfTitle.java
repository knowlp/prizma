package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.preprocessing.Analyzer;

import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ziynet Nesibe DAYIOGLU 
 * ziynetnesibe@gmail.com www.ziynetnesibe.com <br/>
 *
 * This class contains an feature which counts the punctuation mark count of the title
 */
public class PunctuationCountOfTitle extends Feature {

    public PunctuationCountOfTitle(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, name, weight, description, parameters, analyzer);
    }

    @Override
    public String extract(Document document) {
        String data = getFieldData(document);
        int punctCount = 0;
        String titleOfText = getTitleOfText(data);
        if (titleOfText != null) {
            Pattern p = Pattern.compile("[^a-zA-Z0-9 ŞşÇçÖöĞğÜüİı]");
            Matcher m = p.matcher(titleOfText);

            for (int c = 0; c < titleOfText.length(); c++) {
                if (m.find()) {
                    punctCount++;
                }
            }
        }
        return Integer.toString(punctCount);
    }

    public String getTitleOfText(String source) {
        Scanner scanner = new Scanner(source);
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return null;
    }
}