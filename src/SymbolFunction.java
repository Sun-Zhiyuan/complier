import java.util.ArrayList;

public class SymbolFunction extends Symbol{
    public AstNode blockNode;
    public ArrayList<SymbolVar> formalParameters;
    public SymbolFunction(String name, String type) {
        super(name, type);
    }

    public SymbolFunction(String name, ArrayList<SymbolVar> formalParameters) {
        super(name, null);
        if(formalParameters != null){
            this.formalParameters = formalParameters;
        }
        this.blockNode = null;
    }
}
