package com.hrzafer.prizma.feature.ngram;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 24.02.2014
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */
public interface NGramExtractor {

    public List<NGram> extract(List<String> tokens, int windowSize) ;


}
