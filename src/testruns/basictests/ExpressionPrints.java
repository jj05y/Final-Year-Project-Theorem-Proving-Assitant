package testruns.basictests;

import trees.*;

/**
 * Created by joe on 17/11/16.
 */
public class ExpressionPrints {

    public static void main(String[] args) {
        System.out.println(BoolTrees.absZero());
        System.out.println(BoolTrees.absZeroequivXandY());
        System.out.println(BoolTrees.goldenRulePQ());
        System.out.println(BoolTrees.weirdabsZeroequivXandY());
        System.out.println(BoolTrees.impToOr());
        System.out.println(BoolTrees.orIdem());
        System.out.println(QuantTrees.emptyImpliesTerm());
        System.out.println(QuantTrees.exprWithTwoQuants());
        System.out.println(QuantTrees.ruleWithTwoQuants());
        System.out.println(QuantTrees.XorQuantEquivQuant());
        System.out.println(FloorCeilingTrees.floorX());
        System.out.println(LatticeTrees.defDown());
        System.out.println(LatticeTrees.wDef());
        System.out.println(NumTrees.xGTEy());
        System.out.println(NumTrees.floorDef());
        System.out.println(NumTrees.ceilingDef());
    }
}