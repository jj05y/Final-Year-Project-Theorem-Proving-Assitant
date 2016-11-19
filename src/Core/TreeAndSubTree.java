package Core;

import Interfaces.INode;


public class TreeAndSubTree {
    private INode tree;
    private INode subTree;

    public TreeAndSubTree(INode tree, INode subTree) {
        this.tree = tree;
        this.subTree = subTree;
    }

    public TreeAndSubTree() {
    }

    public INode getTree() {
        return tree;
    }

    public void setTree(INode tree) {
        this.tree = tree;
    }

    public INode getSubTree() {
        return subTree;
    }

    public void setSubTree(INode subTree) {
        this.subTree = subTree;
    }

    public INode attachChildToNode(INode newChildofChild, int i) {

        subTree.children()[i] = newChildofChild;
        newChildofChild.setParent(subTree);

        return tree;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof TreeAndSubTree) {
            //TODO check this out, do i need tree.equals(((TreeAndSubTree) other).getTree())&& (removing it makes everything better ish :/
            return subTree.equals(((TreeAndSubTree)other).getSubTree());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "TreeAndSubTree{" +
                "tree=" + tree +
                ", subTree=" + subTree +
                '}';
    }
}
