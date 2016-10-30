package Theorems;

import Interfaces.INode;
import Terminals.Identifier;
import Workers.ExpressionBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joe on 28/10/16.
 */
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
        renameIds(root);
    }

    private void renameIds(INode node) {
        //walk the tree, and if ID, grab that id, put it in map with new id num,
        //replace tree's ID with ArbId

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

        renameIds(node.children()[0]);
        if (node.children().length > 1) renameIds(node.children()[1]);

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
