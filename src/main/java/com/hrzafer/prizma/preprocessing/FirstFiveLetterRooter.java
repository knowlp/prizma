package com.hrzafer.prizma.preprocessing;

public class FirstFiveLetterRooter extends ModifierFilter {

    private static final int N = 5;

    @Override
    protected boolean eval(String token) {
        return true;
    }

    @Override
    protected String modify(String token) {
        return getRoot(token);
    }

    private String getRoot(String token) {
        if (token.length() >= N) {
            return token.substring(0, N);
        }
        return token;
    }

}
