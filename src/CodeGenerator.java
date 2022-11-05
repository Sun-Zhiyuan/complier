import java.util.ArrayList;

public class CodeGenerator extends NodeVisitor {
    public int sum = -999;
    public int count = 0;
    public String[] parameterRegisters = {"rdi", "rsi", "rdx", "rcx", "r8", "r9"};

    public int alignTo(int n, int align) {
        return (int)((n + align - 1) / align) * align;
    }

    @Override
    public AstNode UnaryOpNodeVisit(NodeUnaryOp node) {

        if (node.op.getType().equals("-")) {
            node.right.accept(this);
            System.out.println("    reg %rax");
        } else if (node.op.getType().equals("!")) {
            node.right.accept(this);
            System.out.println("    not %rax");
        }
        return null;
    }

    @Override
    public AstNode ReturnNodeVisit(NodeReturn node) {
        node.right.accept(this);
        if (node.token.getType().equals("8")) {
            System.out.println("    jmp ." + node.functionName + ".return");
        }
        return null;
    }

    @Override
    public AstNode BinaryOpNodeVisit(NodeBinaryOp node) {
        node.right.accept(this);
        System.out.println("    push %rax");
        node.left.accept(this);
        System.out.println("    pop %rdi");
        //根据不同的操作符进行相应的操作
        if (node.op.getType().equals("22")) {
            System.out.println("    add %rdi, %rax");
        } else if (node.op.getType().equals("-")) {
            System.out.println("    sub %rdi, %rax");
        } else if (node.op.getType().equals("*")) {
            System.out.println("    imul %rdi, %rax");
        } else if (node.op.getType().equals("/")) {
            System.out.println("    cqo");
            System.out.println("    idiv %rdi");
        } else if (node.op.getType().equals("==")) {
            System.out.println("    cmp %rdi, %rax");
            System.out.println("    sete %al");
            System.out.println("    movzb %al, %rax");
        } else if (node.op.getType().equals("!=")) {
            System.out.println("    cmp %rdi, %rax");
            System.out.println("    setne %al");
            System.out.println("    movzb %al, %rax");
        } else if (node.op.getType().equals("<")) {
            System.out.println("    cmp %rdi, %rax");
            System.out.println("    setl %al");
            System.out.println("    movzb %al, %rax");
        } else if (node.op.getType().equals(">")) {
            System.out.println("    cmp %rdi, %rax");
            System.out.println("    setg %al");
            System.out.println("    movzb %al, %rax");
        } else if (node.op.getType().equals("<=")) {
            System.out.println("    cmp %rdi, %rax");
            System.out.println("    setle %al");
            System.out.println("    movzb %al, %rax");
        } else if (node.op.getType().equals(">=")) {
            System.out.println("    cmp %rdi, %rax");
            System.out.println("    setge %al");
            System.out.println("    movzb %al, %rax");
        } else if (node.op.getType().equals("&&")) {
            System.out.println("    and %rdi, %rax");
        } else if (node.op.getType().equals("||")) {
            System.out.println("    or %rdi, %rax");
        }
        return null;
    }


    public void generateArrayItemAddress(NodeVarArray node) {
        int arrayOffset = node.symbol.offset;
        int arrayItemOffset;
        if (node.index.token.getType().equals("int")) {
            arrayItemOffset = (Integer.parseInt(node.index.value) - 1) * 8;
            System.out.println("    mov $" + arrayItemOffset + ", %rax");
            System.out.println("    push %rax");
            System.out.println("    lea " + arrayOffset + "(%rbp), %rax");
            System.out.println("    pop %rdi");
            System.out.println("    add %rdi, %rax");
        } else {
            node.index.accept(this);
            System.out.println("    sub $1, %rax");
            System.out.println("    imul $8, %rax");
            System.out.println("    push %rax");
            System.out.println("    lea " + arrayOffset + "(%rbp), %rax");
            System.out.println("    pop %rdi");
            System.out.println("    add %rdi, %rax");
        }

    }

    @Override
    public AstNode AssignNodeVisit(NodeAssign node) {
        if (node.left.token == null) {
            System.out.println("not an lvalue");
            return null;
        }
        if ((node.left).token.getType().equals("id")) {
            if (node.left.symbol == null) {
                System.out.println("not an lvalue");
                return null;
            }
            int varOffset = node.left.symbol.offset;
            System.out.println("    lea " + varOffset + "(%rbp), %rax");
            System.out.println("    push %rax");
            if (node.left.getClass().equals("class Var_Node")) {
                if (((NodeVar) node.left).array != null) {
                    //TODO
                    //unknown
                    generateArrayItemAddress((NodeVarArray) node.left);

                    System.out.println("    push %rax");
                }
            }
            node.right.accept(this);
            System.out.println("    pop %rdi");
            System.out.println("    mov %rax, (%rdi)");
        } else {
            System.out.println("not an lvalue");
        }
        return null;
    }

