import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.YES_OPTION;

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToeGUI();
            }
        });
    }

    public TicTacToeGUI() {
        super(); // call JFrame default constructor

        // Create content pane
        pane = getContentPane();
        pane.setLayout(new GridLayout(DIM,DIM));
        setTitle("Tic Tac Toe");
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Set up board buttons
        initializeButtons();
        initializeIcons(); // TODO: remove

        // Create board
        board = new Board(DIM);
        bot = new MinimaxBot();

        setVisible(true);
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
    }


    private void initializeButtons() {
        buttons = new JButton[DIM][DIM];
        Font font = new Font("Arial", Font.PLAIN, 150);
        for (int r = 0; r < DIM; r++) {
            for (int c = 0; c < DIM; c++) {
                buttons[r][c] = new JButton();
                JButton currButton = buttons[r][c];     // for easier reference
                currButton.addActionListener(this);
                currButton.setText("");
                currButton.setFont(font);

                // To prevent double clicks, 1000 millisecond threshold
                currButton.setMultiClickThreshhold(1000);

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

    public void actionPerformed(ActionEvent e) {
        // Update board to reflect user's move
        JButton buttonClicked = (JButton)e.getSource();
        //buttonClicked.setEnabled(false);
        moveMade(buttonClicked);
        
        // TODO: don't do this if resetting game
        Board.Move botMove = bot.getNextMove(board);
        moveMade(buttons[botMove.row][botMove.col]);
    }

    private void moveMade(JButton button) {
        // Disable button so that re-clicking it does nothing
        button.removeActionListener(this);

        if (board.getCurrPlayer() == 1) {
            button.setText("O");
            button.setForeground(Color.BLUE);
            // buttonClicked.setIcon(oIcon);
        } else {
            button.setText("X");
            button.setForeground(Color.RED);
            // buttonClicked.setIcon(xIcon);
        }
        int row = (int)button.getClientProperty(BUTTON_ROW);
        int col = (int)button.getClientProperty(BUTTON_COL);
        board.makeMove(new Board.Move(row, col));

        // TODO: remove. For debugging
        System.out.println(board);
        System.out.println();
        if (board.gameOver()) {
            gameOver();
        }
    }

    private void gameOver() {
        // Show game over dialog box
        Object[] options = {"Play Again", "Exit"};
        int response = JOptionPane.showOptionDialog(this,
                    "Game Over!",
                    "Game Over!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,     //do not use a custom Icon
                    options,  //the titles of buttons
                    options[0]); //default button selected

        // Process user's choice
        if (response == YES_OPTION) {
            resetGame();

            // TODO: remove. For debugging
            System.out.println(board);
            System.out.println();
        } else {
            this.dispose();
        }
    }

}
