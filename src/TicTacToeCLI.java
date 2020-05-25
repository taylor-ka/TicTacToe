import java.util.Random;
import java.util.Scanner;

/**
 * Bare bones CLI for TicTacToe. No error checking. For initial debugging and testing purposes.
 */
public class TicTacToeCLI {

    private static final int BOT_MODE = 2;
    private static final int DIM = 3;

    // TODO: no error checking and this uglyy
    // This is terrible code. Please write helper methods and do error checking :))
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Board b = new Board(DIM);

        System.out.println("Welcome to Tic Tac Toe!");
        System.out.println("Game mode:");
        System.out.println("\t(1) Player vs. Player");
        System.out.println("\t(2) Player vs. Bot");
        System.out.print("Choose a mode: ");
        int mode = in.nextInt();

        MinimaxBot bot = new MinimaxBot();
        System.out.println();
        System.out.println(b);
        System.out.println();

        // TODO: Bot order chosen randomly
        Random r = new Random();
        int botPlayer = r.nextInt(2) + 1 ;
        while(!b.gameOver()) {
            int currPlayer = b.getCurrPlayer();
            Board.Move m;
            if (mode != BOT_MODE || currPlayer != botPlayer) {
                System.out.print("Player " + currPlayer + " - make a move: ");
                String moveString = in.next();
                m = new Board.Move(moveString.charAt(0), moveString.charAt(1));
            } else {
                m = bot.getNextMove(b);
                System.out.println("Player " + currPlayer + " - Bot made move: " + m);
            }
            b.makeMove(m);

            System.out.println(b);
            System.out.println();
        }

        int winner = b.getWinner();
        if (winner == Board.TIE) {
            System.out.println("Tie :/ like a bow--better luck next time bro");
        } else if (mode == BOT_MODE){
            if (winner == botPlayer) {
                System.out.println("Boooooo... you lost to BOT--looks like you didn't do so " +
                        "hot >:)");
            } else {
                System.out.println("UH OH--BOT defeated hath been... how on earth did you " +
                        "win!? :O");
            }
        } else { // A normal player one
            System.out.println("Player " + winner + " won :) TicTacToe was fun but now we're " +
                    "done!");
        }
    }

}
