package terminals;

import interfaces.INode;
import interfaces.ITerminal;
import nodes.Node;
import org.omg.PortableInterceptor.INACTIVE;

/**
 * Created by joe on 02/01/17.
 */
public class QuantifiedExpr extends Node implements INode, ITerminal {

    private String opAndDummy;
    private String range;
    private INode term;

    @Override
    public INode copySubTree() {
        INode copyOfThis = new QuantifiedExpr();
        return copyOfThis;
    }
}
