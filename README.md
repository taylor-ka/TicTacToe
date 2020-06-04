# TicTacToe
This project was created to be finished in the final 3 sections of CSE 143 in Spring 2020.

Create an unbeatable (?!?) tic-tac-toe bot using Java! We’ll learn about basic AI by implementing the minimax algorithm (also used to create chess bots!) and build a playable GUI!

Students can definitely write all the code for this project, but for the sake of time, it will probably be best to provide them with the Board class and the simple CLI just so they can get started quickly and focus on minimax / the GUI.

## Goals
- Give students a taste of AI! Build a fun (is tic tac toe fun?) game :))
- Demonstrate that 143 has really given them all the fundamental programming concept they need to build something pretty cool!
- Build something fun to show to friends and family! Even though Swing is ugly, this means that students don't need to install anything new, which means that set up for this project is much less painful / nonexistent. 

## General lesson plan
1. Get familiar with Board class and reading Javadoc. Talk about Model and View/Controller design pattern. Introduction to Swing, start building GUI.
2.  Reminder of canWin method from lecture, introduce minimax algorithm -- recursive backtracking! Show that 143 concepts apply to cool algorithms! Start implementing TicTacToeBot interface.
3. Finish implementing TicTacToe bot. With extra time (if this exists), can talk about building out new features for the GUI and how to Google things effectively, read documentation, etc. Depending on time / interest, can wrap up by discussing multithreading, parallel alpha-beta for a chess bot and other optimizations like move ordering, transposition tables, etc.

*See [slide deck](https://docs.google.com/presentation/d/18C2Egrt0i2aTPiqADXtMK_M_LcdVirvquN7D1z2vWUk/edit?usp=sharing) for more detailed lesson plans.*


## Starter code
Board.java
- Board class with Move abstraction
- Depending on time, students can think about how they would design a board class / write this as well.

TicTacToeBot.java
- Interface for the bot that students will implement.

TicTacToeCLI.java
- Bare bones CLI that uses Board and implementation of TicTacToeBot. This is mostly useful for testing purposes and isn't particularly enjoyable to play. There's also no error checking whatsoever so don't mess up or the game will crash :))

## Documentation
- The documentation folder contains generated Javadocs for the Board class and the TicTacToeBot interface
- Can be a cool way to show students why the comments they’ve been writing (and probably hating) are useful and important!

## Solution code
MinimaxBotExample.java
- Solution code for Minimax algorithm to solve TicTacToe.
- Implements the TicTacToeBot interface and uses the Board class.

TicTacToeGUI.java
- Example of a possible GUI for TicTacToe
- Allows player to choose what mode they want to play in, but not much functionality other than the basic game mechanics.
- Main UI components: JFrame, JButton, JOptionPane

TicTacToeMain.java
- Runs TicTacToe GUI
