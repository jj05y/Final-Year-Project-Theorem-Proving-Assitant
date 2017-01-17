package unittesting.misc;

import interfaces.INode;
import org.junit.Test;
import trees.BoolTrees;
import static org.junit.Assert.*;

/**
 * Created by joe on 12/12/16.
 */
public class WholeTreeCopyTest {

    @Test
    public void copyFromRoot() {

        INode goldenRule = BoolTrees.goldenRulePQ();
        INode copyOf = goldenRule.copyWholeTree();
        assertEquals(goldenRule,copyOf);

    }
    @Test
    public void copyFromOneChildCompareRoot() {

        INode goldenRule = BoolTrees.goldenRulePQ();
        INode copyOf = goldenRule.children()[0].copyWholeTree();
        assertEquals(goldenRule,copyOf.getRoot());
    }

    @Test
    public void copyFromOneChildCompareChild() {

        INode goldenRule = BoolTrees.goldenRulePQ();
        INode copyOf = goldenRule.children()[0].copyWholeTree();
        assertEquals(goldenRule.children()[0],copyOf);
    }

    @Test
    public void makeSureThereNotTheSameTree() {

        INode goldenRule = BoolTrees.goldenRulePQ();
        INode copyOf = goldenRule.copyWholeTree();
        assertNotSame(goldenRule,copyOf);
    }

}
