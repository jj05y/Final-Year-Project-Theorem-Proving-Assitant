package nodes;

import interfaces.*;
import workers.ExpressionBuilder;

/**
 * Created by joe on 18/09/16.
 */

//an expression is  a node
public class UnaryOperator extends Node implements IUnaryOperator, INode {

    public UnaryOperator(char operator, INode child) {
        this.nodeChar = operator;
        children = new INode[1];
        children[0] = child;
    }

    @Override
    public INode copySubTree() {
        INode copyOfThis = new UnaryOperator(nodeChar, children[0].copySubTree());
        copyOfThis.children()[0].setParent(copyOfThis);
        return copyOfThis;
    }

    @Override
    public String toString() {
        return ExpressionBuilder.GetExpression(this);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UnaryOperator ? checkEquality(this, (INode) obj):false;
    }
}

