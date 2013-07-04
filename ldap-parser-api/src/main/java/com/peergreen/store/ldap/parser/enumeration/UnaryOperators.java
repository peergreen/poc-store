package com.peergreen.store.ldap.parser.enumeration;

public enum UnaryOperators {
    NOT("!");
    
    String s;

    UnaryOperators(String s) {
        this.s = s;
    }

    public String getUnaryOperator() {
        return this.s;
    }
    
    public static boolean isUnaryOperator(String s) {
        return (s.equals(NOT.getUnaryOperator()));
    }
}