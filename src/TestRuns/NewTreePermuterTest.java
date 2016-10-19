package TestRuns;

import Interfaces.INode;
import Nodes.CommutativeAssociativeBinaryOperator;
import Nodes.RootNode;
import Terminals.Identifier;
import UnicodeChars.UnicodeChars;
import Workers.NewTreePermuter;
import Workers.TreePermuter;

public class NewTreePermuterTest {

    public static void main(String[] args) {

        System.out.println("PERMUTING X");
        Identifier X = new Identifier('X', null);


        INode root = new RootNode(X);
        X.setParent(root);

        //NewTreePermuter.permute(root);

        System.out.println("\n**************************************************\n");


        System.out.println("PERMUTING X and Y");
        CommutativeAssociativeBinaryOperator XandY = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, new Identifier('X', null), new Identifier('Y', null) ,null);
        XandY.children()[0].setParent(XandY);
        XandY.children()[1].setParent(XandY);


        root = new RootNode(XandY);
        XandY.setParent(root);

        //NewTreePermuter.permute(root);

        System.out.println("\n**************************************************\n");

        System.out.println("PERMUTING X and Y and Z");
        XandY = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, new Identifier('X', null), new Identifier('Y', null) ,null);
        XandY.children()[0].setParent(XandY);
        XandY.children()[1].setParent(XandY);

        CommutativeAssociativeBinaryOperator XandYandZ = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, XandY, new Identifier('Z', null), null);
        XandYandZ.children()[0].setParent(XandYandZ);
        XandYandZ.children()[1].setParent(XandYandZ);

        root = new RootNode(XandYandZ);
        XandYandZ.setParent(root);

        NewTreePermuter.permute(root);


        System.out.println("\n**************************************************\n");
        System.out.println("PERMUTING X and Y and Z and W");

        XandY = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, new Identifier('X', null), new Identifier('Y', null) ,null);
        XandY.children()[0].setParent(XandY);
        XandY.children()[1].setParent(XandY);

        CommutativeAssociativeBinaryOperator ZandW = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, new Identifier('Z', null), new Identifier('W', null) ,null);
        ZandW.children()[0].setParent(ZandW);
        ZandW.children()[1].setParent(ZandW);

        CommutativeAssociativeBinaryOperator XandYandZandW = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, XandY, ZandW, null);
        XandYandZandW.children()[0].setParent(XandYandZandW);
        XandYandZandW.children()[1].setParent(XandYandZandW);

        root = new RootNode(XandYandZandW);
        XandYandZandW.setParent(root);

        //NewTreePermuter.permute(root);



        System.out.println("\n**************************************************\n");
        System.out.println("PERMUTING X and Y and Z and W and Q");

        XandY = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, new Identifier('X', null), new Identifier('Y', null) ,null);
        XandY.children()[0].setParent(XandY);
        XandY.children()[1].setParent(XandY);


        CommutativeAssociativeBinaryOperator ZandQ = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, new Identifier('Z', null), new Identifier('Q', null) ,null);
        ZandQ.children()[0].setParent(ZandQ);
        ZandQ.children()[1].setParent(ZandQ);


        CommutativeAssociativeBinaryOperator ZandWandQ = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, ZandQ, new Identifier('W', null) ,null);
        ZandWandQ.children()[0].setParent(ZandWandQ);
        ZandWandQ.children()[1].setParent(ZandWandQ);

        CommutativeAssociativeBinaryOperator XandYandZandWandQ = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, XandY, ZandWandQ, null);
        XandYandZandWandQ.children()[0].setParent(XandYandZandWandQ);
        XandYandZandWandQ.children()[1].setParent(XandYandZandWandQ);

        root = new RootNode(XandYandZandWandQ);
        XandYandZandWandQ.setParent(root);

        //NewTreePermuter.permute(root);








    }
}