    @Override
    public AstNode IfNodeVisit(NodeIf node) {
        count++;
        int localLabel = count;
        node.condition.accept(this);
        System.out.println("    cmp $0, %rax");
        System.out.println("    je  .L.else." + localLabel);
        if (node.thenStatement != null) {
            node.thenStatement.accept(this);
        }
        System.out.println("    jmp  .L.end." + localLabel);
        System.out.println(".L.else." + localLabel + ":");
        if (node.elseStatement != null) {
            node.elseStatement.accept(this);
        }
        System.out.println(".L.end." + localLabel + ":");
        return null;
    }

    @Override
    public AstNode WhileNodeVisit(NodeWhile node) {
        count++;
        int localLabel = count;
        System.out.println(".L.condition." + localLabel + ":");
        node.condition.accept(this);
        System.out.println("    cmp $0, %rax");
        System.out.println("    je  .L.end." + localLabel);
        if (node.statement != null) {
            node.statement.accept(this);
        }
        System.out.println("    jmp .L.condition." + localLabel);
        System.out.println(".L.end." + localLabel + ":");
        return null;
    }

    @Override
    public AstNode BlockNodeVisit(NodeBlock node) {
        for (int i = 0; i < node.statementNodes.size(); i++) {
            node.statementNodes.get(i).accept(this);
        }
        return null;
    }

    @Override
    public AstNode NumNodeVisit(NodeNum node) {
        if (node.value.equals("true")) {
            System.out.println("    mov $1, %rax");
        } else if (node.value.equals("false")) {
            System.out.println("    mov $0, %rax");
        } else {
            System.out.println("    mov $" + node.value + ", %rax");
        }
        return null;
    }

    @Override
    public AstNode VarNodeVisit(NodeVar node) {
        int var_offset = node.symbol.offset;
        System.out.println("    lea " + var_offset + "(%rbp), %rax");
        System.out.println("    mov (%rax), %rax");
        return null;
    }

    @Override
    public AstNode VarDeclNodeVisit(NodeVarDecl node) {
        if (node.varNode.getClass().equals("class VarNode")) {
            if (((NodeVar) (node.varNode)).array != null) {
                int array_offset = node.varNode.symbol.offset;
                int array_size = ((NodeVar) (node.varNode)).array.size();
                int i = 0;
                int array_item_offset;
                while (i < array_size) {
                    array_item_offset = i * 8;
                    System.out.println("    mov $" + array_item_offset + ", %rax");
                    System.out.println("    push %rax");
                    System.out.println("    lea " + array_offset + "(%rbp), %rax");
                    System.out.println("    pop %rdi");
                    System.out.println("    add %rdi, %rax");
                    //获得值
                    int item_value = Integer.parseInt(((NodeVar) (node.varNode)).array.get(i));
                    System.out.println("    mov $" + item_value + ", %rdi");
                    System.out.println("    mov %rdi, (%rax)");
                    i++;
                }
            }
        }
        return null;
    }

    @Override
    public AstNode FormalParamNodeVisit(NodeFormalParam node) {
        return null;
    }

    @Override
    public AstNode FunctionDefNodeVisit(NodeFunctionDef node) {
        this.sum = 0;
        System.out.println("    .text");
        System.out.println("    .global " + node.functionName);
        System.out.println(node.functionName + ":");
        //进行初始化
        System.out.println("    push %rbp");
        System.out.println("    mov %rsp, %rbp");
        int stack_size = this.alignTo(node.offset, 16);
        System.out.println("    sub $" + stack_size + ", %rsp");

        for (int i = 0; i < node.formalParameters.size(); i++) {
            int parameter_offset = ((SymbolParameter) ((NodeFormalParam) (node.formalParameters.get(i))).parameterSymbol).offset;

            //将形参所在的位置传递到参数寄存器
            System.out.println("    mov %" + parameterRegisters[i] + ", " + parameter_offset + "(%rbp)");
        }
        node.blockNode.accept(this);
        System.out.println("." + node.functionName + ".return:");
        //函数调用结束，释放栈空间
        System.out.println("    mov %rbp, %rsp");
        System.out.println("    pop %rbp");
        System.out.println("    ret");
        return null;
    }


    @Override
    public AstNode FunctionCallNodeVisit(NodeProgram node) {
        int parames = 0;
        for (int i = 0; i < node.parametersNode.size(); i++) {
            node.parametersNode.get(i).accept(this);
            System.out.println("    push %rax");
            parames++;
        }
        for (int i = parames; i > 0; i--) {
            System.out.println("    pop %" + parameterRegisters[i - 1]);
        }

        System.out.println("    mov $0, %rax");
        System.out.println("    call " + node.functionName);

        return null;
    }

    @Override
    public AstNode VarArrayItemNodeVisit(NodeVarArray node) {

        generateArrayItemAddress(node);

        System.out.println("    mov (%rax), %rax");
        return null;
    }

    @Override
    public AstNode BasicTypeNodeVisit(NodeType node) {
        return null;
    }

    public void codeGenerate(ArrayList<AstNode> tree) {
        for (AstNode node : tree) {
            if (node != null) {
                node.accept(this);
            }
        }
    }

    

}
