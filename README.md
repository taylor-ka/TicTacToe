# TicTacToe
Create an unbeatable (?!?) tic-tac-toe bot using Java! We’ll learn about basic AI by  implementing the minimax algorithm (also used to create chess bots!) and build a playable GUI!

## Starter code
Board.java
- Board class with Move abstraction. Depending on time, students can think about how they would design a board class / write this as well.

TicTacToeBot.java
- Interface for the bot that students will implement.

TicTacToeCLI.java
- Bare bones CLI that uses Board and implementation of TicTacToeBot. This is mostly useful for testing purposes and isn't particularly enjoyable to play. There's also no error checking whatsoever so don't mess up or the game will crash :))

## Solution code
MinimaxBot.java
- Solution code for Minimax algorithm to solve TicTacToe. Implements the TicTacToeBot interface and uses the Board class.

TicTacToeGUI.java
- Example of a possible GUI for TicTacToe. Allows player to choose what mode they want to play in, but not much functionality other than the basic game mechanics.
- Main UI components: JFrame, JButton, JOptionPane

TicTacToeMain.java
- Runs TicTacToe GUI
