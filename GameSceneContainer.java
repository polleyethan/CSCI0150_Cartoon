package Cartoon;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class models a container which creates the Game Scene. Using inputs from
 * the menus, it creates a PaneOrganizer fit to the users specifications, which is then given to the stage to be displayed.
 *
 */
public class GameSceneContainer {

	private Stage _stage;
	private GameType _type;
	private int _numrows;
	private int _numcols;
	private Difficulty _difficulty;
	private Character _character1;
	private Character _character2;
	private Theme _theme;

	/**
	 * Constructor for the GameSceneContainer class. Takes in variables that were
	 * chosen in the menus, and stores these variables.
	 */
	public GameSceneContainer(Stage stage, GameType type, int numRows, Difficulty diff, Character character1,
			Character character2, Theme theme) {
		_stage = stage;
		_type = type;
		_numrows = numRows;
		_numcols = numRows;
		_difficulty = diff;
		_character1 = character1;
		_character2 = character2;
		_theme = theme;
	}

	/**
	 * Returns the scene based on what options were chosen in the main menu and the
	 * options menu.
	 */
	public Scene getScene() {
		GameConfig config = new GameConfig(_numrows, _numcols, _difficulty.getSpeed());
		switch (_type) {
			//If Regular game was chosen, return a RegGamePaneOrganizer
		case REGULAR:
			RegGamePaneOrganizer regularorganizer = new RegGamePaneOrganizer(_stage, config, _character1, _theme);
			return new Scene(regularorganizer.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
			//If Multiplayer game was chosen, return a MultiplayerPaneOrganizer
		case MULTIPLAYER:
			MultiplayerPaneOrganizer multiorganizer = new MultiplayerPaneOrganizer(_stage, config, _character1,
					_character2, _theme);
			return new Scene(multiorganizer.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);

		default:
			RegGamePaneOrganizer regulardefaultorganizer = new RegGamePaneOrganizer(_stage, config, _character1,
					_theme);
			return new Scene(regulardefaultorganizer.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
		}

	}
}
