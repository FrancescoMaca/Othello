package org.othello.logic.board;

/**
 * A single instance of a cell object in the Othello game.
 */
public class Cell {

	private final int x;
	private final int y;

	private CellState state;

	/**
	 * Default constructor
	 * @param state the cell initial state
	 * @param x coordinate
	 * @param y coordinate
	 */
	public Cell(CellState state, int x, int y) {
		this.state = state;
		this.x = x;
		this.y = y;
	}

	/**
	 * Default constructor
	 * @param x coordinate
	 * @param y coordinate
	 */
	public Cell(int x, int y) {
		this(CellState.Empty, x, y);
	}

	/**
	 * @return the X coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the Y coordinate.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Changes the current cell state.
	 * @param newState the new cell state
	 */
	public void setState(CellState newState) {
		state = newState;
	}

	/**
	 * @return {@code true} if the cell is available, otherwise {@code false}.
	 */
	public boolean isAvailable() {
		return state == CellState.Available;
	}

	/**
	 * @return {@code true} if the cell is empty or available, otherwise {@code false}.
	 */
	public boolean isFree() {
		return (state == CellState.Available || state == CellState.Empty);
	}

	/**
	 * @return the cell's current state
	 * @see org.othello.logic.board.CellState
	 */
	public CellState getState() {
		return state;
	}

	@Override
	public String toString() {
		return state.name() + "[" + x + ", " + y + "]";
	}
}
