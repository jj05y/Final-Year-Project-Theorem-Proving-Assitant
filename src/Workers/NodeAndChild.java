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

    public INode attachChildToNode(INode newChildofChild, int i) {

        child.children()[i] = newChildofChild;
        newChildofChild.setParent(child);

        return node;
    }
}
