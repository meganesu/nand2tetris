import java.util.HashMap;

public class SymbolTable {
  // Manage the symbol table

  HashMap<String, Integer> st;
  int nextVarAddr;

  SymbolTable() {
    st = new HashMap<String, Integer>();

    st.put("R0", 0);
    st.put("R1", 1);
    st.put("R2", 2);
    st.put("R3", 3);
    st.put("R4", 4);
    st.put("R5", 5);
    st.put("R6", 6);
    st.put("R7", 7);
    st.put("R8", 8);
    st.put("R9", 9);
    st.put("R10", 10);
    st.put("R11", 11);
    st.put("R12", 12);
    st.put("R13", 13);
    st.put("R14", 14);
    st.put("R15", 15);
    st.put("SCREEN", 16384);
    st.put("KBD", 24576);
    st.put("SP", 0);
    st.put("LCL", 1);
    st.put("ARG", 2);
    st.put("THIS", 3);
    st.put("THAT", 4);

    nextVarAddr = 16;
  }

  // Returns true if symbol is in st. Otherwise, returns false.
  public boolean contains(String symbol) {
    return st.containsKey(symbol);
  }

  // Returns the value mapped to symbol. If symbol is not in st, returns -1.
  public int get(String symbol) {
    if (st.containsKey(symbol)) {
      return st.get(symbol);
    }
    else return -1;
  }

  // Add a label to the symbol table.
  public void addLabel(String symbol, int addr) {
    st.put(symbol, addr);
  }

  // Add a variable to the symbol table.
  public void addVar(String symbol) {
    // If a variable isn't in the symbol table yet, add it.
    if (!st.containsKey(symbol)) {
      st.put(symbol, nextVarAddr);
      nextVarAddr++;
    }
  }
}