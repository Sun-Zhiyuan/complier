import java.util.ArrayList;

public class NodeProgram extends AstNode{
    public String functionName;
    public ArrayList<AstNode> parametersNode;

    public NodeProgram(Token token, String functionName, ArrayList<AstNode> parametersNode) {
        super(token);
        this.functionName = functionName;
        this.parametersNode = parametersNode;
    }

    @Override
    public AstNode accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.FunctionCallNodeVisit(this);
    }
}
