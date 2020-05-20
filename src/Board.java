import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int DIM = 3;
    private int[][] board;

    private static final int P1 = 1;
    private static final int P2 = 2;

    // numMovesMade % 2 == 0 --> Player 1's turn
    // numMovesMade % 2 == 1 --> Player 2's turn
    private int numMovesMade;
    private boolean gameOver;

    public static void main(String[] args) {
        Board b = new Board();
        System.out.println(b);
        List<Move> moves = b.getPossibleMoves();
        System.out.println(moves);

        System.out.println("P1 making move " + moves.get(0));
        b.makeMove(moves.get(0));
        System.out.println(b);

        System.out.println("P2 making move " + moves.get(1));
        b.makeMove(moves.get(1));
        System.out.println(b);

        System.out.println("Undoing move " + moves.get(0));
        b.undoMove(moves.get(0));
        System.out.println(b);
    }

    /**
     * Create an empty board
     */
    public Board() {
        board = new int[DIM][DIM];
        numMovesMade = 0;
    }

    public List<Move> getPossibleMoves() {
        List<Move> possibleMoves = new ArrayList<>();
        for (int r = 0; r < DIM; r++) {
            for (int c = 0; c < DIM; c++) {
                if (board[r][c] == 0) { // spot is empty
                    possibleMoves.add(new Move(r, c));
                }
            }
        }
        return possibleMoves;
    }

    public void makeMove(Move m) {
        if (gameOver) {
            throw new IllegalStateException("Cannot make move, game is over");
        } else if (m.row < 0 || m.row >= DIM || m.col < 0 || m.col >= DIM) {
            throw new IllegalArgumentException("Illegal move: " + m.toString());
        } else if (board[m.row][m.col] != 0) {
            throw new IllegalArgumentException("Move already made: " + m.toString());
        }
        board[m.row][m.col] = getCurrPlayer();
        // TODO: figure out if game over
        numMovesMade++;
    }

    public void undoMove(Move m) {
        if (m.row < 0 || m.row >= DIM || m.col < 0 || m.col >= DIM) {
            throw new IllegalArgumentException("Illegal move: " + m.toString());
        } else if (board[m.row][m.col] == 0) {
            throw new IllegalArgumentException("Move was never made: " + m.toString());
        }
        board[m.row][m.col] = 0;
        numMovesMade--;
        gameOver = false;
    }

    private int getCurrPlayer() {
        return numMovesMade % 2 + 1;
    }

    private boolean checkWinner(Move m) {
        return checkRowWinner(m) || checkColWinner(m) || checkDiagWinner(m);
    }

    private boolean checkRowWinner(Move m) {
        int currPlayer = getCurrPlayer();
        for (int c = 0; c < DIM; c++) {
            if (board[m.row][c] != currPlayer) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColWinner(Move m) {
        int currPlayer = getCurrPlayer();
        for (int r = 0; r < DIM; r++) {
            if (board[r][m.col] != currPlayer) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagWinner(Move m) {
        if (m.row == m.col) {
            return false; // TODO
        } else if (m.row + m.col == DIM - 1) {
            return false; // TODO
        } else {
            return false;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < DIM; r++) {
            for (int c = 0; c < DIM; c++) {
                sb.append(board[r][c] + " ");
            }

            if (r != DIM - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public static final class Move {
        public final int row;
        public final int col;

        public Move(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public String toString() {
            return "" + (char)(row + 'a') + col;
        }
    }

}
