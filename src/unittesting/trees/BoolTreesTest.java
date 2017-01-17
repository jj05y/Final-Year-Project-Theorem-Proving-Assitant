package unittesting.trees;

/**
 * Created by joe on 16/01/17.
 */
import interfaces.INode;
import org.junit.*;
import trees.BoolTrees;

import static org.junit.Assert.*;

public class BoolTreesTest {

    @Test
    public void xAndY() {
        INode node = trees.BoolTrees.XandY();
        assertEquals("XandY ToStringTest Fail", "X ∧ Y", node.toString());
    }

    @Test
    public void absZero() {
        INode node = trees.BoolTrees.absZero();
        assertEquals("P ∧ (P ∨ Q) ≡ P", node.toString());
    }

    @Test
    public void absZeroEquivXandY() {
        INode node = trees.BoolTrees.absZeroequivXandY();
        assertEquals("X ∧ (X ∨ Y) ≡ X ∧ Y", node.toString());
    }

    @Test
    public void weirdAbsZero() {
        INode node = trees.BoolTrees.weirdabsZeroequivXandY();
        assertEquals("X ∧ (X ∨ (Z ∧ W)) ≡ X ∧ Y", node.toString());
    }

    @Test
    public void goldenRulePQ() {
        INode node = trees.BoolTrees.goldenRulePQ();
        assertEquals("Q ≡ P ∨ Q ≡ P ∧ Q ≡ P", node.toString());
    }

    @Test
    public void impToOr() {
        INode node = trees.BoolTrees.impToOr();
        assertEquals("X ⇒ Y ≡ ¬X ∨ Y", node.toString());
    }

    @Test
    public void orIdem() {
        INode node = trees.BoolTrees.orIdem();
        assertEquals("X ∨ X ≡ X", node.toString());
    }

}
