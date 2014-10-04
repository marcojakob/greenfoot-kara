package kara.greenfoot.sokoban;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JOptionPane;

import kara.greenfoot.Kara;
import kara.greenfoot.KaraWorld;
import kara.greenfoot.Leaf;
import kara.greenfoot.Mushroom;
import kara.greenfoot.Tree;

/**
 * This is the world for the Kara Sokoban game:
 * <p>
 * This class manages the following:
 * <ul>
 * <li>all information about the world and pixel sizes
 * <li>the Fonts
 * <li>all the screen states
 * <li>all the levels
 * <li>the current level and number of moves
 * <li>the highscore
 * <ul>
 * 
 * @author Marco Jakob (http://edu.makery.ch)
 */
public class GameScreen extends KaraWorld {
	
	// the screen settings
	public static final int WIDTH_IN_CELLS = 21;
	public static final int HEIGHT_IN_CELLS = 18;

	// fonts and colors
	public static final String FONT_NAME = "Tahoma";
	public static final Font FONT_S = new Font(FONT_NAME, Font.PLAIN, 12);
	public static final Font FONT_S_BOLD = new Font(FONT_NAME, Font.BOLD, 12);
	public static final Font FONT_M = new Font(FONT_NAME, Font.PLAIN, 17);
	public static final Font FONT_L = new Font(FONT_NAME, Font.PLAIN, 20);
	public static final Font FONT_XL = new Font(FONT_NAME, Font.PLAIN, 30);
	public static final Font FONT_XL_BOLD = new Font(FONT_NAME, Font.BOLD, 30);
	public static final Font FONT_XXL = new Font(FONT_NAME, Font.PLAIN, 50);
	
    // Sokoban Game images
	public static final GreenfootImage ICON_START_SCREEN = new GreenfootImage("start_screen.png");
    public static final GreenfootImage ICON_START = new GreenfootImage("newmooon_start.png");
    public static final GreenfootImage ICON_OK = new GreenfootImage("newmooon_ok.png");
    public static final GreenfootImage ICON_ARROW_RIGHT = new GreenfootImage("newmooon_arrow_right.png");
    public static final GreenfootImage ICON_ARROW_LEFT = new GreenfootImage("newmooon_arrow_left.png");
    public static final GreenfootImage ICON_LOCKED = new GreenfootImage("newmooon_locked.png");
    public static final GreenfootImage ICON_HOME = new GreenfootImage("newmooon_home.png");
    public static final GreenfootImage ICON_RELOAD = new GreenfootImage("newmooon_reload.png");
    public static final GreenfootImage ICON_FLAG = new GreenfootImage("fatcow_flag.png");
    public static final GreenfootImage ICON_TROPHY = new GreenfootImage("impressions_trophy.png");
    public static final GreenfootImage ICON_HIGHSCORE = new GreenfootImage("icon_highscore.png");
    public static final GreenfootImage ICON_GOLD = new GreenfootImage("fatcow_star_gold.png");
    public static final GreenfootImage ICON_SILVER = new GreenfootImage("fatcow_star_silver.png");
    public static final GreenfootImage ICON_BRONZE = new GreenfootImage("fatcow_star_bronze.png");

	// the screen states
	private ScreenState startState;
	private ScreenState enterNameState;
	private ScreenState levelSplashState;
	private ScreenState gameState;
	private ScreenState levelCompleteState;;
	private ScreenState gameCompleteState;
	private ScreenState highscoreState;

	private ScreenState state;

	private Level[] allLevels;
	private HighscoreManager highscoreManager;
	private int currentLevelNumber;
	private int numberOfMoves;
	private boolean levelComplete;

	/**
	 * Constructor for World.
	 */
	public GameScreen() {
		// Create a new world with the specified cells
		super(WIDTH_IN_CELLS, HEIGHT_IN_CELLS);

		setPaintOrder(Label.class, Kara.class, Tree.class, Mushroom.class,
				Leaf.class);
	}
		
