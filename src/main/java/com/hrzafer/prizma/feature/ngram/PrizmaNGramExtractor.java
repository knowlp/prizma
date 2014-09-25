package com.hrzafer.prizma.feature.ngram;

import java.util.*;

public class PrizmaNGramExtractor implements NGramExtractor {

    private int windowSize;

    public PrizmaNGramExtractor(NGramSize size) {
        this.windowSize = size.size;
    }

    public List<String> extractTermList(List<String> tokens) {
        List<String> terms = new ArrayList<>();
        for (int i = 0; i <= tokens.size() - windowSize; i++) {
            terms.add(getNGram(tokens, i, windowSize));
        }
        return terms;
    }

    @Override
    public Map<String, Integer> extractTermMap(List<String> tokens) {
        Map<String, Integer> terms = new HashMap<>();
        for (int i = 0; i <= tokens.size() - windowSize; i++) {
            String term = getNGram(tokens, i, windowSize);
            if (terms.containsKey(term)) {
                int f = terms.get(term);
                terms.put(term, f + 1);
            } else {
                terms.put(term, 1);
            }
        }
        return terms;
    }

    @Override
    public Set<String> extractTermSet(List<String> tokens) {
        if (windowSize == NGramSize.UNIGRAM.size) {
            return new HashSet<>(tokens);
        }
        Set<String> terms = new HashSet<>();
        for (int i = 0; i <= tokens.size() - windowSize; i++) {
            String term = getNGram(tokens, i, windowSize);
            terms.add(term);
        }
        return terms;
    }

    private String getNGram(List<String> tokens, int index, int windowSize) {
        String prefix = "";
        StringBuilder shingle = new StringBuilder();
        for (int i = 0; i < windowSize; i++) {
            shingle.append(prefix);
            prefix = " ";
            shingle.append(tokens.get(index + i));
        }
        return shingle.toString();
    }
}
