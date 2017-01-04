package nodes;

import constants.Operators;
import gui.core.Bit;
import interfaces.*;
import terminals.Identifier;
import terminals.Literal;
import terminals.QuantifiedExpr;

import java.util.Stack;

/**
 * Created by joe on 12/12/16.
 */
public abstract class Node implements INode {

    protected String nodeChar;
    protected INode[] children;
    protected INode parent;
    protected Bit bit;

    public INode copyWholeTree() {
        //need to keep a reference to this! or a map to how to get here, a route
        //a stack with hold this,
        Stack<Integer> pathToRoot = new Stack<>();

        //need to find the root, and copyThat roots subTree,
        //on the way up the tree, we build the path back down to the original node
        INode foo = this;
        while (foo.getParent() != null && !(foo.getParent() instanceof QuantifiedExpr)) {
            if (foo.getParent().children()[0] == foo) {
                pathToRoot.push(0);
                foo = foo.getParent();
            } else if (foo.getParent().children().length > 1 && foo.getParent().children()[1] == foo) {
                pathToRoot.push(1);
                foo = foo.getParent();
            } else {
                //TODO exception
                //i believe the exception is almost impossible, i guess break will do for now
                System.out.println("broken in copy whole tree");
                break;
            }
        }
        //  System.out.println(pathToRoot);

        //then need to refind THIS in that copy,,, follow the map?
        INode nodeToReturn = foo.copySubTree();
        //we now have a whole new tree and a map to find where we were :O
        while (!pathToRoot.empty()) {
            nodeToReturn = nodeToReturn.children()[pathToRoot.pop()];
        }
        return nodeToReturn;
    }

    protected boolean checkEquality(INode n1, INode n2) {

        if (n1.getNodeChar() != n2.getNodeChar()) return false;

        if (n1 instanceof ITerminal || n2 instanceof ITerminal) {
            return n1.getNodeChar() == n2.getNodeChar();
        }

        //TODO if quantified expression, call extra method to check details of them

        boolean leftChildEqual = checkEquality(n1.children()[0], n2.children()[0]);
        boolean rightChildEqual = true;

        if (n1.children().length > 1 && n2.children().length > 1) {
            rightChildEqual = checkEquality(n1.children()[1], n2.children()[1]);
        } else if (n1.children().length != n2.children().length) {
            return false;
        }
        return leftChildEqual && rightChildEqual;

    }

    @Override
    public String getNodeChar() {
        return nodeChar;
    }

    @Override
    public void setChildren(INode[] newKids) {
        children = newKids;
    }


    @Override
    public void setNodeChar(String s) {
        nodeChar = s;
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
    public INode getRoot() {
        INode foo = this;
        while (!foo.isRoot()) {
            foo = foo.getParent();
        }
        return foo;
    }

    @Override
    public void tellChildAboutParent() {
        if (children == null) return;
        for (INode child : children) {
            child.setParent(this);
            child.tellChildAboutParent();
        }
    }

    @Override
    public boolean isRoot() {
        return parent == null;
    }

    @Override
    public Bit getBit() {
        return bit;
    }

    @Override
    public void setBit(Bit bit) {
        this.bit = bit;
    }


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
    public String toPlainText() {

        return walkForPlainText(this, "");
    }

    private String walkForPlainText(INode node, String expr) {
        if (node instanceof Identifier) {
            return expr + node.getNodeChar();
        } else if (node instanceof Literal) {
            return expr + (node.getNodeChar().equals(Operators.TRUE) ? "true" : "false");
        } else if (node instanceof IBinaryOperator) {
            if (node.getNodeChar().equals(Operators.AND)) {
                return expr +
                        walkForPlainText(node.children()[0], expr) + " and " +
                        walkForPlainText(node.children()[1], expr);
            } else if (node.getNodeChar().equals(Operators.OR)) {
                return expr +
                        walkForPlainText(node.children()[0], expr) + " or " +
                        walkForPlainText(node.children()[1], expr);
            } else if (node.getNodeChar().equals(Operators.IMPLICATION)) {
                return expr +
                        walkForPlainText(node.children()[0], expr) + " => " +
                        walkForPlainText(node.children()[1], expr);
            } else if (node.getNodeChar().equals(Operators.REVERSE_IMPLICATION)) {
                return expr +
                        walkForPlainText(node.children()[0], expr) + " <= " +
                        walkForPlainText(node.children()[1], expr);
            } else if (node.getNodeChar().equals(Operators.EQUIVAL)) {
                //TODO change this to == for boolean
                return expr +
                        walkForPlainText(node.children()[0], expr) + " = " +
                        walkForPlainText(node.children()[1], expr);
            } else {
                return expr + walkForPlainText(node.children()[0], expr) + " " +
                        node.getNodeChar() + " " +
                        walkForPlainText(node.children()[1], expr);
            }
        } else if (node instanceof IUnaryOperator) {
            if (node.getNodeChar().equals(Operators.NOT)) {
                return expr + "! " +
                        walkForPlainText(node.children()[0], expr);
            } else {
                return expr + node.getNodeChar() + " " +
                        walkForPlainText(node.children()[0], expr);
            }
        } else if (node instanceof IBrackets) {
            return expr + "( " +
                    walkForPlainText(node.children()[0], expr) + " )";
        } else {
            //TODO raise exception
            return "";
        }

    }
}
