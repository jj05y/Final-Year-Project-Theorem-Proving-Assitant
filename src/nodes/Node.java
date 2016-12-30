package nodes;

import gui.core.Bit;
import interfaces.INode;
import terminals.Identifier;

import java.util.Stack;

/**
 * Created by joe on 12/12/16.
 */
public abstract class Node implements INode {

    protected char nodeChar;
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
            if (foo.getParent().children()[0] == foo){
                pathToRoot.push(0);
                foo = foo.getParent();
            } else if (foo.getParent().children().length>1 && foo.getParent().children()[1] == foo) {
                pathToRoot.push(1);
                foo = foo.getParent();
            } else {
                //TODO exception
                //i believe the exception is almost impossible, i guess break will do for now
               // System.out.println("broken");
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

        if (n1 instanceof Identifier || n2 instanceof Identifier) {
            return n1.getNodeChar() == n2.getNodeChar();
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
    public char getNodeChar() {
        return nodeChar;
    }

    @Override
    public void setChildren(INode[] newKids) {
        children = newKids;
    }


    @Override
    public void setNodeChar(char c) {
        nodeChar = c;
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
        for (INode child: children) {
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
        if (parent instanceof NodeForBrackets) {
            ((NodeForBrackets) parent).removeBrackets();
        } else {
            //TODO raise exception
            System.out.println("boop: No brackets to remove");
        }
        return this;
    }
}
