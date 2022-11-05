import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author 17383
 */
public class Parser {
    List<Token> tokenList;
    public String functionName = "";
    Stack<Token> stack = new Stack<>();
    Token tempToken = new Token("$", "$");


    public Parser(@NotNull List<Token> tokenList) {
        this.tokenList = tokenList;
        stack.push(tempToken);
        //进栈
        for (int i = tokenList.size() - 1; i >= 0; i--) {
            stack.push(tokenList.get(i));
        }
    }

    private void match(String tokenType) throws Exception {
        if (stack.peek().getType().equals(tokenType)) {
            System.out.println(stack.pop());
        } else {
            throw new Exception(stack.peek().toString());
        }
    }

    //    program = function_definition*
    public ArrayList<AstNode> parseProgram() throws Exception {
        ArrayList<AstNode> functionDefAstNode = new ArrayList<>();

        //可以通过得到函数名来记录更新状态
        while (!"$".equals(stack.peek().getType())) {
            AstNode functionAstNode = parseFunctionDefinition();
            functionDefAstNode.add(functionAstNode);
        }
        if ("$".equals(stack.peek().getType())) {
            match("$");
            if (stack.empty()) {
                System.out.println("accept");
            }
        } else {
            throw new Exception(tempToken.toString());
        }
        return functionDefAstNode;
    }

    //    function_definition = type_specification identifier "(" formal_parameters? ")" block
    private AstNode parseFunctionDefinition() throws Exception {
        AstNode typeAstNode = parseTypeSpecification();
        functionName = stack.peek().getValue();
        ArrayList<AstNode> formalParameters = new ArrayList<>();
        AstNode blockAstNode;

        match("11");
        match("26");
        if (!")".equals(stack.peek().getValue())) {
            formalParameters = parseFormalParameters();
        }
        match("27");
        blockAstNode = parseBlock();

        return new NodeFunctionDef(typeAstNode, functionName, formalParameters, blockAstNode);
    }

    //    type_specification = "int"|"bool"
    private AstNode parseTypeSpecification() throws Exception {
        Token tokenTemp = stack.peek();
        if ("int".equals(stack.peek().getValue())) {
            match("1");
            return new NodeType(tokenTemp);
        }

        if ("bool".equals(stack.peek().getValue())) {
            match("9");
            return new NodeType(tokenTemp);
        }

        tokenTemp.setValue(tokenTemp.getValue() + "ERROR TYPE");
        return null;
    }

    //    formal_parameters = formal_parameter ("," formal_parameter)*
    private ArrayList<AstNode> parseFormalParameters() throws Exception {
        ArrayList<AstNode> formalParameters = new ArrayList<>();
        formalParameters.add(parseFormalParameter());
        while (",".equals(stack.peek().getValue())) {
            match("-1");
            formalParameters.add(parseFormalParameter());
        }
        return formalParameters;
    }

    //    formal_parameter = type_specification identifier
    private AstNode parseFormalParameter() throws Exception {
        AstNode typeAstNode = parseTypeSpecification();
        AstNode parameterAstNode = new NodeVar(stack.peek());
        match("11");
        return new NodeFormalParam(typeAstNode, parameterAstNode);
    }

    //    block = "{" compound_statement "}"
    private AstNode parseBlock() throws Exception {
        Token leftToken = stack.peek();
        match("34");
        ArrayList<AstNode> statementAstNodes = parseCompoundStatement();
        Token rightToken = stack.peek();
        match("35");
        return new NodeBlock(leftToken, rightToken, statementAstNodes);

    }

    //    compound_statement = (variable_declaration | statement)*
    private ArrayList<AstNode> parseCompoundStatement() throws Exception {
        ArrayList<AstNode> statementAstNodes = new ArrayList<>();
        while (!"}".equals(stack.peek().getValue())) {
            if ("int".equals(stack.peek().getValue()) || "bool".equals(stack.peek().getValue())) {
                ArrayList<AstNode> variableAstNodes = parseVariableDeclaration();
                statementAstNodes.addAll(variableAstNodes);

            } else {
                AstNode statementAstNode = parseStatement();
                statementAstNodes.add(statementAstNode);
            }
        }
        return statementAstNodes;
    }
//    variable_declaration = type_specification identifier ("," identifier)* ";"
//            | type_specification identifier "[" num "]" ("=" "{" (num)? ("," num)* "}")?

