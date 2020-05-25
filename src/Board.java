import java.util.ArrayList;
import java.util.List;

/**
 * Models a board for a game of TicTacToe. The dimensions of the board can be adjusted, in case
 * you really love TicTacToe and want to keep playing longer.
 */
public class Board {
    public static final int TIE = 0;
    public static final int P1 = 1;
    public static final int P2 = 2;

    private int[][] board;

    // numMovesMade % 2 == 0 --> Player 1's turn
    // numMovesMade % 2 == 1 --> Player 2's turn
    private int numMovesMade;

    private boolean gameOver;
    private int winner;

    // Dimensions of the board
    private final int dim;

    /**
     * Creates an empty board of the given dimension
     * @param dim resulting board will be of size (dim x dim)
     */
    public Board(int dim) {
        this.dim = dim;
        board = new int[dim][dim];
        numMovesMade = 0;
        winner = 0;
    }

    /**
     * Resets the board to start a new game
     */
    public void reset() {
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                board[r][c] = 0;
            }
        }

        numMovesMade = 0;
        gameOver = false;
        winner = 0;
    }

    /**
     * Returns the player for the the current turn of the game
     * @return either {@value Board#P1} or {@value Board#P2}, representing the current player
     */
    public int getCurrPlayer() {
        return numMovesMade % 2 + 1;
    }

    /**
     * Returns if the Tic Tac Toe game is over
     * @return if game is over
     */
    public boolean gameOver() {
        return gameOver;
    }

    /**
     * Returns the winner of the game
     * @return either {@value Board#P1} or {@value Board#P2}, representing the current player
     */
    public int getWinner() {
        if (!gameOver) {
            throw new IllegalStateException("Game is not over");
        }
        return winner;
    }

    /**
     * Returns a list of all the possible moves given the current board state
     * @return list of possible moves
     * @throws IllegalStateException if the game is over
     */
    public List<Move> getPossibleMoves() {
        if (gameOver) {
            throw new IllegalStateException("Game is over, so there are no possible moves");
        }
        List<Move> possibleMoves = new ArrayList<>();
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                if (board[r][c] == 0) { // spot is empty
                    possibleMoves.add(new Move(r, c));
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Updates the board state with the given move
     * @param m move to make
     * @throws IllegalStateException if the game is over
     * @throws IllegalArgumentException if the move is out of bounds or if move was already made
     */
    public void makeMove(Move m) {
        if (gameOver) {
            throw new IllegalStateException("Cannot make move, game is over");
        } else if (outOfBounds(m)) {
            throw new IllegalArgumentException("Illegal move: " + m.toString());
        } else if (board[m.row][m.col] != 0) {
            throw new IllegalArgumentException("Move already made: " + m.toString());
        }
        board[m.row][m.col] = getCurrPlayer();
        checkWinner(m); // Check if this move causes player to win, update fields accordingly
        numMovesMade++;
    }

    /**
     * Updates the board state to undo the given move
     * @param m move to undo
     * @throws IllegalArgumentException if the move is out of bounds or if move was never made
     */
    public void undoMove(Move m) {
        if (outOfBounds(m)) {
            throw new IllegalArgumentException("Illegal move: " + m.toString());
        } else if (board[m.row][m.col] == 0) {
            throw new IllegalArgumentException("Move was never made: " + m.toString());
        }
        board[m.row][m.col] = 0;
        numMovesMade--;
        gameOver = false;
        winner = TIE; // TODO: remove. sanity check
    }

    // Returns if the given move is within the bounds of the board
    private boolean outOfBounds(Move m) {
        return m.row < 0 || m.row >= dim || m.col < 0 || m.col >= dim;
    }

    // Returns if there is a winner
    private void checkWinner(Move m) {
        boolean winnerExists = isRowWinner(m) || isColWinner(m) || isDiagWinner(m);
        if (!winnerExists && numMovesMade == dim * dim - 1) {  // this will be the last move
            winner = TIE;
            gameOver = true;
        } else if (winnerExists) {
            winner = getCurrPlayer();
            gameOver = true;
        }
    }

    // Returns if there is a winner along the row
    private boolean isRowWinner(Move m) {
        int currPlayer = getCurrPlayer();
        for (int c = 0; c < dim; c++) {
            if (board[m.row][c] != currPlayer) {
                return false;
            }
        }
        return true;
    }

    // Returns if there is a winner along the column
    private boolean isColWinner(Move m) {
        int currPlayer = getCurrPlayer();
        for (int r = 0; r < dim; r++) {
            if (board[r][m.col] != currPlayer) {
                return false;
            }
        }
        return true;
    }

    // Returns if there is a winner along the diagonal
    private boolean isDiagWinner(Move m) {
        boolean backDiag = m.row == m.col;
        boolean frontDiag = m.row + m.col == dim - 1;
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
        for (int i = 0; i < dim; i++) {
            if(board[i][i] != currPlayer) {
                return false;
            }
        }
        return true;
    }

    // Check diagonal from bottom left to top right. this way: "/"
    private boolean isFrontDiagWin(Move m) {
        int currPlayer = getCurrPlayer();
        for (int i = 0; i < dim; i++) {
            if (board[dim - 1 - i][i] != currPlayer) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a string representation of the board. Empty spaces are marked with 0s, player 1's
     * moves are marked with 1s, and player 2's moves are marked with 2s
     * @return string representation of the board
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                sb.append(board[r][c] + " ");
            }

            if (r != dim - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Represents a possible TicTacToe move
     */
    public static final class Move {
        public final int row;
        public final int col;

        /**
         * Constructs a new move at the given row and column
         * @param row row number, indexing starts at 0
         * @param col column number. indexing starts at 0
         */
        public Move(int row, int col) {
            this.row = row;
            this.col = col;
        }

        /**
         * Constructs a new move from the character representation a move
         * @param rowChar 'a', 'b', ...
         * @param colChar '0', '1', ...
         */
        public Move(char rowChar, char colChar) {
            // TODO: do I need this
            this.row = rowChar - 'a';
            this.col = colChar - '0';
        }

        /**
         * Returns a string representation of the move. Displayed as rowCol, where row is 'a',
         * 'b', ... and column is 0, 1, ...
         * @return string representation
         */
        public String toString() {
            return "" + (char)(row + 'a') + col;
        }
    }
}
