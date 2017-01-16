package testing.trees;

import interfaces.INode;
import org.junit.Test;
import trees.QuantExprs;
import static org.junit.Assert.*;


/**
 * Created by joe on 16/01/17.
 */

public class TestQuantTrees {

    @Test
    public void testAllIRiFiToString() {
        INode quant = QuantExprs.allRiFi();
        System.out.println(quant);
        assertEquals("QuantExpr ToString fail", "⟨ ∀ i : r.i : f.i ⟩", quant.toString());
    }


}
