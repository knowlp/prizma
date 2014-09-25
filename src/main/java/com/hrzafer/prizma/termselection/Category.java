package com.hrzafer.prizma.termselection;

import java.util.Collections;
import java.util.List;

/**
 *
 */
public class Category {
    private String name;
    private List<Term> terms;

    public Category(String name, List<Term> terms) {
        this.name = name;
        Collections.sort(terms);
        this.terms = terms;
    }

    public String getName() {
        return name;
    }

    public List<Term> getTerms() {
        return terms;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name).append("\n");
        for (Term term : terms) {
            sb.append(term.getTerm()).append("\t").append(term.getDistribution().toString()).append("\n");
        }
        return sb.toString();
    }
}
