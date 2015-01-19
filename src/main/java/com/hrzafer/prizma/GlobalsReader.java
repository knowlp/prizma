/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.prizma;

import com.hrzafer.prizma.preprocessing.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hrzafer
 */
public class GlobalsReader {

    public static Map<String, Analyzer> read(String xmlFilename) {
        try {
            Map<String, Analyzer> analyzers = new HashMap<>();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();

            //InputStream is = FeatureReader.class.getClassLoader().getResourceAsStream(xmlFilename);
            InputStream is = Resources.get(xmlFilename);
            Document doc = dBuilder.parse(is);

            //DocumentSource doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList root = doc.getElementsByTagName("globals");
            if (root.getLength() != 1) {
                throw new IllegalArgumentException("Features dosyasında birden fazla kök node var!\n ");
            }

            Element rootElement = (Element) root.item(0);
            NodeList analyzerNodes = rootElement.getElementsByTagName("analyzer");
            for (int i=0; i< analyzerNodes.getLength(); i++){
                Element analyzerElement= (Element) analyzerNodes.item(0);
                String name = analyzerElement.getAttribute("name");
                Analyzer analyzer =readAnalyzer(analyzerElement);
                analyzers.put(name, analyzer);
            }

            return analyzers;

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(GlobalsReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static Analyzer readAnalyzer(Element analyzerElement) {
        NodeList normalizerNodes = analyzerElement.getElementsByTagName("normalizer");
        NodeList tokenizerNodes = analyzerElement.getElementsByTagName("tokenizer");
        NodeList filterNodes = analyzerElement.getElementsByTagName("filter");
        List<ICharFilter> normalizers = readNormalizers(normalizerNodes);
        ITokenizer tokenizer = readTokenizer(tokenizerNodes);
        List<IFilter> filters = readFilters(filterNodes);
        return new Analyzer(normalizers, tokenizer, filters);

    }

    private static List<ICharFilter> readNormalizers(NodeList normalizerNodes){
        List<ICharFilter> list = new ArrayList<>();
        for (int i = 0; i < normalizerNodes.getLength(); i++) {
            Element normalizerElement = (Element) normalizerNodes.item(i);
            ICharFilter n = readNormalizer(normalizerElement);
            list.add(n);
        }
        return list;
    }

    private static ICharFilter readNormalizer(Element normalizerElement){
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
}
