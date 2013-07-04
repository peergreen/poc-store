package com.peergreen.store.ldap.parser.impl;

import java.io.PrintStream;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import com.peergreen.store.ldap.parser.ILdapParser;
import com.peergreen.store.ldap.parser.InvalidLdapFormatException;
import com.peergreen.store.ldap.parser.enumeration.BinaryOperators;
import com.peergreen.store.ldap.parser.enumeration.ComparisonOperators;
import com.peergreen.store.ldap.parser.enumeration.NaryOperators;
import com.peergreen.store.ldap.parser.enumeration.Operators;
import com.peergreen.store.ldap.parser.enumeration.UnaryOperators;
import com.peergreen.store.ldap.parser.node.BinaryNode;
import com.peergreen.store.ldap.parser.node.IValidatorNode;
import com.peergreen.store.ldap.parser.node.NaryNode;
import com.peergreen.store.ldap.parser.node.OperandNode;
import com.peergreen.store.ldap.parser.node.UnaryNode;
import com.peergreen.tree.Node;
import com.peergreen.tree.node.SimpleNode;
import com.peergreen.tree.visitor.print.TreePrettyPrintNodeVisitor;

/**
 * Tool class to parse a LDAP filter to produce a tree.
 * 
 * @author Guillaume Dupraz Canard
 */
@Component
@Instantiate
@Provides
public class DefaultLdapParser implements ILdapParser {

    private static TreePrettyPrintNodeVisitor<String> visitor = new TreePrettyPrintNodeVisitor<String>(System.out) {
        @Override
        protected void doPrintInfo(PrintStream stream, Node<String> node) {
            stream.printf("%s%n", node.getData());
        }
    };

