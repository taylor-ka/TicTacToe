/*
1. Create a window
2. Create a grid of buttons
3. Change the look of a button after user presses it
4. Correctly update Board model when a user presses a button
5. Figure out if a user has won!
Allow the user to play multiple games
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGUI extends JFrame implements ActionListener {
    private Container pane;
    private JButton[][] buttons;

    public static final int DIM = 3;
    private static final String ROW = "row";
    private static final String COL = "col";

    private Board board;

    public TicTacToeGUI() {
        super();
        board = new Board(DIM);
        initializePane();
        initializeButtons();
        // other
        setVisible(true);
    }

    private void initializePane() {
        pane = getContentPane();
        pane.setLayout(new GridLayout(DIM, DIM));
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
                buttons[r][c] = new JButton();
                JButton currButton = buttons[r][c];

                currButton.setFont(font);
                currButton.setText("");

                currButton.addActionListener(this);
                currButton.putClientProperty(ROW, r);
                currButton.putClientProperty(COL, c);

                add(buttons[r][c]);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        JButton currButton = (JButton)e.getSource();
        updateMoveMade(currButton);
    }

    private void updateMoveMade(JButton button) {
        button.removeActionListener(this);
        if (board.getCurrPlayer() == Board.P1) {
            button.setText("X");
            button.setForeground(Color.RED);
        } else {
            button.setText("O");
            button.setForeground(Color.BLUE);
        }
        int row = (int)button.getClientProperty(ROW);
        int col = (int)button.getClientProperty(COL);

        board.makeMove(new Board.Move(row, col));

        System.out.println(board);
        System.out.println();

        if (board.gameOver()) {
            showGameOver();
        }
    }

    private void showGameOver() {
        Object[] options = {"Play Again?",
                "Exit"};
        int n = JOptionPane.showOptionDialog(this,
                "Game Over!",
                "Game Over!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title

    }

}
