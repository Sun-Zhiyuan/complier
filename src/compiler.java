import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class compiler {
    public static void main(String[] args){
        try {
            Analyse analyse = new Analyse();
            analyse.check();
            List<Token> tokenList = analyse.getTokenList();
//            for (int i = 0; i < tokenList.size(); i++) {
//                System.out.println(tokenList.get(i));
//            }
            Parser parser = new Parser(tokenList);
            ArrayList<AstNode> AST = new ArrayList<>();

            AST = parser.parseProgram();
            Semantic semantic = new Semantic();
            ArrayList<AstNode> ASTA = new ArrayList<>();

            ASTA = semantic.Analyze(AST);

            CodeGenerator codeGenerator = new CodeGenerator();
            codeGenerator.codeGenerate(ASTA);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
