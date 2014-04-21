package com.hrzafer.prizma.feature.ngram;

public enum NGramSize {
    UNIGRAM(1), BIGRAM(2), TRIGRAM(3);

    public final int size;

    NGramSize(int size) {
        this.size = size;
    }
}
