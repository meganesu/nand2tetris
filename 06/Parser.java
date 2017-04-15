import java.io.*;

public class Parser {
  // Unpack each instruction into its underlying fields
  File asmFile;
  FileReader fileReader;
  BufferedReader reader;
  String currentCmd;

  String cmdType;
  String dest; // Store the dest part of the C command
  String comp; // Store the comp part of the C command
  String jump; // Store the jump part of the C command
  String label; // Store the symbol part of the label "command"
  String addr; // Store the address part of the A command

  Parser(String filename) throws FileNotFoundException {
    currentCmd = "";
    cmdType = "";
    dest = "";
    comp = "";
    jump = "";
    label = "";
    addr = "";

    try {
      asmFile = new File(filename);
      fileReader = new FileReader(asmFile);

      reader = new BufferedReader(fileReader);

      currentCmd = null;
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new FileNotFoundException("File " + filename + " could not be found.");
    }
  }

  public String getCmdType() {
    return cmdType;
  }

  public String dest() {
    return dest;
  }

  public String comp() {
    return comp;
  }

  public String jump() {
    return jump;
  }

  public String label() {
    return label;
  }

  public String addr() {
    return addr;
  }

  public boolean hasMoreCommands() {
    try {
      currentCmd = reader.readLine();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    if (currentCmd != null) {
      return true;
    }
    else {
      // End of file has been reached. Close file and return.
      try {
        reader.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      return false;
    }
  }

  // Get the next command, and parse it.
  public boolean advance() {
    // If you've reached the end of the file, return false.
    if (!hasMoreCommands()) return false;

    // Otherwise, there are still commands to parse. So check out the current line!

    // Remove all whitespace from the command.
    currentCmd = currentCmd.replaceAll("\\s", "");
    // If the cmd is now empty (i.e. it was only whitespace), advance again.
    if (currentCmd.length() == 0) return advance();

    // Remove in-line comments.
    int commentStart = currentCmd.indexOf("//");
    if (commentStart != -1) {
      currentCmd = currentCmd.substring(0,commentStart);
    }
    // If the cmd is now empty (i.e. it was only a comment), advance again.
    if (currentCmd.length() == 0) return advance();

    // If you get here, there is some kind of command to be parsed!

    // Determine the type of command.
    if (currentCmd.charAt(0) == '(') {
      // Parse label
      cmdType = "label";
      parseLabel();
      return true;
    }
    else if (currentCmd.charAt(0) == '@') {
      // Parse A command
      cmdType = "a";
      parseA();
      return true;
    }
    else {
      // Parse C command
      cmdType = "c";
      parseC();
      return true;
    }
  }

  private void parseLabel(){
    // Format: (LOOP)
    dest = "";
    comp = "";
    jump = "";
    addr = "";

    label = currentCmd.replace("(", "").replace(")", "");
  }

  private void parseA() {
    // Format: @addr
    dest = "";
    comp = "";
    jump = "";
    label = "";

    addr = currentCmd.replace("@", "");
  }

  private void parseC() {
    // Format: [dest=]comp[;jump]
    label = "";
    addr = "";
    dest = "";
    jump = "";
    comp = currentCmd;

    // Find '=' for dest
    int idxEquals = comp.indexOf("=");
    if (idxEquals != -1) {
      dest = comp.substring(0, idxEquals);
      // Remove dest part (including '=') from comp.
      comp = comp.substring(idxEquals+1);
    }

    // Find ';' for jump
    int idxSemicolon = comp.indexOf(";");
    if (idxSemicolon != -1) {
      jump = comp.substring(idxSemicolon+1);
      // Remove jump part (including ';') from comp.
      comp = comp.substring(0, idxSemicolon);
    }
    
  }
}