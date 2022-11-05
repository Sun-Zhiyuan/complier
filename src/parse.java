import java.util.List;
import java.util.Stack;

public class parse {
    List<Token> tokenList;

    Stack<Token> stack = new Stack<>();


    public parse() {
    }

    public parse(List<Token> tokenList) throws Exception {
        this.tokenList = tokenList;
        stack.push(new Token("$", "$"));
        for (int i = tokenList.size() - 1; i >= 0; i--) {
            stack.push(tokenList.get(i));
        }
        program();
    }

    public void match(String tokenType) throws Exception {
        if (stack.peek().getType().equals(tokenType)) {
            System.out.println(stack.pop());
        } else {
            throw new Exception();
        }
    }

    private void program() throws Exception {
        while (!stack.peek().getType().equals("$")) {
            functionDefinition();
        }
        if (stack.peek().getType().equals("$")) {
//            match("$");
            System.out.println("accept!!!");
        } else {

            throw new Exception();

        }
    }

    private void functionDefinition() throws Exception {
        typeSpecification();
        match("11");
        match("26");
        if (!stack.peek().getValue().equals(")")) {
            formalParameters();
        }
        match("27");
        parseBlock();
    }

    private void parseBlock() throws Exception {
        match("34");
        compoundStatement();
        match("35");
    }

    private void compoundStatement() throws Exception {
        while (!stack.peek().getValue().equals("}")) {

            if (stack.peek().getValue().equals("int") || stack.peek().getValue().equals("bool")) {
                variableDeclaration();
            } else {
                parseStatement();
            }
        }
    }


    private void parseStatement() throws Exception {
        if (stack.peek().getType().equals("8")) {
            match("8");
            expressionStatement();
        } else if (stack.peek().getType().equals("34")) {
            match("34");
            compoundStatement();
            match("35");
        } else if (stack.peek().getType().equals("2")) {
            match("2");
            match("26");
            expression();
            parseStatement();
            if (stack.peek().getType().equals("4")) {
                match("4");
                parseStatement();
            }
        } else if (stack.peek().getType().equals("3")) {
            match("3");
            match("26");
            expression();
            match("27");
            parseStatement();
        } else {
            expressionStatement();
        }
    }

    private void expressionStatement() throws Exception {
        if (stack.peek().getType().equals("44")) {
            match("44");
        } else {
            expression();
            match("44");
        }
    }

    private void variableDeclaration() throws Exception {
        typeSpecification();
        if (stack.peek().getType().equals("11")) {
            match("11");
            if (stack.peek().getType().equals("24")) {
                match("24");
                expression();
            }
            while (stack.peek().getType().equals("-1")) {
                match("-1");
                match("11");
                if (stack.peek().getType().equals("24")) {
                    match("24");
                    expression();
                }
            }
        }
        match("44");
    }

    private void expression() throws Exception{
        assign();
    }

    private void assign() throws Exception{
        equality();
        if(stack.peek().getType().equals("24")){
            match("24");
            assign();
        }
    }

    private void equality() throws Exception{
        relational();
        while(stack.peek().getType().equals("5")){
            match("5");
            relational();
        }
        while(stack.peek().getType().equals("6")){
            match("6");
            relational();
        }
    }

    private void relational() throws Exception{
        addSub();
        while(stack.peek().getType().equals("32")){
            match("32");
            addSub();
        }
        while(stack.peek().getType().equals("34")){
            match("34");
            addSub();
        }
    }

    private void addSub() throws Exception{
        mulDiv();
        while(stack.peek().getType().equals("22")){
            match("22");
            mulDiv();
        }
        while(stack.peek().getType().equals("23")){
            match("23");
            mulDiv();
        }
    }

    private void mulDiv() throws Exception{
        unary();
        while(stack.peek().getType().equals("20")) {
            match("20");
            unary();
        }
        while(stack.peek().getType().equals("21")) {
            match("21");
            unary();
        }
    }

    private void unary() throws Exception{
        if(stack.peek().getType().equals("22")){
            match("22");
            primary();
        }
        else if(stack.peek().getType().equals("23")){
            match("23");
            primary();
        }
        else {
            primary();
        }
    }

    private void primary() throws Exception{
        if(stack.peek().getType().equals("26")) {
            match("26");
            expression();
            match("27");
        }
        else if(stack.peek().getType().equals("11")){
            match("11");
            if(stack.peek().getType().equals("26")){
                args();
            }
        }
        else{
            match("10");
        }

    }

    private void args() throws Exception{
        match("26");
        if(!stack.peek().getType().equals("27")){
            assign();
            while(stack.peek().getType().equals("-1")){
                match("-1");
                assign();
            }
        }
        match("27");
    }

    private void formalParameters() throws Exception {
        formalParameter();
        while (stack.peek().getValue().equals(",")) {
            match("-1");
            formalParameter();
        }
    }

    private void formalParameter() throws Exception {
        typeSpecification();
        match("11");
    }

    private void typeSpecification() throws Exception {
        if (stack.peek().getValue().equals("int")) {
            match("1");
        } else if (stack.peek().getValue().equals("bool")) {
            match("9");
        } else {
            throw new Exception();
        }
    }


}
