/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.prizma.data;

import com.hrzafer.prizma.Globals;
import com.hrzafer.prizma.Resources;
import com.hrzafer.prizma.feature.Feature;
import com.hrzafer.prizma.preprocessing.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author hrzafer
 */
public class FeatureReader {


    public static List<Feature> read(String xmlFilename) {
        try {
            File fXmlFile = new File(xmlFilename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();

            //InputStream is = FeatureReader.class.getClassLoader().getResourceAsStream(xmlFilename);
            InputStream is = Resources.get(xmlFilename);
            Document doc = dBuilder.parse(is);

            //DocumentSource doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("features");
            if (list.getLength() != 1) {
                throw new IllegalArgumentException("Features dosyasında birden fazla kök node var!\n ");
            }
            Element featuresElement = (Element) list.item(0);
            return readFeatures(featuresElement);

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(FeatureReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private static List<Feature> readFeatures(Element featuresElement) {
        NodeList featureNodes = featuresElement.getElementsByTagName("feature");
        List<Feature> features = new ArrayList<>();
        for (int temp = 0; temp < featureNodes.getLength(); temp++) {
            Element parametersElement = (Element) featureNodes.item(temp);
            Feature a = readFeature(parametersElement);
            features.add(a);
        }
        return features;
    }

    private static Feature readFeature(Element featureElement) {
        Element descriptionElement = getSingleElementByName(featureElement, "description");
        Element parametersElement = getSingleElementByName(featureElement, "parameters");
        Element analyzerElement = getSingleElementByName(featureElement, "analyzer");
        String type = featureElement.getAttribute("type");
        String name = featureElement.getAttribute("name");
        int weight = 1 ;
        try {
            weight = Integer.parseInt(featureElement.getAttribute("weight"));
        }catch (Exception e){ }

        String description = descriptionElement.getTextContent();
        Map<String, String> parameters = readParameters(parametersElement);
        Analyzer analyzer;
        if (analyzerElement.hasAttribute("name")){
            analyzer = Globals.getAnalyzer(analyzerElement.getAttribute("name"));
        }
        else {
            analyzer = readAnalyzer(analyzerElement);
        }

        return Feature.getInstance(type, name, weight, description, parameters, analyzer);
    }

    private static Element getSingleElementByName(Element parent, String name) {
        NodeList nList = parent.getElementsByTagName(name);
        if (nList.getLength() < 1) {
            return null;
        }
        if (nList.getLength() == 1) {
            return (Element) nList.item(0);
        }
        throw new IllegalArgumentException("Too many " + name + " elements in Element " + parent.getTagName());
    }

    private static Analyzer readAnalyzer(Element analyzerElement) {
        NodeList normalizerNodes = analyzerElement.getElementsByTagName("normalizer");
        NodeList tokenizerNodes = analyzerElement.getElementsByTagName("tokenizer");
        NodeList filterNodes = analyzerElement.getElementsByTagName("filter");
        List<INormalizer> normalizers = readNormalizers(normalizerNodes);
        ITokenizer tokenizer = readTokenizer(tokenizerNodes);
        List<IFilter> filters = readFilters(filterNodes);
        return new Analyzer(normalizers, tokenizer, filters);

    }

    private static List<INormalizer> readNormalizers(NodeList normalizerNodes){
        List<INormalizer> list = new ArrayList<>();
        for (int i = 0; i < normalizerNodes.getLength(); i++) {
            Element normalizerElement = (Element) normalizerNodes.item(i);
            INormalizer n = readNormalizer(normalizerElement);
            list.add(n);
        }
        return list;
    }

    private static INormalizer readNormalizer(Element normalizerElement){
        String name = normalizerElement.getAttribute("name");
        return NormalizerFactory.create(name);
    }


    private static ITokenizer readTokenizer(NodeList tokenizerNodes){
        if(tokenizerNodes.getLength() > 0){
            Element tokenizerElement = (Element) tokenizerNodes.item(0);
            String name = tokenizerElement.getAttribute("name");
            return TokenizerFactory.create(name);
        }
        return null;
    }

    private static List<IFilter> readFilters(NodeList filterNodes){
        List<IFilter> list = new ArrayList<>();
        for (int i = 0; i < filterNodes.getLength(); i++) {
            Element preprocessorElement = (Element) filterNodes.item(i);
            IFilter p = readFilter(preprocessorElement);
            list.add(p);
        }
        return list;
    }

    private static Filter readFilter(Element preprocessorElement) {
        String preprocessorName = preprocessorElement.getAttribute("name");
        return FilterFactory.create(preprocessorName);

    }

    private static Map<String, String> readParameters(Element parametersElement) {
        NodeList parameterNodes = parametersElement.getElementsByTagName("parameter");
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < parameterNodes.getLength(); i++) {
            Element parameterElement = (Element) parameterNodes.item(i);
            Entry<String, String> parameter = readParameter(parameterElement);
            map.put(parameter.getKey(), parameter.getValue());
        }
        return map;
    }

    private static Entry<String, String> readParameter(Element element) {
        String name = element.getAttribute("name");
        String value = element.getAttribute("value");
        return new SimpleEntry<>(name, value);
    }

}
