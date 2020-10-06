package Cartoon;

/**
 * Enumeration for all of the Difficulty levels available in the game. Takes in
 * the speed at which the timeline will update, and thus the speed at which the
 * Snakes will move.
 *
 */
public enum Difficulty {
	VERYEASY(1), EASY(.5), MEDIUM(.1), HARD(.05), IMPOSSIBLE(.01);

	private double _speed;

	/**
	 * Constructor for the difficulty class. Takes in the speed.
	 */
	Difficulty(double n) {
		_speed = n;
	}

	/**
	 * Accessor method for the speed of this difficulty.
	 */
	public double getSpeed() {
		return _speed;
	}

}
