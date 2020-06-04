// Taylor Ka - June 2020

/**
 * Interface for a bot that can return the next tic tac toe move it wants to make
 */
public interface TicTacToeBot {

    /**
     * Return the next move the bot wants to make.
     * @param b The current board state. The game should not be over.
     * @return next move to make
     */
    public Board.Move getNextMove(Board b);
}
