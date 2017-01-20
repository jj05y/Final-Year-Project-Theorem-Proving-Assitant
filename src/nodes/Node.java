package nodes;

import constants.Operators;
import gui.core.Bit;
import interfaces.*;
import terminals.ArrayAndIndex;
import terminals.Identifier;
import terminals.Literal;
import terminals.QuantifiedExpr;

import java.util.Stack;

/**
 * Created by joe on 12/12/16.
 */
public abstract class Node implements INode {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
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
        while (foo.getParent() != null) {

            //going to have to do a special case for Quant, left for range, right for term
            if (foo.getParent() instanceof QuantifiedExpr) {
                //it can only be range or term,
                if (((QuantifiedExpr) foo.getParent()).getRange() == foo) {
                    pathToRoot.push(LEFT);
                    foo = foo.getParent();
                } else if (((QuantifiedExpr) foo.getParent()).getTerm() == foo) {
                    pathToRoot.push(RIGHT);
                    foo = foo.getParent();
                }

            } else {

                if (foo.getParent().children()[0] == foo) {
                    pathToRoot.push(LEFT);
                    foo = foo.getParent();
                } else if (foo.getParent().children().length > 1 && foo.getParent().children()[1] == foo) {
                    pathToRoot.push(RIGHT);
                    foo = foo.getParent();
                }
            }
        }


        //then need to refind THIS in that copy,,, follow the map?
        INode nodeToReturn = foo.copySubTree();
        nodeToReturn.tellChildAboutParent();

        //we now have a whole new tree and a map to find where we were :O
        while (!pathToRoot.empty()) {
            if (nodeToReturn instanceof QuantifiedExpr) {
                int direction = pathToRoot.pop();
                switch (direction) {
                    case LEFT:
                        nodeToReturn = ((QuantifiedExpr) nodeToReturn).getRange();
                        break;
                    case RIGHT:
                        nodeToReturn = ((QuantifiedExpr) nodeToReturn).getTerm();
                        break;
                    default:
                        //no neeed, only zero or one is ever pushed
                }
            } else {
                nodeToReturn = nodeToReturn.children()[pathToRoot.pop()];
            }
        }
        return nodeToReturn;
    }

    protected boolean checkEquality(INode n1, INode n2) {

        if (!(n1.getNodeChar().equals(n2.getNodeChar()))) return false;

        if (n1 instanceof ITerminal || n2 instanceof ITerminal) {
            return n1.getNodeChar().equals(n2.getNodeChar());
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
        } else if (node instanceof ArrayAndIndex) {
            return expr + node.getNodeChar();
        } else if (node instanceof QuantifiedExpr) {
            QuantifiedExpr quant = (QuantifiedExpr) node;
            String quantString = "<|";
            if (quant.getQuantifier().equals(Operators.FOR_ALL)) {
                quantString += " forall ";
            } else if (quant.getQuantifier().equals(Operators.THERE_EXISTS)) {
                quantString += " exists ";
            }
            String dummies = "";
            for (String s : quant.getDummys()) {
                dummies += "," + s;
            }
            quantString += dummies.substring(1) + " : ";
            quantString += walkForPlainText(quant.getRange(), "");
            quantString += " : ";
            quantString += walkForPlainText(quant.getTerm(), "");
            quantString += " |>";

            return expr + quantString;
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
                        walkForPlainText(node.children()[0], expr) + " -> " +
                        walkForPlainText(node.children()[1], expr);
            } else if (node.getNodeChar().equals(Operators.REVERSE_IMPLICATION)) {
                return expr +
                        walkForPlainText(node.children()[0], expr) + " <- " +
                        walkForPlainText(node.children()[1], expr);
            } else if (node.getNodeChar().equals(Operators.EQUIVAL)) {
                return expr +
                        walkForPlainText(node.children()[0], expr) + " == " +
                        walkForPlainText(node.children()[1], expr);
            } else if (node.getNodeChar().equals(Operators.NOT_EQUIVAL)) {
                return expr +
                        walkForPlainText(node.children()[0], expr) + " !== " +
                        walkForPlainText(node.children()[1], expr);
            } else if (node.getNodeChar().equals(Operators.GTE)) {
                return expr +
                        walkForPlainText(node.children()[0], expr) + " >= " +
                        walkForPlainText(node.children()[1], expr);
            } else if (node.getNodeChar().equals(Operators.LTE)) {
                return expr +
                        walkForPlainText(node.children()[0], expr) + " <= " +
                        walkForPlainText(node.children()[1], expr);
            } else if (node.getNodeChar().equals(Operators.UNDER)) {
                return expr +
                        walkForPlainText(node.children()[0], expr) + " under " +
                        walkForPlainText(node.children()[1], expr);
            } else if (node.getNodeChar().equals(Operators.OVER)) {
                return expr +
                        walkForPlainText(node.children()[0], expr) + " over " +
                        walkForPlainText(node.children()[1], expr);
            } else if (node.getNodeChar().equals(Operators.UP)) {
                return expr +
                        walkForPlainText(node.children()[0], expr) + " up " +
                        walkForPlainText(node.children()[1], expr);
            } else if (node.getNodeChar().equals(Operators.DOWN)) {
                return expr +
                        walkForPlainText(node.children()[0], expr) + " down " +
                        walkForPlainText(node.children()[1], expr);
            }else if (node.getNodeChar().equals(Operators.NOT_EQUALS)) {
                return expr +
                        walkForPlainText(node.children()[0], expr) + " != " +
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
        } else if (node instanceof NodeForBrackets) {
            String openBracket = node.getNodeChar();
            String closeBracket;
            if (openBracket.equals(Operators.LFLOOR)) {
                openBracket = "|_";
                closeBracket = "_|";
            } else if (openBracket.equals(Operators.LCEILING)) {
                openBracket = "|'";
                closeBracket = "'|";
            } else {
                openBracket = "(";
                closeBracket = ")";
            }
            return expr + openBracket + " " +
                    walkForPlainText(node.children()[0], expr) + " " + closeBracket;
        } else {
            return "";
        }

    }
}
