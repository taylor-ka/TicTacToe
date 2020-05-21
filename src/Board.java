import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int DIM = 3;
    private int[][] board;

    public static final int TIE = 0;
    private static final int P1 = 1;
    private static final int P2 = 2;

    // numMovesMade % 2 == 0 --> Player 1's turn
    // numMovesMade % 2 == 1 --> Player 2's turn
    private int numMovesMade;
    private boolean gameOver;
    private int winner;

    /**
     * Create an empty board
     */
    public Board() {
        board = new int[DIM][DIM];
        numMovesMade = 0;
    }

    public boolean gameOver() {
        return gameOver;
    }

    public int getWinner() {
        if (!gameOver) {
            throw new IllegalStateException("Game is not over");
        }
        return winner;
    }

    public List<Move> getPossibleMoves() {
        if (gameOver) {
            throw new IllegalStateException("Game is over, so there are no possible moves");
        }
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
        checkWinner(m); // Check if this move causes player to win, update fields accordingly
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
        winner = TIE; // TODO: remove. sanity check
    }

    public int getCurrPlayer() {
        return numMovesMade % 2 + 1;
    }

    private void checkWinner(Move m) {
        boolean winnerExists = isRowWinner(m) || isColWinner(m) || isDiagWinner(m);
        if (!winnerExists && numMovesMade == DIM * DIM - 1) {  // this will be the last move
            winner = TIE;
            gameOver = true;
        } else if (winnerExists) {
            winner = getCurrPlayer();
            gameOver = true;
        }
    }

    private boolean isRowWinner(Move m) {
        int currPlayer = getCurrPlayer();
        for (int c = 0; c < DIM; c++) {
            if (board[m.row][c] != currPlayer) {
                return false;
            }
        }
        return true;
    }

    private boolean isColWinner(Move m) {
        int currPlayer = getCurrPlayer();
        for (int r = 0; r < DIM; r++) {
            if (board[r][m.col] != currPlayer) {
                return false;
            }
        }
        return true;
    }

    private boolean isDiagWinner(Move m) {
        boolean backDiag = m.row == m.col;
        boolean frontDiag = m.row + m.col == DIM - 1;
        if (backDiag && frontDiag) {  // Move was in the center of the board
            return isBackDiagWin(m) || isFrontDiagWin(m);
        } else if (backDiag) {
            return isBackDiagWin(m);
        } else if (frontDiag) {
            return isFrontDiagWin(m);
        } else {
            return false;
        }
    }

    // Check diagonal from top left to bottom right. this way: "\"
    private boolean isBackDiagWin(Move m) {
        int currPlayer = getCurrPlayer();
        for (int i = 0; i < DIM; i++) {
            if(board[i][i] != currPlayer) {
                return false;
            }
        }
        return true;
    }

    // Check diagonal from bottom left to top right. this way: "/"
    private boolean isFrontDiagWin(Move m) {
        int currPlayer = getCurrPlayer();
        for (int i = 0; i < DIM; i++) {
            if (board[DIM - 1 - i][i] != currPlayer) {
                return false;
            }
        }
        return true;
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

        public Move(char rowChar, int colChar) {
            // TODO: do I need this
            this.row = rowChar - 'a';
            this.col = colChar - '0';
        }

        public String toString() {
            return "" + (char)(row + 'a') + col;
        }
    }
}
