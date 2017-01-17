package unittesting.misc;

import constants.Operators;
import interfaces.INode;
import org.junit.Test;
import parser.Parser;
import terminals.QuantifiedExpr;
import trees.QuantExprs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created by joe on 29/12/16.
 */
public class QuantComponentTest {

    @Test
    public void wholeExpr() {
        INode node = QuantExprs.allRiXorFi();
        assertEquals("⟨ ∀ i : r.i : X ∨ f.i ⟩", node.toString());
    }

    @Test
    public void quantifier() {
        QuantifiedExpr quant = QuantExprs.allRiXorFi();
        assertEquals(Operators.FOR_ALL, quant.getQuantifier());
    }

    @Test
    public void dummy() {
        QuantifiedExpr quant = QuantExprs.allRiXorFi();
        assertEquals("i", quant.getDummyString());
    }



   @Test
    public void range() {
       QuantifiedExpr quant = QuantExprs.allRiXorFi();
       assertEquals("r.i", quant.getRange().toString());
    }

   @Test
    public void term() {
       QuantifiedExpr quant = QuantExprs.allRiXorFi();
       assertEquals("X ∨ f.i", quant.getTerm().toString());
    }


}
