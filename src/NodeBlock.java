import java.util.ArrayList;

public class NodeBlock extends AstNode{
    public Token leftToken;
    public Token rightToken;
    public ArrayList<AstNode> statementNodes;

    public NodeBlock(Token leftToken, Token rightToken, ArrayList<AstNode> statementNodes) {
        this.leftToken = leftToken;
        this.rightToken = rightToken;
        this.statementNodes = statementNodes;
    }


    @Override
    public AstNode accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.BlockNodeVisit(this);
    }
}
