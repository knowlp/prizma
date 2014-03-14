package com.hrzafer.prizma.preprocessing;

import com.hrzafer.prizma.util.STR;

public class Asciifier extends ModifierFilter {
    @Override
    protected String modify(String token) {
        return STR.toNonTurkish(token);
    }

    @Override
    protected boolean eval(String token) {
        return true;
    }
}
