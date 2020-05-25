/**
 * Interface for a bot that can return the next tic tac toe move it wants to make
 */
public interface TicTacToeBot {

    /**
     * Return the next move the bot wants to make
     * @param b The current board state
     * @return next move to make
     */
    public Board.Move getNextMove(Board b);
}
