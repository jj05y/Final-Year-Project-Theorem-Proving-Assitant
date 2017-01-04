package testruns.basictests;

import trees.BoolTrees;

/**
 * Created by joe on 17/11/16.
 */
public class TreeTest {

    public static void main(String[] args) {
        System.out.println(BoolTrees.absZero());
        System.out.println(BoolTrees.absZeroequivXandY());
        System.out.println(BoolTrees.goldenRulePQ());
        System.out.println(BoolTrees.weirdabsZeroequivXandY());
        System.out.println(BoolTrees.weirdBrokenabsZeroequivXandY());
        System.out.println(BoolTrees.XandYimplX());
    }
}
