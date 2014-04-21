package com.hrzafer.prizma.feature.ngram;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class NGramExtractor {
    protected int windowSize;

    protected NGramExtractor(int windowSize) {
        this.windowSize = windowSize;
    }

    public abstract List<String> extractTermList(List<String> tokens) ;

    public abstract Map<String, Integer> extractTermMap(List<String> tokens) ;

    public abstract Set<String> extractTermSet(List<String> tokens) ;
}
