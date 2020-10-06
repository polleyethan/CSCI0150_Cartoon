package Cartoon;

/**
 * Enumeration for different available row and column configurations.
 */
public enum RowColConfig {
	FIVE("5x5", 5), TEN("10x10", 10), FIFTEEN("15x15", 15), TWENTY("20x20", 20), TWENTYFIVE("25x25", 25),
	THIRTY("30x30", 30), THIRTYFIVE("35x35", 35), FORTY("40x40", 40);

	private String _stringVal;
	private int _num;

	/**
	 * Constructor for the RowColConfig class.
	 */
	private RowColConfig(String s, int n) {
		_stringVal = s;
		_num = n;

	}

	/**
	 * Returns the int representing the number of rows/columns there are.
	 */
	public int getNum() {
		return _num;
	}

	/**
	 * Returns a string representation of how many rows/columns there are.
	 */
	public String toString() {
		return _stringVal;
	}
}
