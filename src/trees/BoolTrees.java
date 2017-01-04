package trees;

import interfaces.INode;
import nodes.BinaryOperator;
import nodes.NodeForBrackets;
import terminals.Identifier;
import constants.Operators;
import terminals.Literal;

/**
 * Created by joe on 22/10/16.
 */
public class BoolTrees {

    public static INode XandY() {
        INode XandY = new BinaryOperator(Operators.AND, new Identifier("X", null), new Identifier("Y", null), null);
        XandY.children()[0].setParent(XandY);
        XandY.children()[1].setParent(XandY);

        return XandY;
    }

    public static INode ZequivQ() {
        INode ZequivQ = new BinaryOperator(Operators.EQUIVAL, new Identifier("Z", null), new Identifier("Q", null), null);
        ZequivQ.children()[0].setParent(ZequivQ);
        ZequivQ.children()[1].setParent(ZequivQ);

        return ZequivQ;
    }

    public static INode YequivX() {
        INode YequivX = new BinaryOperator(Operators.EQUIVAL, new Identifier("Y", null), new Identifier("X", null), null);
        YequivX.children()[0].setParent(YequivX);
        YequivX.children()[1].setParent(YequivX);

        return YequivX;
    }
    public static INode YequivZ() {
        INode YequivZ = new BinaryOperator(Operators.EQUIVAL, new Identifier("Y", null), new Identifier("Z", null), null);
        YequivZ.children()[0].setParent(YequivZ);
        YequivZ.children()[1].setParent(YequivZ);

        return YequivZ;
    }

    public static INode XorY() {
        INode XorY = new BinaryOperator(Operators.OR, new Identifier("X", null), new Identifier("Y", null), null);
        XorY.children()[0].setParent(XorY);
        XorY.children()[1].setParent(XorY);

        return XorY;
    }
    public static INode XorZ() {
        INode XorZ = new BinaryOperator(Operators.OR, new Identifier("X", null), new Identifier("Z", null), null);
        XorZ.children()[0].setParent(XorZ);
        XorZ.children()[1].setParent(XorZ);

        return XorZ;
    }

    public static INode YorX() {
        INode YorX = new BinaryOperator(Operators.OR, new Identifier("Y", null), new Identifier("X", null), null);
        YorX.children()[0].setParent(YorX);
        YorX.children()[1].setParent(YorX);

        return YorX;
    }
    public static INode XorX() {
        INode XorX = new BinaryOperator(Operators.OR, new Identifier("X", null), new Identifier("X", null), null);
        XorX.children()[0].setParent(XorX);
        XorX.children()[1].setParent(XorX);

        return XorX;
    }

    public static INode PorQ() {
        INode PorQ = new BinaryOperator(Operators.OR, new Identifier("P", null), new Identifier("Q", null), null);
        PorQ.children()[0].setParent(PorQ);
        PorQ.children()[1].setParent(PorQ);

        return PorQ;
    }

    public static INode YandX() {
        INode YandX = new BinaryOperator(Operators.AND, new Identifier("Y", null), new Identifier("X", null), null);
        YandX.children()[0].setParent(YandX);
        YandX.children()[1].setParent(YandX);

        return YandX;
    }

    public static INode XandZ() {
        INode XandZ = new BinaryOperator(Operators.AND, new Identifier("X", null), new Identifier("Z", null), null);
        XandZ.children()[0].setParent(XandZ);
        XandZ.children()[1].setParent(XandZ);

        return XandZ;
    }

    public static INode PandQ() {
        INode PandQ = new BinaryOperator(Operators.AND, new Identifier("P", null), new Identifier("Q", null), null);
        PandQ.children()[0].setParent(PandQ);
        PandQ.children()[1].setParent(PandQ);

        return PandQ;
    }

    public static INode YorZ() {
        INode YorZ = new BinaryOperator(Operators.OR, new Identifier("Y", null), new Identifier("Z", null), null);
        YorZ.children()[0].setParent(YorZ);
        YorZ.children()[1].setParent(YorZ);

        return YorZ;
    }

    public static INode ZandW() {
        INode ZandW = new BinaryOperator(Operators.AND, new Identifier("Z", null), new Identifier("W", null), null);
        ZandW.children()[0].setParent(ZandW);
        ZandW.children()[1].setParent(ZandW);

        return ZandW;
    }


