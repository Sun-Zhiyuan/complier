public class NodeAssign extends AstNode {
    public AstNode left;
    public AstNode right;
    public Token token, op;

    public NodeAssign(AstNode left, Token op, AstNode right){
        this.left = left;
        this.token = op;
        this.op = op;
        this.right = right;
    }

    @Override
    public AstNode accept(NodeVisitor nodeVisitor){
        return nodeVisitor.AssignNodeVisit(this);
    }


}
