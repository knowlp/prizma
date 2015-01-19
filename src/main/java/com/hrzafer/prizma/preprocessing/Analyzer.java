package com.hrzafer.prizma.preprocessing;

import java.util.Collections;
import java.util.List;

public class Analyzer {
    private List<ICharFilter> normalizers;
    private ITokenizer tokenizer;
    private List<IFilter> filters;
    private AnalyzerCache cache;

    public Analyzer(List<ICharFilter> charFilters, ITokenizer tokenizer, List<IFilter> filters) {
        this.normalizers = charFilters;
        this.tokenizer = tokenizer;
        this.filters = filters;
        cache = new AnalyzerCache();
    }

    public Analyzer(ITokenizer tokenizer) {
        this(Collections.EMPTY_LIST, tokenizer, Collections.EMPTY_LIST);
    }

    public Analyzer(ITokenizer tokenizer, List<IFilter> filters) {
        this(Collections.EMPTY_LIST, tokenizer, filters);
    }

    public Analyzer(List<ICharFilter> normalizers, ITokenizer tokenizer) {
        this(normalizers, tokenizer, Collections.EMPTY_LIST);
    }

    public List<String> analyze(String str){
        int hash = str.hashCode();
        List<String> tokens;
        if (cache.hit(hash)){
            tokens = cache.get();
            //System.out.println("cache hit");
        }
        else {
            str = normalize(str);
            tokens = tokenize(str);
            tokens = filter(tokens);
            cache.put(hash, tokens);
        }
        return tokens;
    }

    public List<String> tokenize (String str){
        return tokenizer.tokenize(str);
    }

    public String normalize(String str){
        for (ICharFilter normalizer : normalizers) {
            str = normalizer.normalize(str);
        }
        return str;
    }

    public List<String> filter (List<String> tokens){
        for (IFilter filter : filters) {
            filter.filter(tokens);
        }
        return tokens;
    }
}
