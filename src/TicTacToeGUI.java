import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.YES_OPTION;

// Convention: player 1 is X, player 2 is O
// When playing in bot mode, human player is X, bot player is O
public class TicTacToeGUI extends JFrame implements ActionListener {
    private Container pane;
    private JButton[][] buttons;

    private Board board;
    private MinimaxBot bot;

    private ImageIcon xIcon;
    private ImageIcon oIcon;

    private static final int DIM = 3;
    private static final String BUTTON_ROW = "row";
    private static final String BUTTON_COL = "col";
    private static final int PLAYER_X = 1;
    private static final int PLAYER_O = 2;

    private boolean botMode;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToeGUI();
            }
        });
    }

    public TicTacToeGUI() {
        // call JFrame default constructor
        super();

        // Create content pane
        initializePane();

        // Set up board buttons
        initializeButtons();
        initializeIcons(); // TODO: remove

        // Create board and bot
        board = new Board(DIM);
        bot = new MinimaxBot();

        // Start game
        setVisible(true);
        chooseGameMode();
    }

    private void initializePane() {
        pane = getContentPane();
        pane.setLayout(new GridLayout(DIM,DIM));
        setTitle("Tic Tac Toe");
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

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

    private void initializeIcons() {
        xIcon = createImageIcon("img/x.png");
        oIcon = createImageIcon("img/o.png");
    }

    // Returns an ImageIcon, or null if the path was invalid.
    private ImageIcon createImageIcon(String path) {
        // Get image path
        java.net.URL imgURL = TicTacToeGUI.class.getResource(path);
        if (imgURL == null) {
            System.err.println("Couldn't find file: " + path);
            return null;
        }

        // Grab the height and width of a button (all buttons are the same dimension)
        int width = buttons[0][0].getMaximumSize().width;
        int height = buttons[0][0].getMaximumSize().height;

        // Scale image to fit button
        Image original = new ImageIcon(imgURL).getImage();
        //Image scaled = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(original);
    }

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
            // buttonClicked.setIcon(oIcon);
        } else {
            button.setText("O");
            button.setForeground(Color.BLUE);
            // buttonClicked.setIcon(xIcon);
        }
        int row = (int)button.getClientProperty(BUTTON_ROW);
        int col = (int)button.getClientProperty(BUTTON_COL);
        board.makeMove(new Board.Move(row, col));

        // TODO: remove. For debugging
        System.out.println(board);
        System.out.println();

        boolean gameOver = board.gameOver();
        if (gameOver) {
            showGameOver();
        }
        return gameOver;
    }

    private void showGameOver() {
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

            // TODO: remove. For debugging
            System.out.println(board);
            System.out.println();
        } else {
            // Exit the game
            this.dispose();
        }
    }

}
