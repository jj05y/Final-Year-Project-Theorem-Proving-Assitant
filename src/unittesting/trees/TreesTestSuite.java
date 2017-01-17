package unittesting.trees;

/**
 * Created by joe on 16/01/17.
 */
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        BoolTreesTest.class,
        QuantTreesTest.class,
        FloorCeilTreesTest.class,
        MaxMinTreesTest.class
})

public class TreesTestSuite {
}