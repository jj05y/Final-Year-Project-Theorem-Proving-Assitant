package Workers;

import Interfaces.INode;
import Constants.Operators;

/**
 * Created by joe on 24/10/16.
 */
public class Finder {


    //TODO better, going to have to itterate through all tier1 permutaions and return a node and parent pair, THAT subtree with THAT parent
    //infact, a list of them
    public INode find(INode node, String str) {
        if (node.toString().equals(str) && (node.getParent() == null || node.getParent().getChar()==Operators.EQUIVAL)) {
            return node;
        }
        if (node.children()!= null) {
            for (INode n : node.children()){
                INode returned = find(n, str);
                if (returned!= null) return returned;
            }
        }

        return null;
    }
}
