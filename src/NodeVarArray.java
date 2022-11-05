public class NodeVarArray extends AstNode{
    public Token token;
    public AstNode index;
    public String array = "YES";

    public NodeVarArray(Token token, AstNode index) {
        this.token = token;
        this.index = index;
    }

    @Override
    public AstNode accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.VarArrayItemNodeVisit(this);
    }
}
