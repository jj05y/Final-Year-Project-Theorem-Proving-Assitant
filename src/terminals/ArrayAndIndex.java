package terminals;

import interfaces.INode;
import interfaces.ITerminal;
import nodes.Node;

/**
 * Created by joe on 03/01/17.
 */
public class ArrayAndIndex extends Node implements INode, ITerminal {
    char arrayname;
    char index;

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
}