package com.peergreen.store.ldap.parser.enumeration;

public enum BinaryOperators {
    APPROX("~="),
    LESSER_THAN_EQUAL("<="),
    GREATER_THAN_EQUAL(">="),
    EQUAL("=");

    String s;

    BinaryOperators(String s) {
        this.s = s;
    }

    public String getBinaryOperator() {
        return this.s;
    }
    
    public static boolean isBinaryOperator(String s) {
        return (s.equals(EQUAL.getBinaryOperator()) ||
                s.equals(APPROX.getBinaryOperator()) ||
                s.equals(GREATER_THAN_EQUAL.getBinaryOperator()) ||
                s.equals(LESSER_THAN_EQUAL.getBinaryOperator()));
    }
}