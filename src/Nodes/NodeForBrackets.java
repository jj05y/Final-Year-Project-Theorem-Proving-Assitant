package Nodes;

import Constants.Values;
import Interfaces.IBrackets;
import Interfaces.INode;
import Interfaces.IOperatorBase;
import Terminals.Identifier;
import Workers.ExpressionBuilder;

/**
 * Created by joe on 18/09/16.
 */

//an expression is  a node
public class NodeForBrackets implements IBrackets, INode {

    private INode[] children;
    private INode parent;
    private char arbID;


    public NodeForBrackets(INode child, INode parent) {
        children = new INode[1];
        children[0] = child;
        this.parent = parent;
        arbID = Values.NULL_CHAR;

    }

    public NodeForBrackets(INode child) {
        children = new INode[1];
        children[0] = child;
        arbID = Values.NULL_CHAR;

    }

    @Override
    public INode[] children() {
        return children;
    }

    @Override
    public INode getParent() {
        return parent;
    }

    @Override
    public void setParent(INode parent) {
        this.parent = parent;
    }

    @Override
    public INode copy() {
        INode copyOfThis =  new NodeForBrackets(children[0].copy());
        copyOfThis.children()[0].setParent(copyOfThis);
        return copyOfThis;
    }

    @Override
    public void setChildren(INode[] newKids) {
        children = newKids;
    }

    @Override
    public char getArbID() {
        return arbID;
    }

    @Override
    public void setArbID(char c) {
        arbID = c;
    }

    @Override
    public void setChar(char c) {

    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NodeForBrackets ? checkEquality(this, (INode) obj):false;
    }

    private boolean checkEquality(INode n1, INode n2) {

        if (n1.getChar() != n2.getChar()) return false;
        if (n1.getArbID() != n2.getArbID()) return false;

        if (n1 instanceof Identifier || n2 instanceof Identifier) {
            return n1.getChar() == n2.getChar();
        }

        boolean leftChildEqual = checkEquality(n1.children()[0], n2.children()[0]);
        boolean rightChildEqual = true;

        if (n1.children().length > 1 && n2.children().length > 1) {
            rightChildEqual = checkEquality(n1.children()[1], n2.children()[1]);
        } else if (n1.children().length != n2.children().length) {
            return false;
        }
        return leftChildEqual && rightChildEqual;

    }



    @Override
    public char getChar() {
        return '(';
    }

    @Override
    public INode removeBrackets() {
        children[0].setParent(parent);
        if (parent == null) {
            children()[0].setParent(null);
            return children()[0];
        }
        if (parent.children()[0] == this) {
            parent.children()[0] = children()[0];
        } else {
            parent.children()[1] = children()[0];
        }
        return children()[0];
    }

    @Override
    public String toString() {
        return ExpressionBuilder.GetExpression(this);
    }

}

