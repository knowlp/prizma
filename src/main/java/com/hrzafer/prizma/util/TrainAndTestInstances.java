package com.hrzafer.prizma.util;

import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @author test
 */
public class TrainAndTestInstances {

    private Instances trainInstances;
    private Instances testInstances;

    public TrainAndTestInstances(String arffFilePath, double percent) {

        Instances instances;

        instances = readInstances(arffFilePath);
        int trainSize = (int) Math.round(instances.numInstances() * percent / 100);
        int testSize = instances.numInstances() - trainSize;
        trainInstances = new Instances(instances, 0, trainSize);
        testInstances = new Instances(instances, trainSize, testSize);
    }

    private Instances readInstances(String arffFilePath) {
        BufferedReader reader;
        Instances instances = null;
        try {
            reader = new BufferedReader(new FileReader(arffFilePath));
            instances = new Instances(reader);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        if (instances.classIndex() == -1) {
            instances.setClassIndex(instances.numAttributes() - 1);
        }
        return instances;
    }

    public Instances getTestInstances() {
        return testInstances;
    }

    public Instances getTrainInstances() {
        return trainInstances;
    }
}
