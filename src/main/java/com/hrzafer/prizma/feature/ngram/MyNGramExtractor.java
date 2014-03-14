package com.hrzafer.prizma.feature.ngram;

import java.util.ArrayList;
import java.util.List;

public class MyNGramExtractor implements NGramExtractor{

    public List<NGram> extract(List<String> tokens, int windowSize) {
        List<NGram> ng = new ArrayList<>();
        for (int i = 0; i <= tokens.size() - windowSize; i++) {
            ng.add(getNGram(tokens, i, windowSize));
        }
        return ng;
    }

    private NGram getNGram(List<String> tokens, int index, int windowSize) {
        StringBuilder shingle = new StringBuilder();
        for (int i = 0; i < windowSize; i++) {
            shingle.append(tokens.get(index + i)).append(" ");
        }
        shingle.deleteCharAt(shingle.length() - 1);// Remove last space
        return new NGram(shingle.toString());
    }
}
