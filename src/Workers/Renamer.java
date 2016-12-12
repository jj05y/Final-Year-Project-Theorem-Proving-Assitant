package Workers;

import Interfaces.INode;
import Terminals.Identifier;

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
            char realId = node.getChar();
            if (origNameNewName.get(realId) == null) {
                node.setChar(newArbName);
                origNameNewName.put(realId, newArbName);
                newArbName++;
            } else {
                char newName = origNameNewName.get(realId);
                node.setChar(newName);
            }
            return root;
        }

        if (node instanceof Identifier) return root;

        goArb(root, node.children()[0], origNameNewName);
        if (node.children().length > 1) goArb(root, node.children()[1], origNameNewName);

        return root;

    }

    private INode go(INode root, INode node, Map<Character, INode> origNameNewNode) {

        if (!(node instanceof Identifier)) {
            if (origNameNewNode.containsKey(node.children()[0].getChar())) {
                node.children()[0] = origNameNewNode.get(node.children()[0].getChar());
            }
            if (node.children().length > 1 && origNameNewNode.containsKey(node.children()[1].getChar())) {
                node.children()[1] = origNameNewNode.get(node.children()[1].getChar());

            }
        }

        //if we get here, there is a node in the rule, which is not in the map,,,
        //TODO handle later
        if (node instanceof Identifier) return root;

        go(root, node.children()[0], origNameNewNode);
        if (node.children().length > 1) go(root, node.children()[1], origNameNewNode);
        return root;
    }

    public INode renameIdsArbitrarily(INode node) {
        newArbName = 'a';
        INode copyOfNode = node.copySubTree();
        goArb(copyOfNode, copyOfNode, new HashMap<>());
        return copyOfNode;
    }

    public INode renameIdsWithLookupTable(INode node, HashMap<Character, INode> lookUpTable) {
        newArbName = 'A';
        INode copyOfNode = node.copySubTree();
        return go(copyOfNode,copyOfNode, lookUpTable);
    }
}
