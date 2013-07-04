package com.peergreen.store.ldap.parser.enumeration;

public enum NaryOperators {
    AND("&"),
    OR("|");

    String s;

    NaryOperators(String s) {
        this.s = s;
    }

    public String getNaryOperator() {
        return this.s;
    }
    
    public static boolean isNaryOperator(String s) {
        return (s.equals(AND.getNaryOperator()) ||
                s.equals(OR.getNaryOperator()));
    }
}