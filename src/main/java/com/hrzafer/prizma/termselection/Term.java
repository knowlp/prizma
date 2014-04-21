package com.hrzafer.prizma.termselection;

public class Term implements Comparable<Term>{
    private String term;
    private TermDistribution distribution;

    public Term(String term, TermDistribution distribution) {
        this.term = term;
        this.distribution = distribution;
    }

    public String getTerm() {
        return term;
    }

    public TermDistribution getDistribution() {
        return distribution;
    }

    @Override
    public int compareTo(Term other) {
        double a = other.distribution.getMeasurementValue();
        double b = distribution.getMeasurementValue();
        return Double.valueOf(a).compareTo(b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Term term1 = (Term) o;

        if (!term.equals(term1.term)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return term.hashCode();
    }

    @Override
    public String toString() {
        return term + "\t" + distribution;
    }
}
