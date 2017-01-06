package terminals;

import interfaces.INode;
import interfaces.ITerminal;
import nodes.Node;
import nodes.UnaryOperator;

/**
 * Created by joe on 03/01/17.
 */
public class ArrayAndIndex extends Node implements INode, ITerminal {
    private String arrayname;
    private String index;

    public ArrayAndIndex(String arrayname, String index) {
        this.arrayname = arrayname;
        this.index = index;
        this.nodeChar = this.toString();
    }

    public String getIndex() {
        return index;
    }

    public String getArrayname() {
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
            return this.nodeChar.equals(other.nodeChar);
        } else {
            return false;
        }
    }


}