package unittesting.misc;

import interfaces.INode;
import junit.framework.*;
import org.junit.Test;
import parser.Parser;

import static org.junit.Assert.*;


/**
 * Created by joe on 29/12/16.
 */
public class ParserTest {

    @Test
    public void parseTest1() {
        Parser parser = new Parser("X and ! Y == Z == ( X or ( Y and B ) )");
        INode n = parser.getTree();
        assertNotNull(n.toString());
        assertEquals("X ∧ ¬Y ≡ Z ≡ (X ∨ (Y ∧ B))", n.toString());
    }

    @Test
    public void parseTest2() {
        Parser parser = new Parser("X and ! Y == Z == ( X or ( Y and B ) )");
        INode n = parser.getTree();
        assertNotNull(n.children()[0]);
        assertNotNull(n.children()[1]);
        assertEquals("X ∧ ¬Y ≡ Z", n.children()[0].toString());
        assertEquals("(X ∨ (Y ∧ B))", n.children()[1].toString());
    }

    @Test
    public void parseTest3() {
        Parser parser = new Parser("X and <| exists i : : f.i |>");
        INode n = parser.getTree();
        assertNotNull(n.toString());
        assertEquals("X ∧ ⟨ ∃ i :  : f.i ⟩", n.toString());
        assertEquals("X", n.children()[0].toString());
        assertEquals("⟨ ∃ i :  : f.i ⟩", n.children()[1].toString());
    }

    @Test
    public void parseTest4() {
        Parser parser = new Parser("<| exists i : : f.i |>");
        INode n = parser.getTree();
        assertNotNull(n.toString());
        assertEquals("⟨ ∃ i :  : f.i ⟩", n.toString());
    }

   @Test
    public void parseTest5() {
        Parser parser = new Parser("n + y <= z + n == X and Y");
        INode n = parser.getTree();
        assertNotNull(n.toString());
        assertEquals("n + y ≤ z + n ≡ X ∧ Y", n.toString());
    }

   @Test
    public void parseTest6() {
        Parser parser = new Parser(" |_ n _|  + y <= z + |' n '| == X and Y");
        INode n = parser.getTree();
        assertNotNull(n.toString());
        assertEquals("⌊n⌋ + y ≤ z + ⌈n⌉ ≡ X ∧ Y", n.toString());
    }


   @Test
    public void parseTest7() {
        Parser parser = new Parser(" X <= |_ Y + Z _| == X");
        INode n = parser.getTree();
        assertNotNull(n.toString());
        assertEquals("X ≤ ⌊Y + Z⌋ ≡ X", n.toString());
    }

   @Test
    public void parseTest8() {
        Parser parser = new Parser(" x up y under z == x up z and y under z");
        INode n = parser.getTree();
        assertNotNull(n.toString());
        assertEquals("x ↑ y ⊑ z ≡ x ↑ z ∧ y ⊑ z", n.toString());
    }

    @Test
    public void parseTest9() {
        Parser parser = new Parser(" <| forall w,x,y,z : : w under z == x under z and y under z |>");
        INode n = parser.getTree();
        assertNotNull(n.toString());
        assertEquals("⟨ ∀ w,x,y,z :  : w ⊑ z ≡ x ⊑ z ∧ y ⊑ z ⟩", n.toString());
    }

    @Test
    public void parseTest10() {
        Parser parser = new Parser("<| forall x : r.x : <| exists i : : X and f.i |> |>");
        INode n = parser.getTree();
        assertNotNull(n.toString());
        assertEquals("⟨ ∀ x : r.x : ⟨ ∃ i :  : X ∧ f.i ⟩ ⟩", n.toString());
    }


    @Test
    public void parseTest11() {
        Parser parser = new Parser("<| forall x,y : : <| exists w : : <| forall z : : w under z == x under z and y under z |> |> |> and X");
        INode n = parser.getTree();
        assertNotNull(n.toString());
        assertEquals("⟨ ∀ x,y :  : ⟨ ∃ w :  : ⟨ ∀ z :  : w ⊑ z ≡ x ⊑ z ∧ y ⊑ z ⟩ ⟩ ⟩ ∧ X", n.toString());
        assertEquals("⟨ ∀ x,y :  : ⟨ ∃ w :  : ⟨ ∀ z :  : w ⊑ z ≡ x ⊑ z ∧ y ⊑ z ⟩ ⟩ ⟩", n.children()[0].toString());
        assertEquals("X", n.children()[1].toString());
    }


}
