package Cartoon;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.SVGPath;

/**
 * This class models a custom composite shape, which is a subclass of the
 * BodyHead class. It draws my version of Dwight Schrute as the head of the
 * Snake.
 *
 */
public class CustomDwightHead extends BodyHead {

	private Group _dwight;
	private Ellipse _faceBack;
	private Ellipse _leftEyeOuter;
	private Ellipse _rightEyeOuter;
	private Ellipse _leftEyeInner;
	private Ellipse _rightEyeInner;
	private Region _glassesRegion;
	private Region _hairRegion;
	private Arc _leftear;
	private Arc _rightear;

	/**
	 * Constructor for the CustomDwightHead class. Calls super on the BodyHead class, then creates the composite shape. Finally, adds the Head to the MainPane
	 */
	public CustomDwightHead(Coord coord, Direction d, Pane mainPane) {
		super(coord, d, mainPane);
		this.createDwight();
		mainPane.getChildren().add(_dwight);

	}

	/**
	 * Creates all of the components that make up the composite shape. Draws
	 * Ellipses for the Background of the head, the Eyes, the Black on the Eyes.
	 * Draws Arcs for the ears. Draws SVGS for the glasses and the hair.
	 */
	public void createDwight() {

		_dwight = new Group();
		//Sets the centerX and centerY coordinates for the head ellipse to the appropriate values
		double centerX = _coord.getPaneXCoord();
		double centerY = _coord.getPaneYCoord();
		
		//Sets the radii for the ellipse to a scaled value
		double faceRY = _coord.getConfig().getRowHeight()/2;
		double faceRX = faceRY * .8;

		// Create face ellipse
		_faceBack = new Ellipse(centerX, centerY, faceRX, faceRY);
		_faceBack.setFill(Color.rgb(241, 196, 192));
		_faceBack.setStroke(Color.BLACK);
		_faceBack.setStrokeWidth(faceRX / 30);
		
		//creates eye base ellipses
		_leftEyeOuter = new Ellipse(centerX - (6 * faceRX / 16), centerY, (faceRX / 5), (faceRY / 10));
		_rightEyeOuter = new Ellipse(centerX + (6 * faceRX / 16), centerY, (faceRX / 5), (faceRY / 10));
		_leftEyeOuter.setFill(Color.WHITE);
		_rightEyeOuter.setFill(Color.WHITE);
		
		//Creates eye inner ellipses
		_leftEyeInner = new Ellipse(centerX - (6 * faceRX / 16), centerY, (faceRX / 12), (faceRY / 14));
		_rightEyeInner = new Ellipse(centerX + (6 * faceRX / 16), centerY, (faceRX / 12), (faceRY / 14));
		_leftEyeInner.setFill(Color.BLACK);
		_rightEyeInner.setFill(Color.BLACK);

		//Uses an SVG object for Dwights Glasses
		SVGPath glasses = new SVGPath();
		glasses.setContent(
				"M92.126,328.075c79.289-0.024,105.305-77.011,116.414-109.884l0.613-1.806c2.09-6.143,4.182-11.415,6.24-15.61\n"
						+ "			c1.522-3.086,5.057-4.188,7.858-4.188c2.793,0,6.332,1.102,7.851,4.16c2.063,4.224,4.155,9.483,6.248,15.639l0.612,1.806\n"
						+ "			c11.108,32.873,37.121,109.859,116.416,109.884l0.024-4.088v4.088c0,0,0.004,0,0.02,0c27.676,0,49.94-11.081,66.146-32.929\n"
						+ "			c26.489-35.694,28.758-91.667,23.896-107.688c-11.125-36.684-52.822-54.882-76.686-62.466\n"
						+ "			c-13.678-4.348-40.872-6.556-80.807-6.556c-33.262,0-63.642,1.587-63.511,1.603c-0.307-0.016-30.677-1.603-63.933-1.603\n"
						+ "			c-39.934,0-67.128,2.208-80.804,6.556c-23.866,7.584-65.567,25.782-76.684,62.466c-4.857,16.021-2.579,71.977,23.9,107.679\n"
						+ "			C42.146,316.994,64.409,328.075,92.126,328.075z M252.538,166.582c0.101-0.311,10.319-30.85,68.038-30.85\n"
						+ "			c3.759,0,7.69,0.126,11.846,0.399c65.165,4.296,91.658,38.037,94.54,65.624c2.02,19.317-0.457,62.835-24.285,89.292\n"
						+ "			c-13.148,14.57-31.158,21.973-53.596,21.973c-37.842-0.017-62.219-27.516-76.005-50.578\n"
						+ "			C250.297,224.295,245.453,179.915,252.538,166.582z M19.54,201.755c2.883-27.587,29.378-61.328,94.547-65.624\n"
						+ "			c4.135-0.272,8.083-0.399,11.836-0.399c57.276,0,67.637,29.619,68.347,31.566c6.78,12.616,1.931,56.996-20.847,95.143\n"
						+ "			c-13.784,23.07-38.151,50.569-76.053,50.582c-22.389,0-40.407-7.39-53.547-21.969C19.989,264.598,17.516,221.072,19.54,201.755z");

		//Scales and sets coordinates for glasses SVG
		_glassesRegion = new Region();
		_glassesRegion.setShape(glasses);
		_glassesRegion.setMinSize(8 * faceRX / 5, faceRY / 2);
		_glassesRegion.setMaxSize(8 * faceRX / 5, faceRY / 2);
		_glassesRegion.setStyle("-fx-background-color: black;");
		_glassesRegion.setTranslateX(centerX - (4 * faceRX / 5));
		_glassesRegion.setTranslateY(centerY - (faceRY / 7));

		
		// creates an arc to represent the left ear
		_leftear = new Arc();
		_leftear.setCenterX(centerX - faceRX);
		_leftear.setCenterY(centerY);
		_leftear.setFill(Color.rgb(241, 196, 192));
		_leftear.setStartAngle(45);
		_leftear.setStroke(Color.BLACK);
		_leftear.setStrokeWidth(faceRX / 30);
		_leftear.setLength(270);
		_leftear.setRadiusX(faceRX / 8);
		_leftear.setRadiusY((faceRY / 4));
		_leftear.setType(ArcType.OPEN);
		_leftear.setRotate(-10);

		// creates an arc to represent the right ear
		_rightear = new Arc();
		_rightear.setCenterX(centerX + faceRX);
		_rightear.setCenterY(centerY);
		_rightear.setFill(Color.rgb(241, 196, 192));
		_rightear.setStartAngle(135);
		_rightear.setStroke(Color.BLACK);
		_rightear.setStrokeWidth(faceRX / 30);
		_rightear.setLength(-270);
		_rightear.setRadiusX(faceRX / 8);
		_rightear.setRadiusY((faceRY / 4));
		_rightear.setType(ArcType.OPEN);
		_rightear.setRotate(10);

		//Creates an svg object to represent Dwights hair
		SVGPath hair = new SVGPath();
		hair.setContent(
				"M358.533,340.917c0,0-12.284-162.581-92.973-166.667c-80.718-4.099-182.473-21.054-192.999-33.922\n"
						+ "		C60.867,163.729,50.93,200.57,41.57,234.479c-9.365,33.91-8.775,121.641-8.775,121.641S-1.133,229.798,0.029,185.343\n"
						+ "		c1.162-44.431,23.984-95.904,64.918-106.425c36.263-67.844,208.214-88.897,276.63,7.602\n"
						+ "		C410.007,183.007,358.533,340.917,358.533,340.917z");

		//Scales and sets coordinates for Hair svg object
		_hairRegion = new Region();
		_hairRegion.setShape(hair);
		_hairRegion.setMinSize(9 * faceRX / 4, 6 * faceRY / 4);
		_hairRegion.setMaxSize(9 * faceRX / 4, 6 * faceRY / 4);
		_hairRegion.setStyle("-fx-background-color: #351E10;");
		_hairRegion.setTranslateX(centerX - (16 * faceRX / 15));
		_hairRegion.setTranslateY(centerY - (5 * faceRY / 4));

		//Adds all the face components to the _dwight group
		_dwight.getChildren().addAll(_leftear, _rightear, _faceBack, _leftEyeOuter, _rightEyeOuter, _leftEyeInner,
				_rightEyeInner, _glassesRegion, _hairRegion);

	}

