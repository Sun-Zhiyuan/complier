public class NodeFormalParam extends AstNode{
    public AstNode typeNode;
    public AstNode parameterNode;
    public Symbol parameterSymbol = null;

    public NodeFormalParam(AstNode typeNode, AstNode parameterNode) {
        this.typeNode = typeNode;
        this.parameterNode = parameterNode;
    }

    @Override
    public AstNode accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.FormalParamNodeVisit(this);
    }
}
