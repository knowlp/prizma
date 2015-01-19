
import com.hrzafer.prizma.ArffCreator;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.data.io.DocumentFromString;
import com.hrzafer.prizma.feature.DocId;
import com.hrzafer.prizma.feature.Feature;
import com.hrzafer.prizma.feature.TermVector;
import com.hrzafer.prizma.feature.value.DocumentVectors;
import com.hrzafer.prizma.preprocessing.Analyzer;
import com.hrzafer.prizma.preprocessing.FastTokenizer;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import static junit.framework.Assert.assertEquals;


/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 21.04.2014
 * Time: 10:29
 * To change this template use File | Settings | File Templates.
 */
public class IntegrationTests {

    private static final String expectedBinaryUnigrams = "@relation test_dataset\n" +
            "\n" +
            "@attribute w0_content numeric\n" +
            "@attribute w1_content numeric\n" +
            "@attribute w2_content numeric\n" +
            "@attribute w3_content numeric\n" +
            "@attribute w4_content numeric\n" +
            "@attribute w5_content numeric\n" +
            "@attribute w6_content numeric\n" +
            "@attribute w7_content numeric\n" +
            "@attribute w8_content numeric\n" +
            "@attribute w9_content numeric\n" +
            "@attribute class {c1,c2,c3,c4,c5}\n" +
            "\n" +
            "@data\n" +
            "\n" +
            "% c1\\01.txt\n" +
            "1,1,1,1,1,0,0,0,0,0,c1\n" +
            "% c1\\02.txt\n" +
            "1,1,0,1,1,1,0,0,0,0,c1\n" +
            "% c1\\03.txt\n" +
            "1,1,0,0,1,1,1,0,0,0,c1\n" +
            "% c1\\04.txt\n" +
            "1,1,0,0,0,1,1,1,0,0,c1\n" +
            "% c1\\05.txt\n" +
            "1,1,0,0,0,0,1,1,1,0,c1\n" +
            "% c2\\01.txt\n" +
            "1,1,1,1,1,0,0,0,0,0,c2\n" +
            "% c2\\02.txt\n" +
            "0,1,1,1,1,1,0,0,0,0,c2\n" +
            "% c2\\03.txt\n" +
            "0,0,1,1,1,1,1,0,0,0,c2\n" +
            "% c2\\04.txt\n" +
            "0,0,1,1,0,1,1,1,0,0,c2\n" +
            "% c2\\05.txt\n" +
            "0,0,1,1,0,0,1,1,1,0,c2\n" +
            "% c3\\01.txt\n" +
            "0,1,1,1,1,1,0,0,0,0,c3\n" +
            "% c3\\02.txt\n" +
            "0,0,1,1,1,1,1,0,0,0,c3\n" +
            "% c3\\03.txt\n" +
            "0,0,0,1,1,1,1,1,0,0,c3\n" +
            "% c3\\04.txt\n" +
            "0,0,0,0,1,1,1,1,1,0,c3\n" +
            "% c3\\05.txt\n" +
            "0,0,0,0,1,1,0,1,1,1,c3\n" +
            "% c4\\01.txt\n" +
            "0,0,0,1,1,1,1,1,0,0,c4\n" +
            "% c4\\02.txt\n" +
            "0,0,0,0,1,1,1,1,1,0,c4\n" +
            "% c4\\03.txt\n" +
            "0,0,0,0,0,1,1,1,1,1,c4\n" +
            "% c4\\04.txt\n" +
            "1,0,0,0,0,0,1,1,1,1,c4\n" +
            "% c4\\05.txt\n" +
            "1,1,0,0,0,0,1,1,0,1,c4\n" +
            "% c5\\01.txt\n" +
            "0,0,0,0,0,1,0,1,1,1,c5\n" +
            "% c5\\02.txt\n" +
            "1,0,0,0,0,0,1,1,1,0,c5\n" +
            "% c5\\03.txt\n" +
            "1,1,0,0,0,0,0,0,0,1,c5\n" +
            "% c5\\04.txt\n" +
            "1,1,1,0,0,0,0,0,1,1,c5\n" +
            "% c5\\05.txt\n" +
            "0,1,1,1,0,0,0,0,1,1,c5\n";

