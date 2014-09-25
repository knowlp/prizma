package com.hrzafer.prizma;

import com.hrzafer.prizma.data.*;
import com.hrzafer.prizma.data.io.*;
import com.hrzafer.prizma.feature.Feature;
import com.hrzafer.prizma.feature.value.FeatureValue;
import com.hrzafer.prizma.feature.NGramTerms;
import com.hrzafer.prizma.feature.UnigramTerms;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.preprocessing.*;
import com.hrzafer.prizma.util.IO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class creates the ARFF file
 *
 * @author hrzafer
 */
public class OldArffCreator {

//    public static final String HEADER_COMMENT = "% This file is created by the Prizma 1.0.\n"
//            + "% Prizma is a Turkish Text Classification Environment for Weka \n"
//            + "% and developed by Harun Reşit Zafer with Fatih Universtiy Natural Language Processing Group.\n"
//            + "% http://nlp.ceng.fatih.edu.tr/blog/\n"
//            + "% hrzafer@fatih.edu.tr\n"
//            + "% www.hrzafer.com\n\n";
    private static ArffProperties _properties;

    public static void create(ArffProperties properties, String filename) {
        _properties = properties;
        //_filename = filename;
        create(filename);
    }

//    public static void createForEachKlass(ArffProperties properties, String filename) {
//        _properties = properties;
//        Dataset dataset = getDataset();
//        List<String> klassNames = dataset.getKlassNames();
//        for (String klassName : klassNames) {
//            Dataset copy = dataset.oneToMany(klassName, "other");
//            _properties = getPropertyForClass(getFeatureForClass(klassName));
//            create(copy, filename.substring(0, filename.lastIndexOf("/") +1 ) + klassName + ".arff");
//        }
//    }

    private static ArffProperties getPropertyForClass(Feature feature){
        List<Feature> features = new ArrayList<>();
        features.add(feature);
        ArffProperties properties = new ArffProperties(_properties.getDatasetDirectory(),
                features,
                _properties.getRelationName(),
                _properties.getTrainPercentage(), true, false );
        return  properties;
    }

    private static Feature getFeatureForClass(String name){
        List <IFilter> filters = new ArrayList<>();
        filters.add(new TooShortTokenFilter());
        filters.add(new TooLongTokenFilter());
        filters.add(new ToLowerCaseConverter());
        filters.add(new StemmerFilter());
        filters.add(new NumberRemover());
        filters.add(new StopWordRemover());
        Analyzer analyzer = new Analyzer(new StandardTokenizer(), filters);

        Map<String, String> params= new HashMap<>();
        params.put("field", "content");
        params.put("threshold", "2");
        params.put("binary", "true");
        params.put("lexiconEncoded", "false");
        params.put("lexiconFilePath", "experiment/" + name + "Lexicon");
        return new UnigramTerms("UnigramTerms", "bla", 1, "", params, analyzer);
    }

    private static void create(String filename) {
        Dataset dataset = getDataset();
        create(dataset, filename);
    }

    private static Dataset getDataset() {
        DatasetReader reader;
        if (_properties.getDatasetDirectory().endsWith(".csv")){
            reader = new CSVDatasetReader(_properties.getDatasetDirectory());
        }
        else {
            reader = new DirectoryDatasetReader(_properties.getDatasetDirectory());
        }
        Dataset dataset = reader.read();
        if (_properties.isRandom()) {
            dataset.shuffle();
        }
        return dataset;
    }

    private static void create(Dataset dataset, String filename) {
        StringBuilder sb = new StringBuilder();
        for (Feature feature : _properties.getFeatures()) {
            if (feature.getType().endsWith("gramTerms")) {
                ((NGramTerms) feature).buildTermDictionary(dataset.getAllDocuments());
            }
        }
        sb.append(getHeader());
        sb.append(getWekaClassNames(dataset));
        String data = getData(dataset);
        //sb.append(getArffCommentLineForTrainPercentage(dataset));
        sb.append(data);
        IO.write(filename, sb.toString());
    }

    private static String getHeader() {
        String relationName = _properties.getRelationName();
        List<Feature> features = _properties.getFeatures();
        String relationDeclaration = getRelationDeclaration(relationName);
        StringBuilder header = new StringBuilder().append(relationDeclaration);
        for (Feature feature : features) {
            if(!feature.getType().equals("DocumentId"))
            header.append(feature.getDeclarationForArff());
        }
        return header.toString();
    }

