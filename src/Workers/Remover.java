package Workers;

import Interfaces.INode;

import java.util.List;


public class Remover {
    public INode treeWithoutNode(INode tree, INode withoutNode) {

        //if without node is the root, return nothing
        if (withoutNode.getParent() == null) {
            return null;
        }

        //if without node is a child of the root, just make the other child the root
        if (withoutNode.getParent().getParent() == null) {
            if (withoutNode == withoutNode.getParent().children()[0]) {
                tree = withoutNode.getParent().children()[1];
            } else {
                tree = withoutNode.getParent().children()[0];
            }
        } else {
            //we need to check how many kids the parent of without node has
            if (withoutNode.getParent().children().length > 1) {
                if (withoutNode == withoutNode.getParent().children()[0]) {
                    //without was a left child
                    //need to grab other child,
                    INode withoutNodesParent = withoutNode.getParent();
                    INode otherChild = withoutNodesParent.children()[1];
                    //tell parents parent, that it's new child exists,
                    INode withoutNodesParentsParent = withoutNodesParent.getParent();
                    if (withoutNodesParent == withoutNodesParentsParent.children()[0]) {
                        withoutNodesParentsParent.children()[0] = otherChild;
                    } else {
                        withoutNodesParentsParent.children()[1] = otherChild;
                    }
                    //and stick other child too parents parent
                    otherChild.setParent(withoutNodesParentsParent);

                } else {
                    //withoutnode is a right child
                    //need to grab other child, and stick it too parents parent
                    INode withoutNodesParent = withoutNode.getParent();
                    INode otherChild = withoutNodesParent.children()[0];
                    //tell parents parent, that it's new child exists,
                    INode withoutNodesParentsParent = withoutNodesParent.getParent();
                    if (withoutNodesParent == withoutNodesParentsParent.children()[0]) {
                        withoutNodesParentsParent.children()[0] = otherChild;
                    } else {
                        withoutNodesParentsParent.children()[1] = otherChild;
                    }
                    //and stick other child too parents parent
                    otherChild.setParent(withoutNodesParentsParent);
                }

            } else { //if without node is an only child, just lob it off the tree
                withoutNode.getParent().setChildren(null);
            }
        }

        //now goldenRule has been modified be without that child! :O

        return tree;
    }
}
