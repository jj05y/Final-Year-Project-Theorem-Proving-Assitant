package Workers;

import Interfaces.INode;
import Operators.Operators;

/**
 * Created by joe on 24/10/16.
 */
public class Finder {


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
