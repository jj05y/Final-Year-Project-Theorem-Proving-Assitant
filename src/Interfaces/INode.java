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
    char getChar();
    void setChildren(INode[] newKids);
    void setChar(char c);
}
