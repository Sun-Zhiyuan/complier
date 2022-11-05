public abstract class AstNode {
    public Token token;
    public String value;
    public Symbol symbol;

    public AstNode() {
    }

    public AstNode(Token token) {
        this.token = token;
        this.value = token.getValue();
    }

    public abstract AstNode accept(NodeVisitor nodeVisitor);
}
