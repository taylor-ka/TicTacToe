import java.util.Scanner;

public class TicTacToeCLI {
    // TODO: no error checking and this uglyy
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Board b = new Board();

        System.out.println("Welcome to Tic Tac Toe!");
        System.out.println("Game mode:");
        System.out.println("\t(1) Player vs. Player");
        System.out.println("\t(2) Player vs. Bot");
        System.out.print("Choose a mode: ");
        int mode = in.nextInt();

        MinimaxBot bot = new MinimaxBot();
        System.out.println(b);

        // TODO: who is bot? randint
        int botPlayer = 2;
        while(!b.gameOver()) {
            int currPlayer = b.getCurrPlayer();
            Board.Move m;
            if (mode == 1 || currPlayer != botPlayer) {
                System.out.print("Player " + currPlayer + " - make a move: ");
                String moveString = in.next();
                m = new Board.Move(moveString.charAt(0), moveString.charAt(1));
            } else {
                m = bot.getNextMove(b);
                System.out.println("Player " + currPlayer + " - Bot made move: " + m);
            }
            b.makeMove(m);

            System.out.println(b);
        }

        int winner = b.getWinner();
        if (winner == 0) {
            System.out.println("Tie :\\\\");
        } else {
            System.out.println("Player " + winner + " is the winner! :))");
        }
    }
}
