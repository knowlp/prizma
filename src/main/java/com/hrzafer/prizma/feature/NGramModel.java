package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.feature.ngram.NGramData;
import com.hrzafer.prizma.feature.ngram.NGramSize;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.preprocessing.Analyzer;
import com.hrzafer.prizma.util.Timer;

import java.util.List;
import java.util.Map;

public abstract class NGramModel extends Feature {

    private static final String PARAM_TRESHOLD = "threshold";
    private static final String PARAM_BINARY = "binary";
    private static final String PARAM_LEXICONFILEPATH = "lexiconFilePath";
    private static final String PARAM_LEXICONENCODED = "lexiconEncoded";
    protected NGramData nGramData;
    private boolean binary;
    private int treshold;
    private String lexiconFilePath;
    private boolean lexiconEncoded;
    protected NGramSize size;

    protected NGramModel(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, name, weight, description, parameters, analyzer);
        parseAndSetParameters();
    }

    public NGramSize getSize() {
        return size;
    }

    private void parseAndSetParameters() {
        parseAndSetIsBinary();
        parseAndSetLexiconFilePath();
        parseAndSetTreshold();
        parseAndSetIsLexiconEncoded();
    }

    private void parseAndSetLexiconFilePath() {
        String value = parameters.get(PARAM_LEXICONFILEPATH);
        if (value != null) {
            lexiconFilePath = value;
            System.out.println(lexiconFilePath);
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

    private void parseAndSetTreshold() {
        String value = parameters.get(PARAM_TRESHOLD);
        treshold = Integer.parseInt(value);
    }

    public NGramData extractNGramData(List<Document> documents) {
        NGramData ngd = getNewNGramData();
        buildNGramData(documents, ngd);
        return ngd;
    }

    protected final NGramData getNewNGramData() {
        return new NGramData(size);
    }

    public void buildNGramData(List<Document> documents) {
        Timer timer = new Timer();
        timer.start();
        nGramData.clear();
        if (!lexiconFilePath.isEmpty()) {
            buildNGramData(lexiconFilePath);
            return;
        }
        buildNGramData(documents, nGramData);
        timer.stop();
        System.out.println("NGramModel is built in " + timer.getElapsedSeconds() + " sec");
    }

    private void buildNGramData(List<Document> documents, NGramData ngd) {
        for (Document document : documents) {
            String data = getFieldData(document);
            List<String> tokens = analyzer.analyze(data);
            ngd.addSequence(tokens);
        }
        //ngd.eliminateByFrequency(treshold);
        ngd.eliminateByInstanceFrequency(treshold);
        ngd.sort();
        System.out.println("NGram is built from " + documents.size() + " documents");
    }

    private void buildNGramData(String lexiconfile) {
        nGramData.fromFile(lexiconfile, lexiconEncoded);
        System.out.println("NGram is built from lexicon with " + nGramData.getTypeCount() + " features");
    }

    @Override
    public String extract(Document document) {
        String data = getFieldData(document);
        List<String> tokens = analyzer.analyze(data);

        int[] nGramVector;
        if (binary) {
            nGramVector = nGramData.getBinaryNGramVector(tokens);
        } else {
            nGramVector = nGramData.getNGramVector(tokens);
        }
        applyWeight(nGramVector);
        return vectorLineToString(nGramVector);
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
        List<String> nGramNames = nGramData.getNGramNames();
        //Collections.sort(nGramNames);
        StringBuilder sb = new StringBuilder();
        for (String nGramName : nGramNames) {
            sb.append("@attribute ").
                    append(nGramName).append("_").append(parameters.get("field")).append(" ").
                    append("numeric").append("\n");
        }
        return sb.toString();
    }

    public NGramData getNGramData() {
        return nGramData;
    }
}
