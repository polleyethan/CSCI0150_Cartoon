package Cartoon;

/**
 * Models the Game Configuration of a given game. Stores the Number of rows and
 * columns, the row height, the column width, and the delay in the timeline.
 */
public class GameConfig {
	private int _NUM_ROW;
	private int _NUM_COL;
	private double _COL_WIDTH;
	private double _ROW_HEIGHT;
	private double _TIMELINE_DELAY;

	/**
	 * Constructor for the GameConfig class. Takes in the number of rows, columns,
	 * and the speed. Uses the Number of rows and columns, along with the Pane width
	 * and height, in order to initialize the row height and column width.
	 */
	public GameConfig(int rows, int cols, double speed) {

		_NUM_ROW = rows;
		_NUM_COL = cols;
		_COL_WIDTH = Constants.PANE_WIDTH / _NUM_COL;
		_ROW_HEIGHT = Constants.PANE_HEIGHT / _NUM_ROW;
		_TIMELINE_DELAY = speed;

	}

	/**
	 * Accessor method for the number of rows.
	 */
	public int getNumRow() {
		return _NUM_ROW;
	}

	/**
	 * Accessor method for the number of columns
	 */
	public int getNumCol() {
		return _NUM_COL;
	}

	/**
	 * Accessor method for the column width.
	 */
	public double getColWidth() {
		return _COL_WIDTH;
	}

	/**
	 * Accessor method for the row height.
	 */
	public double getRowHeight() {
		return _ROW_HEIGHT;
	}

	/**
	 * Accessor method for the timeline delay.
	 */
	public double getTimelineDelay() {
		return _TIMELINE_DELAY;
	}

}
