package Cartoon;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * App Class. Initiates the program and initializes a MenuPaneOrganizer.
 *
 */

public class App extends Application {

	@Override
	public void start(Stage stage) throws InterruptedException {
		//Instantiate and show the menu paneorganizer
		MenuPaneOrganizer menuorganizer = new MenuPaneOrganizer(stage);
		Scene menuscene = new Scene(menuorganizer.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
		menuscene.getStylesheets().add("file:./Cartoon/resources/styles.css");
		stage.setScene(menuscene);
		stage.show();
		}


	/*
	 * Here is the mainline! No need to change this.
	 */
	public static void main(String[] argv) {
		// launch is a method inherited from Application
		launch(argv);
	}
}
