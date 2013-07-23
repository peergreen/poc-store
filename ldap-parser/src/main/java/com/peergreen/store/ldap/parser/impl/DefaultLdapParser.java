package com.peergreen.store.ldap.parser.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import com.peergreen.store.ldap.parser.ILdapParser;
import com.peergreen.store.ldap.parser.enumeration.BinaryOperators;
import com.peergreen.store.ldap.parser.enumeration.ComparisonOperators;
import com.peergreen.store.ldap.parser.enumeration.NaryOperators;
import com.peergreen.store.ldap.parser.enumeration.Operators;
import com.peergreen.store.ldap.parser.enumeration.UnaryOperators;
import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;
import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.store.ldap.parser.node.BinaryNode;
import com.peergreen.store.ldap.parser.node.IValidatorNode;
import com.peergreen.store.ldap.parser.node.NaryNode;
import com.peergreen.store.ldap.parser.node.OperandNode;
import com.peergreen.store.ldap.parser.node.UnaryNode;


/**
 * Tool class to parse a LDAP filter to produce a tree.
 * 
 * @author Guillaume Dupraz Canard
 */
@Component
@Instantiate
@Provides
public class DefaultLdapParser implements ILdapParser {

    private Set<ILdapHandler> handlers;
    
    /**
     * Default constructor.
     */
    public DefaultLdapParser() {
        handlers = new HashSet<>();
    }
    
    /**
     * Method to add a new handler to the parser.
     * 
     * @param handler handler to add
     */
    public void register(ILdapHandler handler) {
        handlers.add(handler);
    }
    
    /**
     * Method to parse a LDAP filter to a tree.<br />
     * Automatically check if output tree is valid.
     * 
     * @param filter LDAP filter
     * @return tree constructed from LDAP filter parsing.
     * @throws InvalidLdapFormatException
     */
    @Override
    public IValidatorNode<String> parse(String filter) throws InvalidLdapFormatException {
        ArrayList<String> tokens = getTokens(filter);
        
        int i = 0;
        IValidatorNode<String> root = null;
        IValidatorNode<String> parentNode = null;

        // check if there is as many opening parenthesis as closing parenthesis
        int openBr = filter.length() - filter.replace("(", "").length();
        int closeBr = filter.length() - filter.replace(")", "").length();
        if (openBr - closeBr != 0) {
            throw new InvalidLdapFormatException("Malformed LDAP filter: not the same number of opening and closing parenthesis.");
        }
        
        // check if filter begins with opening parenthesis
        if (!tokens.get(0).equals("(")) {
            throw new InvalidLdapFormatException("Malformed LDAP expression: opening parenthesis expected.");
        }

        // check if filter ends with closing parenthesis
        if (!tokens.get(tokens.size() - 1).equals(")")) {
            throw new InvalidLdapFormatException("Malformed LDAP expression: closing parenthesis expected.");
        }
        
        // read all tokens
        while (i < tokens.size()) {
            String s = tokens.get(i);

            if (s.equals("(")) {
                i++;
                IValidatorNode<String> parsedNode = null;

                if (Operators.isOperator(tokens.get(i)) && !ComparisonOperators.isComparisonOperator(tokens.get(i))) {
                    // parse an operator
                    String op = tokens.get(i);
                    parsedNode = createOperatorNode(op);
                } else {
                    // parse an operand
                    parsedNode = createBinaryNode(tokens.get(i));
                    
                    if (!parsedNode.validate()) {
                        throw new InvalidLdapFormatException("Failed to parse a valid BinaryNode.");
                    }
                }

                if (parsedNode != null) {
                    if (i == 1) {
                        // if first parsed node, make it root
                        root = parsedNode;
                        parentNode = parsedNode;
                    } else {
                        // else add parsed node to parentNode
                        parentNode.addChild(parsedNode);
                        parsedNode.setParent(parentNode);

                        // if parsed node is an operator, wait for operand(s)
                        if (Operators.isOperator(parsedNode.getData()) &&
                                !ComparisonOperators.isComparisonOperator(parsedNode.getData())) {
                            parentNode = parsedNode;
                        }
                    }
                    
                    // valid node => generate JPQL
                    if (parentNode.getHandler() != null) {
                        parsedNode.getHandler().toQueryElement();
                    }
                } else {
                    throw new InvalidLdapFormatException("Failed to parse a node.");
                }
                i++;
            } else if (s.equals(")")) {
                // go back a level
                if (tokens.get(i-1).equals(")")) {
                    parentNode.validate();
                    parentNode = parentNode.getParentValidatorNode();
                }

                i++;
            } else {
                throw new InvalidLdapFormatException("Invalid LDAP expression. Parenthesis expected.");
            }
        }

        return root;
    }

