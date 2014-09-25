package com.hrzafer.prizma.feature.ngram;

import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertEquals;

public class PrizmaNGramExtractorTest {

    private final String text = "bir iki üç dört beş bir iki üç dört";

    @Test
    public void testExtractUnigramsAsList() throws Exception {

        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.UNIGRAM.size);

        List<String> tokens = Arrays.asList(text.split(" "));

        List<String> actual = extractor.extractTermList(tokens);

        List<String> expected = Arrays.asList("bir", "iki", "üç", "dört", "beş", "bir", "iki", "üç", "dört");

        assertEquals(expected, actual);
    }

    @Test
    public void testExtractBigramsAsList() throws Exception {

        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.BIGRAM.size);

        List<String> tokens = Arrays.asList(text.split(" "));

        List<String> actual = extractor.extractTermList(tokens);

        List<String> expected = Arrays.asList(
                "bir iki", "iki üç", "üç dört", "dört beş",
                "beş bir", "bir iki", "iki üç", "üç dört");

        assertEquals(expected, actual);
    }

    @Test
    public void testExtractTrigramsAsList() throws Exception {

        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.TRIGRAM.size);

        List<String> tokens = Arrays.asList(text.split(" "));

        List<String> actual = extractor.extractTermList(tokens);

        List<String> expected = Arrays.asList(
                "bir iki üç", "iki üç dört", "üç dört beş", "dört beş bir",
                "beş bir iki", "bir iki üç", "iki üç dört");

        assertEquals(expected, actual);
    }

    @Test
    public void testExtractUnigramsAsSet() throws Exception {

        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.UNIGRAM.size);

        List<String> tokens = Arrays.asList(text.split(" "));

        Set<String> terms = extractor.extractTermSet(tokens);

        Set<String> expected = new HashSet<>(Arrays.asList("bir", "iki", "üç", "dört", "beş"));

        assertEquals(expected, terms);
    }

    @Test
    public void testExtractBigramsAsSet() throws Exception {

        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.BIGRAM.size);

        List<String> tokens = Arrays.asList(text.split(" "));

        Set<String> actual = extractor.extractTermSet(tokens);

        Set<String> expected = new HashSet<>(Arrays.asList("bir iki", "iki üç", "üç dört", "dört beş", "beş bir"));

        assertEquals(expected, actual);
    }

    @Test
    public void testExtractTrigramsAsSet() throws Exception {

        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.TRIGRAM.size);

        List<String> tokens = Arrays.asList(text.split(" "));

        Set<String> actual = extractor.extractTermSet(tokens);

        Set<String> expected = new HashSet<>(Arrays.asList(
                "bir iki üç", "iki üç dört", "üç dört beş",
                "dört beş bir", "beş bir iki"));

        assertEquals(expected, actual);
    }

    @Test
    public void testExtractUnigramsAsMap() throws Exception {

        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.UNIGRAM.size);

        List<String> tokens = Arrays.asList(text.split(" "));

        Map<String, Integer> actual = extractor.extractTermMap(tokens);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("bir", 2);
        expected.put("iki", 2);
        expected.put("üç", 2);
        expected.put("dört", 2);
        expected.put("beş", 1);

        assertEquals(expected, actual);
    }

    @Test
    public void testExtractBigramsAsMap() throws Exception {

        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.BIGRAM.size);

        List<String> tokens = Arrays.asList(text.split(" "));

        Map<String, Integer> actual = extractor.extractTermMap(tokens);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("bir iki", 2);
        expected.put("iki üç", 2);
        expected.put("üç dört", 2);
        expected.put("dört beş", 1);
        expected.put("beş bir", 1);

        assertEquals(expected, actual);
    }

    @Test
    public void testExtractTrigramsAsMap() throws Exception {

        PrizmaNGramExtractor extractor = new PrizmaNGramExtractor(NGramSize.TRIGRAM.size);

        List<String> tokens = Arrays.asList(text.split(" "));

        Map<String, Integer> actual = extractor.extractTermMap(tokens);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("bir iki üç", 2);
        expected.put("iki üç dört", 2);
        expected.put("dört beş bir", 1);
        expected.put("üç dört beş", 1);
        expected.put("beş bir iki", 1);

        assertEquals(expected, actual);
    }

}



