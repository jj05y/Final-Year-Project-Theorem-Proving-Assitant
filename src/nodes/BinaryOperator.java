package nodes;

import interfaces.*;
import constants.Operators;
import workers.ExpressionBuilder;

/**
 * Created by joe on 18/09/16.
 */

//an expression is  a node
public class BinaryOperator extends Node implements IBinaryOperator, INode {

    //TODO detele this constructor carefully
    public BinaryOperator(String operator, INode lhs, INode rhs, INode parent) {
        this.nodeChar = operator;
        children = new INode[2];
        children[0] = lhs;
        children[1] = rhs;
        this.parent = parent;
    }

    public BinaryOperator(String operator, INode left, INode right) {
        this.nodeChar = operator;
        children = new INode[2];
        children[0] = left;
        children[1] = right;
    }




    @Override
    public BinaryOperator copySubTree() {
        INode left = children()[0].copySubTree();
        INode right = children()[1].copySubTree();
        BinaryOperator copyOfThis = new BinaryOperator(nodeChar, left, right);
        left.setParent(copyOfThis);
        right.setParent(copyOfThis);
        return copyOfThis;
    }


    @Override
    public String toString() {
        return ExpressionBuilder.GetExpression(this);
    }

    public INode zig() {
        if (this.parent == null) {
            // System.out.println("boop: Can't zig root of expression");
            return null; //Cannot zig root
        }
        // parent needs to be a binary op, nodeChar AND have the same precedence
        if (!(this.parent instanceof IBinaryOperator && Operators.precedence.get(nodeChar) == Operators.precedence.get(this.parent.getNodeChar()))) {
        //if (!(this.parent instanceof ICommutiveOperator && this.nodeChar == this.getParent().getNodeChar())) {
                return null; //Cannot zig
        }

        if (this == this.parent.children()[0]) {
            rotateRight();
        } else if (this == this.parent.children()[1]) {
            rotateLeft();
        }
        return this;
    }

    private void rotateRight() {
        INode exParent = this.parent;
        INode subtreeParent = exParent.getParent();

        // Move "node"'s right subtree to its former parent.
        exParent.children()[0] = children()[1];
        if (children()[1] != null) {
            children()[1].setParent(exParent);
        }

        // Make exParent become a child of "node".
        children()[1] = exParent;
        exParent.setParent(this);

        // Make "node" become a child of exParent's former parent.
        parent = subtreeParent;
        if (subtreeParent == null) {
            //TODO need to handle
            //Dont need to handle if always have an elevated root
        } else if (subtreeParent.children()[0] == exParent) {
            subtreeParent.children()[0] = this;
        } else {
            subtreeParent.children()[1] = this;
        }
    }


    private void rotateLeft() {
        INode exParent = this.parent;
        INode subtreeParent = exParent.getParent();

        // Move "node"'s left subtree to its former parent.
        exParent.children()[1] = children()[0];
        if (children()[0] != null) {
            children()[0].setParent(exParent);
        }

        // Make exParent become a child of "node".
        children()[0] = exParent;
        exParent.setParent(this);

        // Make "node" become a child of exParent's former parent.
        parent = subtreeParent;
        if (subtreeParent == null) {
            //again, issues unless have elevated root
        } else if (subtreeParent.children()[0] == exParent) {
            subtreeParent.children()[0] = this;
        } else {
            subtreeParent.children()[1] = this;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BinaryOperator ? checkEquality(this, (INode) obj) : false;
    }



}

