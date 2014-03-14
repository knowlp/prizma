package com.hrzafer.prizma.preprocessing;

import java.util.Arrays;
import java.util.List;

/**
 * Daha hızlı bir tokenizer yapmak istiyoruz ama henüz doğru çalşımıyor
 */
public class FastTokenizer implements ITokenizer {
    @Override
    public List<String> tokenize(String str) {

        return Arrays.asList(str.split("\\p{L}\\p{Digit}"));
    }
}
