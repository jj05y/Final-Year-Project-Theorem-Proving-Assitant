package unittesting.workers;

import constants.Operators;
import interfaces.INode;
import nodes.BinaryOperator;
import nodes.NodeForBrackets;
import org.junit.Assert;
import org.junit.Test;
import terminals.ArrayAndIndex;
import terminals.Identifier;
import trees.BoolTrees;
import trees.QuantExprs;
import trees.QuantTrees;
import workers.Matcher;
import workers.Remover;

import java.util.Set;

import static org.junit.Assert.*;
/**
 * Created by joe on 30/10/16.
 */
public class MatcherTest {

    @Test
    public void basicMatch() {
        Matcher matcher = new Matcher();
        INode selection = BoolTrees.XorX();
        INode rule = BoolTrees.orIdem();
        Set<Matcher.Match> matches = matcher.match(selection,rule);
        assertEquals(1,matches.size());
        Matcher.Match m = matches.iterator().next();
        assertTrue(m.getLoopUpTable().containsKey("X"));
        assertTrue(m.getLoopUpTable().containsValue(new Identifier("X")));
        assertEquals(1,m.getLoopUpTable().size());
        assertEquals(BoolTrees.XorX(), m.getRootOfMatchedNode());
        assertEquals(BoolTrees.orIdem(), m.getRootOfExpr());
        assertEquals(Operators.EQUIVAL, m.getTransition());
    }

    @Test
    public void absZero() {
        Matcher matcher = new Matcher();
        INode selection = BoolTrees.absZeroequivXandY().children()[0];
        INode rule = BoolTrees.absZero();
        Set<Matcher.Match> matches = matcher.match(selection,rule);
        assertEquals(matches.size(), 1);
        Matcher.Match m = matches.iterator().next();
        assertTrue(m.getLoopUpTable().containsKey("P"));
        assertTrue(m.getLoopUpTable().containsKey("Q"));
        assertEquals(m.getLoopUpTable().get("P"), new Identifier("X"));
        assertEquals(m.getLoopUpTable().get("Q"), new Identifier("Y"));
        assertEquals(2,m.getLoopUpTable().size());
        assertEquals(new BinaryOperator(Operators.AND, new Identifier("P"), new NodeForBrackets(BoolTrees.PorQ())), m.getRootOfMatchedNode());
        assertEquals(BoolTrees.absZero(), m.getRootOfExpr());
        assertEquals(Operators.EQUIVAL, m.getTransition());
    }

    @Test
    public void absZeroWhereQisASubTree() {
        Matcher matcher = new Matcher();
        INode selection = BoolTrees.weirdabsZeroequivXandY().children()[0];
        INode rule = BoolTrees.absZero();
        Set<Matcher.Match> matches = matcher.match(selection,rule);
        assertEquals(1,matches.size());
        Matcher.Match m = matches.iterator().next();
        assertTrue(m.getLoopUpTable().containsKey("P"));
        assertTrue(m.getLoopUpTable().containsKey("Q"));
        assertEquals(m.getLoopUpTable().get("P"), new Identifier("X"));
        assertEquals(m.getLoopUpTable().get("Q"), new NodeForBrackets(BoolTrees.ZandW()));
        assertEquals(2,m.getLoopUpTable().size());
        assertEquals(new BinaryOperator(Operators.AND, new Identifier("P"), new NodeForBrackets(BoolTrees.PorQ())), m.getRootOfMatchedNode());
        assertEquals(BoolTrees.absZero(), m.getRootOfExpr());
        assertEquals(Operators.EQUIVAL, m.getTransition());
    }

    @Test
    public void absZeroWhereThereIsAnSinsteadOfAmatchingP() {
        Matcher matcher = new Matcher();
        INode selection = BoolTrees.weirdBrokenabsZeroequivXandY().children()[0];
        INode rule = BoolTrees.absZero();
        Set<Matcher.Match> matches = matcher.match(selection,rule);
        assertEquals(0,matches.size());
    }


    @Test
    public void matchingQuants() {
        Matcher matcher = new Matcher();
        INode selection = QuantTrees.exprWithTwoQuants().children()[0];
        INode rule = QuantTrees.ruleWithTwoQuants();
        Set<Matcher.Match> matches = matcher.match(selection,rule);
        assertEquals(1,matches.size());
        Matcher.Match m = matches.iterator().next();
        assertTrue(m.getLoopUpTable().containsKey("f.i"));
        assertTrue(m.getLoopUpTable().containsKey("r.i"));
        assertTrue(m.getLoopUpTable().containsKey("s.i"));
        assertTrue(m.getLoopUpTable().containsKey("X"));
        assertEquals(m.getLoopUpTable().get("X"), new Identifier("Y"));
        assertEquals(m.getLoopUpTable().get("f.i"), new ArrayAndIndex("f","i"));
        assertEquals(m.getLoopUpTable().get("r.i"), new ArrayAndIndex("r","i"));
        assertEquals(m.getLoopUpTable().get("s.i"), new ArrayAndIndex("s","i"));
        assertEquals(4,m.getLoopUpTable().size());
        assertEquals(new BinaryOperator(Operators.AND, QuantExprs.allSiFi(), QuantExprs.allRiXorFi()), m.getRootOfMatchedNode());
        assertEquals(QuantTrees.ruleWithTwoQuants(), m.getRootOfExpr());
        assertEquals(Operators.EQUIVAL, m.getTransition());
    }
    @Test
    public void quantWithEmptyRange() {
        Matcher matcher = new Matcher();
        INode selection = QuantExprs.emptyRangeQuant();
        INode rule = QuantTrees.emptyImpliesTerm();
        Set<Matcher.Match> matches = matcher.match(selection,rule);
        assertEquals(1,matches.size());
        Matcher.Match m = matches.iterator().next();
        assertTrue(m.getLoopUpTable().containsKey(""));
        assertTrue(m.getLoopUpTable().containsKey("f.i"));
        assertEquals(m.getLoopUpTable().get(""), new Identifier(""));
        assertEquals(m.getLoopUpTable().get("f.i"), new ArrayAndIndex("f","i"));
        assertEquals(2,m.getLoopUpTable().size());
        assertEquals(QuantExprs.emptyRangeQuant(), m.getRootOfMatchedNode());
        assertEquals(QuantTrees.emptyImpliesTerm(), m.getRootOfExpr());
        assertEquals(Operators.IMPLICATION, m.getTransition());
    }





}
