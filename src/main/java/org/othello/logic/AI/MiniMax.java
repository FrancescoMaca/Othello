package org.othello.logic.AI;

import org.othello.logic.board.Cell;
import org.othello.logic.board.CellState;
import org.othello.logic.board.GameBoard;

import java.util.List;

public class MiniMax {

    private int maxDepth;
    private static int depth;
    private CellState maxPlayer;

    public Cell getMinMax(GameBoard board, CellState turn) {

        Cell AIMove = null;

        List<Cell> possibleMoves = board.getAvailableCells();

        int score = getScore(board, turn);

        for(Cell cell : possibleMoves) {
            board.setCell(cell.getX(), cell.getY(), turn);

            int moveScore = getScore(board, turn);

            board.setCell(cell.getX(), cell.getY(), cell.getState());

            if (score < moveScore) {
                score = moveScore;
                AIMove = cell;
            }
        }

        return AIMove;
    }

    public int getScore(GameBoard board, CellState turn) {
        return (turn == CellState.Black) ?
                (board.getBlackCount() - board.getWhiteCount()) :
                (board.getWhiteCount() - board.getBlackCount());
    }

    public void setMaxDepth(int maxDepth) {
        if (maxDepth > (GameBoard.SIZE * GameBoard.SIZE - 4)) {
            return;
        }

        this.maxDepth = maxDepth;
    }

    public void setMaxPlayer(CellState player) {
        this.maxPlayer = player;
    }

    public void reset() {
        depth = 0;
    }
}
