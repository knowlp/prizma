package com.hrzafer.prizma.preprocessing;

import com.hrzafer.prizma.util.STR;

public class NumberRemover extends Filter {
  
    @Override
    protected boolean eval(String token) {
        return STR.isInteger(token);
    }    
}
