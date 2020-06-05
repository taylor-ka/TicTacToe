# Tic-Tac-Toe Bot Psuedocode 

## General Strategies
- Follow pseudocode
- Use ideas from recursive backtracking
- Read the comments in the Board.java class to understand how to use it
- Google stuff!!

## Minimax Psuedocode
```
ScoredMove EVALUATE(board, MINIMIZER/MAXIMIZER): 
  If the game is over:
    If tied: return board value = 0
    Else lost: return board value = -1

  If maximizing player’s turn:
    bestMove = MIN_VALUE
    For each possible move:
      Make move
      score = EVALUATE(board, MINIMIZER)
      bestMove = max(bestMove, score)
      Undo move
      Return bestMove

  Else if minimizing player’s turn:
    bestMove = MAX_VALUE
    For each possible move:
      Make move
      score = EVALUATE(board, MAXIMIZER)
      bestMove = min(bestMove, score)
      Undo move
  Return bestMove
```  
## Negamax Psuedocode
```
ScoredMove EVALUATE(board): 
  If the game is over:
    If tied:
       return board value = 0
    Else lost:
      return board value = -1

  Else:
    bestMove = MIN_VALUE
    For each possible move:
      Make move
      score = -EVALUATE(board)
      bestMove = max(bestMove, score)
      Undo move
      Return bestMove
```