    public static INode XandYandZ() {
        INode XandYandZ = new BinaryOperator(Operators.AND, XandY(), new Identifier("Z", null), null);
        XandYandZ.children()[0].setParent(XandYandZ);
        XandYandZ.children()[1].setParent(XandYandZ);
        return XandYandZ;
    }
    public static INode PandQandW() {
        INode PandQandW = new BinaryOperator(Operators.AND, PandQ(), new Identifier("W", null), null);
        PandQandW.children()[0].setParent(PandQandW);
        PandQandW.children()[1].setParent(PandQandW);
        return PandQandW;
    }

    public static INode XandYandZandW() {
        BinaryOperator XandYandZandW = new BinaryOperator(Operators.AND, XandY(), ZandW(), null);
        XandYandZandW.children()[0].setParent(XandYandZandW);
        XandYandZandW.children()[1].setParent(XandYandZandW);
        return XandYandZandW;
    }

    public static INode XandYandZandWandQ() {
        BinaryOperator ZandWandQ = new BinaryOperator(Operators.AND, ZandW(), new Identifier("Q", null), null);
        ZandWandQ.children()[0].setParent(ZandWandQ);
        ZandWandQ.children()[1].setParent(ZandWandQ);

        BinaryOperator XandYandZandWandQ = new BinaryOperator(Operators.AND, XandY(), ZandWandQ, null);
        XandYandZandWandQ.children()[0].setParent(XandYandZandWandQ);
        XandYandZandWandQ.children()[1].setParent(XandYandZandWandQ);

        return XandYandZandWandQ;
    }

    public static INode XandYequivalZ() {
        BinaryOperator XandYequivalZ = new BinaryOperator(Operators.EQUIVAL, XandY(), new Identifier("Z"));
        XandYequivalZ.children()[0].setParent(XandYequivalZ);
        XandYequivalZ.children()[1].setParent(XandYequivalZ);
        return XandYequivalZ;
    }

    public static INode XandYequivalZandW() {
        BinaryOperator XandYequivalZandW = new BinaryOperator(Operators.EQUIVAL, XandY(), ZandW());
        XandYequivalZandW.children()[0].setParent(XandYequivalZandW);
        XandYequivalZandW.children()[1].setParent(XandYequivalZandW);
        return XandYequivalZandW;
    }

    public static INode XandYequivalXandYequivalZ() {
        BinaryOperator XandYequivalZ = new BinaryOperator(Operators.EQUIVAL, XandY(), XandYequivalZ());
        XandYequivalZ.children()[0].setParent(XandYequivalZ);
        XandYequivalZ.children()[1].setParent(XandYequivalZ);
        return XandYequivalZ;
    }

    public static INode XandYorZwithBrackets() {
        NodeForBrackets brackets = new NodeForBrackets(XandY());
        brackets.children()[0].setParent(brackets);

        BinaryOperator XandYorZ = new BinaryOperator(Operators.OR, brackets, new Identifier("Z"));
        XandYorZ.children()[0].setParent(XandYorZ);
        XandYorZ.children()[1].setParent(XandYorZ);
        return XandYorZ;
    }

    public static INode XandYorZ() {
        BinaryOperator XandYorZ = new BinaryOperator(Operators.OR, XandY(), new Identifier("Z"));
        XandYorZ.children()[0].setParent(XandYorZ);
        XandYorZ.children()[1].setParent(XandYorZ);
        return XandYorZ;
    }

    public static INode goldenRule() {
        BinaryOperator YequivalXorY = new BinaryOperator(Operators.EQUIVAL, new Identifier("Y"), XorY());
        YequivalXorY.children()[0].setParent(YequivalXorY);
        YequivalXorY.children()[1].setParent(YequivalXorY);

        BinaryOperator XandYequivalX = new BinaryOperator(Operators.EQUIVAL, XandY(), new Identifier("X"));
        XandYequivalX.children()[0].setParent(XandYequivalX);
        XandYequivalX.children()[1].setParent(XandYequivalX);

        BinaryOperator goldenRule = new BinaryOperator(Operators.EQUIVAL, YequivalXorY, XandYequivalX);
        goldenRule.children()[0].setParent(goldenRule);
        goldenRule.children()[1].setParent(goldenRule);

        return goldenRule;
    }

