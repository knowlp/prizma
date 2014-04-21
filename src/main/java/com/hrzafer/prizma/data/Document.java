package com.hrzafer.prizma.data;

import com.hrzafer.prizma.data.io.DocumentSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a Text Document. Keeps the raw data fields. Feature vectors are extracted from these fields.
 */

public class Document implements Comparable<Document> {
    private final Map<String, String> data;
    public static final String ID_FIELDNAME = "id";
    public static final String CONTENT_FIELDNAME = "content";
    public static final String CATEGORY_FIELDNAME = "category";
    public static final String SUBCATEGORY_FIELDNAME = "subcategory";
    public static final String PREDICTED_CATEGORY_FIELDNAME = "predicted";

    public Document(DocumentSource source, String actualCategory){
        data = source.getData();
        data.put(CATEGORY_FIELDNAME, actualCategory);
    }

    public Document(DocumentSource source) {
        this.data = source.getData();
        data.put(CATEGORY_FIELDNAME, "?");
    }

    public String getActualCategory() {
        return data.get(CATEGORY_FIELDNAME);
    }

    public String getPredictedCategory() {
        return data.get(PREDICTED_CATEGORY_FIELDNAME);
    }

    public String getFieldData(String fieldname){
        return data.get(fieldname);
    }

    public List<String> getFieldNames(){
        return new ArrayList<>(data.keySet());
    }

    public void setPredictedCategory(String predictedCategory) {
        data.put(PREDICTED_CATEGORY_FIELDNAME, predictedCategory);
    }

    public String[] getData(){
        return data.values().toArray(new String[data.size()]);
    }

    @Override
    public int compareTo(Document other) {
        return this.getActualCategory().compareTo(other.getActualCategory());
    }

    /**
     * Returns if the actual category equals to the predicted category.
     * If either of actual or predicted category is empty, returns false.
     */
    public boolean classifiedCorrectly() {
        if (!getActualCategory().isEmpty() && !getActualCategory().isEmpty()){
            return getActualCategory().equals(getPredictedCategory());
        }
        return false;
    }
}
