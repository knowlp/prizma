package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.feature.ngram.NGramSize;
import com.hrzafer.prizma.feature.ngram.TermDictionary;
import com.hrzafer.prizma.feature.value.FeatureValue;
import com.hrzafer.prizma.preprocessing.Analyzer;

import java.util.List;
import java.util.Map;

public abstract class NGramTerms extends Feature {

    private static final String DOC_TRESHOLD = "documentThreshold";
    private static final String FREQ_TRESHOLD = "frequencyThreshold";
    private static final String PARAM_BINARY = "binary";
    private static final String PARAM_LEXICONFILEPATH = "lexiconFilePath";
    private static final String PARAM_LEXICONENCODED = "lexiconEncoded";
    protected TermDictionary termDictionary;
    private boolean binary;
    private int documentThreshold = 0;
    private int frequencyThreshold = 0;
    private String lexiconFilePath;
    private boolean lexiconEncoded;
    private final NGramSize size;

    protected NGramTerms(String type,
                         String name,
                         int weight,
                         String description,
                         Map<String, String> parameters,
                         Analyzer analyzer,
                         NGramSize size) {

        super(type, name, weight, description, parameters, analyzer);

        parseAndSetParameters();
        this.size = size;
        if (!lexiconFilePath.isEmpty()) {
            buildTermDictionaryFromLexicon(lexiconFilePath);
        }
    }

    protected NGramTerms(Map<String, String> parameters, Analyzer analyzer, NGramSize size) {
        super("ngramTerms", parameters, analyzer);
        this.size = size;
        parseAndSetParameters();
    }

    @Override
    public List<FeatureValue> extract(String data) {
        List<String> tokens = analyzer.analyze(data);
        //applyWeight(nGramVector);
        //todo:appyWeight optional olmalı, weight belirtilmemişse boş geçmeli
        return termDictionary.getTermsVector(tokens, binary);
    }

    public void buildTermDictionary(List<Document> documents) {
        if (!lexiconFilePath.isEmpty()) {
            return;
        }
        termDictionary = buildNewTermDictionary(documents);
    }

    public TermDictionary buildNewTermDictionary(List<Document> documents) {
        TermDictionary td = new TermDictionary(size);
        for (Document document : documents) {
            String data = getFieldData(document);
            List<String> tokens = analyzer.analyze(data);
            td.addDocument(tokens);
        }
        if (documentThreshold > 0) {
            td.eliminateByContainingDocumentCount(documentThreshold);
        }

        if (frequencyThreshold > 0) {
            td.eliminateByFrequency(frequencyThreshold);
        }
        System.out.println(size + " dictionary is built from " + documents.size() + " documents with " + td.getTermCount() + " terms");
        return td;
    }

    private void buildTermDictionaryFromLexicon(String lexiconfile) {
        termDictionary = new TermDictionary(size);
        termDictionary.fromFile(lexiconfile, lexiconEncoded);//todo: lexicon okuma işi başka yerde yapılmalı
        System.out.println(size + " dictionary is built with " + termDictionary.getTermCount() + " terms");
    }

    public NGramSize getSize() {
        return size;
    }

    private void parseAndSetParameters() {
        parseAndSetIsBinary();
        parseAndSetLexiconFilePath();
        parseAndSetIsLexiconEncoded();
        parseAndSetDocumentTreshold();
        parseAndSetFrequencyTreshold();
    }

    private void parseAndSetLexiconFilePath() {
        String value = parameters.get(PARAM_LEXICONFILEPATH);
        if (value != null) {
            lexiconFilePath = value;
        } else {
            lexiconFilePath = "";
        }
    }

    private void parseAndSetIsBinary() {
        String value = parameters.get(PARAM_BINARY);
        binary = Boolean.parseBoolean(value);
    }

    private void parseAndSetIsLexiconEncoded() {
        String value = parameters.get(PARAM_LEXICONENCODED);
        lexiconEncoded = Boolean.parseBoolean(value);
    }

    private void parseAndSetDocumentTreshold() {
        String value = parameters.get(DOC_TRESHOLD);
        if (value != null)
            documentThreshold = Integer.parseInt(value);
    }

    private void parseAndSetFrequencyTreshold() {
        String value = parameters.get(FREQ_TRESHOLD);
        if (value != null)
            frequencyThreshold = Integer.parseInt(value);
    }


    private void applyWeight(int[] nGramVector) {
        for (int i = 0; i < nGramVector.length; i++) {
            nGramVector[i] *= weight;
        }
    }

    private String vectorLineToString(int[] nGramVector) {
        StringBuilder sb = new StringBuilder();
        for (int i : nGramVector) {
            sb.append(i).append(",");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    @Override
    public String getDeclarationForArff() {
        StringBuilder sb = new StringBuilder();
        for (String nGramName : termDictionary.getTerms()) {
            sb.append("@attribute ").
                    append(nGramName.replaceAll(" ", "_")).append("_").append(parameters.get("field")).append(" ").
                    append("numeric").append("\n");
        }
        return sb.toString();
    }
}