    public static void main(String[] args) {
        DefaultLdapParser app = new DefaultLdapParser();

//        String f = "(|(&(a=p)(|(b=p)(c=p)))(&(d=p)(e=p))(|(f=v)(g=x)))";
        String f = "(&(groupId<=com.peergreen.store)(artifactId>=controller)(version=1.6.0))";
//        String f = "(&(!(a<=2)))";

        try {
            Node<String> res = app.parse(f);
            res.walk(visitor);
        } catch (InvalidLdapFormatException e) {
            e.printStackTrace();
        }
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
    public Node<String> parse(String filter) throws InvalidLdapFormatException {
        // only check if expression start and finish with parenthesis
        String regex = "^[(].*[)]$";

        Node<String> root = null;
        
        if (filter.matches(regex)) {
            root = parseNode(filter, 0, null);
        } else {
            throw new InvalidLdapFormatException("LDAP expression must begin and finish with parenthesis");
        }

//        checkTree(root);

        return root;
    }

    /**
     * Recursive method to parse a LDAP filter to a tree.
     * 
     * @param filter LDAP filter
     * @return tree constructed from LDAP filter parsing.
     * @throws InvalidLdapFormatException
     */
    protected Node<String> parseNode(String filter, int position, Node<String> parentNode) throws InvalidLdapFormatException {
        SimpleNode<String> currentNode = null;
        
        if (position < filter.length()) {
            String innerText = getInnerText(filter, position);
            
            if (parentNode == null) {
                // root node must be an operator
                String op = readOperator(innerText, 0);

                if (!op.isEmpty()) {
                    // create root node
                    IValidatorNode<String> opNode = createOperatorNode(op);
                    
                    parseNode(filter, position + op.length() + 1, opNode);

                    if (opNode.validate()) {
                        return opNode;
                    } else {
                        throw new InvalidLdapFormatException();
                    }
                } else {
                    throw new InvalidLdapFormatException("First character after initial opening parenthesis must be an operator.");
                    // TODO Can be a leaf too. ex: filter (artifactId=Store)
                }
            } else {
                if (!innerText.isEmpty()) {
                    char c = innerText.charAt(0);
                
                    if (c == '(') {
                        throw new InvalidLdapFormatException("Cannot have two following parenthesis.");
                    } else if(c == ')') {
                        if (parentNode.getParent() == null && (filter.length() - position) > 1) {
                            throw new InvalidLdapFormatException("Superfluous parenthesis at the tail");
                        }
        
                        // go back one level in the tree
                        parseNode(filter, position + 1, parentNode.getParent());
                        
                        return parentNode;
                    } else {
                        Node<String> n = parseOperatorNode(innerText, parentNode);
                        if (n != null) {
                            return n;
                        } else {
                            // create an comparison operator node and operand nodes
                            String operator = "";
                            String[] operands = null;
                            for (ComparisonOperators compOp : ComparisonOperators.values()) {
                                operands = innerText.split(compOp.getComparisonOperator());
                                if (operands.length == 2) {
                                    operator = compOp.getComparisonOperator();
                                    break;
                                }
                            }
                            
                            BinaryNode<String> operatorNode = new BinaryNode<String>(operator);
                            
                            // create operand nodes
                            if (!operator.isEmpty()) {
                                operands = innerText.split(operator);
                                OperandNode<String> left = new OperandNode<String>(operands[0]);
                                left.setParent(operatorNode);
                                OperandNode<String> right = new OperandNode<String>(operands[1]);
                                right.setParent(operatorNode);
                            }

                            if (operatorNode.validate()) {
                                parentNode.getChildren().add(operatorNode);
                                operatorNode.setParent(parentNode);
                            } else {
                                throw new InvalidLdapFormatException("Comparison operator must be applied on two operands.");
                            }
                            
                            // parse rest of filter
                            parseNode(filter, position + innerText.length() + 2, parentNode);
                            
                            return operatorNode;
                        }
                    }
                } else /* if (innerText.isEmpty()) */ {
                    if (filter.charAt(position) == ')') {
                        // go back one level in the tree
                        parseNode(filter, position + 1, parentNode.getParent());
                        
                        return parentNode;
                    } else {
                        throw new InvalidLdapFormatException("Missing closing parenthesis");
                    }
                }
            } // end if (parentNode == null)
        } else {
            if (parentNode != null) {
                throw new InvalidLdapFormatException("Missing closing parenthesis");
            }
            
            return parentNode;
        }
    }

    /**
     * Method to read an operator from the specified position in filter.<br />
     * Operator can be simple (&, |, !) or composed (<=, >=, ~=).<br />
     * Be careful, you have to increase position by the length of returned operator.
     * 
     * @param filter filter
     * @param position beginning read position in filter
     * @return a string containing the operator or {@literal empty string} if no operator to read
     */
    protected String readOperator(String filter, int position) {
        String op = "";

        if (position < filter.length()) {
            String s = Character.toString(filter.charAt(position));
            
            if (Operators.isOperator(s)) {
                op = s;
            }
            
            position += 1;
            if (position < filter.length()) {
                String s2 = filter.substring(position - 1, position + 1);
                if (Operators.isOperator(s2)) {
                    op = s2;
                }
            }
        }

        return op;
    }

    /**
     * Method to parse a node and its children.
     * 
     * @param filter filter to parse
     * @param parentNode parent node
     * @return operator node parsed (with its children)
     * @throws InvalidLdapFormatException
     */
    protected IValidatorNode<String> parseOperatorNode(String filter, Node<String> parentNode) throws InvalidLdapFormatException {
        if (filter.length() > 5) /* minimum length ex: &(x=y) */ {
            String op = readOperator(filter, 0);
            
            // operator case
            if (!op.isEmpty()) {
                // create operator node
                IValidatorNode<String> opNode = createOperatorNode(op);
                                            
                // recursive call to parse children
                parseNode(filter, op.length() + 1, opNode);
                
                if (opNode.validate()) {
                    opNode.setParent(parentNode);
                    return opNode;
                } else {
                    throw new InvalidLdapFormatException("Invalid node.");
                }
            } else {
                return null;
            }
        } else {
            throw new InvalidLdapFormatException("Not enought characters to parse an operator and its operands.");
        }
    }
    
    protected IValidatorNode<String> parseLeaf(String filter, Node<String> parentNode) throws InvalidLdapFormatException {
        int position = 0;
        
        IValidatorNode<String> n = parseOperatorNode(filter, parentNode);
        if (n != null) {
            return n;
        } else {
            // create an comparison operator node and operand nodes
            String operator = "";
            String[] operands = null;
            for (ComparisonOperators compOp : ComparisonOperators.values()) {
                operands = filter.split(compOp.getComparisonOperator());
                if (operands.length == 2) {
                    operator = compOp.getComparisonOperator();
                    break;
                }
            }
            
            BinaryNode<String> operatorNode = new BinaryNode<String>(operator);
            
            // create operand nodes
            if (!operator.isEmpty()) {
                operands = filter.split(operator);
                OperandNode<String> left = new OperandNode<String>(operands[0]);
                left.setParent(operatorNode);
                OperandNode<String> right = new OperandNode<String>(operands[1]);
                right.setParent(operatorNode);
            }

            if (operatorNode.validate()) {
                operatorNode.setParent(parentNode);
            } else {
                throw new InvalidLdapFormatException("Comparison operator must be applied on two operands.");
            }
            
            // parse rest of filter
            parseNode(filter, position + filter.length() + 2, parentNode);
            
            return operatorNode;
        }
    }
    
    /**
     * Method to retrieve text between parenthesis.<br />
     * Take all inner parenthesis blocks.<br />
     * For example, getInnerText("(x=((a+b)&c))", 3) will return "(a+b)&c"
     * 
     * @param filter filter (LDAP like) from which extract inner parenthesis text
     * @param position opening parenthesis position
     * @return inner parenthesis text, {@literal empty string} if incorrect position or no inner text
     */
    protected String getInnerText(String filter, int position) throws InvalidLdapFormatException {
        String innerText = "";
        int openedBr = 0;
        int loop = 0;

        if (position < filter.length()) {
            do {
                char c = filter.charAt(position+loop);
                if (c == ')') {
                    openedBr -= 1;
                } else if (c == '(') {
                    openedBr += 1;
                }

                // if first parenthesis not already closed, compose innerText
                if (openedBr > 0 && loop != 0) {
                    innerText += c;
                }

                loop++;
            } while(position + loop < filter.length() && openedBr != 0);
        }

        if (((position + loop) == filter.length() - 1) && openedBr != 0) {
            throw new InvalidLdapFormatException("Missing closing parenthesis.");
        }

        return innerText;
    }

    protected IValidatorNode<String> createOperatorNode (String op) {
        IValidatorNode<String> opNode = null;
        if (UnaryOperators.isUnaryOperator(op)) {
            opNode = new UnaryNode<String>(op);
            // TODO add handler
        } else if (BinaryOperators.isBinaryOperator(op)) {
            opNode = new BinaryNode<String>(op);
            // TODO add handler
        } else if (NaryOperators.isNaryOperator(op)) {
            opNode = new NaryNode<String>(op);
            // TODO add handler
        }
        
        return opNode;
    }
    
    /**
     * Method to check if tree is valid.<br />
     * Verify every operator node has at least two operands.
     * 
     * @param node root node of tree to check
     * @return {@literal true} if tree is valid, {@literal false} otherwise
     * @throws InvalidLdapFormatException
     */
    protected boolean checkTree(Node<String> node) throws InvalidLdapFormatException {
//        if (node.getData().isOperator()) {
//            if (node.getData().getOperandsNb() < 2) {
//                throw new InvalidLdapFormatException("Operator must be applied on at least two operands");
//            }
//        }
//
//        List<Node<Element>> children = node.getChildren();
//        Iterator<Node<Element>> it = children.iterator();
//        while(it.hasNext()) {
//            checkTree(it.next());
//        }

        return true;
    }

}