public class NodeNum extends AstNode{
    public NodeNum(Token token) {
        super(token);
    }

    @Override
    public AstNode accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.NumNodeVisit(this);
    }
}
