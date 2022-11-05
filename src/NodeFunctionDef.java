import java.util.ArrayList;

public class NodeFunctionDef extends AstNode{
    public AstNode typeNode;
    public String functionName;
    public ArrayList<AstNode> formalParameters;
    public AstNode blockNode;
    public int offset = 0;

    public NodeFunctionDef(AstNode typeNode, String functionName, ArrayList<AstNode> formalParameters, AstNode blockNode) {
        this.typeNode = typeNode;
        this.functionName = functionName;
        this.formalParameters = formalParameters;
        this.blockNode = blockNode;
        this.offset = 0;
    }

    @Override
    public AstNode accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.FunctionDefNodeVisit(this);
    }
}
