package Cartoon;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * Models a regular game.
 *
 */
public class RegGame {

	private Pane _mainPane;
	private Snake _snake;
	private Food _food;
	private Timeline _timeline;
	private RegGamePaneOrganizer _organizer;
	private int _score;
	private double _timer;
	private double _speed;
	private Boolean _isPaused;
	private Boolean _gameOver;
	private GameConfig _config;

	/**
	 * Constructor for the RegGame class. Initializes the Pane that the game will be
	 * in, the Snake, the food, the score and time, and adds an event handler to the
	 * games Pane.
	 */
	public RegGame(RegGamePaneOrganizer organizer, GameConfig config, Character character, Theme theme) {
		_mainPane = new Pane();
		// Set the pane to the correct size
		_mainPane.setPrefHeight(Constants.PANE_HEIGHT);
		_mainPane.setMinHeight(Constants.PANE_HEIGHT);
		_mainPane.setMaxHeight(Constants.PANE_HEIGHT);
		_mainPane.setPrefWidth(Constants.PANE_WIDTH);
		_mainPane.setMinWidth(Constants.PANE_WIDTH);
		_mainPane.setMaxWidth(Constants.PANE_WIDTH);

		// initializing the instance variables for this game
		_config = config;

		_gameOver = false;
		_speed = _config.getTimelineDelay();

		_organizer = organizer;

		_timer = 0;
		_score = 0;

		// draw the grid for the game
		this.drawGrid();

		// create the timeline for the game
		this.createTimeline();

		// Ensure the root pane has focus
		_mainPane.requestFocus();
		_mainPane.setFocusTraversable(true);
		_mainPane.addEventHandler(KeyEvent.KEY_PRESSED, new KeyHandler());

		// initialize the snake object
		_snake = new Snake(_mainPane, _config, character);

		// initialize the food object
		_food = new Food(_mainPane, getRandomCoord(_config), theme);

	}

	/**
	 * Draws the grid that the game is to be played on.
	 */
	private void drawGrid() {
		// draw gridlines
		for (int x = 0; x < _config.getNumRow() + 1; x++) {
			Line line = new Line(0, x * _config.getRowHeight(), _config.getNumCol() * _config.getColWidth(),
					x * _config.getRowHeight());
			line.setStroke(Color.rgb(124, 252, 0));
			_mainPane.getChildren().add(line);
		}
		for (int y = 0; y < _config.getNumCol() + 1; y++) {
			Line line = new Line(y * _config.getColWidth(), 0, y * _config.getColWidth(),
					_config.getNumRow() * _config.getRowHeight());
			line.setStroke(Color.rgb(124, 252, 0));
			_mainPane.getChildren().add(line);
		}
	}

	/**
	 * Accessor method for the root pane.
	 */
	public Pane getPane() {
		return _mainPane;
	}

	/**
	 * Updates the game based on the timeline. Called everytime the timeline
	 * updates. Increments the timer up and updates the snake and labels. Checks for
	 * collisions between Collideable objects.
	 */
	public void update() {
		// increment the timer by the amount specified in the timeline delay
		_timer += _config.getTimelineDelay();

		// update the snake
		_snake.update();

		// check for collisions
		this.checkCollisions();

		// update the labels
		this.updateLabels();
	}

	/**
	 * Accessor method for the games Snake.
	 */
	public Snake getSnake() {
		return _snake;
	}

	/**
	 * Updates the labels on the RegGamePaneOrganizer to the correct score,
	 * distancetofood, scorepermin and time.
	 */
	private void updateLabels() {
		// update the score label on the PaneOrganizer
		_organizer.getScoreLbl().setText(Integer.valueOf(_score).toString());

		// calculate the score per minute
		int scorepermin = (int) ((_score * 60) / (_timer));

		// update the scorepermin label on the PaneOrganizer
		_organizer.getScorePerMinLbl().setText(Integer.valueOf(scorepermin).toString());

		// update the timer label on the PaneOrganizer
		_organizer.getTimerLbl().setText(Integer.valueOf((int) _timer).toString());

		// calculate the distance to the food
		int xdistanceToFood = Math.abs(_snake.getHead().getCoord().getX() - _food.getCoord().getX());
		int ydistanceToFood = Math.abs(_snake.getHead().getCoord().getY() - _food.getCoord().getY());
		int distanceToFood = xdistanceToFood + ydistanceToFood;

		// update the distancetofood label on the PaneOrganizer
		_organizer.getDistanceLbl().setText(Integer.valueOf(distanceToFood).toString());
	}

