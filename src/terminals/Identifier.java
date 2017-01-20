package terminals;

import interfaces.INode;
import interfaces.ITerminal;
import nodes.Node;

/**
 * Created by joe on 18/09/16.
 */

//an expression is  a node
public class Identifier extends Node implements INode, ITerminal {

    public Identifier(String val) {
        this.nodeChar = val;
    }

    @Override
    public INode copySubTree() {
        INode copyOfThis = new Identifier(nodeChar);
        return copyOfThis;
    }

    @Override
    public String toString() {
        return nodeChar;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Identifier ? this.nodeChar.equals(((Identifier)obj).nodeChar) : false;
    }
}
