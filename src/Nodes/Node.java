package Nodes;

import Interfaces.INode;

import java.util.Stack;

/**
 * Created by joe on 12/12/16.
 */
public abstract class Node implements INode {

    public INode copyWholeTree() {
        //need to keep a reference to this! or a map to how to get here, a route
        //a stack with hold this,
        Stack<Integer> pathToRoot = new Stack<>();

        //need to find the root, and copyThat roots subTree,
        //on the way up the tree, we build the path back down to the original node
        INode foo = this;
        while (foo.getParent() != null) {
            if (foo.getParent().children()[0] == foo){
                pathToRoot.push(0);
          //      System.out.println("pushed 0");
                foo = foo.getParent();
            } else if (foo.getParent().children().length>1 && foo.getParent().children()[1] == foo) {
                pathToRoot.push(1);
            //    System.out.println("pushed 1");
                foo = foo.getParent();
            } else {
                //TODO exception
                //i believe the exception is almost impossible, i guess break will do for now
               // System.out.println("broken");
                break;
            }
        }
      //  System.out.println(pathToRoot);

        //then need to refind THIS in that copy,,, follow the map?
        INode nodeToReturn = foo.copySubTree();
        //we now have a whole new tree and a map to find where we were :O
        while (!pathToRoot.empty()) {
            nodeToReturn = nodeToReturn.children()[pathToRoot.pop()];
        }
        return nodeToReturn;
    }


}
