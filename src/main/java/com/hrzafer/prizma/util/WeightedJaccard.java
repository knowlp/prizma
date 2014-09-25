package com.hrzafer.prizma.util;

import java.util.HashSet;
import java.util.Set;

/**
 * http://mathoverflow.net/questions/123339/weighted-jaccard-similarity
 * http://static.googleusercontent.com/media/research.google.com/en/us/pubs/archive/36928.pdf
 */
public class WeightedJaccard {
    public static<T> double Similarity(Set<T> s1, Set<T> s2){
        if (s1.isEmpty() || s2.isEmpty()){
            return 0.0;
        }

        Set<T> union = new HashSet<T>(s1);
        union.addAll(s2);
        Set<T> diff = new HashSet<T>(s1);
        diff.removeAll(s2);

        int a = s1.size() + s2.size() - diff.size();
        int b= s1.size() + s2.size() + diff.size();

        return a /(double) b;
    }
}
