package testing.trees;

/**
 * Created by joe on 16/01/17.
 */
import interfaces.INode;
import org.junit.*;
import static org.junit.Assert.*;

public class TestBoolTrees {


    @Test
    public void XandYToString() {
        INode XandY = trees.BoolTrees.XandY();
        assertEquals("XandY ToStringTest Fail", "X ∧ Y", XandY.toString());
    }

}
