package trees;

import constants.Operators;
import interfaces.INode;
import nodes.BinaryOperator;
import terminals.ArrayAndIndex;
import terminals.Identifier;

/**
 * Created by joe on 04/01/17.
 */
public class QuantTrees {

    public static INode XorAllRiFi() {
        INode xOrAllRiFi = new BinaryOperator(Operators.OR, new Identifier("X"), QuantExprs.allRiFi());
        xOrAllRiFi.tellChildAboutParent();
        return xOrAllRiFi;
    }

    public static INode QuantEquivX(){
        INode rule = new BinaryOperator(Operators.EQUIVAL, QuantExprs.allRiXorFi(), new Identifier("X"));
        rule.tellChildAboutParent();
        return rule;
    }

    public static INode orOverForAll(){
        INode orOverForAll = new BinaryOperator(Operators.EQUIVAL, XorAllRiFi(), QuantExprs.allRiXorFi());
        orOverForAll.tellChildAboutParent();
        return orOverForAll;
    }

    public static INode XorAllRiXorFi(){
        INode xorAllRiXorFi = new BinaryOperator(Operators.OR, new Identifier("X"), QuantExprs.allRiXorFi());
        xorAllRiXorFi.tellChildAboutParent();
        return xorAllRiXorFi;
    }

    public static INode XorAllRiYorFi(){
        INode xorAllRiYorFi = new BinaryOperator(Operators.OR, new Identifier("X"), QuantExprs.allRiYorFi());
        xorAllRiYorFi.tellChildAboutParent();
        return xorAllRiYorFi;
    }

    public static INode ruleWithTwoQuants() {
        INode rule = new BinaryOperator(Operators.EQUIVAL, quantAndQuantAltX(), new Identifier("X"));
        rule.tellChildAboutParent();
        return rule;
    }

    private static INode quantAndQuant() {
        INode qq = new BinaryOperator(Operators.AND, QuantExprs.allRiFi(), QuantExprs.allSiFi());
        qq.tellChildAboutParent();
        return qq;
    }

    private static INode quantAndQuantAltX() {
        INode qq = new BinaryOperator(Operators.AND, QuantExprs.allSiFi(),QuantExprs.allRiXorFi());
        qq.tellChildAboutParent();
        return qq;
    }
    private static INode quantAndQuantAltY() {
        INode qq = new BinaryOperator(Operators.AND, QuantExprs.allSiFi(),QuantExprs.allRiYorFi());
        qq.tellChildAboutParent();
        return qq;
    }


    public static INode exprWithTwoQuants() {
        INode expr = new BinaryOperator(Operators.AND, quantAndQuantAltY(), new Identifier("Y"));
        expr.tellChildAboutParent();
        return expr;
    }

    public static INode XorFi() {
        INode expr = new BinaryOperator(Operators.OR, new Identifier("X"), new ArrayAndIndex("f","i"));
        expr.tellChildAboutParent();
        return expr;
    }

    public static INode quantEquivQuant() {
        INode expr = new BinaryOperator(Operators.EQUIVAL, QuantTrees.XorAllRiXorFi(), QuantExprs.allRiXorFi());
        expr.tellChildAboutParent();
        return expr;
    }

}