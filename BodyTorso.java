package Cartoon;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * Class modeling a torso BodyPart on the Snake. Extends BodyPart and implements
 * Collideable interface.
 *
 */
public class BodyTorso extends BodyPart implements Collideable {

	private Ellipse _body;

	/**
	 * Constructor for the BodyTorso class. Takes in the coordinate of BodyHead, the
	 * Direction it is traveling in, and the Pane it is located in. Initializes the
	 * _body Ellipse
	 */
	public BodyTorso(Coord coord, Direction d, Pane mainPane) {
		super(coord, d);

		_body = new Ellipse(coord.getConfig().getColWidth() / 2, coord.getConfig().getRowHeight() / 2);
		_body.setCenterX(_coord.getPaneXCoord());
		_body.setCenterY(_coord.getPaneYCoord());
		_body.setFill(Color.LIGHTGREEN);
		mainPane.getChildren().add(_body);
	}

	/**
	 * Draws this BodyTorso on the Current coordinate. Sets the X and Y locations on
	 * the pane.
	 */
	public void draw() {
		_body.setCenterX(_coord.getPaneXCoord());
		_body.setCenterY(_coord.getPaneYCoord());
		_body.setFill(Color.LIGHTGREEN);
	}

	/**
	 * Updates the BodyTorso on the screen. Takes in a new Direction, sets the
	 * _lastDirection to the current direction, updates the current direction, moves
	 * the BodyTorso in the new direction, and then draws the BodyTorso.
	 */
	public void update(Direction d) {
		_lastDirection = _direction;
		_direction = d;
		_coord.moveDirection(d);
		this.draw();
	}
}
