package Trees;

import Interfaces.INode;
import Nodes.CommutativeAssociativeBinaryOperator;
import Nodes.NodeForBrackets;
import Terminals.Identifier;
import Constants.Operators;
import com.sun.org.apache.xpath.internal.operations.Or;

/**
 * Created by joe on 22/10/16.
 */
public class Trees {

    public static INode XandY() {
        INode XandY = new CommutativeAssociativeBinaryOperator(Operators.AND, new Identifier('X', null), new Identifier('Y', null), null);
        XandY.children()[0].setParent(XandY);
        XandY.children()[1].setParent(XandY);

        return XandY;
    }

    public static INode ZequivQ() {
        INode ZequivQ = new CommutativeAssociativeBinaryOperator(Operators.EQUIVAL, new Identifier('Z', null), new Identifier('Q', null), null);
        ZequivQ.children()[0].setParent(ZequivQ);
        ZequivQ.children()[1].setParent(ZequivQ);

        return ZequivQ;
    }

    public static INode YequivX() {
        INode YequivX = new CommutativeAssociativeBinaryOperator(Operators.EQUIVAL, new Identifier('Y', null), new Identifier('X', null), null);
        YequivX.children()[0].setParent(YequivX);
        YequivX.children()[1].setParent(YequivX);

        return YequivX;
    }

    public static INode XorY() {
        INode XorY = new CommutativeAssociativeBinaryOperator(Operators.OR, new Identifier('X', null), new Identifier('Y', null), null);
        XorY.children()[0].setParent(XorY);
        XorY.children()[1].setParent(XorY);

        return XorY;
    }
    public static INode PorQ() {
        INode PorQ = new CommutativeAssociativeBinaryOperator(Operators.OR, new Identifier('P', null), new Identifier('Q', null), null);
        PorQ.children()[0].setParent(PorQ);
        PorQ.children()[1].setParent(PorQ);

        return PorQ;
    }

    public static INode YandX() {
        INode YandX = new CommutativeAssociativeBinaryOperator(Operators.AND, new Identifier('Y', null), new Identifier('X', null), null);
        YandX.children()[0].setParent(YandX);
        YandX.children()[1].setParent(YandX);

        return YandX;
    }
    public static INode XandZ() {
        INode XandZ = new CommutativeAssociativeBinaryOperator(Operators.AND, new Identifier('X', null), new Identifier('Z', null), null);
        XandZ.children()[0].setParent(XandZ);
        XandZ.children()[1].setParent(XandZ);

        return XandZ;
    }
    public static INode PandQ() {
        INode PandQ = new CommutativeAssociativeBinaryOperator(Operators.AND, new Identifier('P', null), new Identifier('Q', null), null);
        PandQ.children()[0].setParent(PandQ);
        PandQ.children()[1].setParent(PandQ);

        return PandQ;
    }
    public static INode YorZ() {
        INode YorZ = new CommutativeAssociativeBinaryOperator(Operators.OR, new Identifier('Y', null), new Identifier('Z', null), null);
        YorZ.children()[0].setParent(YorZ);
        YorZ.children()[1].setParent(YorZ);

        return YorZ;
    }

    public static INode ZandW() {
        INode ZandW = new CommutativeAssociativeBinaryOperator(Operators.AND, new Identifier('Z', null), new Identifier('W', null), null);
        ZandW.children()[0].setParent(ZandW);
        ZandW.children()[1].setParent(ZandW);

        return ZandW;
    }


    public static INode XandYandZ() {
        INode XandYandZ = new CommutativeAssociativeBinaryOperator(Operators.AND, XandY(), new Identifier('Z', null), null);
        XandYandZ.children()[0].setParent(XandYandZ);
        XandYandZ.children()[1].setParent(XandYandZ);
        return  XandYandZ;
    }

    public static INode XandYandZandW() {
        CommutativeAssociativeBinaryOperator XandYandZandW = new CommutativeAssociativeBinaryOperator(Operators.AND, XandY(), ZandW(), null);
        XandYandZandW.children()[0].setParent(XandYandZandW);
        XandYandZandW.children()[1].setParent(XandYandZandW);
        return XandYandZandW;
    }

