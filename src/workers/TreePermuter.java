package workers;


import constants.Operators;
import util.LazySet;
import beans.MatchAndTransition;
import beans.TreeAndSubTree;
import interfaces.IBinaryOperator;
import interfaces.INode;
import interfaces.ITerminal;
import terminals.Identifier;
import terminals.Literal;
import terminals.QuantifiedExpr;

import java.util.*;

/**
 * Created by joe on 27/09/16.
 */
public class TreePermuter {


    private List<INode> goSameExprPerms(INode node) {
        List<List<INode>> lists = new Vector<>();
        List<INode> tierList;
        List<INode> passList = new Vector<>();
        List<TreeAndSubTree> dealWithLater = new Vector<>();

        do {
            tierList = new Vector<>();

            if (lists.isEmpty()) {
                tierList.add(node.copyWholeTree());
            }


            do {
                passList.clear();
                for (INode n : tierList) {
                    if (n.children() == null) continue;

                    //reach down and zig left and/or right
                    int numKids = n.children().length;
                    for (int i = 0; i < numKids; i++) {
                        INode thatChild = n.copyWholeTree().children()[i];
                        if (thatChild instanceof IBinaryOperator) {
                            INode zigged = (INode) ((IBinaryOperator) thatChild).zig(); //now that child is at the top
                            if (zigged != null && !tierList.contains(zigged) && !passList.contains(zigged))
                                passList.add(zigged);
                        }
                    }


                    //it's actually just a double depth zig
                    for (int i = 0; i < numKids; i++) {
                        INode child = n.children()[i];
                        if (child.children() != null) {
                            for (int j = 0; j < child.children().length; j++) {
                                INode origCopy = n.copyWholeTree();
                                INode thatChild = origCopy.children()[i];
                                if (thatChild.children() != null) { //it's not an ID
                                    INode thatChildsChild = thatChild.children()[j];
                                    if (thatChildsChild instanceof IBinaryOperator) {
                                        ((IBinaryOperator) thatChildsChild).zig(); //hasn't made it to the top just up one level
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
                        if (!(thatChild instanceof IBinaryOperator)) {
                            dealWithLater.add(new TreeAndSubTree(copyOfOrig, thatChild));
                        }
                    }


                }
                tierList.addAll(passList);

                //Permute Everything In deal with later
                List<INode> permutedStickers = new Vector<>();
                for (TreeAndSubTree nc : dealWithLater) {
                    //goAllPerms a level deeper, past that crap child
                    if (nc.getSubTree().children() != null) {
                        for (int i = 0; i < nc.getSubTree().children().length; i++) {
                            List<INode> permuted = goSameExprPerms(nc.getSubTree().children()[i]);
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
        } while (!tierList.isEmpty());

        //After all the mini lists have been made, join them all into one giant list
        List<INode> theGiantList = new Vector<>();
        for (List<INode> l : lists) {
            for (INode bar : l) {
                if (!theGiantList.contains(bar)) theGiantList.add(bar);
            }
        }

        return theGiantList;
    }

    public List<INode> goAllPerms(INode node) {
        List<INode> listOfTrees = goSameExprPerms(node);
        //now we have unique trees for the expression,
        List<INode> listOfSubExpressions = new Vector<>();
        //need to walk all in baselist and yield a copy of each of their subtree
        for (INode n : listOfTrees) {
            listOfSubExpressions.addAll(walkAndYield(n, new Vector<>()));
        }

        return listOfSubExpressions;
    }

    private List<INode> walkAndYield(INode node, Vector<INode> nodes) {

        if (node instanceof ITerminal) {
            if (!(nodes.contains(node))) nodes.add(node.copyWholeTree());
            return nodes;
        }

        if (!(nodes.contains(node))) nodes.add(node.copyWholeTree());

        walkAndYield(node.children()[0], nodes);
        if (node.children().length > 1) walkAndYield(node.children()[1], nodes);

        return nodes;
    }

    public List<INode> permuteAllTiers(INode node) {

        List<INode> theGiantList = goAllPerms(node);
        return theGiantList;
    }

    public Set<String> getListOfValidSubStrings(INode node) {
        Set<String> subStrings = new LazySet<>();
        for (INode n : permuteAllTiers(node)) {
            subStrings.add(n.toString());
        }
        return subStrings;
    }

    //more efficient than below?
    public List<INode> getTreesForExpression(INode node) {


        return goSameExprPerms(node);
    }

    public Set<MatchAndTransition> nodesWithJoinersAsParentAndMatchingOp(INode node, char op) {

        Set<MatchAndTransition> validSubs = new LazySet<>();

        //walk tree, find equivs, if equiv.child matches op THEN goAllPerms(node, true)
        //NEED TO walk EVERY just one tier perm of rule!!!
        for (INode tierOnePerm : goAllPerms(node)) {
            Set<MatchAndTransition> joiners = lookForJoinersWithMatchingOp(tierOnePerm, op, new LazySet<>());
            validSubs.addAll(joiners);
        }
        return validSubs;

    }


    //node is the rule
    private Set<MatchAndTransition> lookForJoinersWithMatchingOp(INode node, char opToMatch, Set<MatchAndTransition> validSubs) {

        if (node instanceof ITerminal) {
            return validSubs;
        }

        char transition = node.getNodeChar();
        if (transition == Operators.EQUIVAL || transition == Operators.IMPLICATION || transition == Operators.REVERSE_IMPLICATION) {
            if (node.children()[0].getNodeChar() == opToMatch) { //matching op is left child
                //if left child in rule and transition is ff need to swap to implication
                if (transition == Operators.REVERSE_IMPLICATION) transition = Operators.IMPLICATION;

                for (INode n : goSameExprPerms(node.children()[0])) {
                    validSubs.add(new MatchAndTransition(n, transition));
                }
            }
            if (node.children().length > 1 && node.children()[1].getNodeChar() == opToMatch) {
                //simliarly need to swap transistion here
                if (transition == Operators.IMPLICATION) transition = Operators.REVERSE_IMPLICATION;

                for (INode n : goSameExprPerms(node.children()[1])) {
                    validSubs.add(new MatchAndTransition(n, transition));
                }
            }
        }

        lookForJoinersWithMatchingOp(node.children()[0], opToMatch, validSubs);
        if (node.children().length > 1) lookForJoinersWithMatchingOp(node.children()[1], opToMatch, validSubs);
        return validSubs;

    }

    public Set<MatchAndTransition> idNodesWithJoinerAsParent(INode node) {

        Set<MatchAndTransition> validSubs = new LazySet<>();
        //need to walk every single tier perm, and yield id nodes with Joiner as parent
        for (INode tierOnePerm : goAllPerms(node)) {
            Set<MatchAndTransition> joiners = lookForJoinerWithAnIdForAChild(tierOnePerm, new LazySet<>());
            validSubs.addAll(joiners);
        }
        return validSubs;
    }

    private Set<MatchAndTransition> lookForJoinerWithAnIdForAChild(INode node, LazySet<MatchAndTransition> validSubs) {

        if (node instanceof ITerminal) {
            return validSubs;
        }

        char transition = node.getNodeChar();
        if (transition == Operators.EQUIVAL || transition == Operators.IMPLICATION || transition == Operators.REVERSE_IMPLICATION) {
            if (node.children()[0] instanceof Identifier) {
                //if left child in rule and transition is ff need to swap to implication
                if (transition == Operators.REVERSE_IMPLICATION) transition = Operators.IMPLICATION;

                validSubs.add(new MatchAndTransition(node.children()[0].copyWholeTree(), transition));
            }
            if (node.children().length > 1 && node.children()[1] instanceof Identifier) {
                //simliarly need to swap transistion here
                if (transition == Operators.IMPLICATION) transition = Operators.REVERSE_IMPLICATION;

                validSubs.add(new MatchAndTransition(node.children()[1].copyWholeTree(), transition));
            }
        }

        lookForJoinerWithAnIdForAChild(node.children()[0], validSubs);
        if (node.children().length > 1) lookForJoinerWithAnIdForAChild(node.children()[1], validSubs);
        return validSubs;
    }

    public Set<MatchAndTransition> quantNodesWithJoinerAsParent(INode node) {

        Set<MatchAndTransition> validSubs = new LazySet<>();
        //need to walk every single tier perm, and yield  quant nodes with Joiner as parent
        for (INode tierOnePerm : goAllPerms(node)) {
            Set<MatchAndTransition> joiners = lookForJoinerWithAQuantForAChild(tierOnePerm, new LazySet<>());
            validSubs.addAll(joiners);
        }
        return validSubs;
    }

    private Set<MatchAndTransition> lookForJoinerWithAQuantForAChild(INode node, LazySet<MatchAndTransition> validSubs) {

        if (node instanceof ITerminal) {
            return validSubs;
        }

        char transition = node.getNodeChar();
        if (transition == Operators.EQUIVAL || transition == Operators.IMPLICATION || transition == Operators.REVERSE_IMPLICATION) {
            if (node.children()[0] instanceof QuantifiedExpr ) {
                //if left child in rule and transition is ff need to swap to implication
                if (transition == Operators.REVERSE_IMPLICATION) transition = Operators.IMPLICATION;

                validSubs.add(new MatchAndTransition(node.children()[0].copyWholeTree(), transition));
            }
            if (node.children().length > 1 && node.children()[1] instanceof QuantifiedExpr) {
                //simliarly need to swap transistion here
                if (transition == Operators.IMPLICATION) transition = Operators.REVERSE_IMPLICATION;

                validSubs.add(new MatchAndTransition(node.children()[1].copyWholeTree(), transition));
            }
        }

        lookForJoinerWithAnIdForAChild(node.children()[0], validSubs);
        if (node.children().length > 1) lookForJoinerWithAnIdForAChild(node.children()[1], validSubs);
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

    public Set<MatchAndTransition> literalNodesWithJoinerAsParent(INode node, char literal) {


        Set<MatchAndTransition> validSubs = new LazySet<>();
        //need to walk every single tier perm, and yield id nodes with equiv as parent
        for (INode tierOnePerm : goAllPerms(node)) {
            Set<MatchAndTransition> joiners = lookForJoinerWithALiteralForAChild(tierOnePerm, new LazySet<>(), literal);
            validSubs.addAll(joiners);
        }
        return validSubs;
    }

    private Set<MatchAndTransition> lookForJoinerWithALiteralForAChild(INode node, LazySet<MatchAndTransition> validSubs, char literal) {
        if (node instanceof ITerminal) {
            return validSubs;
        }

        char transition = node.getNodeChar();
        if (transition == Operators.EQUIVAL || transition == Operators.IMPLICATION || transition == Operators.REVERSE_IMPLICATION) {
            if (node.children()[0] instanceof Literal && node.children()[0].getNodeChar() == literal) {
                //if left child in rule and transition is ff need to swap to implication
                if (transition == Operators.REVERSE_IMPLICATION) transition = Operators.IMPLICATION;

                validSubs.add(new MatchAndTransition(node.children()[0].copyWholeTree(), transition));
            }
            if (node.children().length > 1 && node.children()[1] instanceof Literal && node.children()[1].getNodeChar() == literal) {
                //simliarly need to swap transistion here
                if (transition == Operators.IMPLICATION) transition = Operators.REVERSE_IMPLICATION;

                validSubs.add(new MatchAndTransition(node.children()[1].copyWholeTree(), transition));
            }
        }

        lookForJoinerWithALiteralForAChild(node.children()[0], validSubs, literal);
        if (node.children().length > 1) lookForJoinerWithALiteralForAChild(node.children()[1], validSubs, literal);
        return validSubs;

    }
}


