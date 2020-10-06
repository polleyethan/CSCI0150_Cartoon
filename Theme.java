package Cartoon;

import java.util.EnumSet;

/**
 * Enum for Theme of the Game
 */
public enum Theme {
	//Initializing all of the themes
	TOYSTORY(EnumSet.of(Character.WOODY, Character.BUZZ, Character.MRPOTATO, Character.ALIEN, Character.JESSIE),
			"The characters from Toy Story have come to life and are helping Dwight and The office look for his Beets! Help them locate the Beets!",
			"ANDYSROOM.jpg", "beet.png", "Beets", "ToyStoryLogo.png"),
	THEOFFICE(
			EnumSet.of(Character.DWIGHT1, Character.DWIGHT2, Character.JIM, Character.MICHAEL, Character.ANDYOFFICE,
					Character.PAM),
			"Oh No! While harvesting beets from Schrute Farms, Mose was accidentally spilling the new beets all over the farm! Dwight and the others need your help to find and collect all the beets.",
			"TheOfficeBackground.jpg", "beet.png", "Beets", "OfficeLogo.png"),
	MONSTERSINC(EnumSet.of(Character.SULLY, Character.MIKEWAZ, Character.ROZ, Character.BOO, Character.RANDALL),
			"Oh No! Mike Wazowski accidentally untied a cart full of Scare Tanks, causing dozens of Scare Tanks to spill out on the Scare Floor. Help him collect all the tanks!",
			"MonstersIncBackdrop.jpg", "MonstersIncFood.png", "Scare Tanks", "MonstersIncLogo.png"),
	CARS(EnumSet.of(Character.LIGHTMCQ, Character.MATER, Character.DOC, Character.LUIGI, Character.SALLY,
			Character.GUIDO), "Help Lightning McQueen and the rest of the Cars collect the Piston Cup!",
			"CarsBackdrop.jpg", "CarsFood.png", "Trophies", "CarsLogo.png"),
	CS15(EnumSet.of(Character.AVD),
			"Help Andy give all of his students A's on Cartoon! Quickly gather as many A's as possible!",
			"BrownBackdrop.jpg", "BrownFood.png", "A's", "BrownLogo.png"),
	NEMO(EnumSet.of(Character.DORY, Character.MARLIN, Character.CRUSH, Character.BRUCE),
			"Help the fish find Nemo !! Do it fast before the divers catch him and put him in a tank!",
			"NemoBackdrop.jpg", "NemoFood.png", "Nemo's", "NemoLogo.png");

	private EnumSet<Character> _characters;
	private String _prompt;
	private String _backdropSrc;
	private String _foodImg;
	private String _foodName;
	private String _logoImg;

	/**
	 * Constructor for Theme enum. Stores an EnumSet of all the Characters available
	 * for this theme, the prompt of the theme, and source of the backdrop image to
	 * be used on the main menus.
	 */
	private Theme(EnumSet<Character> characters, String prompt, String imgsrc, String foodImgSrc, String foodName,
			String logoImg) {
		_characters = characters;
		_prompt = prompt;
		_backdropSrc = imgsrc;
		_foodImg = foodImgSrc;
		_foodName = foodName;
		_logoImg = logoImg;
	}

	/**
	 * Returns an EnumSet of all the Characters available for this theme
	 */
	public EnumSet<Character> getCharacters() {
		return _characters;
	}

	/**
	 * Returns the source of the backdrop image to be used on the main menus
	 */
	public String getBackdrop() {
		return ("file:./Cartoon/resources/"+ _backdropSrc);
	}

	/**
	 * Returns the prompt for this theme
	 */
	public String getPrompt() {
		return _prompt + " Select a grid size, difficulty, and character below and press 'Start Game' to begin.";
	}

	/**
	 * Returns the food image source for this theme
	 */
	public String getFoodImgSrc() {
		return ("file:./Cartoon/resources/"+ _foodImg);
	}

	/**
	 * Returns the food name for this theme
	 */
	public String getFoodName() {
		return _foodName;
	}
	
	/**
	 * Returns the logo image source for this theme
	 */
	public String getLogoImg() {
		return ("file:./Cartoon/resources/"+ _logoImg);
	}
}
