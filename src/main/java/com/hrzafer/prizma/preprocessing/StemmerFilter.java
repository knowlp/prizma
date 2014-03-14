
package com.hrzafer.prizma.preprocessing;

import com.hrzafer.prizma.util.NuveCachedStemmer;

public class StemmerFilter extends ModifierFilter {

    @Override
    protected String modify(String token) {
        return NuveCachedStemmer.Instance.stem(token);
    }

    @Override
    protected boolean eval(String token) {
        return true;
    }

}
