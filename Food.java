package Cartoon;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * This class models a food object in the Game. The Food object is what the snakes are supposed to be trying to eat
 *
 */
public class Food implements Collideable {

	private Coord _coord;
	private ImageView _foodView;
	private Pane _mainPane;

	/**
	 * Constructor for the Food class. Initializes the ImageView that shows the Food
	 * on the initial Coordinate.
	 */
	public Food(Pane mainPane, Coord coord, Theme theme) {
		_coord = coord;
		//Imageview for the food image
		Image foodImage = new Image(theme.getFoodImgSrc());
		_foodView = new ImageView(foodImage);
		
		//initializes the locations and size of food image
		_foodView.setFitHeight(_coord.getConfig().getRowHeight());
		_foodView.setFitWidth(_coord.getConfig().getColWidth());
		_foodView.setX(_coord.getPaneXTopCoord());
		_foodView.setY(_coord.getPaneYLeftCoord());
		
		_mainPane = mainPane;
		//Adds the food object to the mainPane
		_mainPane.getChildren().add(_foodView);
	}

	/**
	 * Accessor method for the coordinate of this Food object.
	 */
	public Coord getCoord() {
		return _coord;
	}

	/**
	 * Called whenever the Snake head eats this Food object. Updates the Coordinate
	 * of this Food object to the inputted Coordinate.
	 */
	public void getEaten(Coord newCoord) {
		_coord = newCoord;
		_foodView.setX(_coord.getPaneXTopCoord());
		_foodView.setY(_coord.getPaneYLeftCoord());

	}

}
