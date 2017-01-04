package trees;

import constants.Operators;
import interfaces.INode;
import nodes.BinaryOperator;
import terminals.Identifier;

/**
 * Created by joe on 04/01/17.
 */
public class QuantTrees {

    public static INode orOverForAll() {
        INode lhs = new BinaryOperator(Operators.AND, new Identifier('X'), QuantExprs.allRiFi());
        INode orOverForAll = new BinaryOperator(Operators.EQUIVAL, lhs, QuantExprs.allRiFiAndX());
        orOverForAll.tellChildAboutParent();
        return orOverForAll;

    }

}