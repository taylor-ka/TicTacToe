import java.util.Scanner;

public class TicTacToeCLI {
    // TODO: no error checking and this uglyy
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Board b = new Board();

        System.out.println("Welcome to Tic Tac Toe!");
        System.out.println(b);

        while(!b.gameOver()) {
            int currPlayer = b.getCurrPlayer();
            System.out.print("Player " + currPlayer + " - make a move: ");
            String moveString = in.nextLine();
            Board.Move m =  new Board.Move(moveString.charAt(0), moveString.charAt(1));
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
