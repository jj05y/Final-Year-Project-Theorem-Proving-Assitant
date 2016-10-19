package Workers;

import Interfaces.IBrackets;
import Interfaces.ICommutiveOperator;
import Interfaces.INode;
import Nodes.RootNode;
import Terminals.Identifier;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by joe on 27/09/16.
 */
public class TreePermuter {

    static INode root;
    static Set<String> strings;

    public static void permute(INode node) {

        strings = new HashSet<>();

        root = node;

        int size;
        int numPasses = 0;
        do {
            size = strings.size();
            zigWalker(root);
           numPasses++;
        } while (strings.size() != size);

        for (String s : strings) {
    //        System.out.println(s);
        }
        System.out.println("Number Perms Yielded: " + strings.size());
        System.out.println("Number Passes: " + (numPasses - 1)); //-1 because the final pass was to establish stability
    }

    private static void zigWalker(INode node) {

        if (node.getParent() == null) {
            node = node.children()[0];
        }

        //if id
        if (node.children() == null) {
            strings.add(node.toString());
            return;
        }

        if (node.getParent().getParent() == null) {
            zigWalker(node.children()[0]);
            if (node.children().length > 1) zigWalker(node.children()[1]);
        }

        commuteWalker(root);

        if (node instanceof ICommutiveOperator) {

            INode firstChild = node.children()[0];
            INode secondChild = node.children()[1];
            ((ICommutiveOperator) node).zig();
            ((ICommutiveOperator) node).swapLhsRhs();
            zigWalker(secondChild);
            zigWalker(firstChild);
            zigWalker(secondChild);//******************************

        } else if (node.children() != null) {
            zigWalker(node.children()[0]);
            if (node.children().length > 1) zigWalker(node.children()[1]);
        }

    }


    private static void commuteWalker(INode node) {
        //if id
        if (node instanceof Identifier) {
            strings.add(node.toString());
            return;
        }
        if (node instanceof IBrackets || node instanceof RootNode) {
            commuteWalker(node.children()[0]);
        }

        if (node instanceof ICommutiveOperator) ((ICommutiveOperator) node).swapLhsRhs();
        strings.add(root.toString());
        strings.add(node.toString());
        if (node instanceof ICommutiveOperator) ((ICommutiveOperator) node).swapLhsRhs();

        commuteWalker(node.children()[0]);
        if (node.children().length > 1) commuteWalker(node.children()[1]);
        strings.add(root.toString());
        strings.add(node.toString());
        if (node instanceof ICommutiveOperator) ((ICommutiveOperator) node).swapLhsRhs();
        strings.add(root.toString());
        strings.add(node.toString());







    }


    /*

    private static void commuteWalker(INode node) {
        //if id
        if (node instanceof Identifier) {
            strings.add(node.toString());
            return;
        }
        if (node instanceof IBrackets || node instanceof RootNode) {
            commuteWalker(node.children()[0]);
        }

        strings.add(root.toString());

        strings.add(node.toString());
        if (node instanceof ICommutiveOperator) ((ICommutiveOperator) node).swapLhsRhs();
        strings.add(node.toString());
        commuteWalker(node.children()[0]);
        strings.add(node.toString());
        if (node.children().length > 1) commuteWalker(node.children()[1]);
        strings.add(node.toString());
        strings.add(root.toString());

        //round 2 with zigs
        //undo the swap
        if (node instanceof ICommutiveOperator) {
            ((ICommutiveOperator) node).swapLhsRhs();

            if (node == node.getParent().children()[0]) {
                ((ICommutiveOperator) node).zigWalker();
                node = node.children()[0];
                if (node.children()==null) return;
            } else {
                ((ICommutiveOperator) node).zigWalker();
                node = node.children()[1];
                if (node.children()==null) return;
            }

            strings.add(node.toString());
            ((ICommutiveOperator) node).swapLhsRhs();
            strings.add(node.toString());
            if (node == node.getParent().children()[0]) {
                ((ICommutiveOperator) node).zigWalker();
                node = node.children()[0];
                if (node.children()==null) return;
            } else {
                ((ICommutiveOperator) node).zigWalker();
                node = node.children()[1];
                if (node.children()==null) return;
            }


            strings.add(node.toString());
            commuteWalker(node.children()[0]);
            strings.add(node.toString());
            ((ICommutiveOperator) node).swapLhsRhs();
            strings.add(node.toString());
            if (node.children().length > 1) commuteWalker(node.children()[1]);
            strings.add(node.toString());
            ((ICommutiveOperator) node).swapLhsRhs();
            strings.add(node.toString());

        }
    }



*/

    /*
    public static void commuteWalker(INode node) {
        //for every node in the tree, I need to flip and/or zigWalker it

        if (node.children() != null) {

            //without brackets
            //carry on
            commuteWalker(node.children()[0]);
            //just swap
            swap(node.children()[0]);
            commuteWalker(node.children()[0]);
            //just zigWalker
            swap(node.children()[0]);
            zigWalker(node.children()[0]);
            commuteWalker(node.children()[0]);
            //both
            swap(node.children()[0]);
            commuteWalker(node.children()[0]);



            if (node.children().length > 1) {
                //carry on
                commuteWalker(node.children()[1]);
                //just swap
                swap(node.children()[1]);
                commuteWalker(node.children()[1]);
                //just zigWalker
                swap(node.children()[1]);
                zigWalker(node.children()[1]);
                commuteWalker(node.children()[1]);
                //both
                swap(node.children()[1]);
                commuteWalker(node.children()[1]);
            }

            //with
            if (node instanceof IAssociativeOperator) {
                ((IAssociativeOperator) node).addBrackets();
            }
        }
        if (!(node instanceof Identifier)) {
           // strings.add(node);
            strings.add(root);
        }

    }
    */


}


