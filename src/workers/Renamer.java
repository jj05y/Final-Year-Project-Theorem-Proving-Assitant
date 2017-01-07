package workers;

import constants.Operators;
import interfaces.INode;
import interfaces.ITerminal;
import nodes.NodeForBrackets;
import terminals.Identifier;
import terminals.QuantifiedExpr;
import trees.QuantTrees;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joe on 19/11/16.
 */
public class Renamer {

    private String newArbName;

    //TODO this is a manky side effect, fix?
    private INode goArb(INode root, INode node, Map<String, String> origNameNewName) {
        //walk the tree, and if ID, grab that id, put it in map with new id num,
        //treeWithoutNode tree's ID with ArbId

        if (node instanceof Identifier) {
            String realId = node.getNodeChar();
            if (origNameNewName.get(realId) == null) {
                node.setNodeChar(newArbName);
                origNameNewName.put(realId, newArbName);
                newArbName = newArbName + "a";
            } else {
                String newName = origNameNewName.get(realId);
                node.setNodeChar(newName);
            }
            return root;
        }

        goArb(root, node.children()[0], origNameNewName);
        if (node.children().length > 1) goArb(root, node.children()[1], origNameNewName);

        return root;

    }

    private INode walkAndRename(INode root, INode node, Map<String, INode> origNameNewNode) {

        boolean leftDone = false;
        boolean rightDone = false;

        if (!(node instanceof ITerminal)) {


            if (origNameNewNode.containsKey(node.children()[0].getNodeChar())) {

                INode nodeToGoIn = origNameNewNode.get(node.children()[0].getNodeChar());
                //if node going in has an op with lower precedence than that of the node it's being attached too, need brackets
                if (Operators.precedence.containsKey(node.getNodeChar()) && Operators.findLowestPrecendence(nodeToGoIn, Integer.MAX_VALUE) < Operators.precedence.get(node.getNodeChar())) {
                    nodeToGoIn = new NodeForBrackets(nodeToGoIn, nodeToGoIn.getParent());
                }

                node.children()[0] = nodeToGoIn;
                //a replacement has occured
                leftDone = true;
            }
            if (node.children().length > 1 && origNameNewNode.containsKey(node.children()[1].getNodeChar())) {

                INode nodeToGoIn = origNameNewNode.get(node.children()[1].getNodeChar());
                //if node going in has an op with lower precedence than that of the node it's being attached too, need brackets
                if (Operators.precedence.containsKey(node.getNodeChar()) && Operators.findLowestPrecendence(nodeToGoIn, Integer.MAX_VALUE) < Operators.precedence.get(node.getNodeChar())) {
                    nodeToGoIn = new NodeForBrackets(nodeToGoIn, nodeToGoIn.getParent());
                }
                node.children()[1] = nodeToGoIn;
                // a replacement has occured
                rightDone = true;
            }
        }

        if (node instanceof ITerminal) {
            //need to check if it's a quant, and go from there
            if (node instanceof QuantifiedExpr) {
                System.out.println("boom");
                QuantifiedExpr quant = (QuantifiedExpr) node;
                walkAndRename(node, quant.getRange(), origNameNewNode);
                walkAndRename(node, quant.getTerm(), origNameNewNode);
            }
            return root;
        }
        if (!leftDone) walkAndRename(root, node.children()[0], origNameNewNode);

        if (!rightDone && node.children().length > 1) walkAndRename(root, node.children()[1], origNameNewNode);
        return root;
    }

    public INode renameIdsArbitrarily(INode node) {
        newArbName = "a";
        INode copyOfNode = node.copySubTree();
        goArb(copyOfNode, copyOfNode, new HashMap<>());
        return copyOfNode;
    }

    public INode renameIdsWithLookupTable(INode node, HashMap<String, INode> lookUpTable) {
        System.out.println("eh: " + lookUpTable);

        //what if the expression is just an terminal, no need to walk
        if (node instanceof ITerminal && lookUpTable.containsKey(node.getNodeChar())) {
            return lookUpTable.get(node.getNodeChar());
        } else if (node instanceof ITerminal) {
            if (node instanceof QuantifiedExpr) {
                System.out.println("boop");
                QuantifiedExpr quant = (QuantifiedExpr) node;
                walkAndRename(node, quant.getRange(), lookUpTable);
                walkAndRename(node, quant.getTerm(), lookUpTable);
            }
            //then there's nothing in the lookup table for it, so just leave it?
            //TODO, find out if I can just leave it
            return node.copySubTree();
        }

        INode copyOfNode = node.copySubTree();
        return walkAndRename(copyOfNode, copyOfNode, lookUpTable);
    }
}
