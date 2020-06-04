// Taylor Ka - June 2020

import java.util.List;

public class MinimaxBotExample implements TicTacToeBot {

    private static final ScoredMove TIED = new ScoredMove(0, null);
    private static final ScoredMove LOST = new ScoredMove(-1, null);

    public Board.Move getNextMove(Board b) {
        ScoredMove bestMove = minimaxSearch(b);
        return bestMove.move;
    }

    private ScoredMove minimaxSearch(Board b) {
        if (b.gameOver()) {
            // The last player made the "winning" move, so you either tied or lost
            if (b.getWinner() == Board.TIE) {
                return TIED;
            } else {
                return LOST;
            }
        }

        List<Board.Move> possibleMoves = b.getPossibleMoves();
        ScoredMove bestMove = new ScoredMove(Integer.MIN_VALUE, null);
        for(Board.Move move : possibleMoves) {
            b.makeMove(move);
            ScoredMove opponentScored = minimaxSearch(b);
            int ourCurrScore = -opponentScored.score;
            if (ourCurrScore > bestMove.score) {
                bestMove = new ScoredMove(ourCurrScore, move);
            }
            b.undoMove(move);
        }
        return bestMove;
    }

    private static class ScoredMove {
        public final int score;
        public final Board.Move move;

        public ScoredMove(int score, Board.Move move) {
            this.score = score;
            this.move = move;
        }
    }

}
