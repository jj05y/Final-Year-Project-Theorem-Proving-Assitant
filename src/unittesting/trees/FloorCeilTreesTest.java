package unittesting.trees;

/**
 * Created by joe on 16/01/17.
 */

import interfaces.INode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FloorCeilTreesTest {

    @Test
    public void floorX() {
        INode node = trees.FloorCeilingTrees.floorX();
        assertEquals("⌊X⌋", node.toString());
    }

    @Test
    public void floorDef() {
        INode node = trees.NumTrees.floorDef();
        assertEquals("n ≤ ⌊x⌋ ≡ n ≤ x", node.toString());
    }

    @Test
    public void ceilingDef() {
        INode node = trees.NumTrees.ceilingDef();
        assertEquals("⌈x⌉ ≤ n ≡ x ≤ n", node.toString());
    }

}
