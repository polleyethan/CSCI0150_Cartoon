package Cartoon;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * This class models a head of the snake which is displayed as an image on the
 * screen. It is a subclass of BodyHead.
 *
 */
public class ImageHead extends BodyHead {

	private ImageView _headView;

	/**
	 * Constructor for the ImageHead class. Calls super to the BodyHead class, and then creates a head using the given character.
	 */
	public ImageHead(Coord coord, Direction d, Pane mainPane, Character character) {
		super(coord, d, mainPane);
		this.createHead(character);
	}

	/**
	 * Creates the _headView ImageView object at the initial coord.
	 */
	public void createHead(Character character) {
		//Gets the file location for the head of the given character
		Image headImage = new Image(character.getFileLoc());
		_headView = new ImageView(headImage);
		//Scales and set coordinates for the heads imageView
		_headView.setFitHeight(_coord.getConfig().getRowHeight());
		_headView.setPreserveRatio(true);
		_headView.setX(_coord.getPaneXTopCoord());
		_headView.setY(_coord.getPaneYLeftCoord());
		//Adds headview to the mainpane
		_mainPane.getChildren().add(_headView);

	}

	/**
	 * Redraws the Image at the new coord.
	 */
	public void draw() {
		// ImageView.setX and .setY set the X and Y coordinate of the Top Left corner of
		// the image to the X and Y coordinates given. That is why I use
		// getPaneXTopCoord instead of getPaneXCoord
		_headView.setX(_coord.getPaneXTopCoord());
		_headView.setY(_coord.getPaneYLeftCoord());

	}

}
