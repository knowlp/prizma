
package com.hrzafer.prizma.preprocessing;

import java.util.List;

/**
 * @author test
 */
public abstract class Filter implements IFilter{

    public void filter(List<String> tokens) {
        for (int i = tokens.size()-1; i >= 0; i--) {
            String token = tokens.get(i);
            if (eval(token)) {
                tokens.remove(i);
            }
        }
    }

    protected abstract boolean eval(String token);




}


