package Terminals;

import Constants.Values;
import Interfaces.INode;
import Interfaces.IOperatorBase;
import Nodes.Node;

/**
 * Created by joe on 18/09/16.
 */

//an expression is  a node
public class Identifier extends Node implements INode {

    private char val;
    private INode parent;

    public Identifier(char val, INode parent) {
        this.val = val;
        this.parent = parent;

    }

    public Identifier(char val) {
        this.val = val;
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
    public INode copySubTree() {
        INode copyOfThis = new Identifier(val);
        return copyOfThis;
    }

    @Override
    public void setChildren(INode[] newKids) {
        //nmp
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