	protected void prepare() {
		// maximum speed for fast reaction 
		Greenfoot.setSpeed(100);
		
		// Read all the levels from the level file
		try {
			this.allLevels = Level.parseFromFile(MyKaraSokoban.LEVEL_FILE, MyKaraSokoban.class);
			
			if (allLevels == null || allLevels.length == 0) {
				String message = "<html>" + "Could not load Levels from file: <p><i>" 
						+ "Konnte Levels nicht laden von der Datei: "
						+ "</i><p><p>" + MyKaraSokoban.LEVEL_FILE
						+ "<p><p>(A Level-file must contain at least one String \"Level:\")</html>";

				KaraWorld.DialogUtils.showMessageDialogEdt(null, message, "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (IOException e) {
			String message = "<html>" + "Could not find level file: <p><i>" 
					+ "Konnte die Level Datei nicht finden: "
					+ "</i><p><p>" + MyKaraSokoban.LEVEL_FILE + "</html>";
			
			KaraWorld.DialogUtils.showMessageDialogEdt(null, message, "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
		
		// init the screen states
		startState = new StartState(this);
		enterNameState = new EnterNameState(this);
		levelSplashState = new LevelSplashState(this);
		gameState = new GameState(this);
		levelCompleteState = new LevelCompleteState(this);
		gameCompleteState = new GameCompleteState(this);
		highscoreState = new HighscoreState(this);
		
		// init the level number and number of moves
		currentLevelNumber = 1;
		numberOfMoves = 0;
		
		// init the highscore manager
		if (MyKaraSokoban.HIGHSCORE_ENABLED) {
			// First tries to use the FileHighscore, if not possible, the
			// ServerHighscore is used.
			if (FileHighscore.isAvailable()) {
				highscoreManager = new FileHighscore();
				highscoreManager.initHighscores();
			} else if (ServerHighscore.isAvailable()) {
				highscoreManager = new ServerHighscore();
				highscoreManager.initHighscores();
			}
		}
		
		// skip the start menu if in developer mode
		if (MyKaraSokoban.DEVELOPER_MODE) {
			setState(gameState);
		} else {
			setState(startState);
		}
	}

	/**
	 * Sets and initializes the specified screen state. Before the new screen is
	 * initialized, all objects in the world are removed.
	 * 
	 * @param state
	 *            the new state of the screen
	 */
	protected void setState(ScreenState state) {
		setState(state, true);
	}

	/**
	 * Sets and initializes the specified screen state.
	 * 
	 * @param state
	 *            the new state of the screen
	 * @param clearWorld
	 *            if true, all objects in the world are removed before the new
	 *            state is initialized.
	 */
	protected void setState(ScreenState state, boolean clearWorld) {
		if (clearWorld) {
			// Remove all objects in the world
			removeObjects(getObjects(null));
		}
		this.state = state;
		state.initScreen();
	}

	/**
	 * Returns the start screen state.
	 */
	protected ScreenState getStartState() {
		return startState;
	}

	/**
	 * Returns the enter name screen state.
	 */
	protected ScreenState getEnterNameState() {
		return enterNameState;
	}

	/**
	 * Returns the level splash screen state.
	 */
	protected ScreenState getLevelSplashState() {
		return levelSplashState;
	}

	/**
	 * Returns the game screen state.
	 */
	protected ScreenState getGameState() {
		return gameState;
	}

	/**
	 * Returns the level complete screen state.
	 */
	protected ScreenState getLevelCompleteState() {
		return levelCompleteState;
	}

	/**
	 * Returns the game complete screen state.
	 */
	protected ScreenState getGameCompleteState() {
		return gameCompleteState;
	}

	/**
	 * Returns the highscore screen state.
	 */
	protected ScreenState getHighscoreState() {
		return highscoreState;
	}

	/**
	 * Removes the tiled background images and sets the bg color to black with
	 * no grid.
	 */
	protected void createBlackBackground() {
        GreenfootImage img = new GreenfootImage(GameScreen.WIDTH_IN_CELLS, GameScreen.HEIGHT_IN_CELLS);
        img.setColor(Color.BLACK);
        img.fill();
        setBackground(img);
	}
	
	/**
	 * Initializes the background tiles with the field icon.
	 */
	protected void createFieldBackground() {
		super.createFieldBackground();
	}
	
	/**
	 * Initializes the actors based on actor information in the current level.
	 */
	protected void initActorsForCurrentLevel() {
		Level level = getCurrentLevel();
		
		if (level != null) {
			initActorsFromWorldSetup(level);
		}
	}

	/**
	 * Returns all the levels.
	 */
	protected Level[] getAllLevels() {
		return allLevels;
	}

	/**
	 * Returns the total number of levels.
	 */
	protected int getNumberOfLevels() {
		return allLevels.length;
	}

	/**
	 * Sets the level number for the current level.
	 */
	protected void setCurrentLevelNumber(int currentLevelNumber) {
		this.currentLevelNumber = currentLevelNumber;
	}

	/**
	 * Returns the level number for the current level.
	 */
	protected int getCurrentLevelNumber() {
		return currentLevelNumber;
	}

	/**
	 * Returns the current level.
	 */
	protected Level getCurrentLevel() {
		if (currentLevelNumber > 0 && currentLevelNumber - 1 < allLevels.length) {
			return allLevels[currentLevelNumber - 1];
		}
		return null;
	}

	/**
	 * Returns the level with the specified number.
	 */
	protected Level getLevel(int levelNumber) {
		if (levelNumber > 0 && levelNumber - 1 < allLevels.length) {
			return allLevels[levelNumber - 1];
		}
		return null;
	}

	/**
	 * Returns the number of moves that were made.
	 */
	protected int getNumberOfMoves() {
		return numberOfMoves;
	}

	/**
	 * Sets the number of moves.
	 */
	protected void setNumberOfMoves(int moves) {
		numberOfMoves = moves;
	}

	/**
	 * Returns true if the current level is complete.
	 */
	protected boolean isLevelComplete() {
		return levelComplete;
	}

	/**
	 * Sets whether the current level is complete.
	 */
	protected void setLevelComplete(boolean levelComplete) {
		this.levelComplete = levelComplete;
	}

	/**
	 * Returns whether the game is complete, i.e. the last level is completed.
	 * 
	 * @return true if the game is complete
	 */
	protected boolean isGameComplete() {
		return isLevelComplete()
				&& getCurrentLevelNumber() >= getNumberOfLevels();
	}

	/**
	 * Returns if the highscore is enabled and available.
	 */
	protected boolean isHighscoreAvailable() {
		return highscoreManager != null;
	}

	/**
	 * Returns if the highscore is enabled and available.
	 */
	protected boolean isHighscoreReadOnly() {
		if (highscoreManager == null) {
			return true;
		}

		return highscoreManager.isReadOnly();
	}

	/**
	 * Returns the name of the current player or empty String if none has been
	 * set.
	 */
	protected String getPlayerName() {
		if (highscoreManager == null) {
			return "";
		}
		return highscoreManager.getCurrentPlayerName();
	}

	/**
	 * Sets the name of the current player. (Will be ignored if ServerHighscore
	 * is used since the username of UserInfo is used).
	 */
	protected void setPlayerName(String playerName) {
		if (highscoreManager == null) {
			return;
		}
		highscoreManager.setCurrentPlayerName(playerName);
	}

	/**
	 * Returns true if the name of the current player can be set.
	 */
	protected boolean canSetPlayerName() {
		// Name of player can only be set if using the FileHighscore.
		return highscoreManager instanceof FileHighscore;
	}

	/**
	 * Returns the Highscore for the current level. May be null.
	 */
	protected Highscore getHighscoreForCurrentLevel() {
		return getHighscoreForLevel(currentLevelNumber);
	}

	/**
	 * Returns the Highscore for the specified level.
	 */
	protected Highscore getHighscoreForLevel(int levelNumber) {
		if (highscoreManager == null) {
			return null;
		}
		return highscoreManager.getHighscoreForLevel(levelNumber);
	}

	/**
	 * Sets the specified Highscore.
	 */
	protected void setHighscore(Highscore highscore) {
		highscoreManager.setHighscore(highscore);
	}

	/**
	 * The act method is called by the framework at each action step.
	 * The world's act method is called before the act method of any objects in
	 * the world.
	 * <p>
	 * Delegates act to the current state.
	 */
	public void act() {
		// Delegate to the current state
		state.act();
	}
	
	/**
	 * Creates an ASCII-representation of all the actors in the world.
	 * 
	 * @return the world as ASCII text
	 */
	@SuppressWarnings("unchecked")
	protected String toASCIIText() {
		return Level.createFromActors(getObjects(null), 0, "XXXX").toASCIIText(false);
	}
	
	/**
	 * Prints the world setup to the console.
	 */
	public void printWorldSetupToConsole() {
		// We repeat the method here so that it appears in the context menu in Greenfoot
		super.printWorldSetupToConsole();
	}
	
	/**
	 * Saves the world setup to a file that the user can choose.
	 */
	public void saveWorldSetupToFile() {
		// We repeat the method here so that it appears in the context menu in Greenfoot
		super.saveWorldSetupToFile();
	}
}
