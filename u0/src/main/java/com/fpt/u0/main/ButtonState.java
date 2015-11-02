package com.fpt.u0.main;


/**
 * ButtonState holder
 *
 * @author Sufian Vaio
 *
 */
public class ButtonState {
	/**
	 * Row position
	 */
	private int row;

	/**
	 * Column position
	 */
	private int column;

	/**
	 * State of button
	 */
	private boolean state = false;

	/**
	 * Constructor
	 *
	 * @param row
	 * @param column
	 * @param state
	 */
	public ButtonState(int row, int column, boolean state) {
		this.setColumn(column);
		this.setRow(row);
		this.setState(state);
	}

	/**
	 * Get row
	 *
	 * @return
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * Get column
	 *
	 * @return
	 */
	public int getColumn() {
		return this.column;
	}

	/**
	 * Get state
	 * @return
	 */
	public boolean getState() {
		return this.state;
	}

	/**
	 * Set row
	 *
	 * @param row
	 * @return
	 */
	public ButtonState setRow(int row) {
		this.row = row;

		return this;
	}


	/**
	 * Set column
	 *
	 * @param column
	 * @return
	 */
	public ButtonState setColumn(int column) {
		this.column = column;

		return this;
	}

	/**
	 * Set state
	 *
	 * @param state
	 * @return
	 */
	public ButtonState setState(boolean state) {
		this.state = state;

		return this;
	}

	/**
	 * Invert state
	 */
	public void invert() {
		this.setState(!this.getState());
	}

	@Override
	/**
	 * toString()
	 */
	public String toString() {
		return "Pos["+(this.getColumn()+1)+"]["+(this.getRow()+1)+"] S["+this.getState()+"]";
	}
}
