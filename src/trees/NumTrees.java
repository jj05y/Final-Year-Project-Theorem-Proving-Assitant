package trees;

import constants.Operators;
import interfaces.INode;
import nodes.BinaryOperator;
import nodes.NodeForBrackets;
import org.omg.PortableInterceptor.INACTIVE;
import terminals.Identifier;

/**
 * Created by joe on 10/01/17.
 */
public class NumTrees {
    public static INode xLTy() {
        INode rule = new BinaryOperator(Operators.LT, new Identifier("X"), new Identifier("Y"));
        rule.tellChildAboutParent();
        return rule;
    }

    public static INode xGTEy() {
        INode rule = new BinaryOperator(Operators.GTE, new Identifier("X"), new Identifier("Y"));
        rule.tellChildAboutParent();
        return rule;
    }

    public static INode floorDef() {
        INode lhs = new BinaryOperator(Operators.LTE, new Identifier("n"), new NodeForBrackets(new Identifier("x"), Operators.LFLOOR));
        INode rhs = new BinaryOperator(Operators.LTE, new Identifier("n"), new Identifier("x"));
        INode rule = new BinaryOperator(Operators.EQUIVAL, lhs, rhs);
        rule.tellChildAboutParent();
        return rule;
    }

    public static INode ceilingDef() {
        INode lhs = new BinaryOperator(Operators.LTE, new NodeForBrackets(new Identifier("x"), Operators.LCEILING), new Identifier("n"));
        INode rhs = new BinaryOperator(Operators.LTE, new Identifier("x"), new Identifier("n"));
        INode rule = new BinaryOperator(Operators.EQUIVAL, lhs, rhs);
        rule.tellChildAboutParent();
        return rule;
    }
}
