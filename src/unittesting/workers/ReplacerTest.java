package unittesting.workers;

import beans.ExprAndHintandTransition;
import constants.Operators;
import interfaces.INode;
import nodes.BinaryOperator;
import org.junit.Test;
import parser.Parser;
import terminals.ArrayAndIndex;
import terminals.Identifier;
import trees.BoolTrees;
import trees.QuantExprs;
import trees.QuantTrees;
import workers.Replacer;

import java.util.List;
import java.util.Set;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by joe on 30/10/16.
 */
public class ReplacerTest {

    @Test
    public void basicReplacement() {
        Replacer replacer = new Replacer();
        INode selection = BoolTrees.PandQandY().children()[0];
        INode rule = BoolTrees.PandQequivX();
        Set<ExprAndHintandTransition> options = replacer.getReplacements(selection, rule, false);
        assertEquals(2, options.size());
        assertEquals("[ExprAndHintandTransition{lookupTable={P=P, Q=Q}, expression=X ∧ Y, transition=≡}, ExprAndHintandTransition{lookupTable={X=P ∧ Q}, expression=(P ∧ Q) ∧ Y, transition=≡}]",options.toString());
    }

    @Test
    public void replacement2() {
        Replacer replacer = new Replacer();
        INode selection = BoolTrees.XandYorZwithBrackets().children()[1];
        INode rule = BoolTrees.goldenRulePQ();
        Set<ExprAndHintandTransition> options = replacer.getReplacements(selection, rule, false);
        assertEquals(2, options.size());
        assertEquals("[ExprAndHintandTransition{lookupTable={Q=Z}, expression=(X ∧ Y) ∨ (P ∨ Z ≡ P ∧ Z ≡ P), transition=≡}, ExprAndHintandTransition{lookupTable={P=Z}, expression=(X ∧ Y) ∨ (Q ≡ Z ∨ Q ≡ Z ∧ Q), transition=≡}]", options.toString());
    }


    @Test
    public void replacementWithLoadsOfBrackets() {
        Replacer replacer = new Replacer();
        INode selection = BoolTrees.weirdBrokenabsZeroequivXandY().children()[0].children()[0];
        INode rule = BoolTrees.goldenRulePQ();
        Set<ExprAndHintandTransition> options = replacer.getReplacements(selection, rule, false);
        assertEquals(2, options.size());
        assertEquals("[ExprAndHintandTransition{lookupTable={Q=X}, expression=(P ∨ X ≡ P ∧ X ≡ P) ∧ (S ∨ (Z ∧ W)) ≡ X ∧ Y, transition=≡}, ExprAndHintandTransition{lookupTable={P=X}, expression=(Q ≡ X ∨ Q ≡ X ∧ Q) ∧ (S ∨ (Z ∧ W)) ≡ X ∧ Y, transition=≡}]", options.toString());
    }

    @Test
    public void replacement3() {
        Replacer replacer = new Replacer();
        INode selection = BoolTrees.weirdBrokenabsZeroequivXandY().children()[0].children()[0];
        INode rule = BoolTrees.goldenRulePQ();
        Set<ExprAndHintandTransition> options = replacer.getReplacements(selection, rule, false);
        assertEquals(2, options.size());
        assertEquals("[ExprAndHintandTransition{lookupTable={Q=X}, expression=(P ∨ X ≡ P ∧ X ≡ P) ∧ (S ∨ (Z ∧ W)) ≡ X ∧ Y, transition=≡}, ExprAndHintandTransition{lookupTable={P=X}, expression=(Q ≡ X ∨ Q ≡ X ∧ Q) ∧ (S ∨ (Z ∧ W)) ≡ X ∧ Y, transition=≡}]", options.toString());
    }

    @Test
    public void replacementWithQuantEmptyRange() {
        Replacer replacer = new Replacer();
        INode selection = QuantExprs.emptyRangeQuant();
        INode rule = QuantTrees.emptyImpliesTerm();
        Set<ExprAndHintandTransition> options = replacer.getReplacements(selection, rule, false);
        assertEquals(1, options.size());

        for (ExprAndHintandTransition e : options) {
            assertEquals(new ArrayAndIndex("f", "i"), e.getExpression());
        }

    }

    @Test
    public void replacementWithTwoQuantRule() {
        Replacer replacer = new Replacer();
        INode selection = QuantTrees.exprWithTwoQuants().children()[0];
        INode rule = QuantTrees.ruleWithTwoQuants();
        Set<ExprAndHintandTransition> options = replacer.getReplacements(selection, rule, false);
        assertEquals(2, options.size());
        assertEquals("[ExprAndHintandTransition{lookupTable={f.i=f.i, X=Y, r.i=r.i, s.i=s.i}, expression=Y ∧ Y, transition=≡}, ExprAndHintandTransition{lookupTable={X=⟨ ∀ i : s.i : f.i ⟩ ∧ ⟨ ∀ i : r.i : Y ∨ f.i ⟩}, expression=(⟨ ∀ i : s.i : f.i ⟩ ∧ ⟨ ∀ i : r.i : (⟨ ∀ i : s.i : f.i ⟩ ∧ ⟨ ∀ i : r.i : Y ∨ f.i ⟩) ∨ f.i ⟩) ∧ Y, transition=≡}]", options.toString());

    }

