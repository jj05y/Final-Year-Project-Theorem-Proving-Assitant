package unittesting.workers;

import beans.MatchAndTransition;
import constants.Operators;
import interfaces.INode;
import nodes.BinaryOperator;
import nodes.NodeForBrackets;
import org.junit.Test;
import terminals.Identifier;
import terminals.Literal;
import trees.BoolTrees;
import trees.QuantExprs;
import trees.QuantTrees;
import util.LazySet;
import workers.TreePermuter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by joe on 30/10/16.
 */
public class TreePermuterTest {

    @Test
    public void treesForExpr() {
        TreePermuter permuter = new TreePermuter();
        INode node = BoolTrees.XandYandZ();
        List<INode> tress = permuter.getTreesForExpression(node);
        assertEquals(2, tress.size());
    }


    @Test
    public void subExpr() {
        TreePermuter permuter = new TreePermuter();
        INode node = BoolTrees.XandYandZ();
        Set<INode> trees = permuter.goAllSubExpressions(node);
        assertTrue(trees.contains(BoolTrees.XandY()));
        assertTrue(trees.contains(new Identifier("Z")));
    }

    @Test
    public void subExpr2() {
        TreePermuter permuter = new TreePermuter();
        INode node = BoolTrees.XandYequivalZandW();
        Set<INode> trees = permuter.goAllSubExpressions(node);
        assertTrue(trees.contains(BoolTrees.XandY()));
        assertTrue(trees.contains(BoolTrees.ZandW()));
    }

    @Test
    public void subExprWithBrackets() {
        TreePermuter permuter = new TreePermuter();
        INode node = BoolTrees.XandYorZwithBrackets();
        Set<INode> trees = permuter.goAllSubExpressions(node);
        assertTrue(trees.contains(new NodeForBrackets(BoolTrees.XandY())));
    }

    @Test
    public void commuteRulesForMatches() {
        TreePermuter permuter = new TreePermuter();
        INode node = BoolTrees.equivId();
        Set<INode> trees = permuter.getTreesForExpressionWithCommutativeOptions(node);
        Set<INode> all = new LazySet<>();
        for (INode n : trees) {
            all.addAll(permuter.goAllSubExpressions(n));
        }
        assertTrue(all.contains(new BinaryOperator(Operators.EQUIVAL, new Identifier("X"), new Identifier("X"))));
        assertTrue(all.contains(new BinaryOperator(Operators.EQUIVAL, new Identifier("X"), new Literal(true))));
    }

    @Test
    public void subExpressionsWithinBrackets() {
        TreePermuter permuter = new TreePermuter();
        INode node = BoolTrees.braker();
        Set<INode> trees = permuter.getTreesForExpressionWithCommutativeOptions(node);
        Set<INode> all = new LazySet<>();
        for (INode n : trees) {
            all.addAll(permuter.goAllSubExpressions(n));
        }
        assertTrue(all.contains(BoolTrees.XandY()));
    }

    @Test
    public void impNodesWithJoinerParent() {
        TreePermuter permuter = new TreePermuter();
        INode n = BoolTrees.impToOr();
        Set<MatchAndTransition> subs = permuter.nodesWithJoinersAsParentAndMatchingOp(n, Operators.IMPLICATION);
        assertEquals(subs.size(), 1);

    }

    @Test
    public void idsWithJoinderParent() {
        TreePermuter permuter = new TreePermuter();
        INode n = BoolTrees.goldenRule();
        Set<MatchAndTransition> subs = permuter.idNodesWithJoinerAsParent(n);
        MatchAndTransition m1 = new MatchAndTransition(new Identifier("X"), Operators.EQUIVAL);
        MatchAndTransition m2 = new MatchAndTransition(new Identifier("Y"), Operators.EQUIVAL);
        assertTrue(subs.contains(m1));
        assertTrue(subs.contains(m2));
    }

    @Test
    public void literalsWithJoinerParent() {
        TreePermuter permuter = new TreePermuter();
        INode n = BoolTrees.equivId();
        Set<MatchAndTransition> subs = permuter.literalNodesWithJoinerAsParent(n, "true");
        MatchAndTransition m1 = new MatchAndTransition(new Literal(true), Operators.EQUIVAL);
        assertTrue(subs.contains(m1));
    }

    @Test
    public void quantsWithJoinerParent() {
        TreePermuter permuter = new TreePermuter();
        INode n = QuantTrees.quantEquivQuant();
        Set<MatchAndTransition> subs = permuter.quantOrIdNodesWithJoinerAsParent(n);
        MatchAndTransition m1 = new MatchAndTransition(QuantExprs.allRiXorFi(), Operators.EQUIVAL);
        assertTrue(subs.contains(m1));
    }

    @Test
    public void splitOnLowestPrecedenceJoiner() {
        TreePermuter permuter = new TreePermuter();
        INode n = BoolTrees.impToOr();
        Set<INode> subs = permuter.getPermsSplitOnLowestPrecedenceJoiner(n);
        assertTrue(subs.contains(new BinaryOperator(Operators.IMPLICATION, new Identifier("X"), new Identifier("Y"))));
        assertEquals(subs.size(), 2);
    }

    @Test
    public void balancedEquivSemSubs() {
        TreePermuter permuter = new TreePermuter();
        INode n = BoolTrees.equivSemBalanced();
        Set<INode> subs = permuter.goAllSubExpressions(n);
        assertEquals(14, subs.size());
        assertTrue(subs.contains(BoolTrees.XequivY()));
        assertTrue(subs.contains(BoolTrees.YequivX()));
    }

    @Test
    public void unBalancedEquivSemSubs() {
        TreePermuter permuter = new TreePermuter();
        INode n = BoolTrees.equivSemUnbalanced();
        Set<INode> subs = permuter.goAllSubExpressions(n);
        assertEquals(14, subs.size());
        assertTrue(subs.contains(BoolTrees.XequivY()));
        assertTrue(subs.contains(BoolTrees.YequivX()));
    }

    @Test
    public void unBalancedVsBalancedEquivSemCommOptions() {
        TreePermuter permuter = new TreePermuter();
        Set<INode> balanced = permuter.getTreesForExpressionWithCommutativeOptions(BoolTrees.equivSemBalanced());
        Set<INode> unbalanced = permuter.getTreesForExpressionWithCommutativeOptions(BoolTrees.equivSemUnbalanced());
        Set<String> unbalancedStrings = new HashSet<>();
        for (INode n : unbalanced) {
            unbalancedStrings.add(n.toString());
        }
        assertTrue(balanced.size() == unbalanced.size());
        for (INode bal : balanced) {
            assertTrue(unbalancedStrings.contains(bal.toString()));
        }
    }

}
