package com.hrzafer.prizma.util;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.InputStream;

public class WekaNaiveBayesClassifier {
    private Classifier NB;

    public WekaNaiveBayesClassifier(Classifier NB) {
        this.NB = NB;
    }

    public double classify(String instanceArff) {
        try {
            weka.core.Instance wekaInstance = toInstance(instanceArff);
            return NB.classifyInstance(wekaInstance);
        } catch (Exception e) {
            throw new RuntimeException("Document couldn't be classified!!!", e);
        }
    }

    public double predict(String instanceArff) {
        try {
            weka.core.Instance wekaInstance = toInstance(instanceArff);
            double[] predictions = NB.distributionForInstance(wekaInstance);
            return predictions[1];
        } catch (Exception e) {
            throw new RuntimeException("Document couldn't be predicted!!!", e);
        }
    }

    private weka.core.Instance toInstance(String arff) {
        InputStream is = IO.toInputStream(arff);
        try {
            Instances instances = new ConverterUtils.DataSource(is).getDataSet();
            if (instances.classIndex() == -1) {
                instances.setClassIndex(instances.numAttributes() - 1);
            }
            return instances.firstInstance();
        } catch (Exception e) {
            throw new RuntimeException("Content could not be converted to Document", e);
        }
    }


}
