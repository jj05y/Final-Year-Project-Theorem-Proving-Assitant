package unittesting.workers;

import beans.ExprAndHintandTransition;
import constants.Operators;
import interfaces.INode;
import nodes.BinaryOperator;
import nodes.NodeForBrackets;
import org.junit.Test;
import terminals.ArrayAndIndex;
import terminals.Identifier;
import terminals.QuantifiedExpr;
import trees.BoolTrees;
import trees.QuantExprs;
import trees.QuantTrees;
import workers.Matcher;
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
        assertEquals(1, options.size());
        ExprAndHintandTransition ehat = options.iterator().next();
        assertEquals(Operators.EQUIVAL, ehat.getTransition());
        assertTrue(ehat.getLookupTable().containsKey("P"));
        assertTrue(ehat.getLookupTable().containsKey("Q"));
        assertEquals(new Identifier("P"), ehat.getLookupTable().get("P"));
        assertEquals(new Identifier("Q"), ehat.getLookupTable().get("Q"));
        assertEquals(BoolTrees.XandY(), ehat.getExpression());
    }

    @Test
    public void replacement2() {
        Replacer replacer = new Replacer();
        INode selection = BoolTrees.XandYorZwithBrackets().children()[1];
        INode rule = BoolTrees.goldenRulePQ();
        Set<ExprAndHintandTransition> options = replacer.getReplacements(selection, rule, false);
        assertEquals(2, options.size());
        List<String> possibles = new Vector<>();
        possibles.add("(X ∧ Y) ∨ (Z ∧ Q ≡ Q ≡ Z ∨ Q)");
        possibles.add("(X ∧ Y) ∨ (P ∧ Z ≡ P ≡ P ∨ Z)");
        for (ExprAndHintandTransition e : options) {
            assertTrue(possibles.contains(e.getExpression().toString()));
        }
    }


    @Test
    public void replacementWithLoadsOfBrackets() {
        Replacer replacer = new Replacer();
        INode selection = BoolTrees.weirdBrokenabsZeroequivXandY().children()[0].children()[0];
        INode rule = BoolTrees.goldenRulePQ();
        Set<ExprAndHintandTransition> options = replacer.getReplacements(selection, rule, false);
        assertEquals(2, options.size());
        List<String> possibles = new Vector<>();
        possibles.add("(X ∧ Q ≡ Q ≡ X ∨ Q) ∧ (S ∨ (Z ∧ W)) ≡ X ∧ Y");
        possibles.add("(P ∧ X ≡ P ≡ P ∨ X) ∧ (S ∨ (Z ∧ W)) ≡ X ∧ Y");

        for (ExprAndHintandTransition e : options) {
            assertTrue(possibles.contains(e.getExpression().toString()));
        }
    }

    @Test
    public void replacement3() {
        Replacer replacer = new Replacer();
        INode selection = BoolTrees.weirdBrokenabsZeroequivXandY().children()[0].children()[0];
        INode rule = BoolTrees.goldenRulePQ();
        Set<ExprAndHintandTransition> options = replacer.getReplacements(selection, rule, false);
        assertEquals(2, options.size());
        List<String> possibles = new Vector<>();
        possibles.add("(X ∧ Q ≡ Q ≡ X ∨ Q) ∧ (S ∨ (Z ∧ W)) ≡ X ∧ Y");
        possibles.add("(P ∧ X ≡ P ≡ P ∨ X) ∧ (S ∨ (Z ∧ W)) ≡ X ∧ Y");
        ExprAndHintandTransition ehat = options.iterator().next();
        for (ExprAndHintandTransition e : options) {
            assertTrue(possibles.contains(e.getExpression().toString()));
        }
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
        assertEquals(1, options.size());
        for (ExprAndHintandTransition e : options) {
            assertEquals(new BinaryOperator(Operators.AND, new Identifier("Y"), new Identifier("Y")), e.getExpression());
        }

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


}
