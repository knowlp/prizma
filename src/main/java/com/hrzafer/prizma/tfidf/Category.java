package com.hrzafer.prizma.tfidf;


import com.hrzafer.prizma.feature.NGramModel;
import com.hrzafer.prizma.feature.ngram.NGramData;
import com.hrzafer.prizma.feature.ngram.NGramFrequency;
import com.hrzafer.prizma.feature.ngram.NGramSize;
import com.hrzafer.prizma.data.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hrzafer
 */
public class Category {

    private String id;
    private NGramData nGramData;
    private int totalWordCount;
    private int totalInstanceCount;
    private boolean bigram;

    public Category(List<Document> documents, NGramModel nGramModel, String id) {
        this.id = id;
        this.nGramData = nGramModel.extractNGramData(documents);
        System.out.println(id + "-" + nGramData.getTypeCount() + "-" + nGramData.getTokenCount());
        totalWordCount = nGramData.getTokenCount();
        totalInstanceCount = documents.size();
        if (nGramModel.getSize() == NGramSize.BIGRAM){
            bigram = true;
        }
    }

    private double LLR(Map.Entry<String, NGramFrequency> nGram ){

            int total = nGramData.getTokenCount();

            int w11= nGram.getValue().getByKlass();
            int w22= 0;
            int w12=0;
            int w21=0;
            String[] bigram = nGram.getKey().split(" ");
            for (Map.Entry<String, NGramFrequency> nGram2 : nGramData.getAllNGrams().entrySet()) {

                if (!nGram2.equals(nGram)){
                    if (nGram2.getKey().startsWith(bigram[0] + " ") && !nGram2.getKey().endsWith(" " + bigram[1])){
                        w21+=nGram2.getValue().getByKlass();
                    }
                    else if (!nGram2.getKey().startsWith(bigram[0] + " ") && nGram2.getKey().endsWith(" " + (bigram[1]))){
                        w12+=nGram2.getValue().getByKlass();
                    }
                    else{
                        w22+=nGram2.getValue().getByKlass();
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
        for (Map.Entry<String, NGramFrequency> nGramMapEntry : nGramData.getAllNGrams().entrySet()) {

            //double tf = nGramMapEntry.getValue().getByKlass() / (double) totalWordCount;
            //double tf = nGramMapEntry.getValue().getByInstance() / totalWordCount;
            double tf = nGramMapEntry.getValue().getByInstance() /(double) totalInstanceCount;
            //double tf = (nGramMapEntry.getValue().getByKlass() / (double) totalWordCount) * (nGramMapEntry.getValue().getByInstance() /(double) totalInstanceCount);


            TfIdf tfIdf = new TfIdf(nGramMapEntry, tf);
            if (bigram){
                tfIdf.setLlr(LLR(nGramMapEntry));
            }
            tfIdfs.add(tfIdf);
        }
        return tfIdfs;
    }

    public boolean contains(String shingle) {
        return nGramData.getAllNGrams().containsKey(shingle);
    }

    public String getId() {
        return id;
    }

}
