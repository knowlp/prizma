package com.hrzafer.prizma.feature;

//import com.hrzafer.javanta.IO;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.preprocessing.Analyzer;

import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author Ziynet
 *
 * This class contains an feature which counts the word number of the title
 */
public class WordCountOfTitle extends Feature {

    public WordCountOfTitle(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, name, weight, description, parameters, analyzer);
    }

    @Override
    public String extract(Document document) {
        String data = getFieldData(document);
        Integer wordCount = 0;
        String titleOfText = getTitleOfText(data);
        if (titleOfText != null) {
            StringTokenizer st = new StringTokenizer(titleOfText, " ,';?.");
            while (st.hasMoreTokens()) {
                st.nextToken();
                wordCount++;
            }
        }
        return wordCount.toString();
    }

    public String getTitleOfText(String source) {
        Scanner scanner = new Scanner(source);
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return null;
    }
 
}