package com.fpt.u0.main;

public class ButtonState {
	private int row;
	private int column;

	private boolean state = false;

	public ButtonState(int row, int column, boolean state) {
		this.setColumn(column);
		this.setRow(row);
		this.setState(state);
	}

	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}

	public boolean getState() {
		return this.state;
	}

	public ButtonState setRow(int row) {
		this.row = row;

		return this;
	}

	public ButtonState setColumn(int column) {
		this.column = column;

		return this;
	}

	public ButtonState setState(boolean state) {
		this.state = state;

		return this;
	}

	public void invert() {
		this.setState(!this.getState());
	}

	@Override
	public String toString() {
		return "Pos["+(this.getColumn()+1)+"]["+(this.getRow()+1)+"] S["+this.getState()+"]";
	}
}