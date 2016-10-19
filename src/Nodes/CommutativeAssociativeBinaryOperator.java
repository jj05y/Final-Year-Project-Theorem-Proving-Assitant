package Nodes;

import Interfaces.*;
import Terminals.Identifier;
import Workers.ExpressionBuilder;

/**
 * Created by joe on 18/09/16.
 */

//an expression is  a node
public class CommutativeAssociativeBinaryOperator implements IBinaryOperator, ICommutiveOperator, IAssociativeOperator, IOperatorBase, INode {

    private char operator;
    private INode[] children;
    private INode parent;

    public CommutativeAssociativeBinaryOperator(char operator, INode lhs, INode rhs, INode parent) {
        this.operator = operator;
        children = new INode[2];
        children[0] = lhs;
        children[1] = rhs;
        this.parent = parent;
    }

    public CommutativeAssociativeBinaryOperator(char operator, INode left, INode right) {
        this.operator = operator;
        children = new INode[2];
        children[0] = left;
        children[1] = right;
    }

    @Override
    public char getOperator() {
        return operator;
    }

    @Override
    public ICommutiveOperator swapLhsRhs() {
        INode temp = children[0];
        children[0] = children[1];
        children[1] = temp;
        return this;
    }

    @Override
    public void addBrackets() {
        NodeForBrackets foo = new NodeForBrackets(this, parent);
        if (this.parent == null) { //is root node
            System.out.println("boop: dont add brackets to root node!");
            this.parent = foo;
            this.parent.children()[0] = this;
        } else {
            if (this.parent.children()[0] == this) {
                this.parent.children()[0] = foo;
            } else {
                this.parent.children()[1] = foo;
            }
            this.parent = foo;
        }
    }

    @Override
    public void removeBrackets() {
        if (parent instanceof NodeForBrackets) {
            ((NodeForBrackets) parent).removeBrackets();
        } else {
            //TODO raise exception
            System.out.println("boop: No brackets to remove");
        }
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
    public CommutativeAssociativeBinaryOperator copy() {
        INode left = children()[0].copy();
        INode right = children()[1].copy();
        CommutativeAssociativeBinaryOperator copyOfThis = new CommutativeAssociativeBinaryOperator(operator, left, right);
        left.setParent(copyOfThis);
        right.setParent(copyOfThis);
        return copyOfThis;
    }

    @Override
    public boolean hasOperator(char otherOperator) {
        return operator == otherOperator;
    }

    @Override
    public String toString() {
        return ExpressionBuilder.GetExpression(this);
    }

    public ICommutiveOperator zig() {
        if (this.parent == null) {
            // System.out.println("boop: Can't zig root of expression");
            return this; //Cannot zig root
        }

        // needs to be a commuitive operator AND have the same operator
        //if (!(this.parent instanceof ICommutiveOperator && ((ICommutiveOperator) this.parent).hasOperator(operator))) {
        //     System.out.println("boop: Can't zig unless operators match");
        //     return; //Cannot zig
        //(  }

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

    private boolean checkEquality(INode n1, INode n2) {

        if (n1.getChar() != n2.getChar()) return false;

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

    public char getChar() {
        return operator;
    }
}