    private static final String expectedIntegerUnigrams = "@relation test_dataset\n" +
            "\n" +
            "@attribute w0_content numeric\n" +
            "@attribute w1_content numeric\n" +
            "@attribute w2_content numeric\n" +
            "@attribute w3_content numeric\n" +
            "@attribute w4_content numeric\n" +
            "@attribute w5_content numeric\n" +
            "@attribute w6_content numeric\n" +
            "@attribute w7_content numeric\n" +
            "@attribute w8_content numeric\n" +
            "@attribute w9_content numeric\n" +
            "@attribute class {c1,c2,c3,c4,c5}\n" +
            "\n" +
            "@data\n" +
            "\n" +
            "% c1\\01.txt\n" +
            "1,1,1,1,1,0,0,0,0,0,c1\n" +
            "% c1\\02.txt\n" +
            "1,1,0,1,1,1,0,0,0,0,c1\n" +
            "% c1\\03.txt\n" +
            "1,1,0,0,1,1,1,0,0,0,c1\n" +
            "% c1\\04.txt\n" +
            "1,1,0,0,0,1,1,1,0,0,c1\n" +
            "% c1\\05.txt\n" +
            "1,1,0,0,0,0,1,1,1,0,c1\n" +
            "% c2\\01.txt\n" +
            "1,1,1,1,1,0,0,0,0,0,c2\n" +
            "% c2\\02.txt\n" +
            "0,1,1,1,1,1,0,0,0,0,c2\n" +
            "% c2\\03.txt\n" +
            "0,0,1,1,1,1,1,0,0,0,c2\n" +
            "% c2\\04.txt\n" +
            "0,0,1,1,0,1,1,1,0,0,c2\n" +
            "% c2\\05.txt\n" +
            "0,0,1,1,0,0,1,1,1,0,c2\n" +
            "% c3\\01.txt\n" +
            "0,1,1,1,1,1,0,0,0,0,c3\n" +
            "% c3\\02.txt\n" +
            "0,0,1,1,1,1,1,0,0,0,c3\n" +
            "% c3\\03.txt\n" +
            "0,0,0,1,1,1,1,1,0,0,c3\n" +
            "% c3\\04.txt\n" +
            "0,0,0,0,1,1,1,1,1,0,c3\n" +
            "% c3\\05.txt\n" +
            "0,0,0,0,1,1,0,1,1,1,c3\n" +
            "% c4\\01.txt\n" +
            "0,0,0,1,1,1,1,1,0,0,c4\n" +
            "% c4\\02.txt\n" +
            "0,0,0,0,1,1,1,1,1,0,c4\n" +
            "% c4\\03.txt\n" +
            "0,0,0,0,0,1,1,1,1,1,c4\n" +
            "% c4\\04.txt\n" +
            "1,0,0,0,0,0,1,1,1,1,c4\n" +
            "% c4\\05.txt\n" +
            "1,1,0,0,0,0,1,1,0,1,c4\n" +
            "% c5\\01.txt\n" +
            "0,0,0,0,0,2,0,1,1,1,c5\n" +
            "% c5\\02.txt\n" +
            "1,0,0,0,0,0,1,1,2,0,c5\n" +
            "% c5\\03.txt\n" +
            "1,1,0,0,0,0,0,0,0,3,c5\n" +
            "% c5\\04.txt\n" +
            "1,1,1,0,0,0,0,0,1,1,c5\n" +
            "% c5\\05.txt\n" +
            "0,1,1,1,0,0,0,0,1,1,c5\n";

