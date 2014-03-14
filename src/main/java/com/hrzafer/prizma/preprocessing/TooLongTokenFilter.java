package com.hrzafer.prizma.preprocessing;

import com.hrzafer.prizma.Config;


public class TooLongTokenFilter extends Filter {
    private static final int MAX_TOKEN_LENGTH = Config.getAsInt("maxTokenLength");

    @Override
    protected boolean eval(String token) {
        return token.length() > MAX_TOKEN_LENGTH;
    }
}
