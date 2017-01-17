package unittesting.workers;

import beans.MatchAndTransition;
import constants.Operators;
import interfaces.INode;
import nodes.BinaryOperator;
import nodes.NodeForBrackets;
import org.junit.Test;
import terminals.Identifier;
import terminals.Literal;
import trees.BoolTrees;
import trees.QuantExprs;
import trees.QuantTrees;
import util.LazySet;
import workers.Remover;
import workers.TreePermuter;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
/**
 * Created by joe on 30/10/16.
 */
public class RemoverTest {

    @Test
    public void removeRootChild() {
        Remover remover = new Remover();
        INode n = BoolTrees.XandY();
        INode done = remover.treeWithoutNode(n, n.children()[0]);
        assertEquals("Y", done.toString());
    }


    @Test
    public void removeChild() {
        Remover remover = new Remover();
        INode n = BoolTrees.XandYandZ();
        INode done = remover.treeWithoutNode(n, n.children()[0].children()[0]);
        assertEquals("Y " + Operators.AND + " Z", done.toString());
    }

    @Test
    public void removeRoot() {
        Remover remover = new Remover();
        INode n = BoolTrees.XandY();
        INode done = remover.treeWithoutNode(n, n);
        assertNull(done);
    }



}
