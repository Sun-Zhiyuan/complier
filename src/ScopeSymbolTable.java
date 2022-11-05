import java.util.HashMap;

public class ScopeSymbolTable {
    public String scopeName;
    public HashMap<String, Symbol> symbols;
    public int scopeLevel;
    ScopeSymbolTable enClosingScope = null;

    public ScopeSymbolTable(String scopeName, int scopeLevel, ScopeSymbolTable enClosingScope) {
        symbols = new HashMap<>();
        this.scopeName = scopeName;
        this.scopeLevel = scopeLevel;
        this.enClosingScope = enClosingScope;
    }

    public void insert(Symbol symbol) {
        this.symbols.put(symbol.name, symbol);
    }

    public Symbol lookup(String name, boolean currentScopeOnly) {
        Symbol symbol = this.symbols.get(name);
        if (symbol != null) {
            return symbol;
        }
        if (currentScopeOnly) {
            return null;
        }
        if (this.enClosingScope != null) {
            return enClosingScope.lookup(name, false);
        }
        System.out.println("Error in Scope");
        return null;
    }
}
