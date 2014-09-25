package com.hrzafer.prizma.util;

import org.junit.Test;

import java.util.*;
import static junit.framework.Assert.assertEquals;

/**
 *
 */
public class JaccardTest {

    @Test
    public void testSimilarity() throws Exception {

        Set<String> s1 = new HashSet<>();
        Set<String> s2 = new HashSet<>();
        double expected = 0;
        double actual = Jaccard.Similarity(s1,s2);
        assertEquals(expected, actual);

        s1 = new HashSet<>(Arrays.asList("bir", "iki", "üç"));
        s2 = new HashSet<>();
        expected = 0;
        actual = Jaccard.Similarity(s1,s2);
        assertEquals(expected, actual);

        s1 = new HashSet<>(Arrays.asList("bir", "iki", "üç"));
        s2 = new HashSet<>(Arrays.asList("bir", "iki", "üç"));
        expected = 1;
        actual = Jaccard.Similarity(s1,s2);
        assertEquals(expected, actual);

        s1 = new HashSet<>(Arrays.asList("bir", "iki", "üç"));
        s2 = new HashSet<>(Arrays.asList("iki", "üç", "dört"));
        expected = 0.5;
        actual = Jaccard.Similarity(s1,s2);
        assertEquals(expected, actual);

        s1 = new HashSet<>(Arrays.asList("bir", "iki", "üç"));
        s2 = new HashSet<>(Arrays.asList("dört", "beş", "altı"));
        expected = 0;
        actual = Jaccard.Similarity(s1,s2);
        assertEquals(expected, actual);

        s1 = new HashSet<>(Arrays.asList("bir", "iki", "üç"));
        s2 = new HashSet<>(Arrays.asList("iki", "üç"));
        expected = 2/(double)3;
        actual = Jaccard.Similarity(s1,s2);
        assertEquals(expected, actual);
    }

}
