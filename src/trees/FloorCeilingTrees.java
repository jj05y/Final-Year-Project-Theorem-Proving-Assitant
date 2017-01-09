package trees;

import constants.Operators;
import interfaces.INode;
import nodes.NodeForBrackets;
import terminals.Identifier;

/**
 * Created by joe on 09/01/17.
 */
public class FloorCeilingTrees {

    public static INode floorX() {
        INode floorX = new NodeForBrackets(new Identifier("X"), Operators.LFLOOR);
        floorX.tellChildAboutParent();
        return floorX;
    }
}
