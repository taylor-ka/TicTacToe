import javax.swing.*;

public class TicTacToeMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            // TODO: replace with your GUI class
            public void run() {
                new TicTacToeGUI();
            }
        });
    }
}