    @Test
    public void orOverForAll() {
        Replacer replacer = new Replacer();
        INode selection = QuantTrees.XorAllRiFi();
        INode rule = QuantTrees.orOverForAll();
        Set<ExprAndHintandTransition> options = replacer.getReplacements(selection, rule, false);
        assertEquals(1, options.size());
        for (ExprAndHintandTransition e : options) {
            assertEquals(QuantExprs.allRiXorFi(), e.getExpression());
        }

    }

    @Test
    public void xEquivYinEquivSemBoolTreeRule() {
        Replacer replacer = new Replacer();
        INode selection = BoolTrees.XequivY();
        INode rule = BoolTrees.equivSemBalanced();
        Set<ExprAndHintandTransition> options = replacer.getReplacements(selection, rule, false);
        assertEquals("[ExprAndHintandTransition{lookupTable={X=X, Y=Y}, expression=Y ≡ X, transition=≡}, ExprAndHintandTransition{lookupTable={X=Y, Y=X}, expression=Y ≡ X, transition=≡}, ExprAndHintandTransition{lookupTable={X=X ≡ Y}, expression=Y ≡ Y ≡ (X ≡ Y), transition=≡}, ExprAndHintandTransition{lookupTable={Y=X ≡ Y}, expression=X ≡ (X ≡ Y) ≡ X, transition=≡}]", options.toString());
    }

    @Test
    public void xEquivYinEquivSemParsedRule() {
        Replacer replacer = new Replacer();
        INode selection = BoolTrees.XequivY();
        Parser parser = new Parser("X == Y == Y == X");
        INode rule = parser.getTree();
        Set<ExprAndHintandTransition> options = replacer.getReplacements(selection, rule, false);
        assertEquals("[ExprAndHintandTransition{lookupTable={X=X ≡ Y}, expression=(X ≡ Y) ≡ Y ≡ Y, transition=≡}, ExprAndHintandTransition{lookupTable={X=X, Y=Y}, expression=Y ≡ X, transition=≡}, ExprAndHintandTransition{lookupTable={Y=X ≡ Y}, expression=X ≡ (X ≡ Y) ≡ X, transition=≡}, ExprAndHintandTransition{lookupTable={X=Y, Y=X}, expression=Y ≡ X, transition=≡}]", options.toString());
    }

    @Test
    public void xEquivYinEquivSemUnbalancedRule() {
        Replacer replacer = new Replacer();
        INode selection = BoolTrees.XequivY();
        INode rule = BoolTrees.equivSemUnbalanced();
        Set<ExprAndHintandTransition> options = replacer.getReplacements(selection, rule, false);
        assertEquals("[ExprAndHintandTransition{lookupTable={X=X ≡ Y}, expression=(X ≡ Y) ≡ Y ≡ Y, transition=≡}, ExprAndHintandTransition{lookupTable={X=X, Y=Y}, expression=Y ≡ X, transition=≡}, ExprAndHintandTransition{lookupTable={Y=X ≡ Y}, expression=X ≡ (X ≡ Y) ≡ X, transition=≡}, ExprAndHintandTransition{lookupTable={X=Y, Y=X}, expression=Y ≡ X, transition=≡}]", options.toString());
    }

    @Test
    public void equivRelexiveID() {
        Replacer replacer = new Replacer();
        INode selection = new Identifier("B");
        INode rule = BoolTrees.equivReflex();
        Set<ExprAndHintandTransition> options = replacer.getReplacements(selection, rule, false);
        assertEquals("[ExprAndHintandTransition{lookupTable={X=B}, expression=B, transition=≡}]", options.toString());
    }

    @Test
    public void equivRelexiveQuant() {
        Replacer replacer = new Replacer();
        INode selection = QuantExprs.allRiFi();
        INode rule = BoolTrees.equivReflex();
        Set<ExprAndHintandTransition> options = replacer.getReplacements(selection, rule, false);
        assertEquals("[ExprAndHintandTransition{lookupTable={X=⟨ ∀ i : r.i : f.i ⟩}, expression=⟨ ∀ i : r.i : f.i ⟩, transition=≡}]",options.toString());
    }

    @Test
    public void equivRelexiveExpr() {
        Replacer replacer = new Replacer();
        INode selection = BoolTrees.XequivY();
        INode rule = BoolTrees.equivReflex();
        Set<ExprAndHintandTransition> options = replacer.getReplacements(selection, rule, false);
        assertEquals("[ExprAndHintandTransition{lookupTable={X=X ≡ Y}, expression=X ≡ Y, transition=≡}]", options.toString());
    }

    @Test
    public void nullPointerCase() {
        Replacer replacer = new Replacer();
        Parser parser = new Parser("X or ( Y == Z == Y or Z )");
        INode selection = parser.getTree();
        INode rule = BoolTrees.orOverEquiv();
        Set<ExprAndHintandTransition> options = replacer.getReplacements(selection, rule, false);
        assertEquals("[ExprAndHintandTransition{lookupTable={X=X, Y=Y ≡ Z, Z=Y ∨ Z}, expression=X ∨ (Y ≡ Z) ≡ X ∨ (Y ∨ Z), transition=≡}, ExprAndHintandTransition{lookupTable={X=X, Y=(Y ≡ Z ≡ Y ∨ Z)}, expression=X ∨ ((Y ≡ Z ≡ Y ∨ Z) ≡ Z) ≡ X ∨ Z, transition=≡}, ExprAndHintandTransition{lookupTable={X=X, Z=(Y ≡ Z ≡ Y ∨ Z)}, expression=X ∨ (Y ≡ (Y ≡ Z ≡ Y ∨ Z)) ≡ X ∨ Y, transition=≡}]", options.toString());
    }


}
