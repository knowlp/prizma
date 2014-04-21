package com.hrzafer.prizma.feature.ngram;

/**
 * Represents a terms frequency values in a corpus.Those values are:
 * - the number of this term's occurrence in the corpus (all documents).
 * - the number of documents that contains this term
 */
public class TermFrequency {
    private int corpusFrequency = 1; // sınıftaki tüm numunelerde toplam geçme sayısı
    private int containingDocumentCount = 0; //kaç farklı numunede geçiyor

    /**
     * @return returns how many times this term occurs in the corpus (all documents).
     */
    public int getCorpusFrequency() {
        return corpusFrequency;
    }

    /**
     * @return returns the number of documents in Corpus that contains the term
     */
    public int getContainingDocumentCount() {
        return containingDocumentCount;
    }

    /**
     * When a new document containing this terms is added to the corpus, this method is called.
     * @return returns the updated number of documents containing this term
     */
    public int incrementContainingDocumentCount(){
        containingDocumentCount++;
        return containingDocumentCount;
    }

    /**
     * Increments this terms corpus frequency by one
     * @return the updated corpus frequency
     */
    public int incrementCorpusFrequency(){
        corpusFrequency++;
        return corpusFrequency;
    }

    @Override
    public String toString() {
        return containingDocumentCount + "/" + corpusFrequency;
    }
}
