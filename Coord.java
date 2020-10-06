package Cartoon;

/**
 * Class modeling the coordinates of any objects in the game (BodyParts and
 * Food).
 *
 */
public class Coord {

	private int _x;
	private int _y;
	private GameConfig _config;

	/**
	 * Constructor method for Coord class. Takes in the X and Y coordinates as well
	 * as the configuration object of the game.
	 */
	public Coord(int x, int y, GameConfig config) {
		_x = x;
		_y = y;
		_config = config;
	}

	/**
	 * Returns whether or not this Coord has collided with the inputted coord.
	 * Tells whether two collideable objects have collided.
	 */
	public Boolean checkCollide(Coord coord) {
		if (_x == coord.getX() && _y == coord.getY()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Accessor method for the _x instance variable.
	 */
	public int getX() {
		return _x;
	}

	/**
	 * Accessor method for the _y instance variable.
	 */
	public int getY() {
		return _y;
	}

	/**
	 * Mutator method for the _x instance variable. Sets _x to the inputted int.
	 */
	public void setX(int x) {
		_x = x;
	}

	/**
	 * Mutator method for the _y instance variable. Sets _y to the inputted int.
	 */
	public void setY(int y) {
		_y = y;
	}

	/**
	 * Changes the coordinates _x and _y variables depending on what direction is
	 * set as a parameter.
	 */
	public void moveDirection(Direction d) {
		switch (d) {
		case UP:
			if (_y == 1) {
				_y = _config.getNumRow();
				break;
			} else {
				_y--;
				break;
			}
		case DOWN:
			if (_y == _config.getNumRow()) {
				_y = 1;
				break;
			} else {
				_y++;
				break;
			}
		case LEFT:
			if (_x == 1) {
				_x = _config.getNumCol();
				break;
			} else {
				_x--;
				break;
			}
		case RIGHT:
			if (_x == _config.getNumCol()) {
				_x = 1;
				break;
			} else {
				_x++;
				break;
			}
		}
	}

	/**
	 * Mutator method for the _x instance variable. Adds inputted int to the _x
	 * instance variable.
	 */
	public void alterX(int x) {
		_x += x;
	}

	/**
	 * Mutator method for the _y instance variable. Adds inputted int to the _y
	 * instance variable.
	 */
	public void alterY(int y) {
		_y += y;
	}

	/**
	 * Gets the start coordinate for a new BodyPart. Uses the Direction of the last
	 * BodyPart on the Snake and returns a coord that is in the opposite Direction from that BodyPart.
	 */
	public Coord getStartCoord(Direction lastdirection) {
		Coord lastcoord = new Coord(_x, _y, _config);
		switch (lastdirection) {
		case UP:
			if (lastcoord._y == _config.getNumRow()) {
				lastcoord.setY(1);
				break;
			} else {
				lastcoord.alterY(1);
				break;
			}
		case DOWN:
			if (lastcoord._y == 1) {
				lastcoord.setY(_config.getNumRow());
				break;
			} else {
				lastcoord.alterY(-1);
				break;
			}
		case LEFT:
			if (lastcoord._x == _config.getNumCol()) {
				lastcoord.setX(1);
				break;
			} else {
				lastcoord.alterX(1);
				break;
			}
		case RIGHT:
			if (lastcoord._x == 1) {
				lastcoord.setX(_config.getNumCol());
				break;
			} else {
				lastcoord.alterX(-1);
				break;
			}
		}
		return lastcoord;
	}

	/**
	 * Uses the Game Configuration to get the x location ON THE PANE, of a given
	 * Coord. This is the location on the pane that the item should be drawn. This
	 * gets the X location of the CENTER of a Coordinate.
	 */
	public int getPaneXCoord() {
		return (int) ((_x - 1) * _config.getColWidth() + (_config.getColWidth() / 2));
	}

	/**
	 * Uses the Game Configuration to get the y location ON THE PANE, of a given
	 * Coord. This is the location on the pane that the item should be drawn. This
	 * gets the Y location of the CENTER of a Coordinate.
	 */
	public int getPaneYCoord() {
		return (int) ((_y - 1) * _config.getRowHeight() + (_config.getRowHeight() / 2));
	}

	/**
	 * Uses the Game Configuration to get the x location ON THE PANE, of a given
	 * Coord. This is the location on the pane that the item should be drawn. This
	 * gets the X location of the TOP LEFT CORNER of a Coordinate.
	 */
	public int getPaneXTopCoord() {
		return (int) ((_x - 1) * _config.getColWidth());
	}

	/**
	 * Uses the Game Configuration to get the y location ON THE PANE, of a given
	 * Coord. This is the location on the pane that the item should be drawn. This
	 * gets the Y location of the TOP LEFT CORNER of a Coordinate.
	 */
	public int getPaneYLeftCoord() {
		return (int) ((_y - 1) * _config.getRowHeight());
	}

	/**
	 * Accessor method for the GameConfig of this Coordinate.
	 */
	public GameConfig getConfig() {
		return _config;
	}

}
