package Cartoon;

/**
 * Abstract class that models a BodyPart on the Snake.
 *
 */
public abstract class BodyPart {

	protected Coord _coord;
	protected Direction _direction;
	protected Direction _lastDirection;

	/**
	 * Constructor method for a BodyPart. Takes in the coordinate of BodyPart, and
	 * the Direction it is traveling in.
	 */
	public BodyPart(Coord coord, Direction d) {
		_coord = coord;
		_direction = d;
		_lastDirection = d;
	}

	/**
	 * Abstract method to draw the BodyPart.
	 */
	public abstract void draw();

	/**
	 * Abstract method to update the BodyPart.
	 */
	public abstract void update(Direction d);

	/**
	 * Accessor method for the current Coordinate of this BodyPart.
	 */
	public Coord getCoord() {
		return _coord;
	}

	/**
	 * Accessor method for the current Direction of this BodyPart.
	 */
	public Direction getDirection() {
		return _direction;
	}

	/**
	 * Acessor method for the last Direction of this BodyPart.
	 */
	public Direction getLastDirection() {
		return _lastDirection;
	}

}
