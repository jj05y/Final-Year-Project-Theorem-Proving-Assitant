package unittesting;

/**
 * Created by joe on 16/01/17.
 */
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import unittesting.misc.MiscTestSuite;
import unittesting.trees.BoolTreesTest;
import unittesting.trees.QuantTreesTest;
import unittesting.trees.TreesTestSuite;
import unittesting.workers.WorkersTestSuite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        TreesTestSuite.class,
        MiscTestSuite.class,
        WorkersTestSuite.class
})

public class AllTestsSuite {
}