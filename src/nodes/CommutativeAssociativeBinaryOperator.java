package nodes;

import interfaces.*;
import constants.Operators;
import workers.ExpressionBuilder;

/**
 * Created by joe on 18/09/16.
 */

//an expression is  a node
public class CommutativeAssociativeBinaryOperator extends Node implements IBinaryOperator, ICommutiveOperator, IAssociativeOperator, INode {

    //TODO detele this constructor carefully
    public CommutativeAssociativeBinaryOperator(char operator, INode lhs, INode rhs, INode parent) {
        this.nodeChar = operator;
        children = new INode[2];
        children[0] = lhs;
        children[1] = rhs;
        this.parent = parent;
    }

    public CommutativeAssociativeBinaryOperator(char operator, INode left, INode right) {
        this.nodeChar = operator;
        children = new INode[2];
        children[0] = left;
        children[1] = right;
    }

    @Override
    public ICommutiveOperator swapLhsRhs() {
        INode temp = children[0];
        children[0] = children[1];
        children[1] = temp;
        return this;
    }

    @Override
    public INode addBrackets() {
        NodeForBrackets foo = new NodeForBrackets(this, this.parent);
        if (this.parent == null) { //is root node
            this.parent = foo;
        } else {
            if (this.parent.children()[0] == this) {
                this.parent.children()[0] = foo;
            } else {
                this.parent.children()[1] = foo;
            }
            this.parent = foo;
        }
        return foo;
    }

    @Override
    public INode removeBrackets() {
        if (parent instanceof NodeForBrackets) {
            ((NodeForBrackets) parent).removeBrackets();
        } else {
            //TODO raise exception
            System.out.println("boop: No brackets to remove");
        }
        return this;
    }


    @Override
    public CommutativeAssociativeBinaryOperator copySubTree() {
        INode left = children()[0].copySubTree();
        INode right = children()[1].copySubTree();
        CommutativeAssociativeBinaryOperator copyOfThis = new CommutativeAssociativeBinaryOperator(nodeChar, left, right);
        left.setParent(copyOfThis);
        right.setParent(copyOfThis);
        return copyOfThis;
    }




    @Override
    public boolean hasOperator(char otherOperator) {
        return nodeChar == otherOperator;
    }

    @Override
    public String toString() {
        return ExpressionBuilder.GetExpression(this);
    }

    public ICommutiveOperator zig() {
        if (this.parent == null) {
            // System.out.println("boop: Can't zig root of expression");
            return null; //Cannot zig root
        }
        // parent needs to be a commuitive nodeChar AND have the same precedence
        if (!(this.parent instanceof ICommutiveOperator && Operators.precedence.get(nodeChar) == Operators.precedence.get(this.parent.getNodeChar()))) {
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
        return obj instanceof CommutativeAssociativeBinaryOperator ? checkEquality(this, (INode) obj) : false;
    }



}

