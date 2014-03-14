/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.prizma.preprocessing;

import java.util.List;

/**
 *
 * @author hrzafer
 */
public abstract class ModifierFilter extends Filter {

    @Override
    public void filter(List<String> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (eval(token)) {
                tokens.set(i, modify(token));
            }
        }
    }
    
    protected abstract String modify(String token);
        
}
