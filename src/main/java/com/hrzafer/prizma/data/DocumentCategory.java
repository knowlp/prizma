package com.hrzafer.prizma.data;

import java.util.List;

/**
 * Represents a set of instances labeled with the same category.
 * Note: To prevent confusion the name "Category" is prefferred over the name "Class" for this class.
 * @author hrzafer
 */
public abstract class DocumentCategory {

    private final String name;
    private double trainPercentage;

    public DocumentCategory(String name) {
        this.name = name;
        this.trainPercentage = 100.0;
    }

    public String getName() {
        return name;
    }

    public void setTrainPercentage(double trainPercentage) {
        this.trainPercentage = trainPercentage;
    }

    public double getTrainPercentage() {
        return trainPercentage;
    }

    @Override
    public String toString() {
        return "Cat: " + getName() + " with " + getInstanceCount() + " instances.";
    }


    /**
     * Shuffles the instances in the category
     */
    abstract public void shuffle();

    abstract public List<Document> getTrainInstances();

    abstract public List<Document> getTestInstances();

    abstract public List<Document> getAllInstances();

    abstract public int getInstanceCount();

    public int getTestInstanceCount() {
        return getInstanceCount() - getTrainInstanceCount();
    }

    public int getTrainInstanceCount() {
        int trainInstanceCount = (int) Math.round((getInstanceCount() * getTrainPercentage()) / 100);
        return trainInstanceCount;
    }
}
