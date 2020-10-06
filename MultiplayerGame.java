package Cartoon;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Models a multiplayer Game.
 *
 */
public class MultiplayerGame {

	private Pane _gamePane;
	private Pane _root;
	private Snake _user1Snake;
	private Snake _user2Snake;
	private Food _food;
	private Timeline _timeline;
	private MultiplayerPaneOrganizer _organizer;
	private int _user1Score;
	private int _user2Score;
	private double _timer;
	private double _speed;
	private Boolean _isPaused;
	private Boolean _gameOver;
	private GameConfig _config;
	private Character _character1;
	private Character _character2;
	private Label _countdownLabel;
	private Theme _theme;

	/**
	 * Constructor for the MultiplayerGame class. Initializes all game related
	 * objects.
	 */
	public MultiplayerGame(MultiplayerPaneOrganizer organizer, GameConfig config, Character character1,
			Character character2, Theme theme) {
		_root = new Pane();
		// Set the pane to the correct size
		_root.setPrefHeight(Constants.PANE_HEIGHT);
		_root.setMinHeight(Constants.PANE_HEIGHT);
		_root.setMaxHeight(Constants.PANE_HEIGHT);
		_root.setPrefWidth(Constants.PANE_WIDTH);
		_root.setMinWidth(Constants.PANE_WIDTH);
		_root.setMaxWidth(Constants.PANE_WIDTH);

		// initializing the instance variables for this game
		_config = config;

		_gameOver = false;
		_speed = _config.getTimelineDelay();
		_theme = theme;

		_organizer = organizer;

		_character1 = character1;
		_character2 = character2;

		_timer = 60.0;
		_user1Score = 0;
		_user2Score = 0;

		// Ensure the root pane has focus
		_root.requestFocus();
		_root.setFocusTraversable(true);

		// Add an event handler to the root for whenever a key is pressed
		_root.addEventHandler(KeyEvent.KEY_PRESSED, new KeyHandler());

		// Creates the Pane that the game will be stored in.
		this.createGamePane();

		// Starts the animations that precede the game. At the end of animations, the
		// game is started.
		this.startCountdown();

	}

	/**
	 * Creates the Pane that the game will be displayed in and draws the gridlines
	 * to this pane.
	 */
	private void createGamePane() {
		_gamePane = new Pane();
		_gamePane.setPrefHeight(Constants.PANE_HEIGHT);
		_gamePane.setMinHeight(Constants.PANE_HEIGHT);
		_gamePane.setMaxHeight(Constants.PANE_HEIGHT);
		_gamePane.setPrefWidth(Constants.PANE_WIDTH);
		_gamePane.setMinWidth(Constants.PANE_WIDTH);
		_gamePane.setMaxWidth(Constants.PANE_WIDTH);

		// Draw gridlines
		for (int x = 0; x < _config.getNumRow() + 1; x++) {
			Line line = new Line(0, x * _config.getRowHeight(), _config.getNumCol() * _config.getColWidth(),
					x * _config.getRowHeight());
			line.setStroke(Color.rgb(124, 252, 0));
			_gamePane.getChildren().add(line);
		}
		for (int y = 0; y < _config.getNumCol() + 1; y++) {
			Line line = new Line(y * _config.getColWidth(), 0, y * _config.getColWidth(),
					_config.getNumRow() * _config.getRowHeight());
			line.setStroke(Color.rgb(124, 252, 0));
			_gamePane.getChildren().add(line);
		}
	}

	/**
	 * Accessor method for the root pane
	 */
	public Pane getPane() {
		return _root;
	}

