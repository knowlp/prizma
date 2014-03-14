package com.hrzafer.prizma.preprocessing;

public class URLRemover extends Filter {

    private static final String regex = "<\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]>";

    @Override
    protected boolean eval(String token) {
        return token.matches(regex);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
