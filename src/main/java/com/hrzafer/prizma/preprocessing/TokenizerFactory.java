package com.hrzafer.prizma.preprocessing;

import com.hrzafer.prizma.gui.Frame;
import com.hrzafer.prizma.util.GUI;
import com.hrzafer.prizma.util.STR;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 24.02.2014
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
public class TokenizerFactory {

    public static ITokenizer create(String name){
        String packagePath = "preprocessing";
        try {
            Class<?> c = Class.forName(packagePath + "." + name);
            Constructor<?> cons = c.getConstructor();
            return (ITokenizer) cons.newInstance();
        } catch (ClassNotFoundException ex) {
            GUI.messageBox("ITokenizer " + STR.addDoubleQuote(name)
                    + " can not be found in the package " + STR.addDoubleQuote(packagePath), "Missing Tokenizer");
            return null;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
