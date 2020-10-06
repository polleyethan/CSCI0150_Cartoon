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
 * Models the PaneOrganizer for a Regular game.
 *
 **/
public class RegGamePaneOrganizer {

	private BorderPane _root;
	private RegGame _game;
	private VBox _labelsPane;
	private HBox _mainPane;
	private Label _scoreLabel;
	private Label _scorePerMinLabel;
	private Label _timeLabel;
	private Label _distanceLabel;
	private Button _pauseButton;
	private Button _quitButton;
	private Button _returntoHomeButton;
	private Stage _stage;

	/**
	 * Constructor for the RegGamePaneOrganizer class. Initializes the root
	 * BorderPane, as well as the Labels and the RegGame Object.
	 */
	public RegGamePaneOrganizer(Stage stage, GameConfig config, Character character, Theme theme) {
		//initialize all instance variables
		_stage = stage;
		_root = new BorderPane();
		_mainPane = new HBox();
		_root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		_root.setFocusTraversable(false);
		_mainPane.setFocusTraversable(false);

		//create the RegGame object
		_game = new RegGame(this, config, character, theme);

		Pane gamePane = _game.getPane();

		//setup the labels pane
		this.setupLabels();

		_mainPane.getChildren().add(gamePane);
		_mainPane.setAlignment(Pos.CENTER);
		_root.setCenter(_mainPane);

	}

	/**
	 * Returns the root BorderPane
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

		//create score label
		Label scoreTitle = new Label("Score:");
		scoreTitle.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
		scoreTitle.setTextFill(Color.rgb(124, 252, 0));
		_scoreLabel = new Label("0");
		_scoreLabel.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
		_scoreLabel.setTextFill(Color.rgb(124, 252, 0));

		//create score per minute label
		Label scoreperminTitle = new Label("Score Per Minute:");
		scoreperminTitle.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
		scoreperminTitle.setTextFill(Color.rgb(124, 252, 0));
		_scorePerMinLabel = new Label("0.0");
		_scorePerMinLabel.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
		_scorePerMinLabel.setTextFill(Color.rgb(124, 252, 0));

		//create timer label
		Label timeTitle = new Label("Timer:");
		timeTitle.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
		timeTitle.setTextFill(Color.rgb(124, 252, 0));
		_timeLabel = new Label("0.0");
		_timeLabel.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
		_timeLabel.setTextFill(Color.rgb(124, 252, 0));

		//create distance label
		Label dist = new Label("Distance To Food:");
		dist.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
		dist.setTextFill(Color.rgb(124, 252, 0));
		_distanceLabel = new Label("0");
		_distanceLabel.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
		_distanceLabel.setTextFill(Color.rgb(124, 252, 0));

		//create pause button
		_pauseButton = new Button("Pause");
		_pauseButton.setOnAction(new ButtonHandler());
		_pauseButton.setFocusTraversable(false);
		_pauseButton.setPadding(new Insets(5, 20, 5, 20));
		_pauseButton.getStyleClass().add("menu-button");

		//create quit button
		_quitButton = new Button("Quit");
		_quitButton.setOnAction(new ButtonHandler());
		_quitButton.setFocusTraversable(false);
		_quitButton.setPadding(new Insets(5, 20, 5, 20));
		_quitButton.getStyleClass().add("menu-button");

		//create a back home button
		_returntoHomeButton = new Button("Return To Home");
		_returntoHomeButton.setOnAction(new ButtonHandler());
		_returntoHomeButton.setFocusTraversable(false);
		_returntoHomeButton.setPadding(new Insets(5, 20, 5, 20));
		_returntoHomeButton.getStyleClass().add("menu-button");

		//add everything to labels pane
		_labelsPane.setAlignment(Pos.CENTER);
		_labelsPane.setSpacing(7);
		_labelsPane.getChildren().addAll(scoreTitle, _scoreLabel, scoreperminTitle, _scorePerMinLabel, timeTitle,
				_timeLabel, dist, _distanceLabel, _pauseButton, _returntoHomeButton, _quitButton);

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
	 * Accessor method for the score label.
	 */
	public Label getScoreLbl() {
		return _scoreLabel;
	}

	/**
	 * Accessor method for the scorepermin label
	 */
	public Label getScorePerMinLbl() {
		return _scorePerMinLabel;
	}

	/**
	 * Accessor method for the timer label
	 */
	public Label getTimerLbl() {
		return _timeLabel;
	}

	/**
	 * Accessor method for the distance label
	 */
	public Label getDistanceLbl() {
		return _distanceLabel;
	}

	/**
	 * Handles all of the Button Actions on the Labels Pane.
	 *
	 */
	private class ButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (e.getSource() == _quitButton) {
				// If Quit Button is pressed, quit the game.
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
