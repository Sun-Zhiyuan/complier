public abstract class NodeVisitor {
    public abstract AstNode UnaryOpNodeVisit(NodeUnaryOp nodeUnaryOp);
    public abstract AstNode ReturnNodeVisit(NodeReturn nodeReturn);
    public abstract AstNode BinaryOpNodeVisit(NodeBinaryOp nodeBinaryOp);
    public abstract AstNode AssignNodeVisit(NodeAssign nodeAssign);
    public abstract AstNode IfNodeVisit(NodeIf ifNode);
    public abstract AstNode WhileNodeVisit(NodeWhile whileNode);
    public abstract AstNode BlockNodeVisit(NodeBlock nodeBlock);
    public abstract AstNode NumNodeVisit(NodeNum nodeNum);
    public abstract AstNode VarNodeVisit(NodeVar nodeVar);
    public abstract AstNode VarDeclNodeVisit(NodeVarDecl nodeVarDecl);
    public abstract AstNode FormalParamNodeVisit(NodeFormalParam nodeFormalParam);
    public abstract AstNode FunctionDefNodeVisit(NodeFunctionDef nodeFunctionDef);
    public abstract AstNode FunctionCallNodeVisit(NodeProgram nodeProgram);
    public abstract AstNode VarArrayItemNodeVisit(NodeVarArray nodeVarArray);
    public abstract AstNode BasicTypeNodeVisit(NodeType nodeType);

}
