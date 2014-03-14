package com.hrzafer.prizma.preprocessing;

public class VowelRemover extends ModifierFilter {

    private String _token;
    
    @Override
    protected String modify(String token) {
        return removeVocals(token);
    }

    @Override
    protected boolean eval(String token) {
        _token = removeVocals(token);
        return !_token.isEmpty();
    }

    public String removeVocals(String token) {
        StringBuilder sb = new StringBuilder(token);
        for (int i = sb.length() - 1; i >= 0; i--) {
            if (isVowel(sb.charAt(i))) {
                sb.deleteCharAt(i);
            }
        }
        return sb.toString();
    }

    private boolean isVowel(char ch) {
        String str = "aeıioöuüAEIİOÖUÜ";
        return str.indexOf(ch) != -1;
    }
}
