package terminals;

import constants.Operators;
import interfaces.INode;
import interfaces.ITerminal;
import nodes.Node;

import java.util.List;
import java.util.Vector;

/**
 * Created by joe on 02/01/17.
 */

public class QuantifiedExpr extends Node implements INode, ITerminal {

    private char op;
    private List<Character> dummys;
    private INode range;
    private INode term;

    public QuantifiedExpr(char op, List<Character> dummys, INode range, INode term) {
        this.op = op;
        this.dummys = dummys;
        this.range = range;
        this.term = term;
    }

    @Override
    public INode copySubTree() {
        INode copyOfThis = new QuantifiedExpr(op, new Vector<>(dummys), range.copyWholeTree(), term.copyWholeTree());
        return copyOfThis;
    }

    @Override
    public String toString() {
        String dummyString = "";
        for (Character c : dummys) dummyString += c +",";
        dummyString = dummyString.substring(0, dummyString.length()-1);

        String string = Operators.LANGLE + " " + op + " " + dummyString + " : " + range + " : " + term + " " + Operators.RANGLE;
        return string;
    }


}

