/*
package CrapCode;

import Interfaces.INode;
import Terminals.Identifier;
import Workers.ExpressionBuilder;

import java.util.HashMap;
import java.util.Map;

*/
/**
 * Created by joe on 28/10/16.
 *//*

public class ArbIDExpr {

    private INode root;
    private Map<Character, Character> origNameNewName;
    private char newArbName;

    //root will be a copy
    //depth is an indication of how deep to go with arb renaming,
    public ArbIDExpr(INode root) {
        this.root = root;
        origNameNewName = new HashMap<>();
        newArbName = 'a';
        renameIdsArbitrarily(root);
    }

    private void renameIdsArbitrarily(INode node) {
        //walk the tree, and if ID, grab that id, put it in map with new id num,
        //treeWithoutNode tree's ID with ArbId

        if (node instanceof Identifier) {
            char realId = node.getChar();
            if (origNameNewName.get(realId) == null) {
                node.setArbID(newArbName);
                origNameNewName.put(realId, newArbName);
                newArbName++;
            } else {
                char newName = origNameNewName.get(realId);
                node.setArbID(newName);
            }
            return;
        }


        if (node instanceof Identifier) return;

        renameIdsArbitrarily(node.children()[0]);
        if (node.children().length > 1) renameIdsArbitrarily(node.children()[1]);

    }

    public INode getRoot() {
        return root;
    }

    public void setRoot(INode root) {
        this.root = root;
    }

    public Map<Character, Character> getOrigNameNewName() {
        return origNameNewName;
    }

    public void setOrigNameNewName(Map<Character, Character> origNameNewName) {
        this.origNameNewName = origNameNewName;
    }

    @Override
    public String toString() {
        return ExpressionBuilder.getArbExpression(root);
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
*/
