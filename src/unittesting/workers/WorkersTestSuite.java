package unittesting.workers;

/**
 * Created by joe on 16/01/17.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import unittesting.trees.BoolTreesTest;
import unittesting.trees.FloorCeilTreesTest;
import unittesting.trees.MaxMinTreesTest;
import unittesting.trees.QuantTreesTest;
import workers.Remover;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        TreePermuterTest.class,
        RemoverTest.class,
        MatcherTest.class,
        RenamerTest.class,
        ReplacerTest.class
})

public class WorkersTestSuite {
}
