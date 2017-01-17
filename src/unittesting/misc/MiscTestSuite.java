package unittesting.misc;

/**
 * Created by joe on 16/01/17.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import unittesting.trees.BoolTreesTest;
import unittesting.trees.FloorCeilTreesTest;
import unittesting.trees.MaxMinTreesTest;
import unittesting.trees.QuantTreesTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        ParserTest.class,
        WholeTreeCopyTest.class,
        QuantComponentTest.class
})

public class MiscTestSuite {
}