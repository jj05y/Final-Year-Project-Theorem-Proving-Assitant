package workers;


import constants.Operators;
import nodes.NodeForBrackets;
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

    public Set<INode> goAllPerms(INode node) {
        List<INode> listOfTrees = goSameExprPerms(node);
        //now we have unique trees for the expression,
        Set<INode> listOfSubExpressions = new LazySet<>();
        //need to walk all in baselist and yield a copy of each of their subtree
        for (INode n : listOfTrees) {
            listOfSubExpressions.addAll(walkAndYieldSubExpr(n, new Vector<>()));
        }

        return listOfSubExpressions;
    }

    public Set<INode> getUniqueStringPermsSplitOnJoiners(INode node) {
        Set<INode> uniques = new LazySet<>();
        Set<String> tracker = new HashSet<>();
        for (INode n : getPermsSplitOnLowestPrecedenceJoiner(node)) {
            if (tracker.add(n.toString())) {
                uniques.add(n);
            }
        }
        return uniques;
    }

    public Set<INode> getPermsSplitOnLowestPrecedenceJoiner(INode node) {
        List<INode> listofTrees = goSameExprPerms(node);
        Set<INode> listOfSubExprs = new LazySet<>();
        int lowestPrecedence = Operators.findLowestPrecendence(node, Integer.MAX_VALUE);
        for (INode n : listofTrees) {
            listOfSubExprs.addAll(walkAndYieldSegmentsJoinedByLowestPrecedenceJoiner(n, new Vector<>(),lowestPrecedence));
        }
        return listOfSubExprs;
    }

    private List<INode> walkAndYieldSubExpr(INode node, Vector<INode> nodes) {

        if (node instanceof ITerminal) {
            if (!(nodes.contains(node))) nodes.add(node.copyWholeTree());
            return nodes;
        }

        if (!(nodes.contains(node))) nodes.add(node.copyWholeTree());

        walkAndYieldSubExpr(node.children()[0], nodes);
        if (node.children().length > 1) walkAndYieldSubExpr(node.children()[1], nodes);

        return nodes;
    }

    private List<INode> walkAndYieldSegmentsJoinedByLowestPrecedenceJoiner(INode node, Vector<INode> nodes, int lowestPrecedence) {

        if (node instanceof ITerminal || node instanceof NodeForBrackets) {
            return nodes;
        }

        if (!(nodes.contains(node)) && Operators.precedence.get(node.getNodeChar()) == lowestPrecedence) {
            //TODO do i need this?
            //nodes.add(node.copyWholeTree());
            nodes.add(node.children()[0]);
            if (node.children().length>1) nodes.add(node.children()[1]);
        }


        walkAndYieldSegmentsJoinedByLowestPrecedenceJoiner(node.children()[0], nodes, lowestPrecedence);
        if (node.children().length > 1) walkAndYieldSegmentsJoinedByLowestPrecedenceJoiner(node.children()[1], nodes, lowestPrecedence);

        return nodes;
    }


    public List<INode> getTreesForExpression(INode node) {
        return goSameExprPerms(node);
    }

    public Set<INode> getTreesForExpressionWithCommutativeOptions(INode node) {
        Set<INode> trees = new LazySet<>();
        List<INode> sameExprPerms = goSameExprPerms(node);
        for (INode n : sameExprPerms) {
            walkAndCommute(n, trees);
        }
        trees.addAll(sameExprPerms);
        return trees;
    }

    private void walkAndCommute(INode node, Set<INode> trees) {
        if (node instanceof ITerminal) {
            return;
        }
        INode copy = null;

        //(A commutative joiner, so far only equival
        if (node.getNodeChar().equals(Operators.EQUIVAL) && node instanceof IBinaryOperator) {
            //swap
            copy = node.copyWholeTree();
            INode foo = copy.children()[0];
            copy.children()[0] = copy.children()[1];
            copy.children()[1] = foo;
            trees.add(copy.getRoot());
        }

        walkAndCommute(node.children()[0], trees);
        if (node.children().length > 1) walkAndCommute(node.children()[1], trees);

        if (copy != null) walkAndCommute(copy.children()[0], trees);
        if (copy != null && copy.children().length > 1) walkAndCommute(copy.children()[1], trees);
    }

    public Set<MatchAndTransition> nodesWithJoinersAsParentAndMatchingOp(INode node, String op) {

        Set<MatchAndTransition> validSubs = new LazySet<>();

        int lowestPrecedence = Operators.findLowestPrecendence(node, Integer.MAX_VALUE);

        //walk tree, find equivs, if equiv.child matches op THEN goAllPerms(node, true)
        //NEED TO walk EVERY just one tier perm of rule!!!
        for (INode tierOnePerm : goAllPerms(node)) {
            Set<MatchAndTransition> joiners = lookForJoinersWithMatchingOp(tierOnePerm, op, new LazySet<>(), lowestPrecedence);
            validSubs.addAll(joiners);
        }
        return validSubs;

    }


    //node is the rule
    private Set<MatchAndTransition> lookForJoinersWithMatchingOp(INode node, String opToMatch, Set<MatchAndTransition> validSubs, int lowestPrecedence) {

        if (node instanceof ITerminal) {
            return validSubs;
        }

        String transition = node.getNodeChar();
        if (Operators.isJoiner(transition) && Operators.precedence.get(transition) == lowestPrecedence) {
            if (node.children()[0].getNodeChar().equals(opToMatch)) { //matching op is left child
                //if left child in rule and transition is right to left need to swap
                if (Operators.isRightToLeft(transition)) transition = Operators.oppositeJoiner(transition);

                for (INode n : goSameExprPerms(node.children()[0])) {
                    validSubs.add(new MatchAndTransition(n, transition));
                }
            }
            if (node.children().length > 1 && node.children()[1].getNodeChar().equals(opToMatch)) {
                //simliarly need to swap transistion here
                if (Operators.isLeftToRight(transition)) transition = Operators.oppositeJoiner(transition);

                for (INode n : goSameExprPerms(node.children()[1])) {
                    validSubs.add(new MatchAndTransition(n, transition));
                }
            }
        }

        lookForJoinersWithMatchingOp(node.children()[0], opToMatch, validSubs, lowestPrecedence);
        if (node.children().length > 1) lookForJoinersWithMatchingOp(node.children()[1], opToMatch, validSubs, lowestPrecedence);
        return validSubs;

    }

    public Set<MatchAndTransition> idNodesWithJoinerAsParent(INode node) {

        Set<MatchAndTransition> validSubs = new LazySet<>();
        int lowestPrecedence = Operators.findLowestPrecendence(node, Integer.MAX_VALUE);

        //need to walk every single tier perm, and yield id nodes with Joiner as parent
        for (INode tierOnePerm : goAllPerms(node)) {
            Set<MatchAndTransition> joiners = lookForJoinerWithAnIdForAChild(tierOnePerm, new LazySet<>(), lowestPrecedence);
            validSubs.addAll(joiners);
        }
        return validSubs;
    }

    private Set<MatchAndTransition> lookForJoinerWithAnIdForAChild(INode node, LazySet<MatchAndTransition> validSubs, int lowestPrecedence) {

        if (node instanceof ITerminal) {
            return validSubs;
        }

        String transition = node.getNodeChar();
        if (Operators.isJoiner(transition) && Operators.precedence.get(transition) == lowestPrecedence) {
            if (node.children()[0] instanceof Identifier) {
                //if left child in rule and transition is right to left need to swap
                if (Operators.isRightToLeft(transition)) transition = Operators.oppositeJoiner(transition);

                validSubs.add(new MatchAndTransition(node.children()[0].copyWholeTree(), transition));
            }
            if (node.children().length > 1 && node.children()[1] instanceof Identifier) {
                //simliarly need to swap transistion here
                if (Operators.isLeftToRight(transition)) transition = Operators.oppositeJoiner(transition);

                validSubs.add(new MatchAndTransition(node.children()[1].copyWholeTree(), transition));
            }
        }

        lookForJoinerWithAnIdForAChild(node.children()[0], validSubs, lowestPrecedence);
        if (node.children().length > 1) lookForJoinerWithAnIdForAChild(node.children()[1], validSubs, lowestPrecedence);
        return validSubs;
    }

    public Set<MatchAndTransition> quantNodesWithJoinerAsParent(INode node) {

        Set<MatchAndTransition> validSubs = new LazySet<>();
        int lowestPrecedence = Operators.findLowestPrecendence(node, Integer.MAX_VALUE);

        //need to walk every single tier perm, and yield  quant nodes with Joiner as parent
        for (INode tierOnePerm : goAllPerms(node)) {
            Set<MatchAndTransition> joiners = lookForJoinerWithAQuantForAChild(tierOnePerm, new LazySet<>(), lowestPrecedence);
            validSubs.addAll(joiners);
        }
        return validSubs;
    }

    private Set<MatchAndTransition> lookForJoinerWithAQuantForAChild(INode node, LazySet<MatchAndTransition> validSubs, int lowestPrecedence) {

        if (node instanceof ITerminal) {
            return validSubs;
        }

        String transition = node.getNodeChar();
        if (Operators.isJoiner(transition) && Operators.precedence.get(transition) == lowestPrecedence) {
            if (node.children()[0] instanceof QuantifiedExpr) {
                //if left child in rule and transition is right to left need to swap
                if (Operators.isRightToLeft(transition)) transition = Operators.oppositeJoiner(transition);

                validSubs.add(new MatchAndTransition(node.children()[0].copyWholeTree(), transition));
            }
            if (node.children().length > 1 && node.children()[1] instanceof QuantifiedExpr) {
                //simliarly need to swap transistion here
                if (Operators.isLeftToRight(transition)) transition = Operators.oppositeJoiner(transition);

                validSubs.add(new MatchAndTransition(node.children()[1].copyWholeTree(), transition));
            }
        }

        lookForJoinerWithAQuantForAChild(node.children()[0], validSubs,lowestPrecedence);
        if (node.children().length > 1) lookForJoinerWithAQuantForAChild(node.children()[1], validSubs,lowestPrecedence);
        return validSubs;
    }

    public Set<MatchAndTransition> literalNodesWithJoinerAsParent(INode node, String literal) {


        Set<MatchAndTransition> validSubs = new LazySet<>();
        int lowestPrecedence = Operators.findLowestPrecendence(node, Integer.MAX_VALUE);

        //need to walk every single tier perm, and yield id nodes with equiv as parent
        for (INode tierOnePerm : goAllPerms(node)) {
            Set<MatchAndTransition> joiners = lookForJoinerWithALiteralForAChild(tierOnePerm, new LazySet<>(), literal,lowestPrecedence);
            validSubs.addAll(joiners);
        }
        return validSubs;
    }

    private Set<MatchAndTransition> lookForJoinerWithALiteralForAChild(INode node, LazySet<MatchAndTransition> validSubs, String literal, int lowestPrecedence) {
        if (node instanceof ITerminal) {
            return validSubs;
        }

        String transition = node.getNodeChar();
        if (Operators.isJoiner(transition) && Operators.precedence.get(transition) == lowestPrecedence) {
            if (node.children()[0] instanceof Literal && node.children()[0].getNodeChar().equals(literal)) {
                //if left child in rule and transition is right to left need to swap
                if (Operators.isRightToLeft(transition)) transition = Operators.oppositeJoiner(transition);

                validSubs.add(new MatchAndTransition(node.children()[0].copyWholeTree(), transition));
            }
            if (node.children().length > 1 && node.children()[1] instanceof Literal && node.children()[1].getNodeChar().equals(literal)) {
                //simliarly need to swap transistion here
                if (Operators.isLeftToRight(transition)) transition = Operators.oppositeJoiner(transition);

                validSubs.add(new MatchAndTransition(node.children()[1].copyWholeTree(), transition));
            }
        }

        lookForJoinerWithALiteralForAChild(node.children()[0], validSubs, literal, lowestPrecedence);
        if (node.children().length > 1) lookForJoinerWithALiteralForAChild(node.children()[1], validSubs, literal, lowestPrecedence);
        return validSubs;

    }


    public void reportOn(INode node) {

        Set<INode> theGiantList = goAllPerms(node);

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


