package testruns.misctests;

import interfaces.INode;
import parser.Parser;

/**
 * Created by joe on 29/12/16.
 */
public class ParserTest {

    public static void main (String[] args) {
        Parser parser = new Parser("X and ! Y = Z = ( X or ( Y and B ) )");
        INode n = parser.getTree();
        System.out.println("root: " + n);

        System.out.println("root left child: "+ n.children()[0]);
        System.out.println("root op: " + n.getNodeChar());
        System.out.println("root right child: "+ n.children()[1]);

        System.out.println("\n====================================================\n");

        parser = new Parser("X and ! Y => Z or ( X or ( Y and B ) )");
        n = parser.getTree();
        System.out.println("root: " + n);

        System.out.println("root left child: "+ n.children()[0]);
        System.out.println("root op: " + n.getNodeChar());
        System.out.println("root right child: "+ n.children()[1]);

        System.out.println("\n====================================================\n");

        parser = new Parser("X and < exists i : r.i : f.i > ");
        n = parser.getTree();
        System.out.println("root: " + n);

        System.out.println("root left child: "+ n.children()[0]);
        System.out.println("root op: " + n.getNodeChar());
        System.out.println("root right child: "+ n.children()[1]);

        System.out.println("\n====================================================\n");

        parser = new Parser(" < exists i : : f.i > ");
        n = parser.getTree();
        System.out.println("root: " + n);

    }
}
