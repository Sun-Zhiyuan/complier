//import jdk.nashorn.internal.ir.ReturnNode;

public class NodeReturn extends AstNode{
    public AstNode right;
    public Token token;
    public String functionName;

    public NodeReturn(Token token, AstNode right, String functionName) {
        this.right = right;
        this.token = token;
        this.functionName = functionName;
    }

    @Override
    public AstNode accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.ReturnNodeVisit(this);
    }
}
