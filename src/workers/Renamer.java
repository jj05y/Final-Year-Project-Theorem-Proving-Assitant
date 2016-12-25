package workers;

import interfaces.INode;
import terminals.Identifier;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joe on 19/11/16.
 */
public class Renamer {

    private char newArbName;

    //TODO this is a manky side effect, fix?
    private INode goArb(INode root, INode node, Map<Character, Character> origNameNewName) {
        //walk the tree, and if ID, grab that id, put it in map with new id num,
        //treeWithoutNode tree's ID with ArbId

        if (node instanceof Identifier) {
            char realId = node.getNodeChar();
            if (origNameNewName.get(realId) == null) {
                node.setNodeChar(newArbName);
                origNameNewName.put(realId, newArbName);
                newArbName++;
            } else {
                char newName = origNameNewName.get(realId);
                node.setNodeChar(newName);
            }
            return root;
        }

        goArb(root, node.children()[0], origNameNewName);
        if (node.children().length > 1) goArb(root, node.children()[1], origNameNewName);

        return root;

    }

    private INode go(INode root, INode node, Map<Character, INode> origNameNewNode) {

        boolean leftDone = false;
        boolean rightDone = false;

        if (!(node instanceof Identifier)) {
            if (origNameNewNode.containsKey(node.children()[0].getNodeChar())) {
                node.children()[0] = origNameNewNode.get(node.children()[0].getNodeChar());
                //a replacement has occured
                leftDone = true;
            }
            if (node.children().length > 1 && origNameNewNode.containsKey(node.children()[1].getNodeChar())) {
                node.children()[1] = origNameNewNode.get(node.children()[1].getNodeChar());
                // a replacement has occured
                rightDone = true;
            }
        }

        if (node instanceof Identifier) {
            return root;
        }
        if (!leftDone) go(root, node.children()[0], origNameNewNode);

        if (!rightDone && node.children().length > 1) go(root, node.children()[1], origNameNewNode);
        return root;
    }

    public INode renameIdsArbitrarily(INode node) {
        newArbName = 'a';
        INode copyOfNode = node.copySubTree();
        goArb(copyOfNode, copyOfNode, new HashMap<>());
        return copyOfNode;
    }

    public INode renameIdsWithLookupTable(INode node, HashMap<Character, INode> lookUpTable) {

        //what if the expression is just an id, no need to walk
        if (node instanceof Identifier && lookUpTable.containsKey(node.getNodeChar())) {
            return lookUpTable.get(node.getNodeChar());
        } else if (node instanceof Identifier) {
            //todo throw exception
            System.err.println("need exception handle: renameIdsWithLookupTable");
        }

        newArbName = 'A';
        INode copyOfNode = node.copySubTree();
        return go(copyOfNode,copyOfNode, lookUpTable);
    }
}