    public static INode XandYandZandWandQ() {
        CommutativeAssociativeBinaryOperator ZandWandQ = new CommutativeAssociativeBinaryOperator(Operators.AND, ZandW(), new Identifier('Q', null) ,null);
        ZandWandQ.children()[0].setParent(ZandWandQ);
        ZandWandQ.children()[1].setParent(ZandWandQ);

        CommutativeAssociativeBinaryOperator XandYandZandWandQ = new CommutativeAssociativeBinaryOperator(Operators.AND, XandY(), ZandWandQ, null);
        XandYandZandWandQ.children()[0].setParent(XandYandZandWandQ);
        XandYandZandWandQ.children()[1].setParent(XandYandZandWandQ);

        return  XandYandZandWandQ;
    }

    public static INode XandYequivalZ() {
        CommutativeAssociativeBinaryOperator XandYequivalZ = new CommutativeAssociativeBinaryOperator(Operators.EQUIVAL, XandY(), new Identifier('Z'));
        XandYequivalZ.children()[0].setParent(XandYequivalZ);
        XandYequivalZ.children()[1].setParent(XandYequivalZ);
        return XandYequivalZ;
    }

    public static INode XandYequivalZandW() {
        CommutativeAssociativeBinaryOperator XandYequivalZandW = new CommutativeAssociativeBinaryOperator(Operators.EQUIVAL, XandY(),ZandW());
        XandYequivalZandW.children()[0].setParent(XandYequivalZandW);
        XandYequivalZandW.children()[1].setParent(XandYequivalZandW);
        return XandYequivalZandW;
    }

    public static INode XandYequivalXandYequivalZ() {
        CommutativeAssociativeBinaryOperator XandYequivalZ = new CommutativeAssociativeBinaryOperator(Operators.EQUIVAL, XandY(),XandYequivalZ());
        XandYequivalZ.children()[0].setParent(XandYequivalZ);
        XandYequivalZ.children()[1].setParent(XandYequivalZ);
        return XandYequivalZ;
    }

    public static INode XandYorZwithBrackets() {
        NodeForBrackets brackets = new NodeForBrackets(XandY());
        brackets.children()[0].setParent(brackets);

        CommutativeAssociativeBinaryOperator XandYorZ = new CommutativeAssociativeBinaryOperator(Operators.OR, brackets, new Identifier('Z'));
        XandYorZ.children()[0].setParent(XandYorZ);
        XandYorZ.children()[1].setParent(XandYorZ);
        return  XandYorZ;
    }

    public static INode XandYorZ() {
        CommutativeAssociativeBinaryOperator XandYorZ = new CommutativeAssociativeBinaryOperator(Operators.OR, XandY(), new Identifier('Z'));
        XandYorZ.children()[0].setParent(XandYorZ);
        XandYorZ.children()[1].setParent(XandYorZ);
        return  XandYorZ;
    }

    public static INode goldenRule() {
        CommutativeAssociativeBinaryOperator YequivalXorY = new CommutativeAssociativeBinaryOperator(Operators.EQUIVAL,new Identifier('Y'),XorY());
        YequivalXorY.children()[0].setParent(YequivalXorY);
        YequivalXorY.children()[1].setParent(YequivalXorY);

        CommutativeAssociativeBinaryOperator XandYequivalX = new CommutativeAssociativeBinaryOperator(Operators.EQUIVAL,XandY(),new Identifier('X'));
        XandYequivalX.children()[0].setParent(XandYequivalX);
        XandYequivalX.children()[1].setParent(XandYequivalX);

        CommutativeAssociativeBinaryOperator goldenRule = new CommutativeAssociativeBinaryOperator(Operators.EQUIVAL, YequivalXorY,XandYequivalX);
        goldenRule.children()[0].setParent(goldenRule);
        goldenRule.children()[1].setParent(goldenRule);

        return  goldenRule;
    }
    public static INode goldenRulePQ() {
        CommutativeAssociativeBinaryOperator QequivalPorQ = new CommutativeAssociativeBinaryOperator(Operators.EQUIVAL,new Identifier('Q'),PorQ());
        QequivalPorQ.children()[0].setParent(QequivalPorQ);
        QequivalPorQ.children()[1].setParent(QequivalPorQ);

        CommutativeAssociativeBinaryOperator PandQequivalP = new CommutativeAssociativeBinaryOperator(Operators.EQUIVAL,PandQ(),new Identifier('P'));
        PandQequivalP.children()[0].setParent(PandQequivalP);
        PandQequivalP.children()[1].setParent(PandQequivalP);

        CommutativeAssociativeBinaryOperator goldenRule = new CommutativeAssociativeBinaryOperator(Operators.EQUIVAL, QequivalPorQ,PandQequivalP);
        goldenRule.children()[0].setParent(goldenRule);
        goldenRule.children()[1].setParent(goldenRule);

        return  goldenRule;
    }

