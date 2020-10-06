package Cartoon;

/**
 * Enumeration for all of the Characters available in the game. Each Character
 * has a string representation of itself and the path to its images.
 *
 */
public enum Character {
	DWIGHT1("Dwight 1", "DWIGHT.png"), DWIGHT2("Dwight 2", "DWIGHT.png"), JIM("Jim", "JIM.png"),
	MICHAEL("Michael", "MICHAEL.png"), ANDYOFFICE("Andy", "ANDY.png"), PAM("Pam", "PAM.png"),
	WOODY("Woody", "WOODY.png"), BUZZ("Buzz Lightyear", "BUZZ.png"), MRPOTATO("Mr. Potato Head", "MRPOTATO.png"),
	ALIEN("Alien", "ALIEN.png"), JESSIE("Jessie", "JESSIE.png"), MIKEWAZ("Mike Wazowski", "MIKE.png"),
	SULLY("Sully", "SULLY.png"), RANDALL("Randall", "RANDALL.png"), ROZ("Roz", "ROZ.png"), BOO("Boo", "BOO.png"),
	LIGHTMCQ("Lightning McQueen", "LIGHTMC.png"), MATER("Mater", "MATER.png"), DOC("Doc Hudson", "DOC.png"),
	LUIGI("Luigi", "LUIGI.png"), SALLY("Sally", "SALLY.png"), GUIDO("Guido", "GUIDO.png"), AVD("Andy", "AVD.png"),
	DORY("Dory", "DORY.png"), MARLIN("Marlin", "MARLIN.png"), CRUSH("Crush", "CRUSH.png"), BRUCE("Bruce", "BRUCE.png");

	private String _stringVal;
	private String _fileLoc;

	/**
	 * Constructor for the Character class. Takes in a string representation of
	 * itself and the path to its images.
	 */
	private Character(String s, String loc) {
		_stringVal = s;
		_fileLoc = loc;

	}

	/**
	 * Accessor method to get the file location of the image representing this
	 * character.
	 */
	public String getFileLoc() {
		return ("file:./Cartoon/resources/"+ _fileLoc);
	}

	/**
	 * Accessor method to get the file location of the image that is displayed when
	 * this character wins.
	 */
	public String getWinFileLoc() {
		return ("file:./Cartoon/resources/"+ _fileLoc.replace(".png", "WIN.gif"));
	}

	/**
	 * Accessor method to get the file location of the image that is displayed when
	 * this character loses.
	 */
	public String getLoseFileLoc() {
		return ("file:./Cartoon/resources/"+ _fileLoc.replace(".png", "LOSE.gif"));
	}

	/**
	 * Accessor method to get the string representation of this character.
	 */
	public String toString() {
		return _stringVal;
	}
}
