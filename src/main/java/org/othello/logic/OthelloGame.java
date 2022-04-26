package org.othello.logic;

import org.othello.logic.AI.MiniMax;
import org.othello.logic.board.*;

public class OthelloGame {

	/**
	 * Flag that indicates weather the game has started.
	 */
	private boolean hasStarted = false;

	/**
	 * The current turn, by default the first turn is black's.
	 */
	private CellState currentTurn = CellState.Black;

	/**
	 * A {@code GameBoard} object that will be used as the main board.
	 */
	private final GameBoard board = new GameBoard();

	/**
	 * @param x coordinate
	 * @param y coordinate
	 * @return the cell at the given coordinate
	 */
	public Cell getCell(int x, int y) {
		return board.getCell(x, y);
	}

	/**
	 * Sets the given cell to the current player's turn, if available.
	 * @param x coordinate
	 * @param y coordinate
	 */
	public void setCell(int x, int y) {

		// Check for cell's eligibility
		if (!getCell(x, y).isAvailable() || !hasStarted) {
			return;
		}

		// Change the cell's state
		board.setCell(x, y, getTurn());

		int radius = 1;

		for(int i = x - radius; i <= x + radius; i++) {
			for(int j = y - radius; j <= y + radius; j++) {

				CellState validTurn = (getTurn() == CellState.Black) ? CellState.White : CellState.Black;

				if (board.getCell(i, j) == null || board.getCell(i, j).getState() != validTurn || (i == x && j == y)) {
					continue;
				}

				int offsetX = i - x;
				int offsetY = j - y;

				int tmpX = i, tmpY = j;

				// Goes forward until a different color cell is reached
				while(board.getCell(tmpX, tmpY).getState() == ((getTurn() == CellState.Black) ? CellState.White : CellState.Black)) {
					tmpX += offsetX;
					tmpY += offsetY;

					if (board.getCell(tmpX, tmpY) == null) {
						break;
					}
				}

				// If the last cell is the same color of the starting one then its a valid move
				if (board.getCell(tmpX, tmpY) != null && board.getCell(tmpX, tmpY).getState() == getTurn()) {

					// Flips all the cells from the last one to the first one
					do {
						tmpX -= offsetX;
						tmpY -= offsetY;

						board.setCell(tmpX, tmpY, getTurn());
					} while(tmpX !=  (x + offsetX) || tmpY != (y + offsetY));
				}
			}
		}

		// Changes turn
		if (currentTurn == CellState.White) {
			currentTurn = CellState.Black;
		}
		else if (currentTurn == CellState.Black) {
			currentTurn = CellState.White;
		}
	}

	/**
	 * Finds the best move for the given turn.
	 * @param difficulty the recursion depth the algorithm will go through
	 * @return the cell with the best move
	 */
	public Cell getBestMove(int difficulty) {
		board.setMaxDepth(difficulty * 6);
		board.setMaxPlayer(getTurn());

		return board.getMinMax(board.clone(), getTurn());
	}

	/**
	 * @return white's point
	 */
	public int getWhiteScore() {
		return board.getWhiteCount();
	}

	/**
	 * @return black's point
	 */
	public int getBlackScore() {
		return board.getBlackCount();
	}

	/**
	 * @return the current player's turn
	 */
	public CellState getTurn() {
		return currentTurn;
	}

	/**
	 * @return the current board's state
	 */
	public GameState getState() {
		return board.getState();
	}

	/**
	 * Tries a move on a certain set of coordinates.
	 * @param x coordinate
	 * @param y coordinate
	 * @return the value of the given move with the current turn, if the value is 0 then the move is not
	 * available
	 */
	public int tryMove(int x, int y) {

		int moveValue = 0;

		if (!board.getCell(x, y).isFree()) {
			return moveValue;
		}

		int radius = 1;

		for(int i = x - radius; i <= x + radius; i++) {
			for(int j = y - radius; j <= y + radius; j++) {

				// Gets the color to check for
				CellState validTurn = (getTurn() == CellState.Black) ? CellState.White : CellState.Black;

				// if the coordinates are not valid or its not the right color then skips the iteration
				if (board.getCell(i, j) == null || board.getCell(i, j).getState() != validTurn || (i == x && j == y)) {
					continue;
				}

				moveValue += connect(board.getCell(x, y), i - x, j - y);
			}
		}

		return moveValue;
	}

	/**
	 * Calculates the score of a given move in a given direction [+offsetX; +offsetY].
	 * @param starting the starting {@code Cell} object from which to start calculating
	 * @param offsetX X offset of the direction compared to the previous coordinate
	 * @param offsetY Y offset of the direction compared to the previous coordinate
	 * @return the total points made with that connection
	 */
	private int connect(Cell starting, int offsetX, int offsetY) {

		int score = 0;

		int x = starting.getX() + offsetX;
		int y = starting.getY() + offsetY;

		// The second condition is just to make sure there are no endless loops
		if (board.getCell(x, y) == null || (offsetX == 0 && offsetY == 0)) {
			return score;
		}

		while(board.getCell(x, y).getState() == ((getTurn() == CellState.Black) ? CellState.White : CellState.Black)) {
			x += offsetX;
			y += offsetY;

			score++;

			if (board.getCell(x, y) == null) {
				return 0;
			}
		}

		if (board.getCell(x, y).getState() == getTurn()) {
			return score;
		}

		return 0;
	}

	/**
	 * Resets the board to the beginning of the game
	 */
	public void reset() {
		board.reset();
		currentTurn = CellState.Black;
		this.hasStarted = false;
	}

	/**
	 * Starts the current game
	 */
	public void start() {
		this.hasStarted = true;
	}

	/**
	 * @return {@code true} if the game has started, otherwise {@code false}
	 */
	public boolean hasStarted() {
		return hasStarted;
	}

	public OthelloGame clone() {
		try {
			return (OthelloGame) super.clone();
		}
		catch(CloneNotSupportedException e) {
			return null;
		}
	}
	
	@Override
	public String toString() {
		return board.toString();
	}
}
