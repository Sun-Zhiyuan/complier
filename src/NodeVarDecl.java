public class NodeVarDecl extends AstNode{
    public AstNode typeNode;
    public AstNode varNode;

    public NodeVarDecl(AstNode typeNode, AstNode varNode) {
        this.typeNode = typeNode;
        this.varNode = varNode;
    }

    @Override
    public AstNode accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.VarDeclNodeVisit(this);
    }
}
