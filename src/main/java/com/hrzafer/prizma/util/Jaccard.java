package com.hrzafer.prizma.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 28.04.2014
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */
public class Jaccard {
    public static<T> double Similarity(Set<T> s1, Set<T> s2){
        if (s1.isEmpty() || s2.isEmpty()){
            return 0.0;
        }

        Set<T> union = new HashSet<T>(s1);
        union.addAll(s2);
        Set<T> intersection = new HashSet<T>(s1);
        intersection.retainAll(s2);
        return intersection.size() /(double) union.size();
    }
}
