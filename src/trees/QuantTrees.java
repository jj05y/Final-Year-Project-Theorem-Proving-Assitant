package trees;

import constants.Operators;
import interfaces.INode;
import nodes.BinaryOperator;
import terminals.Identifier;

/**
 * Created by joe on 04/01/17.
 */
public class QuantTrees {

    public static INode XorAllRiFi() {
        INode xOrAllRiFi = new BinaryOperator(Operators.OR, new Identifier('X'), QuantExprs.allRiFi());
        xOrAllRiFi.tellChildAboutParent();
        return xOrAllRiFi;
    }

    public static INode orOverForAll() {
        INode orOverForAll = new BinaryOperator(Operators.EQUIVAL, XorAllRiFi(), QuantExprs.allRiXorFi());
        orOverForAll.tellChildAboutParent();
        return orOverForAll;

    }

    public static INode XorAllRiXorFi(){
        INode xorAllRiXorFi = new BinaryOperator(Operators.OR, new Identifier('X'), QuantExprs.allRiXorFi());
        xorAllRiXorFi.tellChildAboutParent();
        return xorAllRiXorFi;
    }

}