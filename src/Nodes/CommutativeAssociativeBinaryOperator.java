package Nodes;

import Constants.Values;
import Interfaces.*;
import Constants.Operators;
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
    private char arbID;

    public CommutativeAssociativeBinaryOperator(char operator, INode lhs, INode rhs, INode parent) {
        this.operator = operator;
        children = new INode[2];
        children[0] = lhs;
        children[1] = rhs;
        this.parent = parent;
        arbID = Values.NULL_CHAR;
    }

    public CommutativeAssociativeBinaryOperator(char operator, INode left, INode right) {
        this.operator = operator;
        children = new INode[2];
        children[0] = left;
        children[1] = right;
        arbID = Values.NULL_CHAR;

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
            return null; //Cannot zig root
        }
        // parent needs to be a commuitive operator AND have the same precedence
        if (!(this.parent instanceof ICommutiveOperator && Operators.precedence.get(operator) == Operators.precedence.get(this.parent.getChar()))) {
        //if (!(this.parent instanceof ICommutiveOperator && this.operator == this.getParent().getChar())) {
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

    private boolean checkEquality(INode n1, INode n2) {

        if (n1.getChar() != n2.getChar()) return false;
        if (n1.getArbID() != n2.getArbID()) return false;

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
}

