package com.peergreen.store.ldap.parser.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import com.peergreen.store.ldap.parser.content.NodeContent;
import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;
import com.peergreen.tree.Node;
import com.peergreen.tree.node.SimpleNode;

/**
 * Tool class to parse a LDAP filter to produce a tree.
 * 
 * @author Guillaume Dupraz Canard
 */
@Component
@Instantiate
@Provides
public class DefaultLdapParser {

    /**
     * Method to parse a LDAP filter to a tree.<br />
     * Automatically check if output tree is valid.
     * 
     * @param filter LDAP filter
     * @return tree constructed from LDAP filter parsing.
     * @throws InvalidLdapFormatException
     */
    public Node<NodeContent> parse(String filter) throws InvalidLdapFormatException {
        // only check if expression start and finish with parenthesis
        String regex = "^[(].*[)]$";

        Node<NodeContent> root = null;
        
        if (filter.matches(regex)) {
            root = parseNode(filter, 1, null);
        } else {
            throw new InvalidLdapFormatException("LDAP expression must begin and finish with parenthesis");
        }

        checkTree(root);

        return root;
    }

    /**
     * Recursive method to parse a LDAP filter to a tree.
     * 
     * @param filter LDAP filter
     * @return tree constructed from LDAP filter parsing.
     * @throws InvalidLdapFormatException
     */
    protected Node<NodeContent> parseNode(String filter, int position, Node<NodeContent> parentNode) throws InvalidLdapFormatException {
        char character;
        NodeContent currentContent;
        SimpleNode<NodeContent> currentNode = null;

        // get the current character, if not at the end of entry
        if (position < filter.length()) {

            if (parentNode == null) {
                // root node must be an operator
                String op = readOperator(filter, position);

                if (!op.isEmpty()) {
                    // create root node
                    currentContent = new NodeContent(true, op, 0);
                    currentNode = new SimpleNode<NodeContent>(currentContent);

                    parseNode(filter, position + op.length(), currentNode);

                    return currentNode;
                } else {
                    throw new InvalidLdapFormatException("First character after initial opening parenthesis must be an operator.");
                }
            } else {
                // read the character at given position
                character = filter.charAt(position);
                // try to read an operator
                String op = readOperator(filter, position);

                if (!op.isEmpty()) {
                    // add a new operator child
                    NodeContent content = new NodeContent(true, op, 0);
                    currentNode = new SimpleNode<NodeContent>(content);
                    parentNode.getChildren().add(currentNode);
                    currentNode.setParent(parentNode);

                    // operator must be followed by open parenthesis
                    if (filter.charAt(position + op.length()) != '(') {
                        throw new InvalidLdapFormatException("Operator must be followed by an opening parenthesis");
                    } else {
                        parseNode(filter, position + op.length(), currentNode);
                    }

                    return currentNode;
                } else if ((character == '(')) {
                    // try to read an operator
                    String op2 = readOperator(filter, position + 1);

                    // if following char is an operator
                    if (!op2.isEmpty()) {
                        NodeContent content = new NodeContent(true, op2, 0);
                        currentNode = new SimpleNode<NodeContent>(content);
                        parentNode.getChildren().add(currentNode);
                        currentNode.setParent(parentNode);

                        // skip operator and continue to parse filter
                        position += op2.length();

                        // operator must be followed by open parenthesis
                        if (filter.charAt(position) != '(') {
                            throw new InvalidLdapFormatException("Operator must be followed by an opening parenthesis");
                        } else {
                            parseNode(filter, position, currentNode);
                        }

                        return currentNode;
                    } else {
                        // couldn't have 2 following parenthesis
                        if (filter.charAt(position + 1) == '(') {
                            throw new InvalidLdapFormatException("Couldn't have two following parenthesis");
                        }

                        String innerText = getInnerText(filter, position);
                        String[] operands = innerText.split("=");
                        if (operands.length != 2) {
                            throw new InvalidLdapFormatException("Leaf content must match pattern \"key=value\".");
                        }
                        
                        // create an operator node (=)
                        NodeContent opContent = new NodeContent(true, "=", 2);
                        SimpleNode<NodeContent> opNode = new SimpleNode<NodeContent>(opContent);
                        // add node to its parent node
                        parentNode.getChildren().add(opNode);
                        opNode.setParent(parentNode);
                        // increment parent operands number
                        parentNode.getData().setOperandsNb(parentNode.getData().getOperandsNb()+1);
                        
                        // add the two operands (key and value) to the parent operator node (=)
                        for (String s : operands) {
                            NodeContent content = new NodeContent(false, s, 0);
                            SimpleNode<NodeContent> node = new SimpleNode<NodeContent>(content);
                            opNode.addChild(node);
                            node.setParent(opNode);
                        }

                        parseNode(filter, position + innerText.length() + 2, parentNode);

                        return currentNode;
                    }
                } else if (character == ')') {
                    if (parentNode.getParent() == null && (filter.length() - position) > 1) {
                        throw new InvalidLdapFormatException("Superfluous parenthesis at the tail");
                    }

                    // go back one level in the tree
                    parseNode(filter, position + 1, parentNode.getParent());
                    
                    return parentNode;
                } else {
                    return null;
                }
            }
        } else { // enf if (position < filter.length())
            if (parentNode != null) {
                throw new InvalidLdapFormatException("Missing closing parenthesis");
            }
            
            return parentNode;
        }
    }

