package com.hrzafer.prizma.feature.ngram;

import java.util.ArrayList;
import java.util.List;

public class NGram implements Comparable<NGram> {

    String shingle;
    int frequency;

    public NGram(String shingle) {
        this.shingle = shingle;
        frequency = 0;
    }

    public NGram(String shingle, int frequency ) {
        this.frequency = frequency;
        this.shingle = shingle;
    }

    public int getFrequency() {
        return frequency;
    }

    public void incrementFrequencyByOne() {
        frequency++;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NGram)) {
            return false;
        }
        NGram other = (NGram) obj;
        return this.shingle.equals(other.shingle);
    }

    public String getShingle() {
        return shingle;
    }

    @Override
    public int hashCode() {
     return shingle.hashCode();
    }

    
    public String toWekaString(){
        StringBuilder sb = new StringBuilder();
        sb.append(shingle.replaceAll(" ", "_").replaceAll("'", "^"));
        return sb.toString();
    }
    
    private static void removeLastUnderscore(StringBuilder sb) {
        sb.deleteCharAt(sb.length() - 1);
    }

    @Override
    public int compareTo(NGram o) {
        return o.frequency - frequency;
    }

    private static void convertToBinary(int[] vector) {
        for (int i = 0; i < vector.length; i++) {
            if (vector[i] > 1) {
                vector[i] = 1;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(shingle).append(" ").append(frequency);
        return sb.toString();
    }

    public static void main(String args[]) {
        List<String> a = new ArrayList<>();
        a.add("a");
        a.add("b");

        List<String> b = new ArrayList<>();
        b.add("a");
        b.add("b");

        System.out.println(a.equals(b));

        int[] arr = {1, 2, 3, 4};
        convertToBinary(arr);
        for (int i : arr) {
            System.out.println(i);
        }

    }
}
