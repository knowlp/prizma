package com.hrzafer.prizma.preprocessing;

import com.hrzafer.prizma.Config;

public class TooShortTokenFilter extends Filter {
    @Override
    protected boolean eval(String token) {
        return token.length() < Config.getAsInt("minTokenLength");
    }
}
