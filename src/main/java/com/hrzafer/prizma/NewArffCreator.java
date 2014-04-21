package com.hrzafer.prizma;

import com.hrzafer.prizma.feature.Feature;
import com.hrzafer.prizma.feature.value.DocumentVectors;
import com.hrzafer.prizma.feature.value.FeatureValue;
import com.hrzafer.prizma.util.STR;

import java.util.Iterator;
import java.util.List;

/**
 *
 */

public class NewArffCreator {

    public static String create(DocumentVectors vectors) {
        StringBuilder sb = new StringBuilder();
        sb.append(getHeader(vectors));
        sb.append(getWekaClassNames(vectors.getCategoryNames()));
        String data = getInctanceDataLines(vectors);
        sb.append(data);
        return sb.toString();
    }

    private static String getHeader(DocumentVectors vectors) {
        String relationName = vectors.getName();
        List<Feature> features = vectors.getFeatures();
        String relationDeclaration = getRelationDeclaration(relationName);
        StringBuilder header = new StringBuilder().append(relationDeclaration);
        for (Feature feature : features) {
            if(!feature.getType().equals("DocumentId"))
                header.append(feature.getDeclarationForArff());
        }
        return header.toString();
    }

    private static String getRelationDeclaration(String relation) {
        return "@relation " + relation + "\n\n";
    }

    private static String getWekaClassNames(String[] names) {
        StringBuilder sb = new StringBuilder("@attribute class {");
        String commaSeperatedNames = STR.join(names, ',');
        sb.append(commaSeperatedNames);
        sb.append("}\n\n");
        return sb.toString();
    }

    private static String getInctanceDataLines(DocumentVectors relation) {
        StringBuilder dataLines = new StringBuilder("@data\n\n");
        List<List<FeatureValue>> vectors = relation.getVectors();
        for (List<FeatureValue> vector : vectors) {
            dataLines.append(getInstanceDataLine(vector)).append("\n");

        }
        return dataLines.toString();
    }

    public static String getInstanceDataLine(List<FeatureValue> values) {
        StringBuilder dataLine = new StringBuilder("");
        StringBuilder comment = new StringBuilder("");
        Iterator<FeatureValue> it = values.iterator();
        String delim = "";
        while (it.hasNext()){
            String value = it.next().toString();
            if (value.startsWith("%")){
                dataLine.append(delim).append(value);
                delim = " ";
                it.remove();
            }
        }
        dataLine.append("\n");
        dataLine.append(STR.join(values, ','));
        return dataLine.toString();
    }



}
