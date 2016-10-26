package Nodes;

import Interfaces.IBrackets;
import Interfaces.INode;
import Interfaces.IOperatorBase;
import Workers.ExpressionBuilder;

/**
 * Created by joe on 18/09/16.
 */

//an expression is  a node
public class RootNode implements INode {


    private INode[] children;


    public RootNode(INode child) {
        children = new INode[1];
        children[0] = child;
    }

    @Override
    public INode[] children() {
        return children;
    }

    @Override
    public INode getParent() {
        return null;
    }

    @Override
    public void setParent(INode parent) {
    }

    @Override
    public INode copy() {
        return new RootNode(children[0].copy());
    }

    @Override
    public char getChar() {
        return  '~';
    }

    @Override
    public void setChildren(INode[] newKids) {
        children = newKids;
    }


    @Override
    public String toString() {
        return ExpressionBuilder.GetExpression(this);
    }

}

