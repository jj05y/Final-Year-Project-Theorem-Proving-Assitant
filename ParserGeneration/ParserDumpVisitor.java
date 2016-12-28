import java.lang.StringBuilder;
import java.util.Vector;
import java.util.HashMap;

public class ParserDumpVisitor implements ParserVisitor {

    private int indent;

    public ParserDumpVisitor(){
        indent = 0;
    }



    private String indentString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }

    public Object visit(SimpleNode node, Object data){
        System.out.println(indentString() + node);
        indent++;
        data = node.childrenAccept(this, data);
        indent--;
        return data;
    }
    public Object visit(ASTExpr node, Object data){
        System.out.println(indentString() + node);
        indent++;
        data = node.childrenAccept(this, data);
        indent--;
        return data;
    }
    public Object visit(ASTPlus node, Object data){
        System.out.println(indentString() + node);
        indent++;
        data = node.childrenAccept(this, data);
        indent--;
        return data;
    }
    public Object visit(ASTId node, Object data){
        System.out.println(indentString() + node);
        indent++;
        data = node.childrenAccept(this, data);
        indent--;
        return data;
    }

}