package TestRuns;

import Interfaces.INode;
import Nodes.CommutativeAssociativeBinaryOperator;
import Nodes.NodeForBrackets;
import Nodes.RootNode;
import Terminals.Identifier;
import UnicodeChars.UnicodeChars;

public class ExpressionBuilderTest {

    public static void main(String[] args) {


        CommutativeAssociativeBinaryOperator XandY = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, new Identifier('X', null), new Identifier('Y', null) ,null);
        XandY.children()[0].setParent(XandY);
        XandY.children()[1].setParent(XandY);

        CommutativeAssociativeBinaryOperator XandYandZ = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, XandY, new Identifier('Z', null), null);
        XandYandZ.children()[0].setParent(XandYandZ);
        XandYandZ.children()[1].setParent(XandYandZ);

        INode root = new RootNode(XandYandZ);
        XandYandZ.setParent(root);
        System.out.println(root);

        XandY.addBrackets();

        System.out.println(root);

        XandY.swapLhsRhs();

        System.out.println(root);

        XandYandZ.swapLhsRhs();

        System.out.println(root);

        XandYandZ.addBrackets();

        System.out.println(root);

        System.out.println("\nZIG TIME\n");
        XandYandZ.swapLhsRhs();
        XandY.swapLhsRhs();
        ((NodeForBrackets)XandY.getParent()).removeBrackets();
        ((NodeForBrackets)XandYandZ.getParent()).removeBrackets();

        System.out.println(root);
        XandY.zig();
        System.out.println(root);

        XandY.addBrackets();
        System.out.println(root);
        XandYandZ.addBrackets();
        System.out.println(root);
        XandY.removeBrackets();
        System.out.println(root);
        XandYandZ.removeBrackets();
        System.out.println(root);
        XandYandZ.zig();
        XandY.addBrackets();
        System.out.println(root);


        System.out.println("\n Testing deep copy\n");
        System.out.println("original: " + root);
        System.out.println("copy: " + root.copy());










    }
}
