import java.io.*;

public class HackAssembler {
  // Translate Hack assembly programs into executable Hack binary code.
  
  // Usage: > java HackAssembler programName.asm

  // Input: text file "programName.asm"
  // Output: text file "programName.hack"
  // Assume programName.asm is free of errors.

  /* Overall Logic:
      1) Initialization. Create Parser, Code, SymbolTable.
      2) First pass. Read all commands, only paying attention
            to labels and updating the SymbolTable.
      3) Restart reading and translating commands.
      4) Main loop:
            - Get next assembly language command and parse it.
            - If A command: Translate symbols to binary addresses.
            - If C command: Get code for each part & put them together.
            - Output the resulting machine language command.
  */

  public static void main(String[] args) {
    System.out.println("Here we go! " + args[0]);

    // PART 1: INITIALIZATION
    Parser parser = new Parser(args[0]);
    Code code = new Code();
    SymbolTable st = new SymbolTable();

    // Open .hack file to write to
    // Get the name of the program (i.e. without .asm extension)
    String programName = args[0].substring(0, args[0].length()-4);
    String hackFile = programName + ".hack";
    FileWriter writer = null;
    try {
      writer = new FileWriter(hackFile);
    } catch(IOException ex) {
      ex.printStackTrace();
    }

    // Main loop:
    while (parser.advance()) { // Get and parse the next assembly language command.
      // If the command is a label, we don't need to write anything to the file.
      if (parser.getCmdType() == "label") continue;
      // If the command is an A or C instruction, we'll need to write to .hack file.
      else {
        String binaryCmd = ""; // The binary string we'll write to .hack file.

        if (parser.getCmdType() == "a") {
          // If A command, translate symbols to binary addresses.
          String addr = parser.addr();
          String binaryString = Integer.toBinaryString(Integer.parseInt(addr));
          binaryCmd = "0000000000000000".substring(binaryString.length()) + binaryString;
        }

        // If C command, get code for each part and put them together.
        else if (parser.getCmdType() == "c") {

          // Use Code object to translate from parser symbols to binary code
          System.out.println(parser.comp());
          String c = code.comp(parser.comp());
          String d = code.dest(parser.dest());
          String j = code.jump(parser.jump());
          binaryCmd = "111" + c + d + j;
        }

        // Output the resulting machine language command to file.hack.
        try {
          writer.write(binaryCmd + "\n");
        } catch(IOException ex) {
          ex.printStackTrace();
        }
      } // end else (write to .hack file)
    } // end main loop

    try {
      writer.close();
    } catch(IOException ex) {
      ex.printStackTrace();
    }

  } // end public static void main
}