// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

(LOOP)
  // Initialize screen word
  @SCREEN
  D=A
  @addr
  M=D // addr = SCREEN

  // Get input from keyboard
  @KBD
  D=M
  @FILL
  D; JNE

(CLEAR)
  @KBD
  D=A
  @addr
  D=D-M // D = KBD - addr
  @STOP
  D; JEQ // if addr == KBD (addr is past screen map), goto STOP

  @addr
  A=M // set A to address stored in addr
  M=0 // RAM[addr] = 0

  @addr
  M=M+1 // addr = addr + 1
  @CLEAR
  0; JMP

(FILL)
  @KBD
  D=A
  @addr
  D=D-M // D = KBD - addr
  @STOP
  D; JEQ // if addr == KBD (addr is past screen map), goto STOP

  @addr
  A=M // set A to address stored in addr
  M=-1 // RAM[addr] = -1

  @addr
  M=M+1 // addr = addr + 1
  @FILL
  0; JMP

(STOP)
  @LOOP
  0; JMP