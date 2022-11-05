public class NodeBinaryOp extends AstNode{
    public AstNode left;
    public AstNode right;
    public Token token, op;

    public NodeBinaryOp(AstNode left, Token op, AstNode right) {
        this.left = left;
        this.right = right;
        this.token = op;
        this.op = op;
    }

    @Override
    public AstNode accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.BinaryOpNodeVisit(this);
    }
}
