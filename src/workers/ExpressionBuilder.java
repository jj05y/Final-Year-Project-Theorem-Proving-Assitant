package workers;

import interfaces.*;
import terminals.Identifier;


/**
 * Created by joe on 18/09/16.
 */
public class ExpressionBuilder {

    public static String GetExpression(INode root) {
        return build(root, "");
    }

    private static String build(INode node, String expr) {
        if (node instanceof Identifier) {
            return expr + node.getNodeChar();
        } else if (node instanceof IBinaryOperator) {
            return  expr  +
                    build(node.children()[0], expr) +  " " +
                    node.getNodeChar() +  " " +
                    build(node.children()[1], expr);
        } else if (node instanceof IUnaryOperator) {
            return expr + node.getNodeChar() +
                    build(node.children()[0], expr);
        } else if (node instanceof IBrackets) {
            return expr + "(" +
                    build(node.children()[0], expr) + ")";
        } else {
            //TODO raise exception
            return "";
        }
    }





}