	/**
	 * Updates the game based on the timeline. Called everytime the timeline
	 * updates. Increments the timer down and updates the snake and labels. Checks
	 * for collisions between Collideable objects.
	 */
	public void update() {
		// Timer decreases by amount specified by the configuration of this game
		_timer -= _config.getTimelineDelay();

		// If the timer is greater than 0 (i.e the game is still occurring), update both
		// snakes, check for collisions, and then update the labels
		if (_timer > 0.0) {
			_user1Snake.update();
			_user2Snake.update();
			this.checkCollisions();
			this.updateLabels();
			// Otherwise, if the timeline is less than or equal to 0, end the game
		} else {
			// If User1's score is greater than User 2's, call game over with user 1 being
			// the winner and a string saying how much they won by
			if (_user1Score > _user2Score) {
				this.gameOver(1, _character1, _character2, false, "Times up! " + _character1.toString() + " collected "
						+ Integer.valueOf(_user1Score - _user2Score).toString() + " more " + _theme.getFoodName()
						+ " than " + _character2.toString() + " did! " + _character1.toString() + " wins!");
				// If both users scores are equal, set the tie parameter to true, and pass in a
				// string describing the tie
			} else if (_user1Score == _user2Score) {
				this.gameOver(1, _character1, _character2, true, "Both players collected "
						+ Integer.valueOf(_user1Score).toString() + " " + _theme.getFoodName() + "!! It is a tie!!");
				// Otherwise, If User2's score is greater than User 1's, call game over with
				// user 2 being the winner and a string saying how much they won by
			} else {
				this.gameOver(2, _character2, _character1, false, "Times up! " + _character2.toString() + " collected "
						+ Integer.valueOf(_user2Score - _user1Score).toString() + " more " + _theme.getFoodName()
						+ " than " + _character1.toString() + " did!" + _character2.toString() + " wins!");
			}
		}

	}

