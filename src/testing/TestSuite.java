package testing;

/**
 * Created by joe on 16/01/17.
 */
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import testing.trees.TestBoolTrees;
import testing.trees.TestQuantTrees;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        TestBoolTrees.class,
        TestQuantTrees.class
})

public class TestSuite {
}