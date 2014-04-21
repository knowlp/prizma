package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.Resources;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.feature.value.FeatureValue;
import com.hrzafer.prizma.feature.value.FeatureValueFactory;
import com.hrzafer.prizma.preprocessing.Analyzer;
import com.hrzafer.prizma.util.STR;

import java.util.*;

/**
 *
 * @author hrzafer
 */
public class StopwordRatio extends Feature {

    public StopwordRatio(String type, String name, int weight, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, name, weight, description, parameters, analyzer);
    }

    private static Set<String> stops;
    static {
        List<String> list = Resources.getLines("stopwords.txt");
        stops = new HashSet<>(list);
    }

    @Override
    public List<FeatureValue> extract(String data) {
        String source = data.replaceAll("[\\*\\-\\:\\\"\\.,\\(\\);?!']", "");
        Scanner s = new Scanner(source);
        int stopwordCount = 0;
        int wordCount = 0;
        while (s.hasNext()) {
            String token = s.next();
            if (isStopWord(token)) {
                stopwordCount++;
            }
            wordCount++;
        }
        s.close();
        double ratio = (double) stopwordCount / wordCount;
        FeatureValue value =  FeatureValueFactory.create(ratio);
        return unitList(value);
    }

    public static boolean isStopWord(String token) {
        return stops.contains(token);
    }

    public static boolean isStopWordAsciify(String token) {
        for (String stopword : stopwords) {
            if (token.equalsIgnoreCase(stopword) || token.equalsIgnoreCase(STR.toNonTurkish(stopword))) {
                return true;
            }
        }
        return false;
    }

    public static String[] stopwords = {
        "acaba",
        "altı",
        "ama",
        "ancak",
        "artık",
        "asla",
        "aslında",
        "az",
        "bana",
        "bazen",
        "bazı",
        "bazıları",
        "bazısı",
        "belki",
        "ben",
        "beni",
        "benim",
        "beş",
        "bile",
        "bir",
        "birçoğu",
        "birçok",
        "birçokları",
        "biri",
        "birisi",
        "birkaç",
        "birkaçı",
        "birşey",
        "birşeyi",
        "biz",
        "bize",
        "bizi",
        "bizim",
        "böyle",
        "böylece",
        "bu",
        "buna",
        "bunda",
        "bundan",
        "bunu",
        "bunun",
        "burada",
        "bütün",
        "çoğu",
        "çoğuna",
        "çoğunu",
        "çok",
        "çünkü",
        "da",
        "daha",
        "de",
        "değil",
        "demek",
        "diğer",
        "diğeri",
        "diğerleri",
        "diye",
        "dokuz",
        "dolayı",
        "dört",
        "elbette",
        "en",
        "fakat",
        "falan",
        "felan",
        "filan",
        "gene",
        "gibi",
        "hâlâ",
        "hangi",
        "hangisi",
        "hani",
        "hatta",
        "hem",
        "henüz",
        "hep",
        "hepsi",
        "hepsine",
        "hepsini",
        "her",
        "her biri",
        "herkes",
        "herkese",
        "herkesi",
        "hiç",
        "hiç kimse",
        "hiçbiri",
        "hiçbirine",
        "hiçbirini",
        "için",
        "içinde",
        "iki",
        "ile",
        "ise",
        "işte",
        "kaç",
        "kadar",
        "kendi",
        "kendine",
        "kendini",
        "ki",
        "kim",
        "kime",
        "kimi",
        "kimin",
        "kimisi",
        "madem",
        "mı",
        "mı",
        "mi",
        "mu",
        "mu",
        "mü",
        "mü",
        "nasıl",
        "ne",
        "neden",
        "nedir",
        "nerde",
        "nerede",
        "nereden",
        "nereye",
        "nesi",
        "neyse",
        "niçin",
        "niye",
        "on",
        "ona",
        "ondan",
        "onlar",
        "onlara",
        "onlardan",
        "onların",
        "onların",
        "onu",
        "onun",
        "orada",
        "oysa",
        "oysaki",
        "öbürü",
        "ön",
        "önce",
        "ötürü",
        "öyle",
        "rağmen",
        "sana",
        "sekiz",
        "sen",
        "senden",
        "seni",
        "senin",
        "siz",
        "sizden",
        "size",
        "sizi",
        "sizin",
        "son",
        "sonra",
        "şayet",
        "şey",
        "şeyden",
        "şeye",
        "şeyi",
        "şeyler",
        "şimdi",
        "şöyle",
        "şu",
        "şuna",
        "şunda",
        "şundan",
        "şunlar",
        "şunu",
        "şunun",
        "tabi",
        "tamam",
        "tüm",
        "tümü",
        "üç",
        "üzere",
        "var",
        "ve",
        "veya",
        "veyahut",
        "ya",
        "yada",
        "yani",
        "yedi",
        "yerine",
        "yine",
        "yoksa",
        "zaten",
    };

}