    public static INode braker() {
        NodeForBrackets brackets= new NodeForBrackets(XandYandZ());

        CommutativeAssociativeBinaryOperator braker = new CommutativeAssociativeBinaryOperator(Operators.AND, new Identifier('Q'), brackets);
        braker.children()[0].setParent(braker);
        braker.children()[1].setParent(braker);

        return braker;
    }

    public static INode andOverOr() {
        NodeForBrackets bracketsYorZ = new NodeForBrackets(YorZ(), null);
        bracketsYorZ.children()[0].setParent(bracketsYorZ);

        CommutativeAssociativeBinaryOperator XandYorZ = new CommutativeAssociativeBinaryOperator(Operators.AND, new Identifier('X'), bracketsYorZ);
        XandYorZ.children()[0].setParent(XandYorZ);
        XandYorZ.children()[1].setParent(XandYorZ);

        NodeForBrackets bracketsXandY = new NodeForBrackets(XandY(), null);
        bracketsXandY.children()[0].setParent(bracketsXandY);

        NodeForBrackets bracketsXandZ = new NodeForBrackets(XandZ(), null);
        bracketsXandZ.children()[0].setParent(bracketsXandZ);

        CommutativeAssociativeBinaryOperator XandYorXandZ = new CommutativeAssociativeBinaryOperator(Operators.OR, bracketsXandY, bracketsXandZ);
        XandYorXandZ.children()[0].setParent(XandYorXandZ);
        XandYorXandZ.children()[1].setParent(XandYorXandZ);


        CommutativeAssociativeBinaryOperator andOverOr = new CommutativeAssociativeBinaryOperator(Operators.EQUIVAL, XandYorZ, XandYorXandZ);
        andOverOr.children()[0].setParent(andOverOr);
        andOverOr.children()[1].setParent(andOverOr);

        return andOverOr;
    }

    public static INode absZero() {
        INode lhs = new CommutativeAssociativeBinaryOperator(Operators.AND, new Identifier('P'), new NodeForBrackets(PorQ()));
        lhs.children()[0].setParent(lhs);
        lhs.children()[1].setParent(lhs);
        INode absZero = new CommutativeAssociativeBinaryOperator(Operators.EQUIVAL, lhs, new Identifier('P'));
        absZero.children()[0].setParent(absZero);
        absZero.children()[1].setParent(absZero);
        return absZero;
    }


    public static INode absZeroequivXandY() {
        INode lhs = new CommutativeAssociativeBinaryOperator(Operators.AND, new Identifier('X'), new NodeForBrackets(XorY()));
        lhs.children()[0].setParent(lhs);
        lhs.children()[1].setParent(lhs);
        INode expr = new CommutativeAssociativeBinaryOperator(Operators.EQUIVAL, lhs, XandY());
        expr.children()[0].setParent(expr);
        expr.children()[1].setParent(expr);
        return expr;
    }
    public static INode weirdabsZeroequivXandY() {
        INode XorZandW = new CommutativeAssociativeBinaryOperator(Operators.OR, new Identifier('X'), new NodeForBrackets(ZandW()));
        XorZandW.children()[0].setParent(XorZandW);
        XorZandW.children()[1].setParent(XorZandW);

        INode lhs = new CommutativeAssociativeBinaryOperator(Operators.AND, new Identifier('X'), new NodeForBrackets(XorZandW));
        lhs.children()[0].setParent(lhs);
        lhs.children()[1].setParent(lhs);
        INode expr = new CommutativeAssociativeBinaryOperator(Operators.EQUIVAL, lhs, XandY());
        expr.children()[0].setParent(expr);
        expr.children()[1].setParent(expr);
        return expr;
    }

    public static INode weirdBrokenabsZeroequivXandY() {
        INode XorZandW = new CommutativeAssociativeBinaryOperator(Operators.OR, new Identifier('S'), new NodeForBrackets(ZandW()));
        XorZandW.children()[0].setParent(XorZandW);
        XorZandW.children()[1].setParent(XorZandW);

        INode lhs = new CommutativeAssociativeBinaryOperator(Operators.AND, new Identifier('X'), new NodeForBrackets(XorZandW));
        lhs.children()[0].setParent(lhs);
        lhs.children()[1].setParent(lhs);
        INode expr = new CommutativeAssociativeBinaryOperator(Operators.EQUIVAL, lhs, XandY());
        expr.children()[0].setParent(expr);
        expr.children()[1].setParent(expr);
        return expr;
    }

}
