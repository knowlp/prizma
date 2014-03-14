package com.hrzafer.prizma.util;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.core.Instances;

/**
 *
 * @author hrzafer
 */
public class WekaHelper {
    
    
    public static String evaluate(String arffFilePath, double percentage, String classifierName){
        TrainAndTestInstances trainAndTestInstances = new TrainAndTestInstances(arffFilePath, percentage);
        Instances train = trainAndTestInstances.getTrainInstances();
        Instances test  = trainAndTestInstances.getTestInstances();
        Classifier classifier = getClassifierInstance(classifierName);
        Evaluation evaluation = evaluate(train, test,  classifier);
        return getConfusionMatrix(evaluation);
    }
    
    public static Classifier getClassifierInstance(String name){
        if (name.equals("NaiveBayes")){
            return new NaiveBayes();
        }
        if (name.equals("NaiveBayesMultinomial")){
            return new NaiveBayesMultinomial();
        }
        else if(name.equals("SMO")){
            return new SMO();
        }
        else if(name.equals("MultilayerPerceptron")){
            return new MultilayerPerceptron();
        }
        else{
            throw new IllegalArgumentException("Invalid classifier:" + name);
        }
    }
    
    public static Evaluation evaluate(Instances train, Instances test, Classifier classifier) {
        try {
            classifier.buildClassifier(train);
            Evaluation eval = new Evaluation(train);            
            eval.evaluateModel(classifier, test);            
            return eval;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
     public static String getConfusionMatrix(Evaluation eval) {
        StringBuilder strBuilder = new StringBuilder(eval.toSummaryString(true));
        try {
            strBuilder.append(eval.toMatrixString());            
            return strBuilder.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
   
}
