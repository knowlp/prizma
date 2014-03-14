package com.hrzafer.prizma.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Bu sınıf her zaman ihtiyaç duyulabilecek bir takım String işlemlerini
 * gerçekleştiren metodları içerir. İhtiyaca göre genişletilebilir.
 *
 * @author hrzafer.com
 */
public class STR {

    public static final String WHITE_SPACES = " \t\n\r\f";
    public static final String PUNCTUATION_MARKS = ".,;";
    public static String CLEAN_REGEX = "[\\*\\-\\:\\\"\\.,\\(\\);?!]";

    /**
     * Bir string'deki standart ASCII olmayan karakterleri (ç,ı,ğ,ö,ş,ü,İ, vb.
     * Türkçe karakterleri) ASCII eşdeğerleri ile değiştirir.<br/>
     * Mesela toNonTurkish("türkçe") metodu "turkce" string'ini döndürecektir.
     */
    public static String toNonTurkish(String str) {
        str = str.replace("ç", "c");
        str = str.replace("ö", "o");
        str = str.replace("ş", "s");
        str = str.replace("ğ", "g");
        str = str.replace("ü", "u");
        str = str.replace("ı", "i");
        str = str.replace("Ç", "C");
        str = str.replace("Ö", "O");
        str = str.replace("Ş", "S");
        str = str.replace("Ğ", "G");
        str = str.replace("Ü", "U");
        str = str.replace("İ", "I");
        return str;
    }

    /**
     * Bir string'i kelimeleri ve noktalama işaretlerini (delims) liste
     * (ArrayList) olarak döndürür.
     */
    public static List<String> tokenize(String str, String delims, boolean returnDelims) {
        StringTokenizer tokenizer = new StringTokenizer(str, delims, returnDelims);
        List<String> words = new ArrayList<String>();
        while (tokenizer.hasMoreTokens()) {
            words.add(tokenizer.nextToken());
        }
        return words;
    }

    public static List<String> tokenize(String str, String delims) {
        return tokenize(str, delims, false);
    }

    public static String getSubString(String source, int maxLength, int parts) {
        StringBuilder sb = new StringBuilder();
        int sectionSize = source.length() / parts;
        int partSize = maxLength / parts;

        for (int i = 0; i < parts; i++) {
            sb.append(source.substring(i * sectionSize, i * sectionSize + partSize));
        }
        return sb.toString();
    }

    /**
     * Bir string'i boşluk karakterlerine (" \t\n\r\f") göre parçalara ayırır.
     */
    public static List<String> tokenize(String str) {
        return tokenize(str, WHITE_SPACES, false);
    }

    /**
     * Bir stringdeki sesli harfleri siler. Mesela removeVowels("deneme")
     * ifadesi "dnm" String'ini döndürür.
     */
    public static String removeVowels(String str) {
        return str.replaceAll("[aeıouüiö]", "");
    }

    /**
     * Bir String'de iki ve daha fazla sayıdaki yan yana boşlukları teke
     * indirir.<br/>
     * Mesela removeDuplicateSpaces("bu bir denemedir") ifadesi "bu bir
     * denemedir" String'ini döndürür.
     */
    public static String removeDuplicateSpaces(String str) {
        return str.replaceAll(" +", " ");
    }

    /**
     * Bir String'deki tüm parantezleri yani '(' ve ')' karakterlerini
     * siler.<br/>
     * Mesela "(a)*(b+c)" ifadesi "a*b+c" ifadesine dönüşür.
     */
    public static String removeParantheses(String str) {
        return str.replaceAll("(\\(|\\))", "");
    }

    /**
     * Düzenli ifadelerde (regex) '[' ve ']' karakterlerini '\[' '\]' şekline
     * dönüştürür.
     */
    public static String escapeSquareBrackets(String str) {
        return str.replaceAll("\\[", "\\\\[").replaceAll("\\]", "\\\\]");
    }

    /**
     * Bir String'deki tüm whitespace (boşluk, tab, newline) karakterleri siler.
     * <br/>
     * Mesela removeAllSpaces("üs satır\nalt satır") ifadesi "üssatıraltsatır"
     * String'ini döndürür.
     */
    public static String removeAllSpaces(String str) {
        return str.replaceAll("\\s", "");
    }

    /**
     * Bir String'in null veya boş olması halinde true değerini döndürür.
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * String'deki Türkçe karakterleri HTML kodları ile değiştirerek %100 HTML
     * uyumlu hale getirir. Bu sayede karakter kodu ne olursa olsun Türkçe
     * karakterler doğru görüntülenir.
     */
    public static String convertTurkishCharsToHtmlCode(String str) {
        str = str.replace("ç", "&#231;");
        str = str.replace("Ç", "&#199;");
        str = str.replace("ı", "&#305;");
        str = str.replace("İ", "&#304;");
        str = str.replace("ğ", "&#287;");
        str = str.replace("Ğ", "&#286;");
        str = str.replace("ö", "&#246;");
        str = str.replace("Ö", "&#214;");
        str = str.replace("ş", "&#351;");
        str = str.replace("Ş", "&#350;");
        str = str.replace("ü", "&#252;");
        str = str.replace("Ü", "&#220;");
        return str;
    }

    /**
     * Bir string'i çift tırnak içerisine almak için kullanılır.<br/>
     * Mesela str isiminde bir String değişkeni çift tırnak içerisinde almak
     * için "\"" + str +"\"" yerine STR.addDoubleQuote(str) yazmak daha
     * kolaydır. Birincisinde hata yapma riski de oldukça yüksektir.
     */
    public static String addDoubleQuote(String str) {
        return "\"" + str + "\"";
    }

    /**
     * Bir String'in tam sayı olup olmadığına bakar
     * isInteger("13"); true döndürür
     * isInteger("abc123"); false döndürür
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * double tipindeki değeri istenilen biçimde String'e dönüştürür.
     * Mesela vigülden sonra en az 2 en fazla 4 basamak gösterilsin istiyorsak
     * pattern = "0.00##" olmalıdır.
     */
    public static String formatDouble(double value, String pattern) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            return Double.toString(Double.NaN);
        }
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern(pattern);
        return df.format(value);
    }

    public static String encodeBase64(String str) {
        byte[] bytes = str.getBytes(Charset.forName("UTF-8"));
        return Base64.encode(bytes);
    }

    public static String decodeBase64(String str) {
        byte[] decodedBytes = Base64.decode(str);
        return new String(decodedBytes, Charset.forName("UTF-8"));
    }

    /**
     * Bir string'i (varsa) satırlarına ayırır
     */
    public static List<String> toLines(String str) {
        Scanner scanner = new Scanner(str);
        List<String> lines = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }
        scanner.close();
        return lines;
    }

    public static String TokensToString(List<String> tokens){
        StringBuilder sb = new StringBuilder();
        for (String token : tokens) {
            sb.append(token).append(" ");
        }
        return sb.toString().trim();
    }

}
