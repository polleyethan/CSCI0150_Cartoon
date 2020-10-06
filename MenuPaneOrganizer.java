package Cartoon;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Models the PaneOrganizer for the menu screens. Allows the user to switch
 * between different themes, as well as the main menu and the options menu. User
 * uses buttons and ComboBox to specify the settings for the game they want to
 * play
 */
public class MenuPaneOrganizer {
	private Stage _stage;
	private BorderPane _root;
	private VBox _mainMenu;
	private Button _regGameButton;
	private Button _multiplayerButton;
	private Button _quitButton;
	private GameType _gameType;
	private VBox _options;
	private ComboBox<Difficulty> _difficultyBox;
	private ComboBox<RowColConfig> _rowsxcolsBox;
	private ComboBox<Character> _character1Box;
	private ComboBox<Character> _character2Box;
	private Button _startGameButton;
	private Button _backHomeButton;
	private Theme _theme;
	private ImageView _logoView;

	/**
	 * Constructor for the MenuPaneOrganizer class. Sets up the Main menu (Offers
	 * user to play either GameType or quit).
	 */
	public MenuPaneOrganizer(Stage stage) {
		_stage = stage;
		_root = new BorderPane();
		// Sets the initial theme to "The Office"
		this.setTheme(Theme.THEOFFICE);
		this.setupMenu();
		_root.setFocusTraversable(false);

	}

	/**
	 * Sets the theme for the menus. Changes the background image and the logo.
	 */
	public void setTheme(Theme theme) {
		_theme = theme;
		// Gets the backdrop image for this theme
		Image bPhoto = new Image(_theme.getBackdrop());
		// sets the backdrop for the root Pane to the correct image for the current
		// theme
		BackgroundImage backgroundImg = new BackgroundImage(bPhoto, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
		Background background = new Background(backgroundImg);
		_root.setBackground(background);
		// gets the logo image for this theme
		_logoView = new ImageView(new Image(_theme.getLogoImg()));
		_logoView.setFitWidth(Constants.PANE_WIDTH / 2);
		_logoView.setPreserveRatio(true);
		// Adds a MouseHandler for when the logo is clicked
		_logoView.setOnMouseClicked(new MouseHandler());
		// adds the logo image to the root pane and aligns it
		_root.setTop(_logoView);
		_root.setAlignment(_logoView, Pos.CENTER);
	}

	/**
	 * Sets up the main menu, which asks the user whether they want to play a
	 * regular game or a multiplayer game, or if they want to quit.
	 */
	private void setupMenu() {
		_mainMenu = new VBox();
		// Creates Regular Game Button
		_regGameButton = new Button("Play Regular Game");
		_regGameButton.setOnAction(new ButtonHandler());
		_regGameButton.setPadding(new Insets(5, 40, 5, 40));
		_regGameButton.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 20));
		_regGameButton.getStyleClass().add("menu-button");

