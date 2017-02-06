package unittesting.workers;

import constants.Operators;
import interfaces.INode;
import nodes.BinaryOperator;
import nodes.NodeForBrackets;
import org.junit.Test;
import parser.Parser;
import terminals.ArrayAndIndex;
import terminals.Identifier;
import trees.BoolTrees;
import trees.QuantExprs;
import trees.QuantTrees;
import workers.Matcher;

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
        Set<Matcher.Match> matches = matcher.match(selection, rule);
        assertEquals(2, matches.size());
        assertEquals("[Match{rootOfExpr=X ∨ X ≡ X, rootOfMatchedNode=X ∨ X, loopUpTable={X=X}, transition=≡}, Match{rootOfExpr=X ∨ X ≡ X, rootOfMatchedNode=X, loopUpTable={X=X ∨ X}, transition=≡}]", matches.toString());
    }

    @Test
    public void absZero() {
        Matcher matcher = new Matcher();
        INode selection = BoolTrees.absZeroequivXandY().children()[0];
        INode rule = BoolTrees.absZero();
        Set<Matcher.Match> matches = matcher.match(selection,rule);
        assertEquals(matches.size(), 2);
        assertEquals("[Match{rootOfExpr=P ∧ (P ∨ Q) ≡ P, rootOfMatchedNode=P ∧ (P ∨ Q), loopUpTable={P=X, Q=Y}, transition=≡}, Match{rootOfExpr=P ∧ (P ∨ Q) ≡ P, rootOfMatchedNode=P, loopUpTable={P=X ∧ (X ∨ Y)}, transition=≡}]", matches.toString());
    }

    @Test
    public void absZeroWhereQisASubTree() {
        Matcher matcher = new Matcher();
        INode selection = BoolTrees.weirdabsZeroequivXandY().children()[0];
        INode rule = BoolTrees.absZero();
        Set<Matcher.Match> matches = matcher.match(selection,rule);
        assertEquals(2,matches.size());
        assertEquals("[Match{rootOfExpr=P ∧ (P ∨ Q) ≡ P, rootOfMatchedNode=P ∧ (P ∨ Q), loopUpTable={P=X, Q=(Z ∧ W)}, transition=≡}, Match{rootOfExpr=P ∧ (P ∨ Q) ≡ P, rootOfMatchedNode=P, loopUpTable={P=X ∧ (X ∨ (Z ∧ W))}, transition=≡}]", matches.toString());
    }

    @Test
    public void absZeroWhereThereIsAnSinsteadOfAmatchingP() {
        Matcher matcher = new Matcher();
        INode selection = BoolTrees.weirdBrokenabsZeroequivXandY().children()[0];
        INode rule = BoolTrees.absZero();
        Set<Matcher.Match> matches = matcher.match(selection,rule);
        assertEquals("[Match{rootOfExpr=P ∧ (P ∨ Q) ≡ P, rootOfMatchedNode=P, loopUpTable={P=X ∧ (S ∨ (Z ∧ W))}, transition=≡}]", matches.toString());
    }


    @Test
    public void matchingQuants() {
        Matcher matcher = new Matcher();
        INode selection = QuantTrees.exprWithTwoQuants().children()[0];
        INode rule = QuantTrees.ruleWithTwoQuants();
        Set<Matcher.Match> matches = matcher.match(selection,rule);
        assertEquals(2,matches.size());
        assertEquals("[Match{rootOfExpr=⟨ ∀ i : s.i : f.i ⟩ ∧ ⟨ ∀ i : r.i : X ∨ f.i ⟩ ≡ X, rootOfMatchedNode=⟨ ∀ i : s.i : f.i ⟩ ∧ ⟨ ∀ i : r.i : X ∨ f.i ⟩, loopUpTable={f.i=f.i, X=Y, r.i=r.i, s.i=s.i}, transition=≡}, Match{rootOfExpr=⟨ ∀ i : s.i : f.i ⟩ ∧ ⟨ ∀ i : r.i : X ∨ f.i ⟩ ≡ X, rootOfMatchedNode=X, loopUpTable={X=⟨ ∀ i : s.i : f.i ⟩ ∧ ⟨ ∀ i : r.i : Y ∨ f.i ⟩}, transition=≡}]", matches.toString());

    }
    @Test
    public void quantWithEmptyRange() {
        Matcher matcher = new Matcher();
        INode selection = QuantExprs.emptyRangeQuant();
        INode rule = QuantTrees.emptyImpliesTerm();
        Set<Matcher.Match> matches = matcher.match(selection,rule);
        assertEquals(1,matches.size());
        assertEquals("[Match{rootOfExpr=⟨ ∀ i :  : f.i ⟩ ⇒ f.i, rootOfMatchedNode=⟨ ∀ i :  : f.i ⟩, loopUpTable={=, f.i=f.i}, transition=⇒}]", matches.toString());
    }

    @Test
    public void xEquivYinEquivSemFromBoolTreesBalanced() {
        Matcher matcher = new Matcher();
        INode selection = BoolTrees.XequivY();
        INode rule = BoolTrees.equivSemBalanced();
        Set<Matcher.Match> matches = matcher.match(selection, rule);
        assertEquals(4, matches.size());
        assertEquals("[Match{rootOfExpr=X ≡ Y ≡ Y ≡ X, rootOfMatchedNode=X ≡ Y, loopUpTable={X=X, Y=Y}, transition=≡}, Match{rootOfExpr=X ≡ Y ≡ Y ≡ X, rootOfMatchedNode=Y ≡ X, loopUpTable={X=Y, Y=X}, transition=≡}, Match{rootOfExpr=X ≡ Y ≡ Y ≡ X, rootOfMatchedNode=X, loopUpTable={X=X ≡ Y}, transition=≡}, Match{rootOfExpr=X ≡ Y ≡ Y ≡ X, rootOfMatchedNode=Y, loopUpTable={Y=X ≡ Y}, transition=≡}]", matches.toString());
    }

    @Test
    public void xEquivYinEquivSemFromBoolTreesUnbalanced() {
        Matcher matcher = new Matcher();
        INode selection = BoolTrees.XequivY();
        INode rule = BoolTrees.equivSemUnbalanced();
        Set<Matcher.Match> matches = matcher.match(selection, rule);
        assertEquals(4, matches.size());
        assertEquals("[Match{rootOfExpr=X ≡ Y ≡ Y ≡ X, rootOfMatchedNode=X, loopUpTable={X=X ≡ Y}, transition=≡}, Match{rootOfExpr=X ≡ Y ≡ Y ≡ X, rootOfMatchedNode=X ≡ Y, loopUpTable={X=X, Y=Y}, transition=≡}, Match{rootOfExpr=X ≡ Y ≡ Y ≡ X, rootOfMatchedNode=Y, loopUpTable={Y=X ≡ Y}, transition=≡}, Match{rootOfExpr=X ≡ Y ≡ Y ≡ X, rootOfMatchedNode=Y ≡ X, loopUpTable={X=Y, Y=X}, transition=≡}]", matches.toString());
    }
    @Test
    public void xEquivYinEquivSemParsed() {
        Matcher matcher = new Matcher();
        INode selection = BoolTrees.XequivY();
        Parser parser = new Parser("X == Y == Y == X");
        INode rule = parser.getTree();
        Set<Matcher.Match> matches = matcher.match(selection, rule);
        assertEquals(4, matches.size());
        assertEquals("[Match{rootOfExpr=X ≡ Y ≡ Y ≡ X, rootOfMatchedNode=X, loopUpTable={X=X ≡ Y}, transition=≡}, Match{rootOfExpr=X ≡ Y ≡ Y ≡ X, rootOfMatchedNode=X ≡ Y, loopUpTable={X=X, Y=Y}, transition=≡}, Match{rootOfExpr=X ≡ Y ≡ Y ≡ X, rootOfMatchedNode=Y, loopUpTable={Y=X ≡ Y}, transition=≡}, Match{rootOfExpr=X ≡ Y ≡ Y ≡ X, rootOfMatchedNode=Y ≡ X, loopUpTable={X=Y, Y=X}, transition=≡}]", matches.toString());
    }
    @Test
    public void xEquivxParsed() {
        Matcher matcher = new Matcher();
        INode selection = BoolTrees.XandYorZwithBrackets();
        Parser parser = new Parser("X == X");
        INode rule = parser.getTree();
        Set<Matcher.Match> matches = matcher.match(selection, rule);
        assertEquals("[Match{rootOfExpr=X ≡ X, rootOfMatchedNode=X, loopUpTable={X=(X ∧ Y) ∨ Z}, transition=≡}]",matches.toString());
    }


    @Test
    public void xEquivxBoolTreesQuant() {
        Matcher matcher = new Matcher();
        INode selection = QuantExprs.allRiFi();
        INode rule = BoolTrees.equivReflex();
        Set<Matcher.Match> matches = matcher.match(selection, rule);
        assertEquals("[Match{rootOfExpr=X ≡ X, rootOfMatchedNode=X, loopUpTable={X=⟨ ∀ i : r.i : f.i ⟩}, transition=≡}]", matches.toString());
    }
    @Test
    public void xEquivxBoolTreesID() {
        Matcher matcher = new Matcher();
        INode selection = new Identifier("B");
        INode rule = BoolTrees.equivReflex();
        Set<Matcher.Match> matches = matcher.match(selection, rule);
        assertEquals("[Match{rootOfExpr=X ≡ X, rootOfMatchedNode=X, loopUpTable={X=B}, transition=≡}]", matches.toString());
    }
    @Test
    public void xEquivxBoolTreesExpr() {
        Matcher matcher = new Matcher();
        INode selection = BoolTrees.XandYorZwithBrackets().children()[0];
        INode rule = BoolTrees.equivReflex();
        Set<Matcher.Match> matches = matcher.match(selection, rule);
        assertEquals("[Match{rootOfExpr=X ≡ X, rootOfMatchedNode=X, loopUpTable={X=(X ∧ Y)}, transition=≡}]", matches.toString());
    }

    @Test
    public void swappingTransTest() {
        Matcher matcher = new Matcher();
        INode selection = BoolTrees.XandY();
        INode rule = BoolTrees.XandYimplX();
        Set<Matcher.Match> matches = matcher.match(selection,rule);
        assertEquals("[Match{rootOfExpr=X ∧ Y ⇒ X, rootOfMatchedNode=X ∧ Y, loopUpTable={X=X, Y=Y}, transition=⇒}, Match{rootOfExpr=X ∧ Y ⇒ X, rootOfMatchedNode=X, loopUpTable={X=X ∧ Y}, transition=⇐}]", matches.toString());
    }





}
