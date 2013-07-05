package com.peergreen.store.ldap.parser;

public class Element {

    private boolean operator;
    private String content;
    private int operandsNb;
    
    public Element(boolean operator, String content, int operandsNb) {
        this.operator = operator;
        this.content = content;
        this.operandsNb = operandsNb;
    }
    
    public boolean isOperator() {
        return operator;
    }
    
    public void setOperator(boolean operator) {
        this.operator = operator;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public int getOperandsNb() {
        return operandsNb;
    }

    public void setOperandsNb(int operandsNb) {
        this.operandsNb = operandsNb;
    }
    
}