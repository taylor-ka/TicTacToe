import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.JOptionPane.YES_OPTION;

/**
 * A simple TicTacToe GUI that allows the user to play in either Player vs. Player or Player vs.
 * Bot mode.
 */
public class TicTacToeGUI extends JFrame implements ActionListener {
    private Container pane;
    private JButton[][] buttons;

    private Board board;
    private TicTacToeBot bot;

    private static final int DIM = 3;
    private static final String BUTTON_ROW = "row";
    private static final String BUTTON_COL = "col";

    // Convention: player 1 is X, player 2 is O
    private static final int PLAYER_X = 1;
    private static final int PLAYER_O = 2;

    // When playing in bot mode, human player is X, bot player is O
    private boolean botMode;

    // When true, prints out board state after each move
    private static final boolean DEBUG_FLAG = true;

    /**
     * Creates a new tic tac toe playing board
     */
    public TicTacToeGUI() {
        // call JFrame default constructor
        super();

        // Create content pane and board buttons
        initializePane();
        initializeButtons();

        // Create board model and bot
        board = new Board(DIM);
        bot = new MinimaxBot();

        // Start game
        setVisible(true);
        chooseGameMode();

        debugPrint();
    }

    /**
     * Initializes ContentPane of GUI
     */
    private void initializePane() {
        pane = getContentPane();
        pane.setLayout(new GridLayout(DIM,DIM));
        setTitle("Tic Tac Toe");
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Creates buttons for board and stores row and column to be used in the future
     */
    private void initializeButtons() {
        buttons = new JButton[DIM][DIM];
        Font font = new Font("Arial", Font.PLAIN, 150);
        for (int r = 0; r < DIM; r++) {
            for (int c = 0; c < DIM; c++) {
                // Create button
                buttons[r][c] = new JButton();
                JButton currButton = buttons[r][c];     // for easier reference
                currButton.addActionListener(this);
                currButton.setText("");
                currButton.setFont(font);

                // Save row and column for future use
                currButton.putClientProperty(BUTTON_ROW, r);
                currButton.putClientProperty(BUTTON_COL, c);
                add(currButton);  // add button to the frame
            }
        }
    }

    /**
     * Displays dialog to have player choose a game mode. If the player exits the dialog box,
     * defaults to Player vs. Bot mode
     */
    private void chooseGameMode() {
        Object[] options = {"Player vs. Player", "Player vs. Bot"};
        int response = JOptionPane.showOptionDialog(this,
                "Choose your game mode:",
                "It's Tic Tac Toe time!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button selected
        botMode = response != 0;
    }

    /**
     * Resets the state of the game. Resets buttons so they are all blank and active, resets
     * board model, and asks player to choose a game mode again
     */
    private void resetGame() {
        for (int r = 0; r < DIM; r++) {
            for (int c = 0; c < DIM; c++) {
                JButton currButton = buttons[r][c];

                // Reset action listener for button
                currButton.removeActionListener(this);
                currButton.addActionListener(this);
                currButton.setText("");
            }
        }
        board.reset();
        chooseGameMode();
    }

    /**
     * Implements ActionListener interface to update the board to reflect a move when a button is
     * pressed
     * @param e button click event
     */
    public void actionPerformed(ActionEvent e) {
        // Update board to reflect user's move
        JButton buttonClicked = (JButton)e.getSource();
        boolean gameOver = updateMoveMade(buttonClicked);

        // If the player didn't win, and we're in bot mode, then ask bot for next move
        if (!gameOver && botMode) {
            Board.Move botMove = bot.getNextMove(board);
            updateMoveMade(buttons[botMove.row][botMove.col]);
        }
    }

    /**
     * Updates game state to reflect that a move was made. Updates GUI to show move, updates
     * board model to reflect move as well.
     * @param button to update with made move
     * @return true if the game is now over, false if the game continues
     */
    private boolean updateMoveMade(JButton button) {
        // Disable button so that re-clicking it does nothing
        button.removeActionListener(this);

        if (board.getCurrPlayer() == PLAYER_X) {
            button.setText("X");
            button.setForeground(Color.RED);
        } else {
            button.setText("O");
            button.setForeground(Color.BLUE);
        }
        int row = (int)button.getClientProperty(BUTTON_ROW);
        int col = (int)button.getClientProperty(BUTTON_COL);
        board.makeMove(new Board.Move(row, col));

        debugPrint(); // TODO: remove. For debugging

        boolean gameOver = board.gameOver();
        if (gameOver) {
            showGameOver();
        }
        return gameOver;
    }

    /**
     * Shows game over dialog, and asks player if they want to play again. If the player exits
     * the dialog, the game closes.
     */
    private void showGameOver() {
        // Determine who won the game and pick message to show
        String winnerMessage = getGameOverMessage();

        // Show game over dialog box
        Object[] options = {"Play Again", "Exit"};
        int response = JOptionPane.showOptionDialog(this,
                    winnerMessage,
                    "Game Over!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,     // TODO: custom icon
                    options,  //the titles of buttons
                    options[0]); //default button selected

        // Process user's choice
        if (response == YES_OPTION) {
            resetGame();
            debugPrint();  // TODO: remove. For debugging
        } else {
            // Exit the game
            this.dispose();
        }
    }

    /**
     * Returns the appropriate game over message to be displayed to the user
     * @return game over message, indicating how the game ended
     */
    private String getGameOverMessage() {
        // Determine who won the game and pick message to show
        int winner = board.getWinner();
        String winnerMessage;

        if (winner == Board.TIE) {
            winnerMessage = "The game was a tie!";
        } else if (botMode){
            if (winner == Board.P1) { // TODO: is bot always second?
                winnerMessage = "Uh oh... You beat the bot?!?! Time to go hunt for bugs!";
            } else {
                winnerMessage = "You lost to the bot! Mwahahhaa >:)";
            }
        } else { // A normal player one
            String playerWinner = winner == PLAYER_X ? "X" : "O";
            winnerMessage = "Player " + playerWinner + " is the winner!";
        }
        return winnerMessage;
    }

    /**
     * When DEBUG_FLAG on, prints out the state of the board after every move.
     */
    private void debugPrint() {
        if (DEBUG_FLAG) {
            System.out.println(board);
            System.out.println();
        }
    }

}
