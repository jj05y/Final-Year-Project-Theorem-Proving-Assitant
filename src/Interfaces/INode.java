package Interfaces;


import Nodes.Node;

/**
 * Created by joe on 18/09/16.
 */
public interface INode {
    INode[] children();
    INode getParent();
    void setParent(INode parent);
    INode copySubTree();
    INode copyWholeTree();
    char getNodeChar();
    void setChildren(INode[] newKids);
    void setNodeChar(char c);
    INode getRoot();
    boolean isRoot();
}
