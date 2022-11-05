public class NodeUnaryOp extends AstNode{
    public AstNode right;
    public Token token, op;

    public NodeUnaryOp(Token op, AstNode right) {
        this.right = right;
        this.token = op;
        this.op = op;
    }

    @Override
    public AstNode accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.UnaryOpNodeVisit(this);
    }
}
