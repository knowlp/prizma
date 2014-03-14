package com.hrzafer.prizma.preprocessing;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 14.02.2014
 * Time: 13:52
 * To change this template use File | Settings | File Templates.
 */
public interface ITokenizer {
    public List<String> tokenize (String str);
}
