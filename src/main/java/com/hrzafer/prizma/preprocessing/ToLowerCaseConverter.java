
package com.hrzafer.prizma.preprocessing;

import java.util.Locale;

public class ToLowerCaseConverter extends ModifierFilter {

    @Override
    protected boolean eval(String token) {
        return true;
    }

    @Override
    protected String modify(String token) {
        return token.toLowerCase(new Locale("TR"));
    }
}
