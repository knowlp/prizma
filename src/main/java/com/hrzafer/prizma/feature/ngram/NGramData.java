package com.hrzafer.prizma.feature.ngram;

import com.hrzafer.prizma.Resources;
import com.hrzafer.prizma.util.IO;

import java.util.*;

public class NGramData {

    private final int windowSize;// For unigram(i.e. 1-gram windowsSize=1)
    private final Map<String, NGramFrequency> nGrams = new TreeMap<>();
    private final NGramExtractor nGramExtractor = new MyNGramExtractor();

    public NGramData(NGramSize size) {
        this.windowSize = size.size;
    }

    public List<String> getNGramNames() {
        List<String> names = new ArrayList<>();
        for (String shingle : nGrams.keySet()) {
            names.add(toWekaString(shingle));
        }
        return names;
    }

    public String toWekaString(String shingle){
        StringBuilder sb = new StringBuilder();
        sb.append(shingle.replaceAll(" ", "_").replaceAll("'", "^"));
        return sb.toString();
    }

    private static void removeLastUnderscore(StringBuilder sb) {
        sb.deleteCharAt(sb.length() - 1);
    }

    /**
     * frekansı limit değerden az olanları eler.
     *
     * @param treshold bu değerden az ise ele
     */
    public void eliminateByFrequency(int treshold) {

        for(Iterator<Map.Entry<String, NGramFrequency>> it = nGrams.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, NGramFrequency> entry = it.next();
            if(entry.getValue().getByKlass() < treshold) {
                it.remove();
            }
        }
    }

    //TF-IDF ile nitelik çıkarırken bir terimin en az treshold kadar farklı dökümanda geçip geçmediğine bakar.
    //Geçmeyenleri eler.
    public void eliminateByInstanceFrequency(int treshold) {
        for(Iterator<Map.Entry<String, NGramFrequency>> it = nGrams.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, NGramFrequency> entry = it.next();
            if(entry.getValue().getByInstance() < treshold) {
                it.remove();
            }
        }
    }

//    public void fromEncodedFile(String ngramFile) {
//        InputStream is = Resources.get(ngramFile);
//        String encoded = IO.read(is);
//
//        //String str = IO.readBase64(ngramFile);
//        byte[] decodedBytes = Base64.decode(encoded);
//        String str = new String(decodedBytes, StandardCharsets.UTF_8);
//        System.out.println(str);
//        String lines[] = str.split("\n");
//        for (String line : lines) {
//            nGrams.put(line, new NGramFrequency());
//        }
//    }

    public void fromFile(String lexiconPath, boolean encoded) {
        List<String> lines = Resources.getLines(lexiconPath, encoded);
        for (String line : lines) {
            nGrams.put(line, new NGramFrequency());
        }
    }

    public Map<String, NGramFrequency> getAllNGrams() {
        return nGrams;
    }

    public void addSequence(List<String> tokens) {
        appendStartStopSymbols(tokens);
        List<NGram> nGrams = nGramExtractor.extract(tokens, windowSize);
        add(nGrams);
        Set<NGram> uniqNGrams = new HashSet<>(nGrams);
        updateInstanceFrequency(uniqNGrams);
    }

    private void appendStartStopSymbols(List<String> tokens) {
        for (int i = 0; i < windowSize - 1; i++) {
            tokens.add(0, "_ss");
        }
        if (windowSize > 1) {
            tokens.add("es_");
        }
    }

    private void add(List<NGram> grams) {
        for (NGram nGram : grams) {
            add(nGram);
        }
    }

    private void updateInstanceFrequency(Set<NGram> nGrams){
        for (NGram nGram : nGrams) {
            updateInstanceFrequency(nGram);
        }
    }

    private void updateInstanceFrequency(NGram nGram){
        String key = nGram.getShingle();
        nGrams.get(key).incrementByInstance();
    }

    private void add(NGram nGram) {
        String key = nGram.getShingle();
        if (nGrams.containsKey(key)){
            nGrams.get(key).incrementByKlass();
            return;
        }
        nGrams.put(key, new NGramFrequency());
    }

    public int[] getNGramVector(List<String> tokens) {
        List<NGram> nGrams = nGramExtractor.extract(tokens, windowSize);
        return compareAndGetVector(nGrams);
    }

    public int[] getBinaryNGramVector(List<String> tokens) {
        int[] vector = getNGramVector(tokens);
        convertToBinary(vector);
        return vector;
    }

    /**
     * dökümandaki toplam tekrarlar dahil ngram sayısını döndürür.
     * Mesela Unigram için bu toplam kelime sayısıdır.
     *
     * @return
     */
    public int getTokenCount() {
        int total = 0;
        for (NGramFrequency freq : nGrams.values()) {
            total += freq.getByKlass();
        }
        return total;
    }

    /**
     * dökümandaki farklı ngram sayısını döndürür.
     * Mesela unigram için bir döküman çok sayıda "bir" kelimesinden oluşuyorsa bu metod 1 döndürür.
     *
     * @return
     */
    public int getTypeCount() {
        return nGrams.size();
    }

    private int[] compareAndGetVector(List<NGram> grams) {
        int[] vector = new int[nGrams.size()];
        int i = 0;
        for (String s : nGrams.keySet()) {
            for (NGram nGram : grams) {
                if (s.equals(nGram.getShingle())) {
                    vector[i]++;
                }
            }
            i++;
        }
        return vector;
    }

    private void convertToBinary(int[] vector) {
        for (int i = 0; i < vector.length; i++) {
            if (vector[i] > 1) {
                vector[i] = 1;
            }
        }
    }

    public void sort() {
        //Collections.sort(nGrams);
    }

    public void clear() {
        nGrams.clear();
    }

    @Override
    public String toString() {
        sort();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, NGramFrequency> entry : nGrams.entrySet()) {
            sb.append(NGramtoString(entry.getKey(), entry.getValue().getByKlass())).append("\n");
        }
        return sb.toString();
    }


    public String NGramtoString(String shingle, int frequency) {
        StringBuilder sb = new StringBuilder();
        sb.append(shingle).append(" ").append(frequency);
        return sb.toString();
    }
}
