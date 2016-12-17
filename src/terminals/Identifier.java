package terminals;

import interfaces.INode;
import nodes.Node;

/**
 * Created by joe on 18/09/16.
 */

//an expression is  a node
public class Identifier extends Node implements INode {

    //TODO delete this constructor
    public Identifier(char val, INode parent) {
        this.nodeChar = val;
        this.parent = parent;

    }

    public Identifier(char val) {
        this.nodeChar = val;
    }

    @Override
    public INode copySubTree() {
        INode copyOfThis = new Identifier(nodeChar);
        return copyOfThis;
    }

    @Override
    public String toString() {
        return nodeChar+"";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Identifier ? this.nodeChar == ((Identifier)obj).nodeChar : false;
    }
}
