package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.data.Dataset;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.util.IO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

/**
 * Created by hrzafer on 06.01.2015.
 */
public class JSONDatasetReader extends DatasetReader {
    private String path;

    public JSONDatasetReader(String path) {
        this.path = path;

    }

    @Override
    public Dataset read() {
        JSONParser parser = new JSONParser();

        Object obj;
        String json = IO.read(path);

        try {
            obj = parser.parse(json);

            JSONObject jsonObject = (JSONObject) obj;

//            // loop array
            JSONArray msg = (JSONArray) jsonObject.get("documents");
            List<Document> documents = new ArrayList<>();

            for (int i = 0; i < msg.size(); i++) {
                JSONObject o = (JSONObject) msg.get(i);
                Iterator iterator = o.keySet().iterator();
                Map<String, String> map = new HashMap<>();
                while (iterator.hasNext()){
                    String key = (String) iterator.next();
                    map.put(key, (String) o.get(key));
                }

                documents.add(new Document(new DocumentFromMap(map)));
            }


            return documentsToDataset(documents);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }
}
