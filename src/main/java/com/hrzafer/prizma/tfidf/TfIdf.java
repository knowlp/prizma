package com.hrzafer.prizma.tfidf;


import com.hrzafer.prizma.feature.ngram.NGramFrequency;
import com.hrzafer.prizma.util.STR;

import java.util.Map;

/**
 * @author hrzafer
 */
public class TfIdf implements Comparable<TfIdf> {

    private Map.Entry<String, NGramFrequency> term;
    private double tf;
    private double idf;
    private double llr;

    public TfIdf(Map.Entry<String, NGramFrequency> term) {
        this.term = term;
    }

    public Map.Entry<String, NGramFrequency> getTerm() {
        return term;
    }
    
    public TfIdf(Map.Entry<String, NGramFrequency> term, double tf) {
        this.term = term;
        this.tf = tf;
    }

    public void setTf(double tf) {
        this.tf = tf;
    }

    public double getTf() {
        return tf;
    }

    public void setIdf(double idf) {
        this.idf = idf;
    }

    public double getIdf() {
        return idf;
    }
    
    public double getTfidf() {
        return tf * Math.log(idf);
    }

    @Override
    public boolean equals(Object obj) {
         if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TfIdf)) {
            return false;
        }
        TfIdf other = (TfIdf) obj;
        return term.getKey().equals(other.term.getKey());
    }

    @Override
    public int hashCode() {
//        System.out.println("geldi-hash");
//        int hash = 5;
//        hash = 61 * hash + Objects.hashCode(this.term);
//        return hash;
        return this.term.getKey().hashCode();
    }
        

    @Override
    public int compareTo(TfIdf other) {
        return Double.valueOf(other.getTfidf()).compareTo(Double.valueOf(getTfidf()));
    }

    public void setLlr(double llr) {
        this.llr = llr;
    }

    public double getLlr() {
        return llr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String _tf = STR.formatDouble(this.tf, "#.#######");
        String _idf = STR.formatDouble(this.idf, "#.#######");
        String _llr = STR.formatDouble(this.llr, "#.#######");
        String tfidf = STR.formatDouble(getTfidf(), "#.#######");
        sb.append(term).append("\t").append(_tf).append("\t").append(_idf).append("\t").append(tfidf).append("\t").append(llr);
        return sb.toString();
    }
}
