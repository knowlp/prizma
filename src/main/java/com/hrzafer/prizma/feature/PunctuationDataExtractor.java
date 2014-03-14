/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.prizma.feature;

/**
 *
 * @author test
 */
public class PunctuationDataExtractor {

    private int punctuationCount;
    private int totalPunctuationCount;
    private int letterOrDigitCount;

    public PunctuationDataExtractor(String source, char punctuation) {
        extractPunctuationData(source, punctuation);
    }

    private void extractPunctuationData(String source, char punctuation) {
        char[] data = source.toCharArray();
        for (char c : data) {
            if (c == punctuation) {
                punctuationCount++;
            }
            // Burada else if değil özellikle if kullanıyoruz ki 
            // aradığımız punctuation toplam punctuation sayısına da dahil edilsin.
            if (Character.isLetterOrDigit(c)) {
                letterOrDigitCount++;
            } else if (!Character.isWhitespace(c)) {
                totalPunctuationCount++;
            }
        }
    }

    public int getLetterOrDigitCount() {
        return letterOrDigitCount;
    }

    public int getPunctuationCount() {
        return punctuationCount;
    }

    public int getTotalPunctuationCount() {
        return totalPunctuationCount;
    }
}
