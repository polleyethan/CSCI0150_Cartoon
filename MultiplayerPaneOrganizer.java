package Cartoon;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Models the PaneOrganizer for a Multiplayer game.
 *
 */
public class MultiplayerPaneOrganizer {

	private BorderPane _root;
	private MultiplayerGame _game;
	private VBox _labelsPane;
	private HBox _mainPane;
	private Label _user1ScoreLabel;
	private Label _user2ScoreLabel;
	private Label _timeLabel;
	private Button _pauseButton;
	private Button _quitButton;
	private Button _returntoHomeButton;
	private Stage _stage;

	/**
	 * Constructor for the MultiplayerPaneOrganizer class. Initializes the root
	 * BorderPane, as well as the Labels and the MultiplayerGame Object.
	 */
	public MultiplayerPaneOrganizer(Stage stage, GameConfig config, Character character1, Character character2,
			Theme theme) {
		//initialize all instance variables
		_stage = stage;
		_root = new BorderPane();
		_mainPane = new HBox();
		_root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		_root.setFocusTraversable(false);
		_mainPane.setFocusTraversable(false);
		
		//create a new multiplayer game instance
		_game = new MultiplayerGame(this, config, character1, character2, theme);

		Pane gamePane = _game.getPane();

		//setup the labels on the screen
		this.setupLabels();

		//add the game and labels to the screen
		_mainPane.getChildren().add(gamePane);
		_mainPane.setAlignment(Pos.CENTER);
		_root.setCenter(_mainPane);

	}

	/**
	 * Accessor method for the root BorderPane.
	 */
	public BorderPane getRoot() {
		return _root;
	}

	/**
	 * Sets up the Labels and buttons in the side VBox. This VBox is on the left
	 * side of the BorderPane.
	 */
	private void setupLabels() {
		_labelsPane = new VBox();
		
		//Creates a label showing the timer
		Label timeTitle = new Label("Time Remaining:");
		timeTitle.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
		timeTitle.setTextFill(Color.rgb(124, 252, 0));
		_timeLabel = new Label("60.0");
		_timeLabel.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
		_timeLabel.setTextFill(Color.rgb(124, 252, 0));

		//creates a label showing user 1's score
		Label scoreuser1Title = new Label("Player 1 Score:");
		scoreuser1Title.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
		scoreuser1Title.setTextFill(Color.rgb(124, 252, 0));
		_user1ScoreLabel = new Label("0");
		_user1ScoreLabel.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
		_user1ScoreLabel.setTextFill(Color.rgb(124, 252, 0));

		//creates a label showing user 2s score
		Label scoreuser2Title = new Label("Player 2 Score:");
		scoreuser2Title.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
		scoreuser2Title.setTextFill(Color.rgb(124, 252, 0));
		_user2ScoreLabel = new Label("0");
		_user2ScoreLabel.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
		_user2ScoreLabel.setTextFill(Color.rgb(124, 252, 0));

		//create pause game button
		_pauseButton = new Button("Pause");
		_pauseButton.setOnAction(new ButtonHandler());
		_pauseButton.setFocusTraversable(false);
		_pauseButton.setPadding(new Insets(5, 20, 5, 20));
		_pauseButton.getStyleClass().add("menu-button");

		//create quit game button
		_quitButton = new Button("Quit");
		_quitButton.setOnAction(new ButtonHandler());
		_quitButton.setFocusTraversable(false);
		_quitButton.setPadding(new Insets(5, 20, 5, 20));
		_quitButton.getStyleClass().add("menu-button");

		//create a return to home button
		_returntoHomeButton = new Button("Return To Home");
		_returntoHomeButton.setOnAction(new ButtonHandler());
		_returntoHomeButton.setFocusTraversable(false);
		_returntoHomeButton.setPadding(new Insets(5, 20, 5, 20));
		_returntoHomeButton.getStyleClass().add("menu-button");

		//add all components to the labels pane
		_labelsPane.setAlignment(Pos.CENTER);
		_labelsPane.setSpacing(7);
		_labelsPane.getChildren().addAll(timeTitle, _timeLabel, scoreuser1Title, _user1ScoreLabel, scoreuser2Title,
				_user2ScoreLabel, _pauseButton, _returntoHomeButton, _quitButton);

		_labelsPane.setFocusTraversable(false);
		_mainPane.getChildren().add(_labelsPane);

	}

	/**
	 * adds actions from the ButtonHandler in this class to buttons in the MultiPlayerGame object
	 */
	public void addActionToButton(Button btn) {
		btn.setOnAction(new ButtonHandler());
	}

	/**
	 * Accessor method for the user1ScoreLabel
	 */
	public Label getuser1ScoreLbl() {
		return _user1ScoreLabel;
	}

	/**
	 * Accessor method for the user2ScoreLabel
	 */
	public Label getuser2ScoreLbl() {
		return _user2ScoreLabel;
	}

	/**
	 * Accessor method for the timeLabel
	 */
	public Label getTimerLbl() {
		return _timeLabel;
	}

	/**
	 * Handles all of the Button Actions on the Labels Pane.
	 *
	 */
	private class ButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (e.getSource() == _quitButton) {
				// if Quit button is pressed, quit the app.
				System.exit(0);
			} else if (e.getSource() == _pauseButton) {
				// If the pause button is pressed, pause the game and set the label to say
				// "Play". If the game is already paused, make it play and set the label to say
				// "Pause"
				if (_game.isPaused()) {
					_game.timelinePlay();
					_pauseButton.setText("Pause");
				} else {
					_game.timelinePause();
					_pauseButton.setText("Play");
				}
			} else {
				// If the return home buttons is pressed, initialize a new MenuPaneOrganizer,
				// and set the scene to the root for the MenuPaneOrganizer.
				MenuPaneOrganizer menuorganizer = new MenuPaneOrganizer(_stage);
				Scene menuscene = new Scene(menuorganizer.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
				menuscene.getStylesheets().add("file:./Cartoon/resources/styles.css");
				_stage.setScene(menuscene);

			}

			e.consume();
		}
	}

}