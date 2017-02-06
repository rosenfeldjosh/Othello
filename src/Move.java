
public class Move {
	private int row;
	private int collumn;
	String direction;

	public Move(int row, int collumn, String direction) {
		this.row = row;
		this.collumn = collumn;
		this.direction = direction;
	}

	public String getDirection() {
		return direction;
	}

	public int getRow() {
		return row;
	}

	public int getCollumn() {
		return collumn;
	}

	@Override
	public boolean equals(Object other) {
		return getRow() == ((Move) other).getRow() && getCollumn() == ((Move) other).getCollumn();
	}
}
