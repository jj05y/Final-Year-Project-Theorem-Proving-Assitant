package nodes;

import interfaces.IBrackets;
import interfaces.INode;
import workers.ExpressionBuilder;

/**
 * Created by joe on 18/09/16.
 */

//an expression is  a node
public class NodeForBrackets extends Node implements IBrackets, INode {

    //TODO delete this constructor carefully
    public NodeForBrackets(INode child, INode parent) {
        children = new INode[1];
        children[0] = child;
        this.parent = parent;
        nodeChar = '(';
    }

    public NodeForBrackets(INode child) {
        children = new INode[1];
        children[0] = child;
        nodeChar = '(';
    }

    @Override
    public INode copySubTree() {
        INode copyOfThis =  new NodeForBrackets(children[0].copySubTree());
        copyOfThis.children()[0].setParent(copyOfThis);
        return copyOfThis;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NodeForBrackets ? checkEquality(this, (INode) obj):false;
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

