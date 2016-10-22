package Workers;

import Interfaces.INode;


public class NodeAndChild {
    private INode node;
    private INode child;

    public NodeAndChild(INode node, INode child) {
        this.node = node;
        this.child = child;
    }

    public NodeAndChild() {
    }

    public INode getNode() {
        return node;
    }

    public void setNode(INode node) {
        this.node = node;
    }

    public INode getChild() {
        return child;
    }

    public void setChild(INode child) {
        this.child = child;
    }

    public INode attachChildToNode(INode newChild) {
        if (child.getParent().children()[0] == child) {
            child.getParent().children()[0] = newChild;
        } else {
            child.getParent().children()[1] = newChild;
        }
        newChild.setParent(child.getParent());

        return node;
    }
}
