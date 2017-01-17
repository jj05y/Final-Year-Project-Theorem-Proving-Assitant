package unittesting.trees;

import interfaces.INode;
import org.junit.Test;
import trees.LatticeTrees;
import trees.QuantExprs;
import trees.QuantTrees;

import static org.junit.Assert.*;


/**
 * Created by joe on 16/01/17.
 */

public class QuantTreesTest {

    @Test
    public void testAllIRiFiToString() {
        INode node = QuantExprs.allRiFi();
        assertEquals("⟨ ∀ i : r.i : f.i ⟩", node.toString());
    }

    @Test
    public void xOrQuantEquivQuant() {
        INode node = QuantTrees.XorQuantEquivQuant();
        assertEquals("X ∨ ⟨ ∀ i : r.i : f.i ⟩ ≡ ⟨ ∀ i : r.i : X ∨ f.i ⟩", node.toString());
    }

    @Test
    public void ruleWithTwoQuants() {
        INode node = QuantTrees.ruleWithTwoQuants();
        assertEquals("⟨ ∀ i : s.i : f.i ⟩ ∧ ⟨ ∀ i : r.i : X ∨ f.i ⟩ ≡ X", node.toString());
    }

    @Test
    public void defW() {
        INode node = LatticeTrees.wDef();
        assertEquals("⟨ ∀ x,y :  : ⟨ ∃ w :  : ⟨ ∀ z :  : w ⊑ z ≡ x ⊑ z ∧ y ⊑ z ⟩ ⟩ ⟩", node.toString());
    }


}
