package Interfaces;


/**
 * Created by joe on 18/09/16.
 */
public interface INode {
    INode[] children();
    INode getParent();
    void setParent(INode parent);
    INode copy();
    char getChar();
    void setChildren(INode[] newKids);

}
