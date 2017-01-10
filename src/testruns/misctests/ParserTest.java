package testruns.misctests;

import interfaces.INode;
import parser.Parser;

/**
 * Created by joe on 29/12/16.
 */
public class ParserTest {

    public static void main(String[] args) {
        Parser parser = new Parser("X and ! Y == Z == ( X or ( Y and B ) )");
        INode n = parser.getTree();
        System.out.println("root: " + n);

        System.out.println("root left child: " + n.children()[0]);
        System.out.println("root op: " + n.getNodeChar());
        System.out.println("root right child: " + n.children()[1]);

        System.out.println("\n====================================================\n");

        parser = new Parser("X and ! Y -> Z or ( X or ( Y and B ) )");
        n = parser.getTree();
        System.out.println("root: " + n);

        System.out.println("root left child: " + n.children()[0]);
        System.out.println("root op: " + n.getNodeChar());
        System.out.println("root right child: " + n.children()[1]);

        System.out.println("\n====================================================\n");

        parser = new Parser("X and <| exists i : e : f.i |> ");
        n = parser.getTree();
        System.out.println("root: " + n);

        System.out.println("root left child: " + n.children()[0]);
        System.out.println("root op: " + n.getNodeChar());
        System.out.println("root right child: " + n.children()[1]);

        System.out.println("\n====================================================\n");

        parser = new Parser(" <| exists i :  e : f.i |> ");
        n = parser.getTree();
        System.out.println("root: " + n);

        System.out.println("\n====================================================\n");

        parser = new Parser("n + y <= z + n == X and Y");
        n = parser.getTree();
        System.out.println("root: " + n);
        System.out.println("rootop: " + n.getNodeChar());
        System.out.println("left child: " + n.children()[0]);
        System.out.println("left child left child: " + n.children()[0].children()[0]);

        System.out.println("\n====================================================\n");

        parser = new Parser(" |_ n _|  + y <= z + |' n '| == X and Y");
        n = parser.getTree();
        System.out.println("root: " + n);

        System.out.println("\n====================================================\n");

        parser = new Parser(" X <= |_ Y + Z _| == X");
        n = parser.getTree();
        System.out.println("root: " + n);


        System.out.println("\n====================================================\n");

        parser = new Parser("x up y under z == x up z and y under z");
        n = parser.getTree();
        System.out.println("root: " + n);


        System.out.println("\n====================================================\n");

        parser = new Parser("<| forall w,x,y,z : e : w under z == x under z and y under z |>");
        n = parser.getTree();
        System.out.println("root: " + n);


     System.out.println("\n====================================================\n");

        parser = new Parser("<| forall x : r.x : <| exists i : b.i : X and f.i |> |>");
        n = parser.getTree();
        System.out.println("root: " + n);

/*

    System.out.println("\n====================================================\n");

        parser = new Parser("<| forall x,y : : <| exists w : : <| forall z : : w under z == x under z and y under z |> |> |>");
        n = parser.getTree();
        System.out.println("root: " + n);

*/



    }
}
