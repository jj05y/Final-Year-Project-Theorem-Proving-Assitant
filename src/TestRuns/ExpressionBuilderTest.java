package TestRuns;

import Nodes.CommutativeAssociativeBinaryOperator;
import Nodes.NodeForBrackets;
import Terminals.Identifier;
import Constants.Operators;

public class ExpressionBuilderTest {

    public static void main(String[] args) {


        CommutativeAssociativeBinaryOperator XandY = new CommutativeAssociativeBinaryOperator(Operators.AND, new Identifier('X', null), new Identifier('Y', null) ,null);
        XandY.children()[0].setParent(XandY);
        XandY.children()[1].setParent(XandY);

        CommutativeAssociativeBinaryOperator XandYandZ = new CommutativeAssociativeBinaryOperator(Operators.AND, XandY, new Identifier('Z', null), null);
        XandYandZ.children()[0].setParent(XandYandZ);
        XandYandZ.children()[1].setParent(XandYandZ);


        System.out.println(XandYandZ);

        XandY.addBrackets();

        System.out.println(XandYandZ);

        XandY.swapLhsRhs();

        System.out.println(XandYandZ);

        XandYandZ.swapLhsRhs();

        System.out.println(XandYandZ);

        XandYandZ.addBrackets();

        System.out.println(XandYandZ);

        System.out.println("\nZIG TIME\n");
        XandYandZ.swapLhsRhs();
        XandY.swapLhsRhs();
        ((NodeForBrackets)XandY.getParent()).removeBrackets();
        ((NodeForBrackets)XandYandZ.getParent()).removeBrackets();

        System.out.println(XandYandZ);
        XandY.zig();
        System.out.println(XandYandZ);

        XandY.addBrackets();
        System.out.println(XandYandZ);
        XandYandZ.addBrackets();
        System.out.println(XandYandZ);
        XandY.removeBrackets();
        System.out.println(XandYandZ);
        XandYandZ.removeBrackets();
        System.out.println(XandYandZ);
        XandYandZ.zig();
        XandY.addBrackets();
        System.out.println(XandYandZ);


        System.out.println("\n Testing deep copySubTree\n");
        System.out.println("original: " + XandYandZ);
        System.out.println("copySubTree: " + XandYandZ.copySubTree());










    }
}
