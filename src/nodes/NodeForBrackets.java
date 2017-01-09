package nodes;

import constants.Operators;
import interfaces.INode;
import workers.ExpressionBuilder;

/**
 * Created by joe on 18/09/16.
 */

//an expression is  a node
public class NodeForBrackets extends Node implements INode {

    public NodeForBrackets(INode child, INode parent) {
        children = new INode[1];
        children[0] = child;
        this.parent = parent;
        nodeChar = Operators.LPAR;
    }

    public NodeForBrackets(INode child) {
        children = new INode[1];
        children[0] = child;
        nodeChar = Operators.LPAR;
    }

    public NodeForBrackets(INode child, String typeOfBracket) {
        children = new INode[1];
        children[0] = child;
        nodeChar = typeOfBracket;
    }

    @Override
    public INode copySubTree() {
        INode copyOfThis =  new NodeForBrackets(children[0].copySubTree(), nodeChar);
        copyOfThis.children()[0].setParent(copyOfThis);
        return copyOfThis;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NodeForBrackets ? checkEquality(this, (INode) obj):false;
    }



    @Override
    public String toString() {
        return ExpressionBuilder.GetExpression(this);
    }

}

