package com.hrzafer.prizma.preprocessing;

import com.hrzafer.prizma.Config;

public class TooShortTokenFilter extends Filter {
    private static final int MIN_TOKEN_LENGTH = Config.getAsInt("minTokenLength");
    @Override
    protected boolean eval(String token) {
        return token.length() < MIN_TOKEN_LENGTH;
    }
}
