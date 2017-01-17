package unittesting.workers;

import constants.Operators;
import interfaces.INode;
import nodes.BinaryOperator;
import nodes.NodeForBrackets;
import org.junit.Test;
import terminals.ArrayAndIndex;
import terminals.Identifier;
import trees.BoolTrees;
import trees.QuantExprs;
import trees.QuantTrees;
import workers.Matcher;
import workers.Renamer;

import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by joe on 30/10/16.
 */
public class RenamerTest {

    @Test
    public void XYtoPQ() {
        Renamer renamer = new Renamer();
        HashMap<String, INode> lookupTable = new HashMap<>();
        lookupTable.put("X", new Identifier("P"));
        lookupTable.put("Y", new Identifier("Q"));
        INode node = BoolTrees.goldenRule();
        node = renamer.renameIdsWithLookupTable(node, lookupTable);
        assertEquals(BoolTrees.goldenRulePQ(), node);
    }

    @Test
    public void XYtoPQandW() {
        Renamer renamer = new Renamer();
        HashMap<String, INode> lookupTable = new HashMap<>();
        lookupTable.put("X", BoolTrees.PandQ());
        lookupTable.put("Y", new Identifier("W"));
        INode node = BoolTrees.goldenRule();
        node = renamer.renameIdsWithLookupTable(node, lookupTable);
        assertEquals("W ≡ (P ∧ Q) ∨ W ≡ (P ∧ Q) ∧ W ≡ P ∧ Q", node.toString());
    }

    @Test
    public void valNotInTable() {
        Renamer renamer = new Renamer();
        HashMap<String, INode> lookupTable = new HashMap<>();
        lookupTable.put("X", new Identifier("P"));
        lookupTable.put("Y", new Identifier("Q"));
        INode node = BoolTrees.XandYandZ();
        node = renamer.renameIdsWithLookupTable(node, lookupTable);
        assertEquals("P ∧ Q ∧ Z", node.toString());
    }

   @Test
    public void renameWithQuant() {
        Renamer renamer = new Renamer();
        HashMap<String, INode> lookupTable = new HashMap<>();
        lookupTable.put("X", new Identifier("P"));
        lookupTable.put("s.i", new Identifier("z.y"));
        INode node = QuantTrees.ruleWithTwoQuants();
        node = renamer.renameIdsWithLookupTable(node, lookupTable);
        assertEquals("⟨ ∀ i : z.y : f.i ⟩ ∧ ⟨ ∀ i : r.i : P ∨ f.i ⟩ ≡ P", node.toString());
    }




}
