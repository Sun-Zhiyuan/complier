public class NodeType extends AstNode{
    public String value;

    public NodeType(Token token) {
        super(token);
        this.value = token.getValue();
    }

    @Override
    public AstNode accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.BasicTypeNodeVisit(this);
    }
}
