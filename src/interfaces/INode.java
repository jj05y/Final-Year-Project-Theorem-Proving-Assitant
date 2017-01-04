package interfaces;


import gui.core.Bit;

/**
 * Created by joe on 18/09/16.
 */
public interface INode {
    INode[] children();
    INode getParent();
    void setParent(INode parent);
    INode copySubTree();
    INode copyWholeTree();
    String getNodeChar();
    void setChildren(INode[] newKids);
    void setNodeChar(String c);
    INode getRoot();
    boolean isRoot();
    Bit getBit();
    void setBit(Bit bit);
    void tellChildAboutParent();
    INode addBrackets();
    INode removeBrackets();
    String toPlainText();
}
