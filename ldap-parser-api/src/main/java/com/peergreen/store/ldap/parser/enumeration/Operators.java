package com.peergreen.store.ldap.parser.enumeration;

public enum Operators {
    AND("&"),
    OR("|"),
    NOT("!"),
    APPROX("~="),
    LESSER_THAN_EQUAL("<="),
    GREATER_THAN_EQUAL(">=");

    String s;

    Operators(String s) {
        this.s = s;
    }

    public String getOperator() {
        return this.s;
    }
    
    public static boolean isOperator(String s) {
        return (s.equals(AND.getOperator()) ||
                s.equals(OR.getOperator()) ||
                s.equals(NOT.getOperator()) ||
                s.equals(APPROX.getOperator()) ||
                s.equals(LESSER_THAN_EQUAL.getOperator()) ||
                s.equals(GREATER_THAN_EQUAL.getOperator()));
    }
}