    private final String expectedTfIdfUnigrams = "@relation test_dataset\n" +
            "\n" +
            "@attribute w0_content numeric\n" +
            "@attribute w1_content numeric\n" +
            "@attribute w2_content numeric\n" +
            "@attribute w3_content numeric\n" +
            "@attribute w4_content numeric\n" +
            "@attribute w5_content numeric\n" +
            "@attribute w6_content numeric\n" +
            "@attribute w7_content numeric\n" +
            "@attribute w8_content numeric\n" +
            "@attribute w9_content numeric\n" +
            "@attribute class {c1,c2,c3,c4,c5}\n" +
            "\n" +
            "@data\n" +
            "\n" +
            "% c1\\01.txt\n" +
            "0.16,0.15,0.18,0.15,0.13,0,0,0,0,0,c1\n" +
            "% c1\\02.txt\n" +
            "0.16,0.15,0,0.15,0.13,0.1,0,0,0,0,c1\n" +
            "% c1\\03.txt\n" +
            "0.16,0.15,0,0,0.13,0.1,0.1,0,0,0,c1\n" +
            "% c1\\04.txt\n" +
            "0.16,0.15,0,0,0,0.1,0.1,0.12,0,0,c1\n" +
            "% c1\\05.txt\n" +
            "0.16,0.15,0,0,0,0,0.1,0.12,0.16,0,c1\n" +
            "% c2\\01.txt\n" +
            "0.16,0.15,0.18,0.15,0.13,0,0,0,0,0,c2\n" +
            "% c2\\02.txt\n" +
            "0,0.15,0.18,0.15,0.13,0.1,0,0,0,0,c2\n" +
            "% c2\\03.txt\n" +
            "0,0,0.18,0.15,0.13,0.1,0.1,0,0,0,c2\n" +
            "% c2\\04.txt\n" +
            "0,0,0.18,0.15,0,0.1,0.1,0.12,0,0,c2\n" +
            "% c2\\05.txt\n" +
            "0,0,0.18,0.15,0,0,0.1,0.12,0.16,0,c2\n" +
            "% c3\\01.txt\n" +
            "0,0.15,0.18,0.15,0.13,0.1,0,0,0,0,c3\n" +
            "% c3\\02.txt\n" +
            "0,0,0.18,0.15,0.13,0.1,0.1,0,0,0,c3\n" +
            "% c3\\03.txt\n" +
            "0,0,0,0.15,0.13,0.1,0.1,0.12,0,0,c3\n" +
            "% c3\\04.txt\n" +
            "0,0,0,0,0.13,0.1,0.1,0.12,0.16,0,c3\n" +
            "% c3\\05.txt\n" +
            "0,0,0,0,0.13,0.1,0,0.12,0.16,0.23,c3\n" +
            "% c4\\01.txt\n" +
            "0,0,0,0.15,0.13,0.1,0.1,0.12,0,0,c4\n" +
            "% c4\\02.txt\n" +
            "0,0,0,0,0.13,0.1,0.1,0.12,0.16,0,c4\n" +
            "% c4\\03.txt\n" +
            "0,0,0,0,0,0.1,0.1,0.12,0.16,0.23,c4\n" +
            "% c4\\04.txt\n" +
            "0.16,0,0,0,0,0,0.1,0.12,0.16,0.23,c4\n" +
            "% c4\\05.txt\n" +
            "0.16,0.15,0,0,0,0,0.1,0.12,0,0.23,c4\n" +
            "% c5\\01.txt\n" +
            "0,0,0,0,0,0.26,0,0.14,0.21,0.28,c5\n" +
            "% c5\\02.txt\n" +
            "0.21,0,0,0,0,0,0.13,0.14,0.41,0,c5\n" +
            "% c5\\03.txt\n" +
            "0.27,0.24,0,0,0,0,0,0,0,1.14,c5\n" +
            "% c5\\04.txt\n" +
            "0.16,0.15,0.18,0,0,0,0,0,0.16,0.23,c5\n" +
            "% c5\\05.txt\n" +
            "0,0.15,0.18,0.15,0,0,0,0,0.16,0.23,c5\n";

