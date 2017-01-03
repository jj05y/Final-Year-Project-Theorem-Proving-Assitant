package terminals;

import interfaces.INode;
import interfaces.ITerminal;
import nodes.Node;
import nodes.UnaryOperator;

/**
 * Created by joe on 03/01/17.
 */
public class ArrayAndIndex extends Node implements INode, ITerminal {
    private char arrayname;
    private char index;

    public ArrayAndIndex(char arrayname, char index) {
        this.arrayname = arrayname;
        this.index = index;
    }

    public char getIndex() {
        return index;
    }

    public char getArrayname() {
        return arrayname;
    }

    @Override
    public INode copySubTree() {
        INode copyOfThis = new ArrayAndIndex(arrayname,index);
        return  copyOfThis;
    }

    @Override
    public String toString() {
        return arrayname + "." + index;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ArrayAndIndex) {
            ArrayAndIndex other = (ArrayAndIndex) obj;
            return other.getIndex() == this.index && other.getArrayname() == this.arrayname;
        } else {
            return false;
        }
    }


}