    public static INode goldenRulePQ() {
        BinaryOperator QequivalPorQ = new BinaryOperator(Operators.EQUIVAL, new Identifier("Q"), PorQ());
        QequivalPorQ.children()[0].setParent(QequivalPorQ);
        QequivalPorQ.children()[1].setParent(QequivalPorQ);

        BinaryOperator PandQequivalP = new BinaryOperator(Operators.EQUIVAL, PandQ(), new Identifier("P"));
        PandQequivalP.children()[0].setParent(PandQequivalP);
        PandQequivalP.children()[1].setParent(PandQequivalP);

        BinaryOperator goldenRule = new BinaryOperator(Operators.EQUIVAL, QequivalPorQ, PandQequivalP);
        goldenRule.children()[0].setParent(goldenRule);
        goldenRule.children()[1].setParent(goldenRule);

        return goldenRule;
    }

    public static INode edgeCaseOne() {//(Y ≡ X ∨ Y ≡ X) ∨ Z
        BinaryOperator partOne = new BinaryOperator(Operators.EQUIVAL, XorY(), new Identifier("X"));
        partOne.children()[0].setParent(partOne);
        partOne.children()[1].setParent(partOne);

        BinaryOperator rule = new BinaryOperator(Operators.EQUIVAL, new Identifier("Y"), partOne);
        rule.children()[0].setParent(rule);
        rule.children()[1].setParent(rule);

        rule = new BinaryOperator(Operators.OR, new NodeForBrackets(rule), new Identifier("Z"));

        return rule;
    }

    public static INode braker() {
        NodeForBrackets brackets = new NodeForBrackets(XandYandZ());

        BinaryOperator braker = new BinaryOperator(Operators.AND, new Identifier("Q"), brackets);
        braker.children()[0].setParent(braker);
        braker.children()[1].setParent(braker);

        return braker;
    }

    public static INode andOverOr() {
        NodeForBrackets bracketsYorZ = new NodeForBrackets(YorZ(), null);
        bracketsYorZ.children()[0].setParent(bracketsYorZ);

        BinaryOperator XandYorZ = new BinaryOperator(Operators.AND, new Identifier("X"), bracketsYorZ);
        XandYorZ.children()[0].setParent(XandYorZ);
        XandYorZ.children()[1].setParent(XandYorZ);

        NodeForBrackets bracketsXandY = new NodeForBrackets(XandY(), null);
        bracketsXandY.children()[0].setParent(bracketsXandY);

        NodeForBrackets bracketsXandZ = new NodeForBrackets(XandZ(), null);
        bracketsXandZ.children()[0].setParent(bracketsXandZ);

        BinaryOperator XandYorXandZ = new BinaryOperator(Operators.OR, bracketsXandY, bracketsXandZ);
        XandYorXandZ.children()[0].setParent(XandYorXandZ);
        XandYorXandZ.children()[1].setParent(XandYorXandZ);


        BinaryOperator andOverOr = new BinaryOperator(Operators.EQUIVAL, XandYorZ, XandYorXandZ);
        andOverOr.children()[0].setParent(andOverOr);
        andOverOr.children()[1].setParent(andOverOr);

        return andOverOr;
    }

    public static INode absZero() {
        INode lhs = new BinaryOperator(Operators.AND, new Identifier("P"), new NodeForBrackets(PorQ()));
        lhs.children()[0].setParent(lhs);
        lhs.children()[1].setParent(lhs);
        INode absZero = new BinaryOperator(Operators.EQUIVAL, lhs, new Identifier("P"));
        absZero.children()[0].setParent(absZero);
        absZero.children()[1].setParent(absZero);
        return absZero;
    }


    public static INode absZeroequivXandY() {
        INode lhs = new BinaryOperator(Operators.AND, new Identifier("X"), new NodeForBrackets(XorY()));
        lhs.children()[0].setParent(lhs);
        lhs.children()[1].setParent(lhs);
        INode expr = new BinaryOperator(Operators.EQUIVAL, lhs, XandY());
        expr.children()[0].setParent(expr);
        expr.children()[1].setParent(expr);
        return expr;
    }

    public static INode weirdabsZeroequivXandY() {
        INode XorZandW = new BinaryOperator(Operators.OR, new Identifier("X"), new NodeForBrackets(ZandW()));
        XorZandW.children()[0].setParent(XorZandW);
        XorZandW.children()[1].setParent(XorZandW);

        INode lhs = new BinaryOperator(Operators.AND, new Identifier("X"), new NodeForBrackets(XorZandW));
        lhs.children()[0].setParent(lhs);
        lhs.children()[1].setParent(lhs);
        INode expr = new BinaryOperator(Operators.EQUIVAL, lhs, XandY());
        expr.children()[0].setParent(expr);
        expr.children()[1].setParent(expr);
        return expr;
    }

