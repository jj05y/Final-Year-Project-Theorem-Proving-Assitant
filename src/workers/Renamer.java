package workers;

import constants.Operators;
import interfaces.INode;
import interfaces.ITerminal;
import nodes.NodeForBrackets;
import terminals.ArrayAndIndex;
import terminals.Identifier;
import terminals.QuantifiedExpr;
import trees.QuantTrees;
import util.LazySet;

import java.util.*;

/**
 * Created by joe on 19/11/16.
 */
public class Renamer {




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
                QuantifiedExpr quant = (QuantifiedExpr) node;
                quant.setRange(renameIdsWithLookupTable(quant.getRange(), origNameNewNode));
                quant.setTerm(renameIdsWithLookupTable(quant.getTerm(), origNameNewNode));
                sortOutDummies(node);
            }
            return root;
        }
        if (!leftDone) walkAndRename(root, node.children()[0], origNameNewNode);

        if (!rightDone && node.children().length > 1) walkAndRename(root, node.children()[1], origNameNewNode);
        return root;
    }


    public INode renameIdsWithLookupTable(INode node, Map<String, INode> lookUpTable) {

        //what if the expression is just an terminal, no need to walk
        if (node instanceof ITerminal && lookUpTable.containsKey(node.getNodeChar())) {
            return lookUpTable.get(node.getNodeChar());
        } else if (node instanceof ITerminal) {
            if (node instanceof QuantifiedExpr) {
                QuantifiedExpr quant = (QuantifiedExpr) node;
                quant.setRange(renameIdsWithLookupTable(quant.getRange(), lookUpTable));
                quant.setTerm(renameIdsWithLookupTable(quant.getTerm(), lookUpTable));
                sortOutDummies(node);
            }
            //then there's nothing in the lookup table for it, so just leave it?
            //TODO, find out if I can just leave it
            return node.copySubTree();
        }


        INode copyOfNode = node.copySubTree();
        return walkAndRename(copyOfNode, copyOfNode, lookUpTable);
    }



    private void sortOutDummies(INode node) {
        QuantifiedExpr quantifiedExpr = (QuantifiedExpr) node;
        Set<String> newDummies = new HashSet<>();

        //need to walkforDummies to find new dummies after the mapping
        walkForDummies(quantifiedExpr.getTerm(), newDummies);
        walkForDummies(quantifiedExpr.getRange(), newDummies);

        quantifiedExpr.setDummys(newDummies);
    }

    private void walkForDummies(INode node, Set<String> newDummies) {
        if (node instanceof ArrayAndIndex) {
            newDummies.add(((ArrayAndIndex) node).getIndex());
        }
        if (node instanceof ITerminal) {
            return;
        }
        walkForDummies(node.children()[0], newDummies);
        if (node.children().length>1) walkForDummies(node.children()[1], newDummies);
    }
}
