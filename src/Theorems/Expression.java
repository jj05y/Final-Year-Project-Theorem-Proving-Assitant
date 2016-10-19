package Theorems;

import Interfaces.IOperatorBase;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by joe on 19/09/16.
 */
public class Expression {

    private IOperatorBase root;
    //a map, key'd by sub expression, that has a list of valid replacements for each subexpression
    private HashMap<Expression, Vector<Expression>> substitutes;


}