    private static String getHeader(String relationName, List<Feature> features) {
        String relationDeclaration = getRelationDeclaration(relationName);
        StringBuilder header = new StringBuilder().append(relationDeclaration);
        for (Feature feature : features) {

            header.append(feature.getDeclarationForArff());
        }
        return header.toString();
    }

    private static String getRelationDeclaration(String relation) {
        return "@relation " + relation + "\n\n";
    }

    private static String getWekaClassNames(Dataset dataset) {
        List<String> names = dataset.getKlassNames();
        StringBuilder sb = new StringBuilder("@attribute class {");
        for (String name : names) {
            sb.append(name).append(",");
        }
        removeCommaAtTheEnd(sb);
        sb.append("}\n\n");

        return sb.toString();
    }

    private static void removeCommaAtTheEnd(StringBuilder sb) {
        sb.deleteCharAt(sb.length() - 1);
    }

    private static String getData(Dataset dataset) {
        List<Document> samples = dataset.getPercentageSplittedInstances(_properties.getTrainPercentage());
        StringBuilder data = new StringBuilder("@data\n\n");
        String dataLines = getInctanceDataLines(samples);
        data.append(dataLines);
        return data.toString();
    }

    private static String getInctanceDataLines(List<Document> documents) {
        StringBuilder dataLines = new StringBuilder();
        int i=0;
        for (Document document : documents) {
            String dataLine = getInstanceDataLine(document);
            dataLines.append(dataLine);
            if (i%200==0){
                System.out.println(i + " documents' vector is extracted");
            }
            i++;
        }
        return dataLines.toString();
    }

    private static String getInstanceDataLine(Document document) {
        List<Feature> features = _properties.getFeatures();
        return getInstanceDataLine(features, document);
    }

    public static String getInstanceDataLine(List<Feature> features, Document document) {
        List<FeatureValue> values = FeatureExtractor.extract(document, features);
        StringBuilder dataLine = new StringBuilder("");
        StringBuilder comment = new StringBuilder("");
        for (FeatureValue value : values) {
            if (value.toString().startsWith("%")){
                comment.append(value);
            }
            else {
                dataLine.append(value).append(",");
            }
        }
        dataLine.insert(0, comment.toString()+ "\n").append(document.getActualCategory()).append("\n");
        return dataLine.toString();
    }

//    private static String getArffCommentLineForTrainPercentage(Dataset dataset) {
//
//        int instanceCount = dataset.getDocumentCount();
//        double trainPercentage = _properties.getTrainPercentage();
//        double testPercentage = 100 - trainPercentage;
//        int _trainInstanceCount = dataset.getTrainInstanceCount();
//        int _testInstanceCount = dataset.getDocumentCount() - _trainInstanceCount;
//
//        MessageFormat message = new MessageFormat("% The first {0} instances (%{1}) is used for training\n"
//                + "% and the rest {2} instances (%{3}) is used as test data.\n"
//                + "% Total instance count is {4}\n"
//                + "% In Weka (to getData correct results), select \"percentage split %{1}\" and \"preserve order for % split\" options\n\n");
//        Object[] args = {_trainInstanceCount, trainPercentage, _testInstanceCount, testPercentage, instanceCount};
//
//        return message.format(args);
//    }

    public static String createArffForSingleInstance(List<Feature> features, Document document) {
        StringBuilder sb = new StringBuilder();
        sb.append(getHeader("oylesine", features));
        sb.append("@attribute class {").append(document.getActualCategory()).append("}\n\n@data\n\n");
        sb.append(getInstanceDataLine(features, document));
        return sb.toString();
    }

    public static String createArffForDocuments(List<Feature> features, List<Document> documents){
        StringBuilder sb = new StringBuilder();
        sb.append(getHeader("oylesine", features));
        sb.append("@attribute class {").append("null").append("}\n\n@data\n\n");
        for (Document document : documents) {
            sb.append(getInstanceDataLine(features, document));
        }
        return sb.toString();
    }

    public static String createArffForSingleSample(List<Feature> features, String data) {
        Document sample = new Document(new DocumentFromString(data), "unknown");
        StringBuilder sb = new StringBuilder();
        sb.append(getHeader("oylesine", features));
        sb.append("@attribute class {").append(sample.getActualCategory()).append("}\n\n@data\n\n");
        sb.append(getInstanceDataLine(features, sample));
        return sb.toString();
    }


}
