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


        //Something like while stillHasKids or while true
        // start loop of this tier

        List<INode> tierList = new Vector<>();


        if (lists.isEmpty()) {
            tierList.add(node.copy());
        } else {
            //for everything in the last tierList in lists, add it's children to the new tierList (if it has children)
            //if no kids, (tierList.isEmpty()) then  stillHasKids == false or break;
        }


        List<INode> passList = new Vector<>();
        int numPasses = 0;
        do {
            passList.clear();
            for (INode n : tierList) {

                //add original.copy
                // It's already there

                //swaplhsrhs
                if (n instanceof ICommutiveOperator) {
                    INode swapped = (INode) ((ICommutiveOperator) n.copy()).swapLhsRhs();
                    if (!tierList.contains(swapped) && !passList.contains(swapped)) passList.add(swapped);
                }

                //reach down and zig left and/or right
                int numKids = n.children().length;
                for (int i = 0; i < numKids; i++) {
                    INode thatChild = n.copy().children()[i];
                    if (thatChild instanceof ICommutiveOperator) {
                        INode zigged = (INode) ((ICommutiveOperator) thatChild).zig(); //now that child is at the top
                        if (zigged != null && !tierList.contains(zigged) && !passList.contains(zigged))
                            passList.add(zigged);
                    }
                }

                //reachdown left and/or right, swap and then zig ##effect is change the orphan
                for (int i = 0; i < numKids; i++) {
                    INode thatChild = n.copy().children()[i];
                    if (thatChild instanceof ICommutiveOperator) {
                        INode thatChildSwapped = (INode) ((ICommutiveOperator) thatChild).swapLhsRhs();
                        INode swappedZigged = (INode) ((ICommutiveOperator) thatChildSwapped).zig(); //now that swapped child is at the top
                        if (swappedZigged != null && !tierList.contains(swappedZigged) && !passList.contains(swappedZigged))
                            passList.add(swappedZigged);
                    }
                }
/*

                //swaplhsrhs and then reach down and zig left and/or right
                if (n instanceof ICommutiveOperator) {
                    INode swapped = (INode) ((ICommutiveOperator) n.copy()).swapLhsRhs();
                    for (int i = 0; i < numKids; i++) {
                        INode thatChild = swapped.copy().children()[i];
                        if (thatChild instanceof ICommutiveOperator) {
                            INode zigged = (INode) ((ICommutiveOperator) thatChild).zig(); //now that child is at the top
                            if (zigged != null && !tierList.contains(zigged) && !passList.contains(zigged))
                                passList.add(zigged);
                        }
                    }
                }


                //reachdown left/right and zig and then swap the (new) root /
                for (int i = 0; i < numKids; i++) {
                    INode thatChild = n.copy().children()[i];
                    if (thatChild instanceof ICommutiveOperator) {
                        INode zigged = (INode) ((ICommutiveOperator) thatChild).zig(); //now that swapped child is at the top
                        INode ziggedSwapped = null;
                        if (zigged != null) ziggedSwapped = (INode) ((ICommutiveOperator) zigged).swapLhsRhs();
                        if (ziggedSwapped != null && !tierList.contains(ziggedSwapped) && !passList.contains(ziggedSwapped))
                            passList.add(ziggedSwapped);
                    }
                }*/
/*
                //reachdown swap left and/or right and then zig and then swap again
                for (int i = 0; i < numKids; i++) {
                    INode thatChild = n.copy().children()[i];
                    if (thatChild instanceof ICommutiveOperator) {
                        INode thatChildSwapped = (INode) ((ICommutiveOperator) thatChild).swapLhsRhs();
                        INode swappedZigged = (INode) ((ICommutiveOperator) thatChildSwapped).zig(); //now that swapped child is at the top
                        INode swappedZiggedswapped = null;
                        if (swappedZigged != null)
                            swappedZiggedswapped = (INode) ((ICommutiveOperator) swappedZigged).swapLhsRhs();
                        if (swappedZiggedswapped != null && !tierList.contains(swappedZiggedswapped) && !passList.contains(swappedZiggedswapped))
                            passList.add(swappedZiggedswapped);
                    }
                }
*/
                //reachdown left/right zig, swap, then reach down again!? and zig again, but that will be done on the next pass, (surely?)
                for (int i = 0; i < numKids; i++) {
                    INode thatChild = n.copy().children()[i];
                    if (thatChild instanceof ICommutiveOperator) {
                        INode zigged = (INode) ((ICommutiveOperator) thatChild).zig(); //now that swapped child is at the top
                        INode ziggedSwapped = null;
                        if (zigged != null) {
                            ziggedSwapped = (INode) ((ICommutiveOperator) zigged).swapLhsRhs();
                           // if (ziggedSwapped.toString().length() != 13) System.out.println("what!?######################"); shows ziggedSwapped to always be a root
                            int numKidsOfZiggedSwapped = ziggedSwapped.children().length;
                            for (int j = 0; j < numKidsOfZiggedSwapped; j++) {
                                INode ziggedSwappedChild = ziggedSwapped.copy().children()[j];
                                if (ziggedSwappedChild instanceof ICommutiveOperator) {
                                    INode ziggedSwappedChildzigged = (INode) ((ICommutiveOperator) ziggedSwappedChild).zig(); //should bring it back to the root!?
                                    if (ziggedSwappedChildzigged.toString().length() == 13)
                                      //  System.out.println("hi" + ziggedSwappedChildzigged.toString());
                                    if (ziggedSwappedChildzigged != null && !tierList.contains(ziggedSwappedChildzigged) && !passList.contains(ziggedSwappedChildzigged))
                                        passList.add(ziggedSwappedChildzigged);
                                }
                            }
                        }

                    }
                }
            }
            numPasses++;
            tierList.addAll(passList);
        } while (passList.size() != 0);


        lists.add(tierList);

        // end loop of this tier


        //After all the mini lists have been made, join them all into one giant list
        List<INode> theGiantList = new Vector<>();
        for (List<INode> l : lists) {
            theGiantList.addAll(l);
        }

        //Identify unique strings in the giant list
        Set<String> uniqueStrings = new HashSet<>();
        for (INode n : theGiantList) {
            if (uniqueStrings.add(n.toString())) System.out.println(n);

        }

        //Report
        System.out.println("Num Unique Strings: " + uniqueStrings.size());
        System.out.println("Num Passes: " + numPasses);
        System.out.println("Num Unique Trees: " + theGiantList.size());
        return theGiantList;


    }

    public static void main(String[] args) {


        System.out.println("PERMUTING X and Y and Z");
        INode XandY = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, new Identifier('X', null), new Identifier('Y', null), null);
        XandY.children()[0].setParent(XandY);
        XandY.children()[1].setParent(XandY);

        INode XandYandZ = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, XandY, new Identifier('Z', null), null);
        XandYandZ.children()[0].setParent(XandYandZ);
        XandYandZ.children()[1].setParent(XandYandZ);

        NewTreePermuter.permute(XandYandZ);


        System.out.println("\n**************************************************\n");
        System.out.println("PERMUTING X and Y and Z and W");

        XandY = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, new Identifier('X', null), new Identifier('Y', null), null);
        XandY.children()[0].setParent(XandY);
        XandY.children()[1].setParent(XandY);

        CommutativeAssociativeBinaryOperator ZandW = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, new Identifier('Z', null), new Identifier('W', null), null);
        ZandW.children()[0].setParent(ZandW);
        ZandW.children()[1].setParent(ZandW);

        CommutativeAssociativeBinaryOperator XandYandZandW = new CommutativeAssociativeBinaryOperator(UnicodeChars.AND, XandY, ZandW, null);
        XandYandZandW.children()[0].setParent(XandYandZandW);
        XandYandZandW.children()[1].setParent(XandYandZandW);

        NewTreePermuter.permute(XandYandZandW);
    }


}


