package com.hrzafer.prizma.preprocessing;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 24.02.2014
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public class WeirdMSWordCharactersNormalizer implements INormalizer {
    @Override
    public String normalize(String str) {
        return replaceWeirdMSWordCharacters(str);
    }

    private String replaceWeirdMSWordCharacters(String str) {
        str = str.replace("''", "\"");
        str = str.replace((char) 145, '\'');
        str = str.replace((char) 8216, '\''); // left single quote
        str = str.replace((char) 146, '\'');
        str = str.replace((char) 8217, '\''); // right single quote
        str = str.replace((char) 147, '\"');
        str = str.replace((char) 148, '\"');
        str = str.replace((char) 8220, '\"'); // left double
        str = str.replace((char) 8221, '\"'); // right double
        str = str.replace((char) 8211, '-'); // em dash
        str = str.replace((char) 8212, '-'); // em dash
        str = str.replace((char) 150, '-');// ?
        str = str.replaceAll("…", "...");
        return str;
    }

}
