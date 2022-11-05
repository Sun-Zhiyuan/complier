import java.util.ArrayList;


public class Semantic extends NodeVisitor {

    public static int sum = -999;

    ScopeSymbolTable currentScope;


    public Semantic() {
        ScopeSymbolTable globalScope = new ScopeSymbolTable("global", 0, currentScope);
        this.currentScope = globalScope;
    }

    @Override
    public AstNode UnaryOpNodeVisit(NodeUnaryOp node) {
        node.right = node.right.accept(this);
        return node;
    }

    @Override
    public AstNode ReturnNodeVisit(NodeReturn node) {
        node.right = node.right.accept(this);
        return node;
    }

    @Override
    public AstNode BinaryOpNodeVisit(NodeBinaryOp node) {
        node.left = node.left.accept(this);
        node.right = node.right.accept(this);
        return node;
    }

    @Override
    public AstNode AssignNodeVisit(NodeAssign node) {
        node.left = node.left.accept(this);
        node.right = node.right.accept(this);
        return node;
    }

    @Override
    public AstNode IfNodeVisit(NodeIf node) {
        node.condition = node.condition.accept(this);
        if (node.thenStatement != null) {
            node.thenStatement = node.thenStatement.accept(this);
        }
        if (node.elseStatement != null) {
            node.elseStatement = node.elseStatement.accept(this);
        }
        return node;
    }

    @Override
    public AstNode WhileNodeVisit(NodeWhile node) {
        node.condition = node.condition.accept(this);
        if (node.statement != null) {
            node.statement = node.statement.accept(this);
        }
        return node;
    }

    @Override
    public AstNode BlockNodeVisit(NodeBlock node) {
        String blockName = this.currentScope.scopeName + " block" + (currentScope.scopeLevel + 1);
        //para
        ScopeSymbolTable blockScope = new ScopeSymbolTable(blockName, currentScope.scopeLevel + 1, currentScope);
        this.currentScope = blockScope;
        ArrayList<AstNode> nodes = new ArrayList<>();
        node.statementNodes.forEach(node1 -> {
            nodes.add(node1.accept(this));
        });
        node.statementNodes = nodes;

        //back
        this.currentScope = this.currentScope.enClosingScope;



        return node;
    }

    @Override
    public AstNode NumNodeVisit(NodeNum node) {
        return node;
    }

    @Override
    public AstNode VarNodeVisit(NodeVar node) {
        String varName = node.name;
        //always false
        Symbol varSymbol = this.currentScope.lookup(varName, false);
        if (varSymbol == null) {
            System.exit(12);
        } else {
            node.symbol = varSymbol;
        }
        return node;
    }

    @Override
    public AstNode VarDeclNodeVisit(NodeVarDecl node) {
        //type convert
        String varType = ((NodeType) node.typeNode).value;
        String varName = ((NodeVar) node.varNode).name;
        int varOffset;
        SymbolVar symbolVar;
        if (((NodeVar) node.varNode).array != null) {
            //size???? //TODO
            sum += 8 * ((NodeVar) node.varNode).array.size();
            varOffset = -sum;
            symbolVar = new SymbolVar(varName, varType, varOffset);
            ((NodeVar) node.varNode).symbol = symbolVar;
            this.currentScope.insert(symbolVar);
        } else {
            sum += 8;
            varOffset = -sum;
            symbolVar = new SymbolVar(varName, varType, varOffset);
            this.currentScope.insert(symbolVar);
        }
        return node;
    }

    @Override
    public AstNode FormalParamNodeVisit(NodeFormalParam node) {
        String parameterName = ((NodeVar) node.parameterNode).name;
        String parameterType = ((NodeType) node.typeNode).value;
        int parameterOffset;
        sum += 8;
        parameterOffset = -sum;
        SymbolParameter parameterSymbol = new SymbolParameter(parameterName, parameterType, parameterOffset);
        this.currentScope.insert(parameterSymbol);
        node.parameterSymbol = parameterSymbol;
        return node;
    }

    @Override
    public AstNode FunctionDefNodeVisit(NodeFunctionDef node) {
        //offset??
        sum = 0;
        String functionName = node.functionName;
        String functionType = ((NodeType) node.typeNode).value;
        SymbolFunction symbolFunction = new SymbolFunction(functionName, functionType);
        this.currentScope.insert(symbolFunction);
        ScopeSymbolTable scopeSymbolTable = new ScopeSymbolTable(functionName, this.currentScope.scopeLevel + 1, this.currentScope);
        this.currentScope = scopeSymbolTable;

        ArrayList<AstNode> nodes = new ArrayList<>();
        node.formalParameters.forEach(node1 ->{
            AstNode nodeTemp = node1.accept(this);
            nodes.add(nodeTemp);
        });
        node.formalParameters = nodes;
        node.blockNode = node.blockNode.accept(this);
        node.offset = sum;
        //完成，跳回
        this.currentScope = this.currentScope.enClosingScope;
        symbolFunction.blockNode = node.blockNode;
        return node;
    }

    @Override
    public AstNode FunctionCallNodeVisit(NodeProgram node) {
        return node;
    }

    @Override
    public AstNode VarArrayItemNodeVisit(NodeVarArray node) {
        String arrayName = node.token.getValue();
        Symbol arrSymbol = this.currentScope.lookup(arrayName, false);
        if (arrSymbol == null) {
            System.exit(1);
        } else {
            node.symbol = arrSymbol;
            node.index = node.index.accept(this);
        }
        return node;
    }

    @Override
    public AstNode BasicTypeNodeVisit(NodeType node) {
        return node;
    }

    public ArrayList<AstNode> Analyze(ArrayList<AstNode> tree) {
        ArrayList<AstNode> treeLocal = new ArrayList<>();
        for (int i = 0; i < tree.size(); i++) {
            AstNode node = tree.get(i).accept(this);
            treeLocal.add(node);
        }
        return treeLocal;
    }
}
