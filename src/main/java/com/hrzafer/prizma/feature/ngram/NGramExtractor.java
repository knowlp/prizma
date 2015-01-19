package com.hrzafer.prizma.feature.ngram;


import java.util.List;
import java.util.Map;
import java.util.Set;

public interface NGramExtractor {

    /**
     * Extracts all ngrams as a list which can contain same ngrams more than one.<br/>
     * For the text "bir iki üç iki üç dört" <br/>
     * Expected unigrams are: {"bir", "iki", "üç", "iki", "üç", "dört"} <br/>
     * Expected bigrams are: {"bir iki", "iki üç", "üç iki", "iki üç", "üç dört"} <br/>
     * @param tokens The tokenized text.
     * @return n-gram list
     */
    public abstract List<String> extractTermList(List<String> tokens);

    /**
     * Extracts all ngrams as a set which contains uniqe ngrams.<br/>
     * For the text "bir iki üç iki üç dört" <br/>
     * Expected unigrams are: {"bir", "iki", "üç", "dört"} <br/>
     * Expected bigrams are: {"bir iki", "iki üç", "üç iki", "üç dört"} <br/>
     * @param tokens The tokenized text.
     * @return ngram set
     */
    public abstract Set<String> extractTermSet(List<String> tokens);

    /**
     * Extracts all ngrams as a map from unique ngrams to their frequencies.<br/>
     * For the text "bir iki üç iki üç dört" <br/>
     * Expected unigrams are: {"bir"=1, "iki"=2, "üç"=2, "dört"=1} <br/>
     * Expected bigrams are: {"bir iki"=1, "iki üç"=2, "üç iki"=1, "üç dört"=1} <br/>
     * @param tokens The tokenized text.
     * @return ngram, frequency map
     */
    public abstract Map<String, Integer> extractTermMap(List<String> tokens);

}
