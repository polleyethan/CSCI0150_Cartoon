package Cartoon;

import javafx.scene.layout.Pane;

/**
 * Abstract class that models the head of the Snake body. Is a subclass of
 * BodyPart and implements the Collideable interface. Subclasses include
 * CustomDwightHead and ImageHead
 *
 */
public abstract class BodyHead extends BodyPart implements Collideable {
	protected Pane _mainPane;

	/**
	 * Constructor for BodyHead class. Takes in the coordinate of BodyHead, the
	 * Direction it is traveling in, and the Pane it is located in.
	 */
	public BodyHead(Coord coord, Direction d, Pane mainPane) {
		super(coord, d);
		this._mainPane = mainPane;
	}

	/**
	 * Abstract method to draw the BodyHead.
	 */
	public abstract void draw();

	/**
	 * Updates the BodyHead on the screen. Takes in a new Direction, sets the
	 * _lastDirection to the current direction, updates the current direction, moves
	 * the BodyHead in the current direction, and then draws the BodyHead on the new
	 * Coord.
	 */
	public void update(Direction d) {
		this._lastDirection = this._direction;
		this._direction = d;
		this._coord.moveDirection(d);
		this.draw();
	}
}
