package Cartoon;

/**
 * Enumeration for the Directions a BodyPart can be traveling in.
 *
 */
public enum Direction {
	UP, DOWN, LEFT, RIGHT;

	private Direction _opposite;

	//Static implementations of the opposite instance variable
	static {
		UP._opposite = DOWN;
		DOWN._opposite = UP;
		LEFT._opposite = RIGHT;
		RIGHT._opposite = LEFT;
	}

	/**
	 * Returns the Direction opposite of this direction.
	 */
	public Direction opposite() {
		return _opposite;
	}
}
