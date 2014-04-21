package com.hrzafer.prizma.feature.ngram;

import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertEquals;

public class PrizmaNGramExtractorTest {

    private final String text = "bir iki üç dört beş";
    private final List<String> unigrams = Arrays.asList("bir", "iki", "üç", "dört", "beş");
    private final List<String> bigrams = Arrays.asList("bir iki", "iki üç", "üç dört", "dört beş");
    //todo: bazı durumlarda "<s> bir" ve "beş </s>" gibi start ve stop sembolleri de içeren terimler gerekebilir. Ya da sadece start sembol. Bunu Jurafsky'den tekrar bir bakmak lazım
    private final List<String> trigrams = Arrays.asList("bir iki üç", "iki üç dört", "üç dört beş");

    @Test
    public void testExtractUnigramsAsList() throws Exception {
        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.UNIGRAM.size);
        List<String> tokens = Arrays.asList(text.split(" "));
        List<String> terms = extractor.extractTermList(tokens);
        assertEquals(unigrams, terms);
    }

    @Test
    public void testExtractUnigramsAsSet() throws Exception {
        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.UNIGRAM.size);
        List<String> tokens = Arrays.asList(text.split(" "));
        Set<String> terms = extractor.extractTermSet(tokens);
        Set<String> unigrams = new HashSet<>(this.unigrams);
        assertEquals(unigrams, terms);
    }

    @Test
    public void testExtractUnigramsAsMap() throws Exception {
        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.UNIGRAM.size);
        List<String> tokens = Arrays.asList(text.split(" "));
        Map<String, Integer> actual = extractor.extractTermMap(tokens);
        Map<String, Integer> expected = new HashMap<>();
        for (String unigram : unigrams) {
            expected.put(unigram, 1);
        }
        assertEquals(expected, actual);
    }

    @Test
    public void testExtractBigramsAsList() throws Exception {
        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.BIGRAM.size);
        List<String> tokens = Arrays.asList(text.split(" "));
        List<String> terms = extractor.extractTermList(tokens);
        assertEquals(bigrams, terms);
    }

    @Test
    public void testExtractBigramsAsSet() throws Exception {
        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.BIGRAM.size);
        List<String> tokens = Arrays.asList(text.split(" "));
        Set<String> terms = extractor.extractTermSet(tokens);
        Set<String> bigrams = new HashSet<>(this.bigrams);
        assertEquals(bigrams, terms);
    }

    @Test
    public void testExtractBigramsAsMap() throws Exception {
        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.BIGRAM.size);
        List<String> tokens = Arrays.asList(text.split(" "));
        Map<String, Integer> actual = extractor.extractTermMap(tokens);
        Map<String, Integer> expected = new HashMap<>();
        for (String bigram : bigrams) {
            expected.put(bigram, 1);
        }
        assertEquals(expected, actual);
    }

    @Test
    public void testExtractTrigramsAsList() throws Exception {
        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.TRIGRAM.size);
        List<String> tokens = Arrays.asList(text.split(" "));
        List<String> terms = extractor.extractTermList(tokens);
        assertEquals(trigrams, terms);
    }

    @Test
    public void testExtractTrigramsAsSet() throws Exception {
        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.TRIGRAM.size);
        List<String> tokens = Arrays.asList(text.split(" "));
        Set<String> actual = extractor.extractTermSet(tokens);
        Set<String> expected = new HashSet<>(trigrams);
        assertEquals(actual, expected);
    }

    @Test
    public void testExtractTrigramsAsMap() throws Exception {
        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.TRIGRAM.size);
        List<String> tokens = Arrays.asList(text.split(" "));
        Map<String, Integer> actual = extractor.extractTermMap(tokens);
        Map<String, Integer> expected = new HashMap<>();
        for (String trigram : trigrams) {
            expected.put(trigram, 1);
        }
        assertEquals(actual, expected);
    }

}

