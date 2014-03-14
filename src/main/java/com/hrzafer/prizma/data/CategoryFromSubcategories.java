package com.hrzafer.prizma.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hrzafer
 */
public class CategoryFromSubcategories extends Category {

    private final List<CategoryFromInstances> subcategories;

    public CategoryFromSubcategories(List<CategoryFromInstances> subcategories, String name) {
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
        for (CategoryFromInstances subcategory : subcategories) {
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
        for (CategoryFromInstances subset : subcategories) {
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
        for (CategoryFromInstances subset : subcategories) {
            subset.shuffle();
        }
    }

    @Override
    public int getInstanceCount() {
        int count = 0;
        for (CategoryFromInstances subset : subcategories) {
            count += subset.getInstanceCount();
        }
        return count;
    }
}