		// Creates multiplayer Game Button
		_multiplayerButton = new Button("Play Multiplayer Game");
		_multiplayerButton.setOnAction(new ButtonHandler());
		_multiplayerButton.setPadding(new Insets(5, 40, 5, 40));
		_multiplayerButton.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 20));
		_multiplayerButton.getStyleClass().add("menu-button");

		// Creates Quit Button
		_quitButton = new Button("Quit");
		_quitButton.setOnAction(new ButtonHandler());
		_quitButton.setPadding(new Insets(5, 40, 5, 40));
		_quitButton.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 20));
		_quitButton.getStyleClass().add("menu-button");

		// Adds all the buttons to the menu pane
		_mainMenu.getChildren().addAll(_regGameButton, _multiplayerButton, _quitButton);

		_mainMenu.setAlignment(Pos.CENTER);

		_mainMenu.setSpacing(10);

		// adds menupane to the root pane
		_root.setCenter(_mainMenu);

	}

	/**
	 * Sets up the options menu, which, depending on the GameType selected in the
	 * main menu, offers the user what character they want to play as, the number of
	 * rows and columns they want, and what difficulty level they want to play at.
	 */
	private void setupOptionsMenu(GameType gt) {
		switch (gt) {
		// if the user chose a regular game on the main menu, display an options menu
		// that has a difficulty box, a row box, and a character box, as well as a back
		// button and a start game button
		case REGULAR: {
			_options = new VBox();

			// Difficulty label, combobox, and a VBox to contain both elements
			Label difflbl = new Label("Select Difficulty:");
			difflbl.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
			difflbl.setTextFill(Color.WHITE);
			_difficultyBox = new ComboBox<>();
			_difficultyBox.getItems().setAll(Difficulty.values());
			VBox diffBox = new VBox(difflbl, _difficultyBox);
			diffBox.setAlignment(Pos.CENTER);

			// row and col number label, combobox, and a VBox to contain both elements
			Label rowcollbl = new Label("Select # of Rows and Columns:");
			rowcollbl.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
			rowcollbl.setTextFill(Color.WHITE);
			_rowsxcolsBox = new ComboBox<>();
			_rowsxcolsBox.getItems().setAll(RowColConfig.values());
			VBox rowcolBox = new VBox(rowcollbl, _rowsxcolsBox);
			rowcolBox.setAlignment(Pos.CENTER);

			// character label, combobox, and a VBox to contain both elements
			Label characterlbl = new Label("Select Your Character:");
			characterlbl.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
			characterlbl.setTextFill(Color.WHITE);
			_character1Box = new ComboBox<>();
			_character1Box.getItems().setAll(_theme.getCharacters());
			VBox characterHBox = new VBox(characterlbl, _character1Box);
			characterHBox.setAlignment(Pos.CENTER);
			characterHBox.getStyleClass().add("options-box");

			// Button to start the game, add CSS styles to it
			_startGameButton = new Button("Start Game!");
			_startGameButton.setOnAction(new ButtonHandler());
			_startGameButton.setPadding(new Insets(5, 20, 5, 20));
			_startGameButton.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
			_startGameButton.getStyleClass().add("menu-button");

			// Button to go back to the main menu, add CSS styles to it
			_backHomeButton = new Button("Back");
			_backHomeButton.setOnAction(new ButtonHandler());
			_backHomeButton.setPadding(new Insets(5, 20, 5, 20));
			_backHomeButton.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
			_backHomeButton.getStyleClass().add("menu-button");

			// HBox for both buttons
			HBox optionsButtonsBox = new HBox(_backHomeButton, _startGameButton);
			optionsButtonsBox.setAlignment(Pos.CENTER);
			optionsButtonsBox.setSpacing(10);

			// Adds all the components to the _options VBox
			_options.getChildren().addAll(diffBox, rowcolBox, characterHBox, optionsButtonsBox);
			_options.setAlignment(Pos.CENTER);
			_options.setSpacing(15);

			// Sets the prompt that is displayed at the top of the screen to the prompt for
			// this theme
			Label topLbl = new Label(_theme.getPrompt());
			topLbl.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 16));
			topLbl.setTextFill(Color.WHITE);
			topLbl.setWrapText(true);
			topLbl.setTextAlignment(TextAlignment.CENTER);
			topLbl.setPrefWidth(Constants.SCENE_WIDTH);
			HBox topbox = new HBox(topLbl);
			topbox.setAlignment(Pos.CENTER);

			// Adds the prompt and the options VBox to the root pane
			_root.setTop(topbox);
			_root.setCenter(_options);

			break;
		}
		// if the user chose a multiplayer game on the main menu, display an options
		// menu
		// that has a difficulty box, a row box, and two character boxes, as well as a
		// back
		// button and a start game button
		case MULTIPLAYER: {
			_options = new VBox();

			// Difficulty label, combobox, and a VBox to contain both elements
			Label difflbl = new Label("Select Difficulty:");
			difflbl.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
			difflbl.setTextFill(Color.WHITE);
			_difficultyBox = new ComboBox<>();
			_difficultyBox.getItems().setAll(Difficulty.values());
			VBox diffBox = new VBox(difflbl, _difficultyBox);
			diffBox.setAlignment(Pos.CENTER);

			// row and col number label, combobox, and a VBox to contain both elements
			Label rowcollbl = new Label("Select # of Rows and Columns:");
			rowcollbl.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
			rowcollbl.setTextFill(Color.WHITE);
			_rowsxcolsBox = new ComboBox<>();
			_rowsxcolsBox.getItems().setAll(RowColConfig.values());
			VBox rowcolBox = new VBox(rowcollbl, _rowsxcolsBox);
			rowcolBox.setAlignment(Pos.CENTER);

			// character1 label, combobox, and a VBox to contain both elements
			Label character1lbl = new Label("Select Your Character 1:");
			character1lbl.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
			character1lbl.setTextFill(Color.WHITE);
			_character1Box = new ComboBox<>();
			_character1Box.getItems().setAll(_theme.getCharacters());
			VBox character1HBox = new VBox(character1lbl, _character1Box);
			character1HBox.setAlignment(Pos.CENTER);
			character1HBox.getStyleClass().add("options-box");

			// character2 label, combobox, and a VBox to contain both elements
			Label character2lbl = new Label("Select Your Character 2:");
			character2lbl.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
			character2lbl.setTextFill(Color.WHITE);
			_character2Box = new ComboBox<>();
			_character2Box.getItems().setAll(_theme.getCharacters());
			VBox character2HBox = new VBox(character2lbl, _character2Box);
			character2HBox.setAlignment(Pos.CENTER);
			character2HBox.getStyleClass().add("options-box");

			// Button to start the game, add CSS styles to it
			_startGameButton = new Button("Start Game!");
			_startGameButton.setOnAction(new ButtonHandler());
			_startGameButton.setPadding(new Insets(5, 20, 5, 20));
			_startGameButton.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
			_startGameButton.getStyleClass().add("menu-button");

			// Button to go back to the main menu, add CSS styles to it
			_backHomeButton = new Button("Back");
			_backHomeButton.setOnAction(new ButtonHandler());
			_backHomeButton.setPadding(new Insets(5, 20, 5, 20));
			_backHomeButton.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
			_backHomeButton.getStyleClass().add("menu-button");

			// HBox for both buttons
			HBox optionsButtonsBox = new HBox(_backHomeButton, _startGameButton);
			optionsButtonsBox.setAlignment(Pos.CENTER);
			optionsButtonsBox.setSpacing(10);

			// Adds all the components to the _options VBox
			_options.getChildren().addAll(diffBox, rowcolBox, character1HBox, character2HBox, optionsButtonsBox);
			_options.setAlignment(Pos.CENTER);
			_options.setSpacing(15);

			// Sets the prompt that is displayed at the top of the screen to the prompt for
			// this theme
			Label topLbl = new Label(_theme.getPrompt());
			topLbl.setFont(Font.loadFont("file:./Cartoon/resources/BPmono.ttf", 18));
			topLbl.setTextFill(Color.WHITE);
			topLbl.setWrapText(true);
			topLbl.setTextAlignment(TextAlignment.CENTER);
			topLbl.setPrefWidth(Constants.SCENE_WIDTH);
			HBox topbox = new HBox(topLbl);
			topbox.setAlignment(Pos.CENTER);

			// Adds the prompt and the options VBox to the root pane
			_root.setTop(topbox);
			_root.setCenter(_options);

			break;
		}
		}
	}

	/**
	 * Acessor method for the Root pane.
	 */
	public Pane getRoot() {
		return _root;
	}

	/**
	 * Handles all of the button inputs on both menus.
	 *
	 */
	private class ButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {

			if (e.getSource() == _quitButton) {
				// When the Quit Button is pressed, exit the program
				System.exit(0);
			} else if (e.getSource() == _regGameButton) {
				// When Regular Game Button is clicked, Sets the gametype to regular and sets up
				// the options menu accordingly
				_gameType = GameType.REGULAR;
				setupOptionsMenu(GameType.REGULAR);
			} else if (e.getSource() == _multiplayerButton) {
				// When Multiplayer Game Button is clicked, Sets the gametype to multiplayer and
				// sets up the options menu accordingly
				_gameType = GameType.MULTIPLAYER;
				setupOptionsMenu(GameType.MULTIPLAYER);
			} else if (e.getSource() == _backHomeButton) {
				// When the back home button is clicked, the main menu is displayed over the
				// options menu
				_root.setCenter(_mainMenu);
				_root.setTop(_logoView);

			} else if (e.getSource() == _startGameButton) {
				// When the start game button is clicked, create a GameSceneContainer object
				// with the user-specified parameters. Then, set the stage to show the scene
				// created by our GameSceneContainer
				if (_gameType == GameType.MULTIPLAYER) {
					GameSceneContainer cont = new GameSceneContainer(_stage, _gameType,
							_rowsxcolsBox.getValue().getNum(), _difficultyBox.getValue(), _character1Box.getValue(),
							_character2Box.getValue(), _theme);
					Scene scene = cont.getScene();
					scene.getStylesheets().add("file:./Cartoon/resources/styles.css");
					_stage.setScene(scene);
				} else {
					GameSceneContainer cont = new GameSceneContainer(_stage, _gameType,
							_rowsxcolsBox.getValue().getNum(), _difficultyBox.getValue(), _character1Box.getValue(),
							null, _theme);
					Scene scene = cont.getScene();
					scene.getStylesheets().add("file:./Cartoon/resources/styles.css");
					_stage.setScene(scene);
				}
			}
			e.consume();
		}
	}

	/**
	 * Handles a click on the Logo. When the Logo is clicked, the theme of the menu
	 * is toggled. It is repeatedly run through until it reaches The Office Theme
	 * again.
	 *
	 */
	private class MouseHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			switch (_theme) {
			case THEOFFICE: {
				setTheme(Theme.TOYSTORY);
				break;
			}
			case TOYSTORY: {
				setTheme(Theme.MONSTERSINC);
				break;
			}
			case MONSTERSINC: {
				setTheme(Theme.CARS);
				break;
			}
			case CARS: {
				setTheme(Theme.NEMO);
				break;
			}
			case NEMO: {
				setTheme(Theme.CS15);
				break;
			}
			case CS15: {
				setTheme(Theme.THEOFFICE);
				break;
			}
			default: {
				setTheme(Theme.THEOFFICE);
				break;
			}
			}

		}
	}

}
