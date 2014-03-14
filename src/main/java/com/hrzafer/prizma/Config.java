package com.hrzafer.prizma;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Config {
    private static Properties properties;
    private static String externalProperties = "experiment/prizma.properties";
    private static String embeddedProperties = "prizma.properties";

    private Config() {
    }

    static {
        properties = new Properties();
        if (!loadExternalProperties()) {
            loadEmbeddedProperties();
        }
    }

    private static boolean loadExternalProperties() {
        File file = new File(externalProperties);
        try {
            properties.load(new FileInputStream(file));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static void loadEmbeddedProperties() {
        InputStream is = Config.class.getClassLoader().getResourceAsStream(embeddedProperties);
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Prizma: Embedded properties file can not be loaded");
        }
    }

    /**
     * Uses default properties embedded in to the jar even if the external properties file exists.
     */
    public static void useEmbedded() {
        properties = new Properties();
        loadEmbeddedProperties();
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static int getAsInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static void set(String key, String value) {
        properties.setProperty(key, value);
    }

    public static void save() {
        try {
            properties.store(new FileOutputStream(externalProperties), null);
        } catch (IOException ex) {
        }
    }

//    public static List<Category> getCategories(){
//        List<Category> categories = new ArrayList<>();
//        for (Map.Entry<Object, Object> propery : properties.entrySet()) {
//            String key = (String)propery.getKey();
//            String value = (String) propery.getValue();
//            if (key.startsWith("cat") && value.equalsIgnoreCase("true")){
//                categories.add(Category.valueOf(key.substring(3)));
//            }
//        }
//        return categories;
//    }




}
