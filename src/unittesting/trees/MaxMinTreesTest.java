package unittesting.trees;

/**
 * Created by joe on 16/01/17.
 */

import interfaces.INode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaxMinTreesTest {

    @Test
    public void xMaxY() {
        INode node = trees.NumTrees.xMaxY();
        assertEquals("x ↑ y", node.toString());
    }
    @Test
    public void xMinY() {
        INode node = trees.NumTrees.xMinY();
        assertEquals("x ↓ y", node.toString());
    }
    @Test
    public void under() {
        INode node = trees.LatticeTrees.defDown();
        assertEquals("z ⊑ x ↓ y ≡ z ⊑ x ∧ z ⊑ y", node.toString());
    }

}
