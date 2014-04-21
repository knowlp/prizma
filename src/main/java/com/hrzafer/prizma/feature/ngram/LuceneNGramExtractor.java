package com.hrzafer.prizma.feature.ngram;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.shingle.ShingleAnalyzerWrapper;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 24.02.2014
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public class LuceneNGramExtractor extends NGramExtractor {

    protected LuceneNGramExtractor(int windowSize) {
        super(windowSize);
    }

    @Override
    public List<String> extractTermList(List<String> tokens) {
        StringBuilder sb = new StringBuilder();
        for (String token : tokens) {
            sb.append(token).append(" ");
        }
        return extract(sb.toString(), windowSize);
    }

    @Override
    public Map<String, Integer> extractTermMap(List<String> tokens) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Set<String> extractTermSet(List<String> tokens) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<String> extract(String str, int windowSize) {
        List<String> ng = new ArrayList<>();
        int minShingleSize = 2;
        int maxShingleSize = 2;

        if (windowSize > 2) {
            minShingleSize = windowSize;
            maxShingleSize = windowSize;
        }

        try {
            Reader reader = new StringReader(str);
            SimpleAnalyzer simpleAnalyzer = new SimpleAnalyzer(Version.LUCENE_46);
            ShingleAnalyzerWrapper shingleAnalyzer = new ShingleAnalyzerWrapper(simpleAnalyzer, minShingleSize, maxShingleSize);
            TokenStream stream = shingleAnalyzer.tokenStream("contents", reader);
            CharTermAttribute charTermAttribute = stream.getAttribute(CharTermAttribute.class);
            stream.reset();
            while (stream.incrementToken()) {
                String shingle = charTermAttribute.toString();
                if (shingle.split(" ").length == windowSize) {
                    ng.add(shingle);
                }
            }
            stream.end();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return ng;

    }
}
