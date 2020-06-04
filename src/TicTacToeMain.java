// Taylor Ka - June 2020

import javax.swing.*;

public class TicTacToeMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToeGUIExample();
            }
        });
    }
}
