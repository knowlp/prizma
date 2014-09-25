package com.hrzafer.prizma.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hrzafer
 */
public class DocumentCategoryFromSubcategories extends DocumentCategory {

    private final List<DocumentCategoryFromDocuments> subcategories;

    public DocumentCategoryFromSubcategories(List<DocumentCategoryFromDocuments> subcategories, String name) {
        super(name);
        this.subcategories = subcategories;
    }

    /**
     * The default train percentage is %100.
     * So if train percentage is not set by setTrainPercentage(double trainPercentage)
     * This method returns all the instances.
     */
    @Override
    public List<Document> getTrainInstances() {
        List<Document> documents = new ArrayList<>();
        for (DocumentCategoryFromDocuments subcategory : subcategories) {
            subcategory.setTrainPercentage(getTrainPercentage());
            documents.addAll(subcategory.getTrainInstances());
        }
        return documents;
    }

    /**
     * The default test percentage is %0.
     * So if train percentage is not set by setTrainPercentage(double trainPercentage)
     * This method returns an empty List
     */
    @Override
    public List<Document> getTestInstances() {
        List<Document> testSamples = new ArrayList<>();
        for (DocumentCategoryFromDocuments subset : subcategories) {
            testSamples.addAll(subset.getTestInstances());
        }
        return testSamples;
    }

    @Override
    public List<Document> getAllInstances() {
        setTrainPercentage(100.0);
        return getTrainInstances();
    }

    @Override
    public void shuffle() {
        for (DocumentCategoryFromDocuments subset : subcategories) {
            subset.shuffle();
        }
    }

    @Override
    public int getDocumentCount() {
        int count = 0;
        for (DocumentCategoryFromDocuments subset : subcategories) {
            count += subset.getDocumentCount();
        }
        return count;
    }
}
