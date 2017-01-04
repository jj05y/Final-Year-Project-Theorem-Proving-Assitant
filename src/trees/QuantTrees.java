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

    public static INode ruleWithTwoQuants() {
        INode rule = new BinaryOperator(Operators.EQUIVAL, quantAndQuant(), new Identifier('X'));
        rule.tellChildAboutParent();
        return rule;
    }

    private static INode quantAndQuant() {
        INode qq = new BinaryOperator(Operators.AND, QuantExprs.allRiFi(), QuantExprs.allSiFi());
        qq.tellChildAboutParent();
        return qq;
    }

    public static INode exprWithTwoQuants() {
        INode expr = new BinaryOperator(Operators.AND, quantAndQuant(), new Identifier('Y'));
        expr.tellChildAboutParent();
        return expr;
    }

}