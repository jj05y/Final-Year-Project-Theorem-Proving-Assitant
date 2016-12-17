package workers;


import constants.Operators;
import core.LazySet;
import core.TreeAndSubTree;
import interfaces.ICommutiveOperator;
import interfaces.INode;
import terminals.Identifier;

import java.util.*;

/**
 * Created by joe on 27/09/16.
 */
public class TreePermuter {


    private List<INode> go(INode node, boolean justOneTier) {
        List<List<INode>> lists = new Vector<>();
        List<INode> tierList;
        List<INode> passList = new Vector<>();
        List<TreeAndSubTree> dealWithLater = new Vector<>();

        do {
            //Something like while stillHasKids or while true
            // start loop of this tier

            tierList = new Vector<>();


            if (lists.isEmpty()) {
                tierList.add(node.copyWholeTree());
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
                    //     INode bracketed = (INode) ((IAssociativeOperator) n.copyWholeTree()).addBrackets();
                    //     if (!tierList.contains(bracketed) && !passList.contains(bracketed)) passList.add(bracketed);
                    //}
                    // System.out.println(tierList.size());
                    // System.out.println(tierList.get(tierList.size()-1));

                    //add original.copyWholeTree
                    // It's already there

                    //swaplhsrhs
                    if (n instanceof ICommutiveOperator) {
                        INode swapped = (INode) ((ICommutiveOperator) n.copyWholeTree()).swapLhsRhs();
                        if (!tierList.contains(swapped) && !passList.contains(swapped)) passList.add(swapped);
                    }

                    //reach down and zig left and/or right
                    int numKids = n.children().length;
                    for (int i = 0; i < numKids; i++) {
                        INode thatChild = n.copyWholeTree().children()[i];
                        if (thatChild instanceof ICommutiveOperator) {
                            INode zigged = (INode) ((ICommutiveOperator) thatChild).zig(); //now that child is at the top
                            if (zigged != null && !tierList.contains(zigged) && !passList.contains(zigged))
                                passList.add(zigged);
                        }
                    }

                    //TODO Does this need to be here?
                    //reachdown left or right and swap
                    for (int i = 0; i < numKids; i++) {
                        INode thatChild = n.copyWholeTree().children()[i];
                        if (thatChild instanceof ICommutiveOperator) {
                            INode thatChildSwapped = (INode) ((ICommutiveOperator) thatChild).swapLhsRhs();
                            if (thatChildSwapped.getParent() != null && !tierList.contains(thatChildSwapped.getParent()) && !passList.contains(thatChildSwapped.getParent()))
                                passList.add(thatChildSwapped.getParent());
                        }
                    }

                    //reachdown left or right, swap and then zig ##effect is change the orphan
                    for (int i = 0; i < numKids; i++) {
                        INode thatChild = n.copyWholeTree().children()[i];
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
                                INode origCopy = n.copyWholeTree();
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
                    // ie, add a copySubTree of n and it's unziggable child to laterList, then call permuteAllTiers on all childs in later list
                    // and then for each permuted child add them all back onto their parent, and into this tier list,
                    for (int i = 0; i < numKids; i++) {
                        INode copyOfOrig = n.copyWholeTree();
                        INode thatChild = copyOfOrig.children()[i];
                        if (!(thatChild instanceof ICommutiveOperator)) {
                            dealWithLater.add(new TreeAndSubTree(copyOfOrig, thatChild));
                        }
                    }


                }
                tierList.addAll(passList);

                //Permute Everything In deal with later
                List<INode> permutedStickers = new Vector<>();
                for (TreeAndSubTree nc : dealWithLater) {
                    //go a level deeper, past that crap child
                    if (nc.getSubTree().children() != null) {
                        for (int i = 0; i < nc.getSubTree().children().length; i++) {
                            List<INode> permuted = go(nc.getSubTree().children()[i], true);
                            for (INode perm : permuted) {
                                tierList.add(nc.attachChildToNode(perm, i).copyWholeTree());
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

    public Set<String> getListOfValidSubStrings(INode node) {
        Set<String> subStrings = new LazySet<>();
        for (INode n : permuteAllTiers(node)) {
            subStrings.add(n.toString());
        }
        return subStrings;
    }

    public List<INode> permuteJustOneTier(INode node) {

        List<INode> theGiantList = go(node, true);
        return theGiantList;
    }

    public Set<INode> nodesWithEquivAsParentAndMatchingOp(INode node, char op){

        Set<INode> validSubs = new LazySet<>();

        //walk tree, find equivs, if equiv.child matches op THEN go(node, true)
        //NEED TO walk EVERY just one tier perm of rule!!!
        for (INode tierOnePerm : go(node,true)) {
            Set<INode> equivs = lookForEquivsWithMatchingOp(tierOnePerm, op, new LazySet<>());
            validSubs.addAll(equivs);
        }
        return validSubs;

    }

    private Set<INode> lookForEquivsWithMatchingOp(INode node, char opToMatch, Set<INode> validSubs) {


        if (node instanceof Identifier) {
            return validSubs;
        }

        if (node.getNodeChar() == Operators.EQUIVAL) {
            if (node.children()[0].getNodeChar() == opToMatch) {
                validSubs.addAll(go(node.children()[0], true));
            }
            if (node.children().length > 1 && node.children()[1].getNodeChar()==opToMatch) {
                validSubs.addAll(go(node.children()[1], true));
            }
        }

        lookForEquivsWithMatchingOp(node.children()[0], opToMatch, validSubs);
        if (node.children().length>1) lookForEquivsWithMatchingOp(node.children()[1], opToMatch, validSubs);
        return validSubs;

    }

    public Set<INode> idNodesWithEquivAsParent(INode node) {

        Set<INode> validSubs = new LazySet<>();
        //need to walk every single tier perm, and yield id nodes with equiv as parent
        for (INode tierOnePerm : go(node,true)) {
            Set<INode> equivs = lookForEquivsWithAnIdForAChild(tierOnePerm, new LazySet<>());
            validSubs.addAll(equivs);
        }
        return validSubs;
    }

    private Set<INode> lookForEquivsWithAnIdForAChild(INode node, LazySet<INode> validSubs) {
        if (node instanceof Identifier) {
            return validSubs;
        }

        if (node.getNodeChar() == Operators.EQUIVAL) {
            if (node.children()[0] instanceof Identifier) {
                validSubs.add(node.children()[0].copyWholeTree());
            }
            if (node.children().length > 1 && node.children()[1] instanceof Identifier) {
                validSubs.add(node.children()[1].copyWholeTree());
            }
        }

        lookForEquivsWithAnIdForAChild(node.children()[0], validSubs);
        if (node.children().length>1) lookForEquivsWithAnIdForAChild(node.children()[1], validSubs);
        return validSubs;
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
        System.out.println("Num Unique trees: " + theGiantList.size());
    }



}


