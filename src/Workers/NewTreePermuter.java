package Workers;


import Interfaces.ICommutiveOperator;
import Interfaces.INode;
import Nodes.CommutativeAssociativeBinaryOperator;
import Nodes.RootNode;
import Terminals.Identifier;
import UnicodeChars.UnicodeChars;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Created by joe on 27/09/16.
 */
public class NewTreePermuter {


    public static List<INode> permute(INode node) {

        List<List<INode>> lists = new Vector<>();



        List<INode> list = new Vector<>();
        list.add(node.copy());

        List<INode> passList = new Vector<>();
        int numPasses=0;
        do {
            passList.clear();
            for (INode n : list) {

                //add original.copy
                INode toAdd = n.copy();
                if (!list.contains(toAdd)) passList.add(toAdd);

                //swaplhsrhs
                if (n instanceof ICommutiveOperator) {
                    toAdd = (INode) ((ICommutiveOperator) n.copy()).swapLhsRhs();
                    if (!list.contains(toAdd)) passList.add(toAdd);
                }

                //reach down and zig left and/or right
                int numKids = n.children().length;
                for (int i = 0; i < numKids; i++) {
                    if (n.children()[i] instanceof ICommutiveOperator) {
                        INode thatChild = n.copy().children()[i];
                        INode zigged = (INode)((ICommutiveOperator) thatChild).zig(); //now that child is at the top
                        if (!list.contains(zigged)) passList.add(zigged);
                    }
                }

                //swaplhsrhs and then reach down and zig left and/or right
                if (n instanceof ICommutiveOperator) {
                    INode swapped = (INode)((ICommutiveOperator) n.copy()).swapLhsRhs();
                    for (int i = 0; i < numKids; i++) {
                        if (swapped.children()[i] instanceof ICommutiveOperator) {
                            INode thatChild = swapped.copy().children()[i];
                            INode zigged = (INode)((ICommutiveOperator) thatChild).zig(); //now that child is at the top
                            if (zigged != null && !list.contains(zigged)) passList.add(thatChild);
                        }
                    }
                }

                //reachdown swap left and/or right and then zig ##effect is change the orphan
                for (int i = 0; i < numKids; i++) {
                    if (n.children()[i] instanceof ICommutiveOperator) {
                        INode thatChildSwapped = (INode)((ICommutiveOperator)n.copy().children()[i]).swapLhsRhs();
                        INode zigged = (INode) ((ICommutiveOperator) thatChildSwapped).zig(); //now that swapped child is at the top
                        if (zigged != null && !list.contains(zigged)) passList.add(zigged);
                    }
                }

                //reachdown swap left and/or right and then zig and then swap again
                for (int i = 0; i < numKids; i++) {
                    if (n.children()[i] instanceof ICommutiveOperator) {
                        INode thatChildSwapped = (INode)((ICommutiveOperator)n.copy().children()[i]).swapLhsRhs();
                        INode zigged = (INode) ((ICommutiveOperator) thatChildSwapped).zig(); //now that swapped child is at the top
                        INode swappedZigged = null;
                        if (zigged!= null) swappedZigged = (INode)((ICommutiveOperator) zigged).swapLhsRhs(); //(INode)((ICommutiveOperator) zigged).swapLhsRhs();
                        if (swappedZigged != null && !list.contains(swappedZigged)) passList.add(swappedZigged);
                    }
                }

              // now need a need to consider the after zig,
                // leave it
                // need to swap - if node was left child, and zig, swap right child


               /* //swaplhsrhs and then reach down and zig left and/or right + EXTRA SWAP
                if (n instanceof ICommutiveOperator) {
                    INode swapped = (INode)((ICommutiveOperator) n.copy()).swapLhsRhs();
                    for (int i = 0; i < numKids; i++) {
                        if (swapped.children()[i] instanceof ICommutiveOperator) {
                            INode thatChild = swapped.copy().children()[i];
                            INode zigged = (INode)((ICommutiveOperator) thatChild).zig(); //now that child is at the top
                            int otherChild = i == 1 ? 0 : 1;
                            ((ICommutiveOperator)zigged.children()[otherChild]).swapLhsRhs();
                            if (zigged != null && !list.contains(zigged)) passList.add(thatChild);
                        }
                    }
                }


                //reach down and zig left and/or right + EXTRA SWAP
                for (int i = 0; i < numKids; i++) {
                    if (n.children()[i] instanceof ICommutiveOperator) {
                        INode thatChild = n.copy().children()[i];
                        INode zigged = (INode)((ICommutiveOperator) thatChild).zig(); //now that child is at the top
                        int otherChild = i == 1 ? 0 : 1;
                        ((ICommutiveOperator)zigged.children()[otherChild]).swapLhsRhs();
                        if (!list.contains(zigged)) passList.add(zigged);
                    }
                }

                //reachdown swap left and/or right and then zig ##effect is change the orphan + EXTRA SWAP
                for (int i = 0; i < numKids; i++) {
                    if (n.children()[i] instanceof ICommutiveOperator) {
                        INode thatChildSwapped = (INode)((ICommutiveOperator)n.copy().children()[i]).swapLhsRhs();
                        INode zigged = (INode) ((ICommutiveOperator) thatChildSwapped).zig(); //now that swapped child is at the top
                        int otherChild = i == 1 ? 0 : 1;
                        ((ICommutiveOperator)zigged.children()[otherChild]).swapLhsRhs();
                        if (zigged != null && !list.contains(zigged)) passList.add(zigged);
                    }
                }

                //reachdown swap left and/or right and then zig and then swap again + EXTRA SWAP
                for (int i = 0; i < numKids; i++) {
                    if (n.children()[i] instanceof ICommutiveOperator) {
                        INode thatChildSwapped = (INode)((ICommutiveOperator)n.copy().children()[i]).swapLhsRhs();
                        INode zigged = (INode) ((ICommutiveOperator) thatChildSwapped).zig(); //now that swapped child is at the top

                        int otherChild = i == 1 ? 0 : 1;
                        ((ICommutiveOperator)zigged.children()[otherChild]).swapLhsRhs();

                        INode swappedZigged = null;
                        if (zigged!= null) swappedZigged = (INode)((ICommutiveOperator) zigged).swapLhsRhs(); //(INode)((ICommutiveOperator) zigged).swapLhsRhs();
                        if (swappedZigged != null && !list.contains(swappedZigged)) passList.add(swappedZigged);
                    }
                }*/


                numPasses++;
            }
            list.addAll(passList);
        } while (passList.size() != 0);

        lists.add(list);


        List<INode> theList = new Vector<>();
        for (List<INode> l : lists) {
            theList.addAll(l);
        }
        Set<String> strings = new HashSet<>();
        for (INode n : list) {
            if (strings.add(n.toString())) System.out.println(n);

        }
        System.out.println("Num Unique Strings: " + strings.size());
        System.out.println("Num Passes: " + numPasses);
        System.out.println("Num Unique Trees: " + theList.size());
        return theList;



    }

    public static void main (String[] args){


        System.out.println("PERMUTING X and Y and Z");
        INode XandY = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, new Identifier('X', null), new Identifier('Y', null) ,null);
        XandY.children()[0].setParent(XandY);
        XandY.children()[1].setParent(XandY);

        INode XandYandZ = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, XandY, new Identifier('Z', null), null);
        XandYandZ.children()[0].setParent(XandYandZ);
        XandYandZ.children()[1].setParent(XandYandZ);

        NewTreePermuter.permute(XandYandZ);


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

        NewTreePermuter.permute(XandYandZandW);
    }



}


