package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.feature.Feature;
import com.hrzafer.prizma.gui.Frame;
import com.hrzafer.prizma.preprocessing.Analyzer;
import com.hrzafer.prizma.util.GUI;
import com.hrzafer.prizma.util.STR;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by hrzafer on 30.12.2014.
 */
public class FeatureFactory {
    public static Feature getInstance(String type, String field, String name, String description, Map<String, String> parameters, Analyzer analyzer) {
        String packagePath = "com.hrzafer.prizma.feature";
        try {
            Class<?> c = Class.forName(packagePath + "." + type);
            Constructor<?> cons = c.getConstructor(String.class, String.class, String.class, String.class, Map.class, Analyzer.class);
            return (Feature) cons.newInstance(type, field, name, description, parameters, analyzer);
        } catch (ClassNotFoundException ex) {
            GUI.messageBox("Prizma Error: Feature class " + STR.addDoubleQuote(type)
                    + " can not be found in the package " + STR.addDoubleQuote(packagePath), "Missing Feature");
            return null;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


}