    /**
     * Method to read an operator from the specified position in filter.<br />
     * Operator can be simple (&, |, <, =, >) or composed (<=, >=).<br />
     * Be careful, you have to increase position by the length of returned operator.
     * 
     * @param filter filter
     * @param position beginning read position in filter
     * @return a string containing the operator or <em>empty string</em> if no operator to read
     */
    protected String readOperator(String filter, int position) {
        String op = "";

        if (position < filter.length()) {
            char c = filter.charAt(position);
            if (isOperator(c)) {
                op += c;
                if (++position < filter.length()) {
                    char c2 = filter.charAt(position);
                    if (isOperator(c2)) {
                        op += c2;
                    }
                }
            }
        }

        return op;
    }

    /**
     * Method to retrieve text between parenthesis.<br />
     * Take all inner parenthesis blocks.<br />
     * For example, getInnerText("(x=((a+b)&c))", 3) will return "(a+b)&c"
     * 
     * @param filter filter (LDAP like) from which extract inner parenthesis text
     * @param position opening parenthesis position
     * @return inner parenthesis text, <em>empty string</em> if incorrect position or no inner text
     */
    protected String getInnerText(String filter, int position) throws InvalidLdapFormatException {
        String innerText = "";
        int openedBr = 0;
        int loop = 0;

        if (position < filter.length()) {
            do {
                char c2 = filter.charAt(position+loop);
                if (c2 == ')') {
                    openedBr -= 1;
                } else if (c2 == '(') {
                    openedBr += 1;
                }

                // if first parenthesis not already closed, compose innerText
                if (openedBr > 0 && loop != 0) {
                    innerText += c2;
                }

                loop++;
            } while(position < filter.length() && openedBr != 0);
        }

        if (((position + loop) == filter.length() - 1) && openedBr != 0) {
            throw new InvalidLdapFormatException("Missing closing parenthesis.");
        }

        return innerText;
    }

    /**
     * Method to check if a character is an operator.
     * 
     * @param c character to check
     * @return <em>true</em> if the character is an operator (or part of a composed operator), <em>false</em> otherwise
     */
    protected boolean isOperator(char c) {
        return ((c == '&') || (c == '|') || (c == '<') || (c == '=') || (c == '>'));
    }

    /**
     * Method to check if tree is valid.<br />
     * Verify every operator node has at least two operands.
     * 
     * @param node root node of tree to check
     * @return <em>true</em> if tree is valid, <em>false</em> otherwise
     * @throws InvalidLdapFormatException
     */
    protected boolean checkTree(Node<NodeContent> node) throws InvalidLdapFormatException {
        if (node.getData().isOperator()) {
            if (node.getData().getOperandsNb() < 2) {
                throw new InvalidLdapFormatException("Operator must be applied on at least two operands");
            }
        }

        List<Node<NodeContent>> children = node.getChildren();
        Iterator<Node<NodeContent>> it = children.iterator();
        while(it.hasNext()) {
            checkTree(it.next());
        }

        return true;
    }

}