	/**
	 * Redraws the Composite shape at the new coordinates.
	 */
	public void draw() {

		//resets the coordinates to the updated coordinates
		double centerX = _coord.getPaneXCoord();
		double centerY = _coord.getPaneYCoord();
		double faceRY = _coord.getConfig().getRowHeight()/2;
		double faceRX = faceRY * .8;

		_faceBack.setCenterX(centerX);
		_faceBack.setCenterY(centerY);
		_leftEyeOuter.setCenterX(centerX - (6 * faceRX / 16));
		_leftEyeOuter.setCenterY(centerY);
		_rightEyeOuter.setCenterX(centerX + (6 * faceRX / 16));
		_rightEyeOuter.setCenterY(centerY);
		_leftEyeInner.setCenterX(centerX - (6 * faceRX / 16));
		_leftEyeInner.setCenterY(centerY);

		_rightEyeInner.setCenterX(centerX + (6 * faceRX / 16));
		_rightEyeInner.setCenterY(centerY);

		_glassesRegion.setTranslateX(centerX - (4 * faceRX / 5));
		_glassesRegion.setTranslateY(centerY - (faceRY / 7));

		_leftear.setCenterX(centerX - faceRX);
		_leftear.setCenterY(centerY);

		_rightear.setCenterX(centerX + faceRX);
		_rightear.setCenterY(centerY);

		_hairRegion.setTranslateX(centerX - (16 * faceRX / 15));
		_hairRegion.setTranslateY(centerY - (5 * faceRY / 4));

	}

}