    /**
     * Method to parse input filter into tokens that can be easily parsed.
     * 
     * @param filter LDAP filter
     * @return list of tokens
     * @throws InvalidLdapFormatException 
     */
    protected ArrayList<String> getTokens(String filter) throws InvalidLdapFormatException {
        ArrayList<String> tokens = new ArrayList<>();

        int pos = 0;
        while (pos < filter.length()) {
            char c = filter.charAt(pos);

            if (c == '(') {
                tokens.add("(");
                pos += 1;
            } else if (c == ')') {
                tokens.add(")");
                pos += 1;
            } else {
                String op = readOperator(filter, pos);
                if (!op.isEmpty()) {
                    tokens.add(op);
                    pos += op.length();
                } else {
                    String s = "";
                    int i = pos;

                    // parse a String until closing parenthesis
                    while (i < filter.length() && filter.charAt(i) != ')') {
                        s += Character.toString(filter.charAt(i));
                        i++;
                    }
                    
                    // If we got an opening parenthesis during reading an item, there is a problem.
                    // It is due to non recognition of an invalid operator (e.g. #)
                    if (s.contains("(")) {
                        throw new InvalidLdapFormatException("Malformed LDAP filter: nvalid operator.");
                    }

                    tokens.add(s);
                    pos += s.length();
                }
            }
        }

        return tokens;
    }

    /**
     * Method to read an operator from the specified position in filter.<br />
     * Operator can be simple (&, |, !, =) or composed (<=, >=, ~=).
     * 
     * @param filter filter
     * @param position beginning read position in filter
     * @return a string containing the operator or {@literal empty string} if no operator to read
     */
    protected String readOperator(String filter, int position) {
        String op = "";
        int pos = position;

        if (pos < filter.length()) {
            String s = Character.toString(filter.charAt(pos));

            if (Operators.isOperator(s)) {
                op = s;
            }

            pos += 1;
            if (pos < filter.length()) {
                String s2 = filter.substring(pos - 1, pos + 1);
                if (Operators.isOperator(s2)) {
                    op = s2;
                }
            }
        }

        return op;
    }

    /**
     * Method to create a node instance accordingly to its cardinality.
     * 
     * @param op operator to use for node creation
     * @return created node
     */
    protected IValidatorNode<String> createOperatorNode (String op) {
        IValidatorNode<String> opNode = null;
        if (UnaryOperators.isUnaryOperator(op)) {
            UnaryNode node = new UnaryNode(op);
            // notify registered handlers
            for (ILdapHandler handler : handlers) {
                handler.onUnaryNodeCreation(node);
            }
            opNode = node;
        } else if (BinaryOperators.isBinaryOperator(op)) {
            BinaryNode node = new BinaryNode(op);
            // notify registered handlers
            for (ILdapHandler handler : handlers) {
                handler.onBinaryNodeCreation(node);
            }
            opNode = node;
        } else if (NaryOperators.isNaryOperator(op)) {
            NaryNode node = new NaryNode(op);
            // notify registered handlers
            for (ILdapHandler handler : handlers) {
                handler.onNaryNodeCreation(node);
            }
            opNode = node;
        }
        
        return opNode;
    }

    /**
     * Method to create a binary node from filter part (comparison).
     * 
     * @param filter filter to use for node creation
     * @return created node
     * @throws InvalidLdapFormatException 
     */
    protected IValidatorNode<String> createBinaryNode(String filter) throws InvalidLdapFormatException {
        // create a comparison operator node and operand nodes
        String operator = "";
        String[] operands = null;
        for (ComparisonOperators compOp : ComparisonOperators.values()) {
            operands = filter.split(compOp.getComparisonOperator());
            if (operands.length == 2) {
                operator = compOp.getComparisonOperator();
                break;
            }
        }

        BinaryNode node = new BinaryNode(operator);

        // create operand nodes
        if (!operator.isEmpty()) {
            operands = filter.split(operator);
            OperandNode left = new OperandNode(operands[0].trim());
            left.setParent(node);
            node.setLeftOperand(left);
            node.addChild(left);
            OperandNode right = new OperandNode(operands[1].trim());
            right.setParent(node);
            node.setRightOperand(right);
            node.addChild(right);
        }

        if (node.validate()) {
            // notify registered handlers
            for (ILdapHandler handler : handlers) {
                handler.onBinaryNodeCreation(node);
            }
            return node;
        } else {
            throw new InvalidLdapFormatException("Comparison operator must be applied on two operands.");
        }
    }
}