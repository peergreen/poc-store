package com.peergreen.store.ldap.parser.enumeration;

public enum Symbols {
    OPENING_PARENTHESIS("("),
    CLOSING_PARENTHESIS(")"),
    AND("&"),
    OR("|"),
    NOT("!"),
    EQUAL("="),
    COLON(":"),
    APPROX("~="),
    LESSER_THAN_EQUAL("<="),
    GREATER_THAN_EQUAL(">=");

    String s;

    Symbols(String s) {
        this.s = s;
    }

    public String getSymbol() {
        return this.s;
    }
    
    public static boolean isSymbol(String s) {
        return (s.equals(OPENING_PARENTHESIS.getSymbol()) ||
                s.equals(CLOSING_PARENTHESIS.getSymbol()) ||
                s.equals(AND.getSymbol()) ||
                s.equals(OR.getSymbol()) ||
                s.equals(NOT.getSymbol()) ||
                s.equals(EQUAL.getSymbol()) ||
                s.equals(COLON.getSymbol()) ||
                s.equals(APPROX.getSymbol()) ||
                s.equals(LESSER_THAN_EQUAL.getSymbol()) ||
                s.equals(GREATER_THAN_EQUAL.getSymbol())
                );
    }
}
