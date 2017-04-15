// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/06/rect/Rect.asm

// Draws a rectangle at the top-left corner of the screen.
// The rectangle is 16 pixels wide and R0 pixels high.

// A: 0 value
// C: 1 1 1 a c1 c2 c3 c4 c5 c6 d1 d2 d3 j1 j2 j3
//    dest = cmp; jmp

// SYMBOL TABLE:
// INFINITE_LOOP -> 23
// LOOP          -> 10
// counter       -> 16
// SCREEN        -> 16384
// address       -> 17

0   @0
1   D=M
2   @INFINITE_LOOP // @23
3   D;JLE 
4   @counter // @16
5   M=D
6   @SCREEN // @16384
7   D=A
8   @address // @17
9   M=D
(LOOP)
10   @address // @17
11   A=M
12   M=-1
13   @address // @17
14   D=M
15   @32
16   D=D+A
17   @address // @17
18   M=D
19   @counter // @16
20   MD=M-1
21   @LOOP // @10
22   D;JGT
(INFINITE_LOOP)
23   @INFINITE_LOOP // @23
24   0;JMP
