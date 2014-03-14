//http://www.mkyong.com/java/json-simple-example-read-and-write-json/
package com.hrzafer.prizma.util;

import au.com.bytecode.opencsv.CSVWriter;
import com.hrzafer.prizma.Config;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hrzafer
 */
public class JsonToCVSParser {

    //Bir klasördeki her bir json dosyası bir query'ye ait olacak.
    //Bunlardan bir data oluşturacağız. Sonra da bunları klasörlere atacağız.
    //
    // Burada hatalı olanların hangisi olduğunu nasıl tespit edebiliriz?

    /**
     * Bir damla.solr json dosyalarındaki dökümanları satır satır CSV dosyasına yazar
     *
     * @param args
     */
    public static void main(String[] args) {
        File dir = new File("data/solr_json/iyi_kotu");
        File[] jsons = dir.listFiles();
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"id", "title", "content", "url", "classname"});
        for (File json : jsons) {
            readContent(rows, json);
        }

        //readContent(rows, new File("data/solr_json/testAdult.json"));
        createCvsDatasetFile(rows, "data/iyi_kotu.csv");
    }

    /**
     * Bir Json dosyasındaki page nesnelerini map'e atar.
     * Böylece ikinci bir json dosyasında aynı page varsa otomatik olarak elenmiş olur.
     */
    public static void readContent(List<String[]> rows, File jsonFile) {
        JSONObject jsonObject = parse(jsonFile);
        JSONArray docs = (JSONArray) ((JSONObject) jsonObject.get("response")).get("docs");
        String filename = jsonFile.getName();
        String classname = filename.substring(0, filename.lastIndexOf('.'));
        putPages(rows, docs, classname);
    }

    private static JSONObject parse(File jsonFile) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(jsonFile));
            return (JSONObject) obj;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }



    private static void putPages(List<String[]> rows, JSONArray array, String classname) {

        for (Object o : array) {
            JSONObject page = (JSONObject) o;
            String id = page.get("id").toString();
            String title = (page.get("title")==null)? "": page.get("title").toString();
            String content = page.get("content").toString().replaceAll("\\\"", "");
            int i=0;
            if (content.length() > Config.getAsInt("maxSampleLength")){
                //content = STR.getSubString(content, Config.getAsInt("maxSampleLength"), Config.getAsInt("subSamplePartCount"));
                i++;
                System.out.println(i + " docs were too long so far");
            }

            String url = page.get("url").toString();
            if (id==null || title==null || content==null || url==null || classname==null ){
                System.out.println(id);
            }

//            long catAdult = 0;
//            if (page.get("catAdult") != null){
//                catAdult = (Long) page.get("catAdult");
//            }
//            else {
//                System.out.println(id);
//            } Long.toString(catAdult)



            String[] row = new String[]{id, title, title + " " + content, url, classname};
            rows.add(row);
        }
    }

    public static void createCvsDatasetFile(List<String[]> rows, String filename) {
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));
            writer.writeAll(rows);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static String jsonObjectToString(Map.Entry<String, JSONObject> entry) {
        JSONObject page = entry.getValue();
        String url = page.get("url").toString();
        String contentStr = (String) page.get("content");
        String title = (String) page.get("title");
        return url + "\n" + title + "\n" + contentStr;
    }

    public static void createDataset(Map<String, JSONObject> map, String dir) {
        int i = 1;
        for (Map.Entry<String, JSONObject> entry : map.entrySet()) {
            String filename = dir + "/" + Integer.toString(i) + ".txt";
            String content = jsonObjectToString(entry);
            IO.write(filename, content);
            i++;
        }
    }


}
