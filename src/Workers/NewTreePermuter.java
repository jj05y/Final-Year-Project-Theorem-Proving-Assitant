package Workers;


import Interfaces.IAssociativeOperator;
import Interfaces.ICommutiveOperator;
import Interfaces.INode;
import Trees.Trees;

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
        List<INode> tierList = new Vector<>();

        do {
            //Something like while stillHasKids or while true
            // start loop of this tier

            tierList = new Vector<>();


            if (lists.isEmpty()) {
                tierList.add(node.copy());
            } else {
                //for everything in the last tierList in lists, add it's children to the new tierList (if it has children)
                //if no kids, (tierList.isEmpty()) then  stillHasKids == false or break;
                for (INode nn : lists.get(lists.size() - 1)) {
                    if (nn.children() != null) {
                        for (int i = 0; i < nn.children().length; i++) {
                            tierList.add(nn.children()[i]);
                        }
                    }
                }
            }


            List<INode> passList = new Vector<>();
            do {
                passList.clear();
                for (INode n : tierList) {
                    if (n.children() == null) continue;

                    //System.out.println(n.toString());

                    //add brackets
                   // if (n instanceof IAssociativeOperator) {
                   //     INode bracketed = (INode) ((IAssociativeOperator) n.copy()).addBrackets();
                   //     if (!tierList.contains(bracketed) && !passList.contains(bracketed)) passList.add(bracketed);
                    //}
                   // System.out.println(tierList.size());
                   // System.out.println(tierList.get(tierList.size()-1));

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

                    //TODO this better,
                    //reachdown left or right and swap
                    for (int i = 0; i < numKids; i++) {
                        INode thatChild = n.copy().children()[i];
                        if (thatChild instanceof ICommutiveOperator) {
                            INode thatChildSwapped = (INode) ((ICommutiveOperator) thatChild).swapLhsRhs();
                            if (thatChildSwapped.getParent() != null && !tierList.contains(thatChildSwapped.getParent()) && !passList.contains(thatChildSwapped.getParent()))
                                passList.add(thatChildSwapped.getParent());
                        }
                    }

                    //reachdown left or right, swap and then zig ##effect is change the orphan
                    for (int i = 0; i < numKids; i++) {
                        INode thatChild = n.copy().children()[i];
                        if (thatChild instanceof ICommutiveOperator) {
                            INode thatChildSwapped = (INode) ((ICommutiveOperator) thatChild).swapLhsRhs();
                            INode swappedZigged = (INode) ((ICommutiveOperator) thatChildSwapped).zig(); //now that swapped child is at the top
                            if (swappedZigged != null && !tierList.contains(swappedZigged) && !passList.contains(swappedZigged))
                                passList.add(swappedZigged);
                        }
                    }
                    int stuck = 0;

                    //it's actually just a double depth zig
                    for (int i = 0; i < numKids; i++) {
                        INode child = n.children()[i];
                        if (child.children() != null) {
                            for (int j = 0; j < child.children().length; j++) {
                                INode origCopy = n.copy();
                                INode thatChild = origCopy.children()[i];
                                if (thatChild.children() != null) { //it's not an ID
                                    INode thatChildsChild = thatChild.children()[j];
                                    if (thatChildsChild instanceof ICommutiveOperator) {
                                        ((ICommutiveOperator) thatChildsChild).zig(); //hasn't made it to the top just up one level
                                        if (thatChildsChild != null && !tierList.contains(origCopy) && !passList.contains(origCopy))
                                            passList.add(origCopy);
                                    }
                                }
                            }
                        }
                    }

                    // if either of n's children can't be zigged, then we need to deal with it later,
                    // ie, add a copy of n and it's unziggable child to laterList, then call permute on all childs in later list
                    // and then for each permuted child add them all back onto their parent, and into this tier list,


                }
                tierList.addAll(passList);
            } while (passList.size() != 0);


            lists.add(tierList);

            // end loop of this tier
        } while (!tierList.isEmpty());

        //After all the mini lists have been made, join them all into one giant list
        List<INode> theGiantList = new Vector<>();
        for (
                List<INode> l : lists)

        {
            theGiantList.addAll(l);
        }

        //Identify unique strings in the giant list
        Set<String> uniqueStrings = new HashSet<>();
        for (
                INode n : theGiantList)

        {
            if (uniqueStrings.add(n.toString())) System.out.println(n);

        }

        //Report
        System.out.println("Num Unique Strings: " + uniqueStrings.size());
        System.out.println("Num Unique Trees: " + theGiantList.size());
        return theGiantList;


    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        NewTreePermuter.permute(Trees.XandYandZ());
        System.out.println("Time: " + (System.currentTimeMillis() - start) +"ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        NewTreePermuter.permute(Trees.XandYequivalZ());
        System.out.println("Time: " + (System.currentTimeMillis() - start) +"ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        NewTreePermuter.permute(Trees.XandYequivalZandW());
        System.out.println("Time: " + (System.currentTimeMillis() - start) +"ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        NewTreePermuter.permute(Trees.XandYequivalXandYequivalZ());
        System.out.println("Time: " + (System.currentTimeMillis() - start) +"ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        NewTreePermuter.permute(Trees.XandYorZwithBrackets());
        System.out.println("Time: " + (System.currentTimeMillis() - start) +"ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        NewTreePermuter.permute(Trees.XandYorZ());
        System.out.println("Time: " + (System.currentTimeMillis() - start) +"ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        NewTreePermuter.permute(Trees.goldenRule());
        System.out.println("Time: " + (System.currentTimeMillis() - start) +"ms");

        System.out.println("\n**********************************************************\n");

        start = System.currentTimeMillis();
        NewTreePermuter.permute(Trees.braker());
        System.out.println("Time: " + (System.currentTimeMillis() - start) +"ms");

    }


}


