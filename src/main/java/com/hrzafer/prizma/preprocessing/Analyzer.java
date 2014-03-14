package com.hrzafer.prizma.preprocessing;

import java.util.Collections;
import java.util.List;

public class Analyzer {
    private List<INormalizer> normalizers;
    private ITokenizer tokenizer;
    private List<IFilter> filters;
    private AnalyzerCache cache;

    public Analyzer(List<INormalizer> normalizers, ITokenizer tokenizer, List<IFilter> filters) {
        this.normalizers = normalizers;
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

    public Analyzer(List<INormalizer> normalizers, ITokenizer tokenizer) {
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
        for (INormalizer normalizer : normalizers) {
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