    static final List<List<String>> dataset = Arrays.asList(
            Arrays.asList("c1\\01.txt", "w0 w1 w2 w3 w4", "c1"),
            Arrays.asList("c1\\02.txt", "w0 w1 w3 w4 w5", "c1"),
            Arrays.asList("c1\\03.txt", "w0 w1 w4 w5 w6", "c1"),
            Arrays.asList("c1\\04.txt", "w0 w1 w5 w6 w7", "c1"),
            Arrays.asList("c1\\05.txt", "w0 w1 w6 w7 w8", "c1"),
            Arrays.asList("c2\\01.txt", "w0 w1 w2 w3 w4", "c2"),
            Arrays.asList("c2\\02.txt", "w1 w2 w3 w4 w5", "c2"),
            Arrays.asList("c2\\03.txt", "w2 w3 w4 w5 w6", "c2"),
            Arrays.asList("c2\\04.txt", "w2 w3 w5 w6 w7", "c2"),
            Arrays.asList("c2\\05.txt", "w2 w3 w6 w7 w8", "c2"),
            Arrays.asList("c3\\01.txt", "w1 w2 w3 w4 w5", "c3"),
            Arrays.asList("c3\\02.txt", "w2 w3 w4 w5 w6", "c3"),
            Arrays.asList("c3\\03.txt", "w3 w4 w5 w6 w7", "c3"),
            Arrays.asList("c3\\04.txt", "w4 w5 w6 w7 w8", "c3"),
            Arrays.asList("c3\\05.txt", "w4 w5 w7 w8 w9", "c3"),
            Arrays.asList("c4\\01.txt", "w3 w4 w5 w6 w7", "c4"),
            Arrays.asList("c4\\02.txt", "w4 w5 w6 w7 w8", "c4"),
            Arrays.asList("c4\\03.txt", "w5 w6 w7 w8 w9", "c4"),
            Arrays.asList("c4\\04.txt", "w0 w6 w7 w8 w9", "c4"),
            Arrays.asList("c4\\05.txt", "w0 w1 w6 w7 w9", "c4"),
            Arrays.asList("c5\\01.txt", "w5 w5 w7 w8 w9", "c5"),
            Arrays.asList("c5\\02.txt", "w0 w6 w7 w8 w8", "c5"),
            Arrays.asList("c5\\03.txt", "w0 w1 w9 w9 w9", "c5"),
            Arrays.asList("c5\\04.txt", "w0 w1 w2 w8 w9", "c5"),
            Arrays.asList("c5\\05.txt", "w1 w2 w3 w8 w9", "c5")

    );

    List<Document> documents;
    Map<String, String> params = new HashMap<>();
    List<Feature> features = new ArrayList<>();
    Analyzer analyzer = new Analyzer(new FastTokenizer());

    @Before
    public void setUp() throws Exception {
        documents = new ArrayList<>();
        for (List<String> strings : dataset) {
            Document d = new Document(new DocumentFromString(strings.get(0), strings.get(1)), strings.get(2));
            documents.add(d);
        }

        params.put("nGramSize", "1");
        features.add(new DocId());
    }

    @Test
      public void unigramsBinary() {

        params.put("weight", "binary");

        TermVector unigramTerms = new TermVector("", "content", "", "", params, analyzer);
        unigramTerms.buildTermDictionary(documents);
        features.add(unigramTerms);

        DocumentVectors relation = new DocumentVectors("test_dataset", features, documents);
        String arff = ArffCreator.create(relation);

        assertEquals(expectedBinaryUnigrams, arff);

    }

    @Test
    public void unigramsInteger() {

        params.put("weight", "integer");

        TermVector unigramTerms = new TermVector("", "content", "", "", params, analyzer);
        unigramTerms.buildTermDictionary(documents);
        features.add(unigramTerms);

        DocumentVectors relation = new DocumentVectors("test_dataset", features, documents);
        String arff = ArffCreator.create(relation);

        assertEquals(expectedIntegerUnigrams, arff);

    }

    @Test
    public void unigramsTfIdf() {

        params.put("weight", "tfidf");

        TermVector unigramTerms = new TermVector("", "content", "", "", params, analyzer);
        unigramTerms.buildTermDictionary(documents);
        features.add(unigramTerms);

        DocumentVectors relation = new DocumentVectors("test_dataset", features, documents);
        String arff = ArffCreator.create(relation);

        assertEquals(expectedTfIdfUnigrams, arff);

    }




}
