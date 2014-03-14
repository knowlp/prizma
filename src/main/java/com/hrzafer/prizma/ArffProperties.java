package com.hrzafer.prizma;

import com.hrzafer.prizma.feature.Feature;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 *
 * @author hrzafer
 */
public class ArffProperties {

    private final String datasetDirectoryPath;
    private final List<Feature> features;
    private final String relationName;
    private final double trainPercentage;
    private boolean unicode;
    private boolean random;

    public ArffProperties(String datasetDirectoryPath, List<Feature> features, String relationName, double trainPercentage, boolean unicode, boolean random) {
        this.datasetDirectoryPath = datasetDirectoryPath;
        this.features = features;
        this.relationName = relationName;
        this.trainPercentage = trainPercentage;
        this.unicode = unicode;
        this.random = random;
    }

    public ArffProperties(String datasetDirectoryPath, List<Feature> features, String relationName, int trainRatio) {
        this.datasetDirectoryPath = datasetDirectoryPath;
        this.features = features;
        this.relationName = relationName;
        this.trainPercentage = trainRatio;
    }

    public ArffProperties(String datasetDirectoryPath, List<Feature> features, int trainRatio) {
        this.datasetDirectoryPath = datasetDirectoryPath;
        this.features = features;
        this.relationName = extractRelationNameFromDatasetDirectoryPath();
        this.trainPercentage = trainRatio;
    }

    public boolean isRandom() {
        return random;
    }

    public boolean isUnicode() {
        return unicode;
    }

    private String extractRelationNameFromDatasetDirectoryPath() {
        int lastIndexOfSlash = datasetDirectoryPath.lastIndexOf("/");
        return datasetDirectoryPath.substring(lastIndexOfSlash + 1);
    }

    public double getTrainPercentage() {
        return trainPercentage;
    }

    public List<Feature> getFeatures() {
        return ImmutableList.copyOf(features);
    }

    public String getDatasetDirectory() {
        return datasetDirectoryPath;
    }

    public String getRelationName() {
        return relationName;
    }
}
