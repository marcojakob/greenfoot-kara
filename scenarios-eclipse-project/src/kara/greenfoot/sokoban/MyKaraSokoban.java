package kara.greenfoot.sokoban;

/**
 * MyKara is a subclass of KaraSokoban. Therefore, it inherits all methods of KaraSokoban <p>
 * <i>MyKara ist eine Unterklasse von KaraSokoban. Sie erbt damit alle Methoden der Klasse KaraSokoban</i> <p>
 */
public class MyKaraSokoban extends KaraSokoban {
	
	/**
	 * The file containing the levels <br>
	 * <i>Die Datei, welche die Levels enthaelt.</i>
	 */
	public static final String LEVEL_FILE = "Levels.txt";

	/**
	 * Set to true to directly show the game (for testing and level design). Set
	 * to false for normal mode. <br>
	 * <i>Bei true wird direkt das Spielfeld angezeigt (zum Testen und Level
	 * Erstellen). Fuer Normaler Modus auf false setzen.</i>
	 */
	public static final boolean DEVELOPER_MODE = true;

	/**
	 * Set to true to enable the highscore. <br>
	 * <i>Wenn auf true gesetzt, dann wir die Highscore aktiviert.</i>
	 */
	public static final boolean HIGHSCORE_ENABLED = false;
	
    /**
     * In the 'act()' method you can write your program for Kara <br>
     * <i>In der Methode 'act()' koennen die Befehle fuer Kara programmiert werden</i>
     */
	public void act() {
		// This class will be replaced by the scenario MyKara class.
	}
}