    private ArrayList<AstNode> parseVariableDeclaration() throws Exception {
        ArrayList<AstNode> varAstNodes = new ArrayList<>();
        AstNode typeAstNode = parseTypeSpecification();
        Token token = stack.peek();
        match("11");
        if (!"24".equals(stack.peek().getType())) {
            AstNode varAstNode = new NodeVar(token);
            AstNode varDecAstNode = new NodeVarDecl(typeAstNode, varAstNode);
            varAstNodes.add(varDecAstNode);
            //多个变量
            while ("-1".equals(stack.peek().getType())) {
                match("-1");
                AstNode varAstNodeS = new NodeVar(stack.peek());
                AstNode varDecAstNodeS = new NodeVarDecl(typeAstNode, varAstNodeS);
                varAstNodes.add(varDecAstNodeS);
                match("11");
            }
            match("44");
        } else if ("[".equals(stack.peek().getType())) {
            ArrayList<String> array = new ArrayList<>();

            match("[");
            int arrSize = Integer.parseInt(stack.peek().getValue());
            match("INT");
            match("]");
            if ("=".equals(stack.peek().getType())) {
                match("=");
                match("{");
                if ("INT".equals(stack.peek().getType())) {
                    array.add(stack.peek().getValue());
                    match("INT");
                    while (",".equals(stack.peek().getType())) {
                        match(",");
                        array.add(stack.peek().getValue());
                        match("INT");
                    }
                }
                match("}");
                AstNode varAstNode = new NodeVar(token, arrSize, array);
                AstNode varDeclAstNode = new NodeVarDecl(typeAstNode, varAstNode);
                varAstNodes.add(varDeclAstNode);
            }
        }
        return varAstNodes;
    }

    //    statement = expression-statement
//                     | "return" expression-statement
//                     | "{" compound_statement "}"
//                     | "if" "(" expression ")" statement ("else" statement)?
//                     | "while" "(" expression ")" statement
    private AstNode parseStatement() throws Exception {
        Token token = stack.peek();
        if ("8".equals(stack.peek().getType())) {
            match("8");
            return new NodeReturn(token, parseExpressionStatement(), functionName);

        } else if ("34".equals(stack.peek().getType())) {
            return parseBlock();
        } else if ("2".equals(stack.peek().getType())) {
            AstNode condition = null;
            AstNode thenStatement = null;
            AstNode elseStatement = null;

            match("2");
            match("26");
            condition = parseExpr();
            thenStatement = parseStatement();
            if ("4".equals(stack.peek().getType())) {
                match("4");
                elseStatement = parseStatement();
            }
            return new NodeIf(condition, thenStatement, elseStatement);
        } else if ("3".equals(stack.peek().getType())) {
            AstNode condition = null;
            AstNode statement = null;
            match("3");
            match("26");
            condition = parseExpr();
            match("27");
            statement = parseStatement();
            return new NodeWhile(condition, statement);
        } else {
            return parseExpressionStatement();
        }

    }

    //    expression-statement = expression? ";"
    private AstNode parseExpressionStatement() throws Exception {

        AstNode exprAstNode = null;
        if ("44".equals(stack.peek().getType())) {
            match("44");
        } else {
            exprAstNode = parseExpr();
            match("44");
        }
        return exprAstNode;
    }

    //      expression = logic ("=" expression)?
    private AstNode parseExpr() throws Exception {
        AstNode AstNode = parseLogic();
        Token token = stack.peek();
        if ("24".equals(stack.peek().getType())) {
            match("24");
            AstNode = new NodeAssign(AstNode, token, parseExpr());
        }
        return AstNode;

    }


    //    logic =equality ("&&" equality | "||" equality)*
    private AstNode parseLogic() throws Exception {
        AstNode AstNode = parseEquality();
        while ("&&".equals(stack.peek().getValue()) || "||".equals(stack.peek().getValue())) {
            Token token = stack.peek();
            if ("&&".equals(stack.peek().getValue())) {
                match("&&");
                AstNode = new NodeBinaryOp(AstNode, token, parseEquality());
                continue;
            }
            if ("||".equals(stack.peek().getValue())) {
                match("||");
                AstNode = new NodeBinaryOp(AstNode, token, parseEquality());
//                continue;
            }
        }
        return AstNode;
    }

