public class SymbolVar extends Symbol{
    public Symbol symbol;

    public SymbolVar(String name, String type){
        super(name, type);
    }
    public SymbolVar(String name, String type, int varOffset) {
        super(name, type);
        this.symbol = null;
        this.offset = varOffset;
    }
}
