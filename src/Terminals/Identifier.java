package Terminals;

import Constants.Values;
import Interfaces.INode;
import Interfaces.IOperatorBase;

/**
 * Created by joe on 18/09/16.
 */

//an expression is  a node
public class Identifier implements INode {

    private char val;
    private INode parent;
    private char arbID;

    public Identifier(char val, INode parent) {
        this.val = val;
        this.parent = parent;
        arbID = Values.NULL_CHAR;

    }

    public Identifier(char val) {
        this.val = val;
        arbID = Values.NULL_CHAR;

    }

    public char getVal() {
        return val;
    }

    @Override
    public INode[] children() {
        return null;
    }

    @Override
    public INode getParent() {
        return  parent;
    }

    @Override
    public void setParent(INode parent) {
        this.parent = parent;
    }

    @Override
    public INode copy() {
        INode copyOfThis = new Identifier(val);
        return copyOfThis;
    }

    @Override
    public void setChildren(INode[] newKids) {
        //nmp
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
        val = c;
    }

    @Override
    public char getChar() {
        return val;
    }

    @Override
    public String toString() {
        return val+"";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Identifier ? this.val == ((Identifier)obj).val : false;
    }
}