    //    equality = relational ("==" relational | "! =" relational)*
    private AstNode parseEquality() throws Exception {
        AstNode AstNode = parseRelational();
        while ("5".equals(stack.peek().getValue()) || "6".equals(stack.peek().getValue())) {
            Token token = stack.peek();
            if ("5".equals(stack.peek().getValue())) {
                match("5");
                AstNode = new NodeBinaryOp(AstNode, token, parseRelational());
                continue;
            }
            if ("6".equals(stack.peek().getType())) {
                match("6");
                AstNode = new NodeBinaryOp(AstNode, token, parseRelational());
            }
        }
        return AstNode;
    }

    //    relational = add_sub ("<" add_sub | "<=" add_sub | ">" add_sub | ">=" add_sub)*
    private AstNode parseRelational() throws Exception {
        AstNode AstNode = parseAddSub();
        while (">".equals(stack.peek().getValue()) || "<".equals(stack.peek().getValue()) ) {
            Token token = stack.peek();
            if (">".equals(stack.peek().getType())) {
                match("30");
                AstNode = new NodeBinaryOp(AstNode, token, parseAddSub());
            }
            if ("<".equals(stack.peek().getType())) {
                match("32");
                AstNode = new NodeBinaryOp(AstNode, token, parseAddSub());
            }
        }
        return AstNode;
    }

    //    add_sub = mul_div ("+" mul_div | "-" mul_div)*
    private AstNode parseAddSub() throws Exception {
        AstNode AstNode = parseMulDiv();
        while ("22".equals(stack.peek().getType()) || "23".equals(stack.peek().getType())) {
            Token token = stack.peek();
            if ("22".equals(stack.peek().getType())) {
                match("22");
                AstNode = new NodeBinaryOp(AstNode, token, parseMulDiv());
            }
            if ("23".equals(stack.peek().getType())) {
                match("23");
                AstNode = new NodeBinaryOp(AstNode, token, parseMulDiv());
            }
        }
        return AstNode;
    }

    //    mul_div = unary ("*" unary | "/" unary)*
    private AstNode parseMulDiv() throws Exception {
        AstNode AstNode = parseUnary();
        while ("20".equals(stack.peek().getType()) || "21".equals(stack.peek().getType())) {
            Token token = stack.peek();
            if ("20".equals(stack.peek().getType())) {
                match("20");
                AstNode = new NodeBinaryOp(AstNode, token, parseUnary());
            }
            if ("21".equals(stack.peek().getType())) {
                match("21");
                AstNode = new NodeBinaryOp(AstNode, token, parseUnary());
            }
        }
        return AstNode;
    }

    //      unary = ("+" | "-" | "!") unary | primary
    private AstNode parseUnary() throws Exception {
        Token token = stack.peek();
        if ("22".equals(stack.peek().getType())) {
            match("22");
            return new NodeUnaryOp(token, parseUnary());
        } else if ("23".equals(stack.peek().getType())) {
            match("23");
            return new NodeUnaryOp(token, parseUnary());
        } else {
            return parsePrimary();
        }

    }

    //  primary = "(" expression ")" | identifier args?| num | identifier "[" expression "]"
    //args = "(" (expression ("," expression)*)? ")"
    private AstNode parsePrimary() throws Exception {
        Token token = stack.peek();
        if ("26".equals(stack.peek().getType())) {
            match("26");
            AstNode AstNode = parseExpr();
            match("27");
            return AstNode;
        } else if ("11".equals(stack.peek().getType())) {
            token = stack.peek();
            match("11");
            if ("26".equals(stack.peek().getType())) {
                String returnFunctionName = token.getValue();
                ArrayList<AstNode> actualParaAstNodes = new ArrayList<>();
                match("26");
                if (!"27".equals(stack.peek().getType())) {
                    AstNode AstNode = parseExpr();
                    actualParaAstNodes.add(AstNode);
                    while ("-1".equals(stack.peek().getType())) {
                        match("-1");
                        AstNode = parseExpr();
                        actualParaAstNodes.add(AstNode);
                    }
                }
                match("27");
                AstNode returnAstNode = new NodeProgram(token, returnFunctionName, actualParaAstNodes);
                return returnAstNode;
            }
            return new NodeVar(token);

        } else if ("10".equals(stack.peek().getType())) {
            match("10");
            return new NodeNum(token);
        } else {
            throw new Exception();
        }

    }



}
