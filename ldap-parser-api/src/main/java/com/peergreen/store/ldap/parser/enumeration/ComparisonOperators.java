package com.peergreen.store.ldap.parser.enumeration;

public enum ComparisonOperators {
    APPROX("~="),
    LESSER_THAN_EQUAL("<="),
    GREATER_THAN_EQUAL(">="),
    EQUAL("=");

    String s;

    ComparisonOperators(String s) {
        this.s = s;
    }

    public String getComparisonOperator() {
        return this.s;
    }
    
    public static boolean isComparisonOperator(String s) {
        return (s.equals(APPROX.getComparisonOperator()) ||
                s.equals(GREATER_THAN_EQUAL.getComparisonOperator()) ||
                s.equals(LESSER_THAN_EQUAL.getComparisonOperator()) ||
                s.equals(EQUAL.getComparisonOperator()));
    }
}
