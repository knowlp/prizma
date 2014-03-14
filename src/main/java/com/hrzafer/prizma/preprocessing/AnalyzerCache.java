package com.hrzafer.prizma.preprocessing;

import java.util.Collections;
import java.util.List;

public class AnalyzerCache {
    int _hashCode;
    private List<String> _tokens;

    public AnalyzerCache() {
        _hashCode = 0;
        _tokens = Collections.EMPTY_LIST;
    }

    public boolean hit(int hashCode){
        if (_hashCode == 0){
            return false;
        }
        return _hashCode == hashCode;
    }

    public List<String> get() {
        return _tokens;
    }

    public void put(int hashCode, List<String> tokens){
        _hashCode = hashCode;
        _tokens = tokens;
    }

}
