package Workers;

import Constants.Values;
import Interfaces.*;
import Terminals.Identifier;


/**
 * Created by joe on 18/09/16.
 */
public class ExpressionBuilder {

    public static String GetExpression(INode root) {
        return build(root, "");
    }

    private static String build(INode node, String expr) {
        if (node instanceof Identifier) {
            return expr + ((Identifier)node).getVal();
        } else if (node instanceof IBinaryOperator) {
            return  expr  +
                    build(node.children()[0], expr) +  " " +
                    ((IOperatorBase) node).getOperator() +  " " +
                    build(node.children()[1], expr);
        } else if (node instanceof IUnaryOperator) {
            return expr + ((IOperatorBase) node).getOperator() +
                    build(node.children()[0], expr);
        } else if (node instanceof IBrackets) {
            return expr + "(" +
                    build(node.children()[0], expr) + ")";
        } else {
            //TODO raise exception
            return "";
        }
    }

    public static String getArbExpression(INode root) {
        return buildArb(root, "");
    }

    private static String buildArb(INode node, String expr) {
        if (node.getArbID() != Values.NULL_CHAR) {
            return expr + node.getArbID();
        } else if (node instanceof IBinaryOperator) {
            return  expr  +
                    buildArb(node.children()[0], expr) +  " " +
                    ((IOperatorBase) node).getOperator() +  " " +
                    buildArb(node.children()[1], expr);
        } else if (node instanceof IUnaryOperator) {
            return expr + ((IOperatorBase) node).getOperator() +
                    buildArb(node.children()[0], expr);
        } else if (node instanceof IBrackets) {
            return expr + "(" +
                    buildArb(node.children()[0], expr) + ")";
        } else {
            //TODO raise exception
            return "";
        }
    }



}
