package kara.greenfoot.sokoban;

import greenfoot.Greenfoot;

import java.awt.Color;

/**
 * The level complete screen state.
 * 
 * @author Marco Jakob (majakob@gmx.ch)
 */
public class LevelCompleteState extends ScreenState {
	private Button nextLevelButton;
	private Button retryLevelButton;

	public LevelCompleteState(GameScreen gameScreen) {
		super(gameScreen);
	}

	/**
	 * Initializes the screen.
	 */
	public void initScreen() {
		Label levelCompleteLabel = new Label("Level "
				+ gameScreen.getCurrentLevelNumber() + " Complete!", 400, 50,
				GameScreen.FONT_XL_BOLD);
		levelCompleteLabel.setIcon(GameScreen.ICON_FLAG);
		levelCompleteLabel.setBackgroundTransparency(180);

		nextLevelButton = new Button("Next Level", 140, 30, GameScreen.FONT_M);
		nextLevelButton.setIcon(GameScreen.ICON_START);
		nextLevelButton.setBackgroundColor(new Color(255, 205, 205));
		nextLevelButton.setBorderColor(Color.RED);
		nextLevelButton.setBackgroundTransparency(180);

		retryLevelButton = new Button("Retry Level", 140, 30, GameScreen.FONT_M);
		retryLevelButton.setIcon(GameScreen.ICON_RELOAD);
		retryLevelButton.setBackgroundColor(new Color(255, 205, 205));
		retryLevelButton.setBorderColor(Color.RED);
		retryLevelButton.setBackgroundTransparency(180);

		if (gameScreen.isHighscoreAvailable()) {
			initHighscore();
			gameScreen.addObject(levelCompleteLabel,
					GameScreen.WIDTH_IN_CELLS / 2, 3);
			gameScreen.addObject(nextLevelButton,
					GameScreen.WIDTH_IN_CELLS / 2, 13);
			gameScreen.addObject(retryLevelButton,
					GameScreen.WIDTH_IN_CELLS / 2, 15);
		} else {
			gameScreen.addObject(levelCompleteLabel,
					GameScreen.WIDTH_IN_CELLS / 2, 7);
			gameScreen.addObject(nextLevelButton,
					GameScreen.WIDTH_IN_CELLS / 2, 11);
			gameScreen.addObject(retryLevelButton,
					GameScreen.WIDTH_IN_CELLS / 2, 13);
		}
	}

	/**
	 * Initializes the highscore.
	 */
	private void initHighscore() {
		// Get the highscore entries
		Highscore highscore = gameScreen.getHighscoreForCurrentLevel();
		String first = highscore.getFirstEntry().toString();
		String second = highscore.getSecondEntry().toString();
		String third = highscore.getThirdEntry().toString();

		Label highscoreGoldLabel = new Label(first, 400, 40, GameScreen.FONT_L);
		highscoreGoldLabel.setTextColor(Color.BLACK);
		highscoreGoldLabel.setBackgroundTransparency(150);
		highscoreGoldLabel.setBorderColor(null);
		highscoreGoldLabel.setIcon(GameScreen.ICON_GOLD);
		gameScreen.addObject(highscoreGoldLabel, GameScreen.WIDTH_IN_CELLS / 2,
				6);

		Label highscoreSilverLabel = new Label(second, 400, 72,
				GameScreen.FONT_L);
		highscoreSilverLabel.setTextColor(Color.BLACK);
		highscoreSilverLabel.setBackgroundTransparency(150);
		highscoreSilverLabel.setBorderColor(null);
		highscoreSilverLabel.setIcon(GameScreen.ICON_SILVER);
		gameScreen.addObject(highscoreSilverLabel,
				GameScreen.WIDTH_IN_CELLS / 2, 8);

		Label highscoreBronzeLabel = new Label(third, 400, 40,
				GameScreen.FONT_L);
		highscoreBronzeLabel.setTextColor(Color.BLACK);
		highscoreBronzeLabel.setBackgroundTransparency(150);
		highscoreBronzeLabel.setBorderColor(null);
		highscoreBronzeLabel.setIcon(GameScreen.ICON_BRONZE);
		gameScreen.addObject(highscoreBronzeLabel,
				GameScreen.WIDTH_IN_CELLS / 2, 10);

		if (gameScreen.isHighscoreReadOnly()) {
			Label highscoreReadOnlyLabel = new Label(
					"You must be logged in to enter your Highscore!", 400, 16,
					GameScreen.FONT_S_BOLD);
			highscoreReadOnlyLabel.setTextColor(Color.BLACK);
			highscoreReadOnlyLabel.setBackgroundColor(new Color(255, 205, 205));
			highscoreReadOnlyLabel.setBackgroundTransparency(150);
			highscoreReadOnlyLabel.setBorderColor(null);
			gameScreen.addObject(highscoreReadOnlyLabel,
					GameScreen.WIDTH_IN_CELLS / 2, 11);
		}
	}

	/**
	 * The act method is called by the GameScreen to perform the action in the
	 * ScreenState.
	 */
	public void act() {
		String key = Greenfoot.getKey();
		if ((key != null && key.equals("enter"))
				|| nextLevelButton.wasClicked()) {
			if (!gameScreen.isGameComplete()) {
				// go to next level
				gameScreen.setCurrentLevelNumber(gameScreen
						.getCurrentLevelNumber() + 1);
				gameScreen.setState(gameScreen.getLevelSplashState());
			} else {
				// game complete --> go to game complete state
				gameScreen.setState(gameScreen.getGameCompleteState());
			}
		}
		
		if (retryLevelButton.wasClicked()) {
			// go to level splash state
			gameScreen.setState(gameScreen.getLevelSplashState());
		}
	}
}
