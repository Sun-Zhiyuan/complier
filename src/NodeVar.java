import java.util.ArrayList;

public class NodeVar extends AstNode{
    public Symbol symbol = null;
    public String name;
    public ArrayList<String> array;
    public int size;

    public NodeVar(Token token) {
        super(token);
        name = token.getValue();
        array = null;
    }

    @Override
    public AstNode accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.VarNodeVisit(this);
    }

    public NodeVar(Token token, int size, ArrayList<String> array) {
        super(token);
        this.size = size;
        this.array = array;
        name = token.getValue();
    }


}
