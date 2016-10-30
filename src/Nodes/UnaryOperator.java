package Nodes;

import Constants.Values;
import Interfaces.*;
import Workers.ExpressionBuilder;

/**
 * Created by joe on 18/09/16.
 */

//an expression is  a node
public class UnaryOperator implements IUnaryOperator, IOperatorBase, INode {

    private char operator;
    private INode[] children;
    private INode parent;
    private char arbID;

    public UnaryOperator(char operator, INode child) {
        this.operator = operator;
        children = new INode[1];
        children[0] = child;
        arbID = Values.NULL_CHAR;
    }


    @Override
    public char getOperator() {
        return operator;
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
        operator = c;
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
        INode copyOfThis = new UnaryOperator(operator, children[0].copy());
        copyOfThis.children()[0].setParent(copyOfThis);
        return copyOfThis;
    }

    @Override
    public char getChar() {
        return operator;
    }

    @Override
    public String toString() {
        return ExpressionBuilder.GetExpression(this);
    }
}

