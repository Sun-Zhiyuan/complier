public class NodeWhile extends AstNode{
    public AstNode condition;
    public AstNode statement;

    public NodeWhile(AstNode condition, AstNode statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public AstNode accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.WhileNodeVisit(this);
    }
}
