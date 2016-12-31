package terminals;

import interfaces.INode;
import interfaces.ITerminal;
import nodes.Node;

/**
 * Created by joe on 18/09/16.
 */

//an expression is  a node
public class Literal extends Node implements INode, ITerminal {

    private boolean val;

    public Literal(boolean val) {
        this.val = val;
        this.nodeChar = (val ? 't' : 'f');
    }

    @Override
    public INode copySubTree() {
        INode copyOfThis = new Literal(val);
        return copyOfThis;
    }

    @Override
    public String toString() {
        return nodeChar+"";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Literal ? this.nodeChar == ((Literal)obj).nodeChar : false;
    }
}