    public static INode weirdBrokenabsZeroequivXandY() {
        INode XorZandW = new BinaryOperator(Operators.OR, new Identifier("S"), new NodeForBrackets(ZandW()));
        XorZandW.children()[0].setParent(XorZandW);
        XorZandW.children()[1].setParent(XorZandW);

        INode lhs = new BinaryOperator(Operators.AND, new Identifier("X"), new NodeForBrackets(XorZandW));
        lhs.children()[0].setParent(lhs);
        lhs.children()[1].setParent(lhs);
        INode expr = new BinaryOperator(Operators.EQUIVAL, lhs, XandY());
        expr.children()[0].setParent(expr);
        expr.children()[1].setParent(expr);
        return expr;
    }

    public static INode XequivY() {
        INode rule = new BinaryOperator(Operators.EQUIVAL, new Identifier("X"), new Identifier("Y"));
        rule.tellChildAboutParent();
        return rule;
    }


    public static INode equivAssoc() {
        INode lhs = new BinaryOperator(Operators.EQUIVAL, new Identifier("Y"), new Identifier("Z"));
        lhs = new NodeForBrackets(lhs);
        lhs = new BinaryOperator(Operators.EQUIVAL, new Identifier("X"), lhs);

        INode rhs = new NodeForBrackets(XequivY());
        rhs = new BinaryOperator(Operators.EQUIVAL, rhs, new Identifier("Z"));

        INode rule = new BinaryOperator(Operators.EQUIVAL, new NodeForBrackets(lhs), new NodeForBrackets(rhs));
        rule.tellChildAboutParent();
        return rule;
    }

    public static INode equivSem() {
        INode rule = new BinaryOperator(Operators.EQUIVAL, XequivY(), YequivX());
        rule.tellChildAboutParent();
        return rule;
    }

    public static INode equivId() {
        INode rule = new BinaryOperator(Operators.EQUIVAL, new Identifier("X"), t());
        rule = new BinaryOperator(Operators.EQUIVAL, new Identifier("X"), rule);
        rule.tellChildAboutParent();
        return rule;
    }

    public static INode equivReflex() {
        INode rule = new BinaryOperator(Operators.EQUIVAL, new Identifier("X"), new Identifier("X"));
        rule.tellChildAboutParent();
        return rule;
    }

    public static INode t() {
        return new Literal(true);
    }

    public static INode orSym() {
        INode rule = new BinaryOperator(Operators.EQUIVAL, XorY(),YorX());
        rule.tellChildAboutParent();
        return rule;
    }

    public static INode orIdem() {
        INode rule = new BinaryOperator(Operators.EQUIVAL, XorX(), new Identifier("X"));
        rule.tellChildAboutParent();
        return rule;
    }

    public static INode orAssoc(){
        INode lhs = new BinaryOperator(Operators.OR, new Identifier("X"), new NodeForBrackets(YorZ()));
        INode rhs = new BinaryOperator(Operators.OR, new NodeForBrackets(XorY()), new Identifier("Z"));
        INode rule = new BinaryOperator(Operators.EQUIVAL, lhs, rhs);
        rule.tellChildAboutParent();
        return rule;
    }

    public static INode orOverEquiv() {
        INode lhs = new BinaryOperator(Operators.OR, new Identifier("X"),new NodeForBrackets(YequivZ()));
        INode rhs = new BinaryOperator(Operators.EQUIVAL, XorY(), XorZ());
        INode rule = new BinaryOperator(Operators.EQUIVAL, lhs, rhs);
        rule.tellChildAboutParent();
        return rule;
    }

    public static INode proofStartOrOverEquiv() {
        INode rule = new BinaryOperator(Operators.OR, new NodeForBrackets(XorY()), new NodeForBrackets(XorZ()));
        rule.tellChildAboutParent();
        return rule;

    }

    public static INode XandYimplX() {
        INode rule = new BinaryOperator(Operators.IMPLICATION, XandY(), new Identifier("X"));
        rule.tellChildAboutParent();
        return rule;
    }


}
