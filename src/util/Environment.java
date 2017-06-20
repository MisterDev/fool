package util;

import ast.SymbolTableEntry;
import ast.type.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

public class Environment {

    public int offset = 0;
    private ArrayList<HashMap<String, SymbolTableEntry>> symbolTable = new ArrayList<>();

    public Environment() {

    }

    public int getNestingLevel() {
        return this.symbolTable.size() - 1;
    }

    public int getCurrentOffset() {
        return this.offset;
    }

    public Environment pushHashMap() {
        this.symbolTable.add(new HashMap<>());
        return this;
    }

    public Environment popHashMap() {
        this.symbolTable.remove(this.symbolTable.size() - 1);
        return this;
    }

    public Environment addEntry(String id, SymbolTableEntry entry) throws RedeclaredVarException {
        SymbolTableEntry oldEntry = this.symbolTable
                .get(this.symbolTable.size() - 1)
                .put(id, entry);
        if (oldEntry != null) {
            throw new RedeclaredVarException(id);
        }
        return this;
    }

    public Type getTypeOf(String id) throws UndeclaredVarException {
        ListIterator<HashMap<String, SymbolTableEntry>> li = symbolTable.listIterator(symbolTable.size());
        while (li.hasPrevious()) {
            HashMap<String, SymbolTableEntry> current = li.previous();
            if (current.containsKey(id)) {
                return current.get(id).getType();
            }
        }
        throw new UndeclaredVarException(id);
    }

}
