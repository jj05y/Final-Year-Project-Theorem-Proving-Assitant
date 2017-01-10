package trees;

import constants.Operators;
import interfaces.INode;
import nodes.BinaryOperator;
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
}
