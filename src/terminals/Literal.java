package terminals;

import constants.Operators;
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
        this.nodeChar = (val ? Operators.TRUE : Operators.FALSE);
    }

    @Override
    public INode copySubTree() {
        INode copyOfThis = new Literal(val);
        return copyOfThis;
    }

    @Override
    public String toString() {
        return this.val+"";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Literal ? this.nodeChar.equals(((Literal)obj).nodeChar) : false;
    }
}
