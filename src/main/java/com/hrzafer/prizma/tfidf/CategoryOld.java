package com.hrzafer.prizma.tfidf;


import com.hrzafer.prizma.feature.TermVector;
import com.hrzafer.prizma.feature.ngram.TermFrequency;
import com.hrzafer.prizma.feature.ngram.TermDictionary;
import com.hrzafer.prizma.feature.ngram.NGramSize;
import com.hrzafer.prizma.data.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hrzafer
 */
public class CategoryOld {

    private String id;
    private TermDictionary termDictionary;
    private int totalWordCount;
    private int totalInstanceCount;
    private boolean bigram;

    public CategoryOld(List<Document> documents, TermVector termVector, String id) {
        this.id = id;
        this.termDictionary = termVector.buildNewTermDictionary(documents);
        System.out.println(id + "-" + termDictionary.getTermCount() + "-" + termDictionary.getTotalTermFrequency());
        totalWordCount = termDictionary.getTotalTermFrequency();
        totalInstanceCount = documents.size();
        if (termVector.getSize() == NGramSize.BIGRAM){
            bigram = true;
        }
    }

    private double LLR(Map.Entry<String, TermFrequency> nGram ){

            int total = termDictionary.getTotalTermFrequency();

            int w11= nGram.getValue().getCorpusFrequency();
            int w22= 0;
            int w12=0;
            int w21=0;
            String[] bigram = nGram.getKey().split(" ");
            for (Map.Entry<String, TermFrequency> nGram2 : termDictionary.getTermsMap().entrySet()) {

                if (!nGram2.equals(nGram)){
                    if (nGram2.getKey().startsWith(bigram[0] + " ") && !nGram2.getKey().endsWith(" " + bigram[1])){
                        w21+=nGram2.getValue().getCorpusFrequency();
                    }
                    else if (!nGram2.getKey().startsWith(bigram[0] + " ") && nGram2.getKey().endsWith(" " + (bigram[1]))){
                        w12+=nGram2.getValue().getCorpusFrequency();
                    }
                    else{
                        w22+=nGram2.getValue().getCorpusFrequency();
                    }
                }
            }

            //smooting
            w11++;
            w12++;
            w21++;
            w22++;

            int row1 = w11 + w12;
            int row2 = w21 + w22;
            int col1 = w11 + w21;
            int col2 = w12 + w22;

            double m11 = (row1 * col1 )/(double) total;
            double m12 = (row1 * col2 )/(double) total;
            double m21 = (row2 * col1 )/(double) total;
            double m22 = (row2 * col2 )/(double) total;

            if (nGram.getKey().equals("enes ünal")){
                System.out.println(w11 + "\t" + w12 + "\n" + w21 + "\t" + w22);
            }

            double llr = 2 * (w11 * Math.log(w11/m11) + w12 * Math.log(w12/m12) +
                    w21 * Math.log(w21 / m21) + w22 * Math.log(w22 / m22));

        return llr;

    }

    public List<TfIdf> getTfidfsByKlassFrequency() {
        List<TfIdf> tfIdfs = new ArrayList<>();
        for (Map.Entry<String, TermFrequency> nGramMapEntry : termDictionary.getTermsMap().entrySet()) {

            //double tf = nGramMapEntry.getValue().getCorpusFrequency() / (double) totalWordCount;
            //double tf = nGramMapEntry.getValue().getContainingDocumentCount() / totalWordCount;
            double tf = nGramMapEntry.getValue().getContainingDocumentCount() /(double) totalInstanceCount;
            //double tf = (nGramMapEntry.getValue().getCorpusFrequency() / (double) totalWordCount) * (nGramMapEntry.getValue().getContainingDocumentCount() /(double) totalInstanceCount);


            TfIdf tfIdf = new TfIdf(nGramMapEntry, tf);
            if (bigram){
                tfIdf.setLlr(LLR(nGramMapEntry));
            }
            tfIdfs.add(tfIdf);
        }
        return tfIdfs;
    }

    public boolean contains(String shingle) {
        return termDictionary.getTerms().contains(shingle);
    }

    public String getId() {
        return id;
    }

}
