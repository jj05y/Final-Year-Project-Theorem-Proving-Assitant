package trees;

import constants.Operators;
import interfaces.INode;
import nodes.BinaryOperator;
import parser.Parser;
import terminals.Identifier;

/**
 * Created by joe on 10/01/17.
 */
public class LatticeTrees {

    public static INode defUp() {
        INode lhs = new BinaryOperator(Operators.UP, new Identifier("x"), new Identifier("y"));
        lhs = new BinaryOperator(Operators.UNDER, lhs, new Identifier("z"));
        INode rhs = new BinaryOperator(Operators.AND, xUndery("x", "z"), xUndery("y", "z"));
        INode rule = new BinaryOperator(Operators.EQUIVAL, lhs, rhs);
        rule.tellChildAboutParent();
        return rule;
    }

    public static INode defDown() {
        INode lhs = new BinaryOperator(Operators.DOWN, new Identifier("x"), new Identifier("y"));
        lhs = new BinaryOperator(Operators.UNDER, new Identifier("z"), lhs);
        INode rhs = new BinaryOperator(Operators.AND, xUndery("z", "x"), xUndery("z", "y"));
        INode rule = new BinaryOperator(Operators.EQUIVAL, lhs, rhs);
        rule.tellChildAboutParent();
        return rule;
    }



    public static INode xUndery(String x, String y) {
        return new BinaryOperator(Operators.UNDER, new Identifier(x), new Identifier(y));
    }

    public static INode xOvery(String x, String y) {
        return new BinaryOperator(Operators.OVER, new Identifier(x), new Identifier(y));
    }

    public static INode wDef() {
        Parser parser = new Parser("<| forall x,y : : <| exists w : : <| forall z : : w under z == x under z and y under z |> |> |>");
        INode n = parser.getTree();
        return n;
    }
}

