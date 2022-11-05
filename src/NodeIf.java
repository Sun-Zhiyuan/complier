public class NodeIf extends AstNode{
    public AstNode condition;
    public AstNode thenStatement;
    public AstNode elseStatement;

    public NodeIf(AstNode condition, AstNode thenStatement, AstNode elseStatement) {
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }


    @Override
    public AstNode accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.IfNodeVisit(this);
    }
}
