package com.hrzafer.prizma.feature.ngram;

import com.hrzafer.prizma.Resources;
import com.hrzafer.prizma.feature.value.*;

import java.util.*;

public class TermDictionary {

    private final int windowSize; // For unigram(i.e. 1-gram windowsSize=1)
    private final Map<String, TermFrequency> terms;
    private final NGramExtractor nGramExtractor;
    private int documentCount;

    public TermDictionary(NGramSize size) {
        this.windowSize = size.size;
        this.nGramExtractor = new PrizmaNGramExtractor(size);
        this.terms = new TreeMap<>();
    }

    public int getDocumentCount() {
        return documentCount;
    }

    public void addDocument(List<String> tokens) {
        appendStartStopSymbols(tokens); //todo: bu optional olmalı, Gerekli gerçekten?
        List<String> ngramList = nGramExtractor.extractTermList(tokens);
        add(ngramList);
        Set<String> nGramSet = new HashSet<>(ngramList);
        updateInstanceFrequency(nGramSet);
        documentCount++;
    }

    public Set<String> getTerms() {
        return terms.keySet();
    }

    public Map<String, TermFrequency> getTermsMap() {
        return terms;
    }

    private void add(List<String> terms) {
        for (String term : terms) {
            add(term);
        }
    }

    private void add(String term) {
        if (terms.containsKey(term)) {
            terms.get(term).incrementCorpusFrequency();
            return;
        }
        terms.put(term, new TermFrequency());
    }

    private void updateInstanceFrequency(Set<String> terms) {
        for (String term : terms) {
            updateInstanceFrequency(term);
        }
    }

    private void updateInstanceFrequency(String term) {
        terms.get(term).incrementContainingDocumentCount();
    }

    /**
     * frekansı limit değerden az olanları eler.
     *
     * @param treshold bu değerden az ise ele
     */
    public void eliminateByFrequency(int treshold) {
        for (Iterator<Map.Entry<String, TermFrequency>> it = terms.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, TermFrequency> entry = it.next();
            if (entry.getValue().getCorpusFrequency() < treshold) {
                it.remove();
            }
        }
    }

    /**
     * Used for term selecton
     * @param treshold
     */
    public void eliminateByContainingDocumentCount(int treshold) {
        for (Iterator<Map.Entry<String, TermFrequency>> it = terms.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, TermFrequency> entry = it.next();
            if (entry.getValue().getContainingDocumentCount() < treshold) {
                it.remove();
            }
        }
    }

    public void fromFile(String lexiconPath, boolean encoded) {
        Set<String> lines = new TreeSet<>(Resources.getLines(lexiconPath, encoded));
        for (String line : lines) {
            terms.put(line, new TermFrequency());
        }
    }

    public int getTermFrequency(String term) {
        if (terms.containsKey(term)) {
            return terms.get(term).getCorpusFrequency();
        }
        return 0;
    }

    public int getContainingDocumentNumber(String term) {
        if (terms.containsKey(term)) {
            return terms.get(term).getContainingDocumentCount();
        }
        return 0;
    }

    private void appendStartStopSymbols(List<String> tokens) {
        for (int i = 0; i < windowSize - 1; i++) {
            tokens.add(0, "_ss");
        }
        if (windowSize > 1) {
            tokens.add("es_");
        }
    }


    /**
     * Returns a FeatureValue Vector.
     * This vector's size is equal to the number terms this class includes.
     * In this vector, values corresponding to any matching term will be either its frequency or a binary value that
     * represents existance of the term in tokens.
     *
     * @param tokens
     * @param binary
     * @return
     */
    public List<FeatureValue> getTermsVector(List<String> tokens, boolean binary) {
        if (binary){
            Set<String> termSet = nGramExtractor.extractTermSet(tokens);
            return compareAndGetBinaryVector(termSet);
        }

        Map<String, Integer> termMap = nGramExtractor.extractTermMap(tokens);
        return  compareAndGetTfIdfVector(termMap);
            //todo: buradaki tf-idf değerleri muhtemelen hatalı, ilk iş olarak burayı düzelt
            //Ya da değerin frequenct mi tf-idf mi, yoksa başka bir şey mi olacağı parametre olarak gelmeli
        //return compareAndGetIntegerVector(termMap);
    }

    private List<FeatureValue> compareAndGetBinaryVector(Set<String> termsMap){
        List<FeatureValue> vector = new ArrayList<>();
        for (String term : terms.keySet()) {
            vector.add(new BinaryValue(termsMap.contains(term)));
        }
        return vector;
    }

    private List<FeatureValue> compareAndGetIntegerVector(Map<String, Integer> termsMap){
        List<FeatureValue> vector = new ArrayList<>();
        for (String term : terms.keySet()) {
            IntegerValue value;
            if (termsMap.containsKey(term)){
                value = new IntegerValue(termsMap.get(term));
            }
            else {
                value = new IntegerValue(0);
            }
            vector.add(value);
        }
        return vector;
    }

    private List<FeatureValue> compareAndGetTfIdfVector(Map<String, Integer> termsMap){
        List<FeatureValue> vector = new ArrayList<>();
        for (String term : terms.keySet()) {
            DoubleValue value;
            if (termsMap.containsKey(term)){
                int f = termsMap.get(term);
                double tf = f/(double)termsMap.size();
                double docCount= terms.get(term).getContainingDocumentCount();
                double idf = Math.log(documentCount/docCount);
                value = new DoubleValue(tf*idf);
            }
            else {
                value = new DoubleValue(0);
            }
            vector.add(value);
        }
        return vector;
    }

    private int[] compareAndGetVector2(Map<String, Integer> termsMap) {
        int[] vector = new int[terms.size()];
        int i = 0;
        //System.out.println("terms size " + termsMap.size());
        for (String term : terms.keySet()) {
            vector[i] = termsMap.containsKey(term) ? termsMap.get(term) : 0;
            i++;
        }
        return vector;
    }

    private List<FeatureValue> toIntegerValues(int[] vector) {
        List<FeatureValue> values = new ArrayList<>();
        for (int i : vector) {
            values.add(FeatureValueFactory.create(i));
        }
        return values;
    }

    private List<FeatureValue> toBinaryValues(int[] vector) {
        List<FeatureValue> values = new ArrayList<>();
        for (int i : vector) {
            values.add(new BinaryValue(i));
        }
        return values;
    }

    /**
     * dökümandaki toplam tekrarlar dahil ngram sayısını döndürür.
     * Mesela Unigram için bu toplam kelime sayısıdır.
     *
     * @return
     */
    public int getTotalTermFrequency() {
        int total = 0;
        for (TermFrequency i : terms.values()) {
            total += i.getCorpusFrequency();
        }
        return total;
    }

    /**
     * dökümandaki farklı ngram sayısını döndürür.
     * Mesela unigram için bir döküman çok sayıda "bir" kelimesinden oluşuyorsa bu metod 1 döndürür.
     *
     * @return
     */
    public int getTermCount() {
        return terms.size();
    }

    private int[] compareAndGetVector(List<String> extractedTerms) {
        int[] vector = new int[terms.size()];
        int i = 0;
        //System.out.println("terms size" + extractedTerms.size());
        for (String term : terms.keySet()) {
            for (String extractedTerm : extractedTerms) {
                if (term.equals(extractedTerm)) {
                    vector[i]++;
                }
            }
            i++;
        }
        return vector;
    }



    public void clear() {
        terms.clear();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, TermFrequency> entry : terms.entrySet()) {
            sb.append(entry.getKey()).append(" ")
                    .append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
