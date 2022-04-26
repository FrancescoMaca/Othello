package org.othello.logic.board;

import org.othello.logic.AI.MiniMax;

import java.util.ArrayList;
import java.util.List;

public class GameBoard extends MiniMax {

    public final static int SIZE = 8;

    /**
     * Board size
     */
    private final static int WIDTH = SIZE;
    private final static int HEIGHT = SIZE;

    /**
     * Keep track of the players' points
     */
    private int whiteCells;
    private int blackCells;

    /**
     * Main board
     */
    private final Cell[][] board = new Cell[WIDTH][HEIGHT];

    /**
     * Default constructor
     */
    public GameBoard() {
        initializeBoard();
    }

    /**
     * Initializes the board with default Othello's values
     */
    private void initializeBoard() {

        whiteCells = 0;
        blackCells = 0;

        for(int i = 0; i < GameBoard.SIZE; i++) {
            for(int j = 0; j < GameBoard.SIZE; j++) {
                board[i][j] = new Cell(i, j);
            }
        }

        // Setting up default cells
        setCell(3, 3, CellState.White);
        setCell(3, 4, CellState.Black);
        setCell(4, 4, CellState.White);
        setCell(4, 3, CellState.Black);
    }

    /**
     * Gets the cell at a given coordinate.
     * @param x coordinate
     * @param y coordinate
     * @return {@code null} if the given cell's coordinates are not inside of the board's bounds,
     * otherwise it returns the {@code Cell} object.
     */
    public Cell getCell(int x, int y) {
        if (checkBounds(x, y)) {
            return null;
        }

        return board[x][y];
    }

    /**
     * Sets to a cell the given state.
     * @param x coordinate
     * @param y coordinate
     * @param state the new cell's state
     */
    public void setCell(int x, int y, CellState state) {

        // This method checks only the essential things like valid
        // coordinates, doesn't check for anything else than this
        if (checkBounds(x, y)) {
            return;
        }

        if (state == CellState.Black) {
            blackCells++;

            if (getCell(x, y).getState() == CellState.White) {
                whiteCells--;
            }
        }
        else if (state == CellState.White) {
            whiteCells++;

            if (getCell(x, y).getState() == CellState.Black) {
                blackCells--;
            }
        }

        board[x][y].setState(state);
    }

    /**
     * @return the black cells count
     */
    public int getBlackCount() {
        return blackCells;
    }

    /**
     * @return the white cells count
     */
    public int getWhiteCount() {
        return whiteCells;
    }

    /**
     * @return the current board's state
     */
    public GameState getState() {

        if (getAvailableCells().size() > 0) {
            return GameState.Unfinished;
        }

        if (blackCells > whiteCells) {
            return GameState.BlackWin;
        }
        else if (whiteCells > blackCells) {
            return GameState.WhiteWin;
        }

        return GameState.Draw;
    }

    /**
     * Calculates the number of available cells on the current board, used to
     * determine if the current player has any moves left.
     * @return a list of available cells in the board
     */
    public List<Cell> getAvailableCells() {
        List<Cell> freeCells = new ArrayList<>();

        for (int i = 0; i < GameBoard.SIZE; i++) {
            for (int j = 0; j < GameBoard.SIZE; j++) {
                if (board[i][j].isAvailable()) {
                    freeCells.add(board[i][j]);
                }
            }
        }

        return freeCells;
    }

    /**
     * Resets the current board.
     */
    public void reset() {
        initializeBoard();
    }

    /**
     * Clones the current board manually. Used to calculate best move
     * with the AI.
     * @return a shadow copy of this board.
     */
    public GameBoard clone() {
        GameBoard copy = new GameBoard();

        for(int i = 0; i < GameBoard.SIZE; i++) {
            for(int j = 0; j < GameBoard.SIZE; j++) {
                copy.setCell(i, j, this.getCell(i, j).getState());
            }
        }

        copy.whiteCells = this.whiteCells;
        copy.blackCells = this.blackCells;

        return copy;
    }

    /**
     * Checks if the given coordinates are valid.
     * @param x coordinate
     * @param y coordinate
     * @return {@code true} if the given coordinates are outside of the bounds, otherwise
     * it returns {@code false}
     */
    private boolean checkBounds(int x, int y) {
        return (x < 0 || x >= SIZE) || (y < 0 || y >= SIZE);
    }

    /**
     * Prints to the console the current board.
     * @return the given board in String format
     */
    public String toString() {

        StringBuilder s = new StringBuilder();

        for(Cell[] row : board) {
            for(Cell c : row) {
                s.append(c.toString().charAt(0)).append(" ");
            }

            s.append("\n");
        }

        return s.toString();
    }
}
