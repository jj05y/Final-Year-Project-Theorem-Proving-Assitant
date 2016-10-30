package Workers;


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
public class TreePermuter {


    private List<INode> go(INode node, boolean justOneTier) {
        List<List<INode>> lists = new Vector<>();
        List<INode> tierList = new Vector<>();
        List<INode> passList = new Vector<>();
        List<NodeAndChild> dealWithLater = new Vector<>();

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

                    //TODO Does this need to be here?
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
                    // ie, add a copy of n and it's unziggable child to laterList, then call permuteAllTiers on all childs in later list
                    // and then for each permuted child add them all back onto their parent, and into this tier list,
                    for (int i = 0; i < numKids; i++) {
                        INode copyOfOrig = n.copy();
                        INode thatChild = copyOfOrig.children()[i];
                        if (!(thatChild instanceof ICommutiveOperator)) {
                            dealWithLater.add(new NodeAndChild(copyOfOrig, thatChild));
                        }
                    }


                }
                tierList.addAll(passList);

                //Permute Everything In deal with later
                List<INode> permutedStickers = new Vector<>();
                for (NodeAndChild nc : dealWithLater) {
                    //go a level deeper, past that crap child
                    if (nc.getChild().children() != null) {
                        for (int i = 0; i < nc.getChild().children().length; i++) {
                            List<INode> permuted = go(nc.getChild().children()[i], true);
                            for (INode perm : permuted) {
                                tierList.add(nc.attachChildToNode(perm, i).copy());
                            }
                        }
                    }
                }

                dealWithLater.clear();

            } while (passList.size() != 0);


            lists.add(tierList);

            // end loop of this tier
        } while (!tierList.isEmpty() && !justOneTier);

        //After all the mini lists have been made, join them all into one giant list
        List<INode> theGiantList = new Vector<>();
        for (List<INode> l : lists) {
            for (INode bar : l) {
                if (!theGiantList.contains(bar)) theGiantList.add(bar);
            }
        }

        return theGiantList;
    }

    public List<INode> permuteAllTiers(INode node) {

        List<INode> theGiantList = go(node, false);
        return theGiantList;
    }

    public List<INode> permuteJustOneTier(INode node) {

        List<INode> theGiantList = go(node, true);
        return theGiantList;
    }

    public void reportOn(INode node) {

        List<INode> theGiantList = permuteAllTiers(node);

        //Identify unique strings in the giant list
        Set<String> uniqueStrings = new HashSet<>();
        for (INode n : theGiantList) {
            if (uniqueStrings.add(n.toString())) System.out.println(n);
        }

        //Report
        System.out.println("Num Unique Strings: " + uniqueStrings.size());
        System.out.println("Num Unique Trees: " + theGiantList.size());
    }



}