	/**
	 * Checks if there were any collisions between BodyHeads and their BodyParts, or
	 * between a BodyHead and Food. If a snakes BodyHead collides with its OWN
	 * BodyPart, the game is automatically over. If a BodyHead collides with the
	 * Food object, one BodyTorso is added to that snake and the score of that user
	 * is increased by 1. Additionally, the Food object is moved to a new, random,
	 * location.
	 */
	public void checkCollisions() {
		BodyHead head = _snake.getHead();
		// check for collisions between the snakes head and any of the snakes BodyTorsos
		for (int i = 1; i < _snake.getBodyParts().size(); i++) {
			if (_snake.getBodyParts().get(i).getCoord().checkCollide(head.getCoord())) {
				this.gameOver();
			}
		}
		// check for collisions between the snakes head and the food object
		if (_snake.getHead().getCoord().checkCollide(_food.getCoord())) {
			_food.getEaten(getRandomCoord(_config));
			_snake.grow();
			_score++;

		}
	}

	/**
	 * Static method to get a random coordinate on the grid. Used to get a new
	 * location for the Food Object.
	 */
	public static Coord getRandomCoord(GameConfig config) {
		int x = (int) (Math.random() * config.getNumCol()) + 1;
		int y = (int) (Math.random() * config.getNumRow()) + 1;
		return new Coord(x, y, config);
	}

	/**
	 * Called whenever the Game is over. Stops the timeline and displays the
	 * gameover pane. Gameoverpane shows label describing the score.
	 */
	public void gameOver() {
		_timeline.stop();
		_gameOver = true;

		// create labels for the game over pane
		Label gover = new Label("Game Over!");
		gover.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 20));
		gover.setTextFill(Color.rgb(124, 252, 0));
		Label gover2 = new Label("You finished with a score of: " + Integer.valueOf(_score).toString());
		gover2.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 20));
		gover2.setTextFill(Color.rgb(124, 252, 0));

		// create a button to go back to the home screen
		Button back = new Button("Back To Home Screen");
		back.setFocusTraversable(false);
		back.setPadding(new Insets(5, 20, 5, 20));
		back.getStyleClass().add("menu-button");
		_organizer.addActionToButton(back);

		VBox gameOverPane = new VBox(gover, gover2, back);
		gameOverPane.setSpacing(6);
		gameOverPane.setAlignment(Pos.CENTER);
		_organizer.getRoot().getChildren().clear();
		_organizer.getRoot().setCenter(gameOverPane);
	}

	/**
	 * Creates the timeline and starts it.
	 */
	public void createTimeline() {
		_isPaused = false;
		KeyFrame kf = new KeyFrame(Duration.seconds(_speed), new TimeHandler());
		_timeline = new Timeline(kf);
		_timeline.setCycleCount(Animation.INDEFINITE);
		_timeline.play();
	}

	/**
	 * Pauses the timeline.
	 */
	public void timelinePause() {
		_isPaused = true;
		_timeline.pause();
	}

	/**
	 * Plays the timeline.
	 */
	public void timelinePlay() {
		_isPaused = false;
		_timeline.play();
	}

	/**
	 * Returns whether or not the game is paused.
	 */
	public Boolean isPaused() {
		return _isPaused;
	}

	/**
	 * Handles every update to the timeline. If the Game is not yet over, update the
	 * game.
	 *
	 */
	private class TimeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (!_gameOver) {
				update();

			}
		}
	}

	/**
	 * Handles all KeyEvents. User uses the keypad to control the direction that the
	 * snake is moving in.
	 *
	 */
	private class KeyHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent e) {
			KeyCode keyPressed = e.getCode();
			if (keyPressed == KeyCode.UP) {
				getSnake().changeDirection(Direction.UP);
			} else if (keyPressed == KeyCode.DOWN) {
				getSnake().changeDirection(Direction.DOWN);
			} else if (keyPressed == KeyCode.LEFT) {
				getSnake().changeDirection(Direction.LEFT);
			} else if (keyPressed == KeyCode.RIGHT) {
				getSnake().changeDirection(Direction.RIGHT);
			}
			e.consume();
		}
	}

}