	/**
	 * Starts the animations that preceed the game.
	 */
	public void startCountdown() {
		// initializes a label that will display the countdown
		_countdownLabel = new Label("3");
		_countdownLabel.setLayoutX(Constants.PANE_WIDTH / 2);
		_countdownLabel.setLayoutY(Constants.PANE_HEIGHT / 2);
		_countdownLabel.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 60));
		_countdownLabel.setTextFill(Color.rgb(124, 252, 0));

		_root.getChildren().add(_countdownLabel);

		// creates the first fade transition
		FadeTransition transition1 = new FadeTransition(Duration.millis(1000), _countdownLabel);
		transition1.setFromValue(1);
		transition1.setToValue(0);
		transition1.setCycleCount(1);

		// creates the second fade transition
		FadeTransition transition2 = new FadeTransition(Duration.millis(1000), _countdownLabel);
		transition2.setFromValue(1);
		transition2.setToValue(0);
		transition2.setCycleCount(1);

		// creates the third and final fade transition
		FadeTransition transition3 = new FadeTransition(Duration.millis(1000), _countdownLabel);
		transition3.setFromValue(1);
		transition3.setToValue(0);
		transition3.setCycleCount(1);

		// after first fade transition is done, set the countdown label text to 2 and
		// start the second fade transition
		transition1.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				_countdownLabel.setText("2");
				transition2.play();
			}
		});

		// after second fade transition is done, set the countdown label text to 1 and
		// start the third fade transition
		transition2.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				_countdownLabel.setText("1");
				transition3.play();
			}
		});

		// after third fade transition is done, start the game
		transition3.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				startGame();
			}
		});

		transition1.play();

	}

	/**
	 * Officially starts the game. Creates both snakes and the food objects
	 */
	private void startGame() {
		// adds the gamePane to the root pane
		_root.getChildren().setAll(_gamePane);

		// create the timeline
		this.createTimeline();

		// Initialize both snake objects
		_user1Snake = new Snake(_gamePane, _config, _character1);
		_user2Snake = new Snake(_gamePane, _config, _character2);

		// initialize the food object
		_food = new Food(_gamePane, getRandomCoord(_config), _theme);

	}

	/**
	 * Updates the labels on the MultiplayerPaneOrganizer to the correct scores and
	 * time.
	 */
	private void updateLabels() {
		// Update the user score labels on the PaneOrganizer
		_organizer.getuser1ScoreLbl().setText(Integer.valueOf(_user1Score).toString());
		_organizer.getuser2ScoreLbl().setText(Integer.valueOf(_user2Score).toString());
		// Update the timer label on the PaneOrganizer
		_organizer.getTimerLbl().setText((Integer.valueOf((int) _timer)).toString());

	}

	/**
	 * Checks if there were any collisions between BodyHeads and their BodyParts, or
	 * between a BodyHead and Food. If a snakes BodyHead collides with its OWN
	 * BodyPart, that snake automatically loses the game and the other user wins. If
	 * a BodyHead collides with the Food object, one BodyTorso is added to that
	 * snake and the score of that user is increased by 1. Additionally, the Food
	 * object is moved to a new, random, location.
	 */
	public void checkCollisions() {
		BodyHead head1 = _user1Snake.getHead();
		// For each body part on Snake 1, check if it has collided with another bodypart
		// on snake 1
		for (int i = 1; i < _user1Snake.getBodyParts().size(); i++) {
			if (_user1Snake.getBodyParts().get(i).getCoord().checkCollide(head1.getCoord())) {
				this.gameOver(2, _character2, _character1, false, _character1.toString()
						+ " bumped into their own body!!! " + _character2.toString() + " wins by default!");
			}
		}
		// Check if the snake1 head collided with food
		if (_user1Snake.getHead().getCoord().checkCollide(_food.getCoord())) {
			_food.getEaten(getRandomCoord(_config));
			_user1Snake.grow();
			_user1Score++;

		}
		// For each body part on Snake 2, check if it has collided with another bodypart
		// on snake 2
		BodyHead head2 = _user2Snake.getHead();
		for (int i = 1; i < _user2Snake.getBodyParts().size(); i++) {
			if (_user2Snake.getBodyParts().get(i).getCoord().checkCollide(head2.getCoord())) {
				this.gameOver(1, _character1, _character2, false, _character2.toString()
						+ " bumped into their own body!!! " + _character1.toString() + " wins by default!");
			}
		}
		// Check if snake2 head collided with food
		if (_user2Snake.getHead().getCoord().checkCollide(_food.getCoord())) {
			_food.getEaten(getRandomCoord(_config));
			_user2Snake.grow();
			_user2Score++;

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
	 * Called when the game is over. The Winning and Losing characters are passed
	 * in, as well as whether or not the game was a tie. Shows a specific gif for
	 * each user depending on whether the user won or lost. Also shows text
	 * describing how/who won and lost.
	 */
	public void gameOver(int winusernum, Character winner, Character loser, Boolean tie, String endstring) {
		//stop the timeline
		_timeline.stop();
		
		//set the gameover to true, ensuring the timeline never runs again
		_gameOver = true;

		//initialize strings representing the winning and losing scores
		String winscore = Integer.valueOf(_user1Score).toString();
		String losescore = Integer.valueOf(_user2Score).toString();

		//create borderpane for the game over screen
		BorderPane gameOverPane = new BorderPane();

		//a label displayed at top of screen showing the description of how the game ended
		Label topLbl = new Label(endstring);
		topLbl.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 24));
		topLbl.setTextFill(Color.rgb(124, 252, 0));
		topLbl.setWrapText(true);
		topLbl.setTextAlignment(TextAlignment.CENTER);
		HBox top = new HBox(topLbl);
		top.setAlignment(Pos.CENTER);
		gameOverPane.setTop(top);
		
		//a Return home button to be displayed at bottom of screen
		Button returnHome = new Button("Return Home");
		_organizer.addActionToButton(returnHome);
		returnHome.setFocusTraversable(false);
		returnHome.setPadding(new Insets(5, 20, 5, 20));
		returnHome.getStyleClass().add("menu-button");
		HBox bottom = new HBox(returnHome);
		bottom.setAlignment(Pos.CENTER);
		gameOverPane.setBottom(bottom);

		if (winusernum == 2) {
			winscore = Integer.valueOf(_user2Score).toString();
			losescore = Integer.valueOf(_user1Score).toString();
		}

		if (tie) {
			//If a tie, set both user images to the characters winning file location
			ImageView winnerImg = new ImageView(new Image(winner.getWinFileLoc()));
			winnerImg.setPreserveRatio(true);
			winnerImg.setFitWidth(Constants.PANE_WIDTH / 2);

			//create a label to describe the outcome for user1
			Label winnerLbl = new Label(winner.toString() + " scored " + winscore + " points. It's a tie!");
			winnerLbl.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
			winnerLbl.setTextFill(Color.rgb(124, 252, 0));
			winnerLbl.setWrapText(true);
			winnerLbl.setTextAlignment(TextAlignment.CENTER);
			winnerLbl.setPrefWidth(Constants.PANE_WIDTH / 2);

			ImageView loserImg = new ImageView(new Image(loser.getLoseFileLoc()));
			loserImg.setFitWidth(Constants.PANE_WIDTH / 2);
			loserImg.setPreserveRatio(true);

			//create a label to describe the outcome for user2
			Label loserLbl = new Label(loser.toString() + " scored " + losescore + " points. It's a tie!");
			loserLbl.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
			loserLbl.setTextFill(Color.rgb(124, 252, 0));
			loserLbl.setWrapText(true);
			loserLbl.setTextAlignment(TextAlignment.CENTER);
			loserLbl.setPrefWidth(Constants.PANE_WIDTH / 2);

			VBox winnerBox = new VBox(winnerLbl, winnerImg);
			winnerBox.setAlignment(Pos.CENTER);

			VBox loserBox = new VBox(loserLbl, loserImg);
			loserBox.setAlignment(Pos.CENTER);

			HBox playerContainerBox = new HBox(winnerBox, loserBox);
			playerContainerBox.setAlignment(Pos.CENTER);

			gameOverPane.setCenter(playerContainerBox);
			_organizer.getRoot().setCenter(gameOverPane);
		} else {
			//otherwise set the winning user image to the image at the win file location
			ImageView winnerImg = new ImageView(new Image(winner.getWinFileLoc()));
			winnerImg.setPreserveRatio(true);
			winnerImg.setFitWidth(Constants.PANE_WIDTH / 2);

			//create a label to describe the outcome for the winner
			Label winnerLbl = new Label(winner.toString() + " scored " + winscore + " points. You Win!");
			winnerLbl.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
			winnerLbl.setTextFill(Color.rgb(124, 252, 0));
			winnerLbl.setWrapText(true);
			winnerLbl.setTextAlignment(TextAlignment.CENTER);
			winnerLbl.setPrefWidth(Constants.PANE_WIDTH / 2);

			// set the losing user image to the image at the lose file location
			ImageView loserImg = new ImageView(new Image(loser.getLoseFileLoc()));
			loserImg.setFitWidth(Constants.PANE_WIDTH / 2);
			loserImg.setPreserveRatio(true);

			//create a label to describe the outcome for the loser
			Label loserLbl = new Label(loser.toString() + " scored " + losescore + " points. You Lose!");
			loserLbl.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
			loserLbl.setTextFill(Color.rgb(124, 252, 0));
			loserLbl.setWrapText(true);
			loserLbl.setTextAlignment(TextAlignment.CENTER);
			loserLbl.setPrefWidth(Constants.PANE_WIDTH / 2);

			VBox winnerBox = new VBox(winnerLbl, winnerImg);
			winnerBox.setAlignment(Pos.CENTER);

			VBox loserBox = new VBox(loserLbl, loserImg);
			loserBox.setAlignment(Pos.CENTER);

			HBox playerContainerBox = new HBox(winnerBox, loserBox);
			playerContainerBox.setAlignment(Pos.CENTER);

			gameOverPane.setCenter(playerContainerBox);
			_organizer.getRoot().setCenter(gameOverPane);
		}

	}

	/**
	 * Creates the timeline that the game is being run on.
	 */
	public void createTimeline() {
		_isPaused = false;
		//creates a keyframe with the timehandler
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
	 * Resumes playing the timeline
	 */
	public void timelinePlay() {
		_isPaused = false;
		_timeline.play();
	}

	/**
	 * Returns whether or not the timeline is paused.
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
	 * Handles all KeyEvents. User 1 uses WASD (Up, Left, Down, Right) to control
	 * their snake, and the User 2 uses the keypad. Changes the direction of the the
	 * snake to the corresponding direction.
	 *
	 */
	private class KeyHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent e) {
			KeyCode keyPressed = e.getCode();
			if (keyPressed == KeyCode.UP) {
				_user2Snake.changeDirection(Direction.UP);
			} else if (keyPressed == KeyCode.DOWN) {
				_user2Snake.changeDirection(Direction.DOWN);
			} else if (keyPressed == KeyCode.LEFT) {
				_user2Snake.changeDirection(Direction.LEFT);
			} else if (keyPressed == KeyCode.RIGHT) {
				_user2Snake.changeDirection(Direction.RIGHT);
			} else if (keyPressed == KeyCode.W) {
				_user1Snake.changeDirection(Direction.UP);
			} else if (keyPressed == KeyCode.S) {
				_user1Snake.changeDirection(Direction.DOWN);
			} else if (keyPressed == KeyCode.A) {
				_user1Snake.changeDirection(Direction.LEFT);
			} else if (keyPressed == KeyCode.D) {
				_user1Snake.changeDirection(Direction.RIGHT);
			}
			e.consume();
		}
	}

}