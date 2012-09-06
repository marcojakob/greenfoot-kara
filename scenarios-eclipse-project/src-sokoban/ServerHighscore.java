import greenfoot.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Saves the highscores to the Greenfoot server using UserInfo. This can be used
 * if published to the Greenfoot Gallery.
 * <p>
 * 
 * UserInfo is originally designed to have only one highscore but we need one
 * highscore for every level. To work around this limitation, we use the
 * available integers and Strings to store the users moves for each level. The
 * score is simply incremented whenever someone adds a new highscore entry. Then
 * we can use the score to get the most recently added players and compare their
 * moves in each level.
 * <p>
 * 
 * We can save move information for a maximum of 105 levels. For each level we
 * store a maximum of 999 moves. <br>
 * The UserInfo can store 10 integers and 5 Strings of 50 characters (at the
 * moment). In an integer we can store 3 levels, in a String 15 levels. This
 * makes the following calcualtion: <br>
 * 3 levels * 10 integers = 30 <br>
 * 15 levels * 5 Strings = 75 <br>
 * Total levels: 105
 * <p>
 * Note: Every User will only be stored once for every level, e.g. it is not
 * possible for one user to be on first and second place.
 * 
 * @author Marco Jakob (majakob@gmx.ch)
 */
public class ServerHighscore extends HighscoreManager {
	private UserInfo myUserInfo;
	private int topScore;

	/**
	 * The highscore map with the level number as a key and the Highscore as a
	 * value.
	 */
	private Map<Integer, Highscore> highscores;

	/**
	 * Constructor.
	 */
	public ServerHighscore() {
		myUserInfo = UserInfo.getMyInfo();
	}

	/**
	 * Returns true if the ServerHighscore is available (can at least read
	 * highscore info).
	 */
	public static boolean isAvailable() {
		return UserInfo.getTop(0) != null;
	}

	/**
	 * Initially loads the highscore from the server.
	 */
	public void initHighscores() {
		initHighscoresFromUserInfos();
	}

	/**
	 * Returns it the manager is read only.
	 */
	public boolean isReadOnly() {
		return myUserInfo == null;
	}

	/**
	 * Returns the name of the current player. If the user is not logged in,
	 * null is returned.
	 */
	public String getCurrentPlayerName() {
		if (myUserInfo == null) {
			return null;
		}
		return myUserInfo.getUserName();
	}

	/**
	 * This is ignored since the user name is set through the login in UserInfo.
	 */
	public void setCurrentPlayerName(String currentPlayerName) {
		// do nothing
	}

	/**
	 * Returns the Highscore for the specified level. The returned Highscore is
	 * a clone. To store a change in the highscore, the method setHighscore(...)
	 * must be called.
	 */
	public Highscore getHighscoreForLevel(int levelNumber) {
		if (highscores == null) {
			return null;
		}

		Highscore h = highscores.get(levelNumber);
		if (h == null) {
			return new Highscore(levelNumber);
		}
		return h.clone();
	}

	/**
	 * Sets the specified Highscore and stores it to the server.
	 */
	public void setHighscore(Highscore highscore) {
		if (myUserInfo == null) {
			// End if user is not logged in
			return;
		}

		// Find the new moves of the current user in high score and store it
		if (highscore.getFirstEntry().getName()
				.equals(myUserInfo.getUserName())) {
			Highscore h = getHighscoreForLevel(highscore.getLevelNumber());
			h.addHighscoreEntry(highscore.getFirstEntry());
			highscores.put(highscore.getLevelNumber(), h);

			saveToMyInfo(highscore.getLevelNumber(), highscore.getFirstEntry()
					.getMoves());
		} else if (highscore.getSecondEntry().getName()
				.equals(myUserInfo.getUserName())) {
			Highscore h = getHighscoreForLevel(highscore.getLevelNumber());
			h.addHighscoreEntry(highscore.getSecondEntry());
			highscores.put(highscore.getLevelNumber(), h);

			saveToMyInfo(highscore.getLevelNumber(), highscore.getSecondEntry()
					.getMoves());
		} else if (highscore.getThirdEntry().getName()
				.equals(myUserInfo.getUserName())) {
			Highscore h = getHighscoreForLevel(highscore.getLevelNumber());
			h.addHighscoreEntry(highscore.getThirdEntry());
			highscores.put(highscore.getLevelNumber(), h);

			saveToMyInfo(highscore.getLevelNumber(), highscore.getThirdEntry()
					.getMoves());
		}

		// other users can't be stored --> do nothing
	}

	/**
	 * Gets the top UserInfos and initializes the level move map.
	 */
	@SuppressWarnings("unchecked")
	private void initHighscoresFromUserInfos() {
		highscores = new HashMap<Integer, Highscore>();

		// Get the top UserInfos: The maximum would be 315 (3 places in 105
		// levels), but
		// it is very unlikely that there are 315 distinct players in the
		// highscore of
		// all 105 levels! So we take the first 100.
		List<UserInfo> userInfos = UserInfo.getTop(100);

		// Save the top score
		if (!userInfos.isEmpty()) {
			topScore = userInfos.get(0).getScore();
		}

		for (UserInfo userInfo : userInfos) {
			processUserInfo(userInfo);
		}
	}

	/**
	 * Processes the UserInfo and adds them to the highscore map.
	 */
	private void processUserInfo(UserInfo userInfo) {
		int currentLevel = 1;

		// process stored integers
		for (int i = 0; i < 10; i++) {
			// decode the moves for next three levels
			int encodedMoves = userInfo.getInt(i);
			int[] decodedMoves = decodeInt(encodedMoves);

			for (int moves : decodedMoves) {
				if (moves > 0) {
					Highscore h = highscores.get(currentLevel);
					if (h == null) {
						h = new Highscore(currentLevel);
						highscores.put(currentLevel, h);
					}
					h.addHighscoreEntry(userInfo.getUserName(), moves);
				}
				currentLevel++;
			}
		}

		// process stored Strings
		for (int i = 0; i < 5; i++) {
			String encodedMoves = userInfo.getString(i);

			int[] decodedMoves = decodeString(encodedMoves);

			for (int moves : decodedMoves) {
				if (moves > 0) {
					Highscore h = highscores.get(currentLevel);
					if (h == null) {
						h = new Highscore(currentLevel);
						highscores.put(currentLevel, h);
					}
					h.addHighscoreEntry(userInfo.getUserName(), moves);
				}
				currentLevel++;
			}
		}
	}

	/**
	 * Savest the number of moves for the given level to the current users
	 * UserInfo.
	 */
	private void saveToMyInfo(int levelNumber, int moves) {
		if (levelNumber <= 30) {
			// For Level 30 and below, the moves are stored in UserInfo ints
			int userInfoIndex = (levelNumber - 1) / 3;
			int arrayIndex = (levelNumber - 1) % 3;

			// get and decode
			int[] userInfoInts = decodeInt(myUserInfo.getInt(userInfoIndex));
			userInfoInts[arrayIndex] = moves;

			// encode and set
			myUserInfo.setInt(userInfoIndex, encodeInt(userInfoInts));
		} else if (levelNumber <= 105) {
			// Above level 30, the moves are stored in UserInfo Strings
			int userInfoIndex = (levelNumber - 31) / 15;
			int arrayIndex = (levelNumber - 31) % 15;

			// get and decode
			int[] userInfoInts = decodeString(myUserInfo
					.getString(userInfoIndex));
			userInfoInts[arrayIndex] = moves;

			// encode and set
			myUserInfo.setString(userInfoIndex, encodeString(userInfoInts));
		} else {
			// do nothing if level > 105
			return;
		}

		// Calc and set the new score and save
		myUserInfo.setScore(newScore());
		myUserInfo.store();
	}

	/**
	 * Creates a new score for the current user. It takes the current highest
	 * score and adds 1. This way we can always be shure that the top 315
	 * players are all possible players in any of the highscores.
	 */
	private int newScore() {
		return topScore + 1;
	}

	/**
	 * Encodes the specified integers into one integer. The specified integers
	 * must be maximum 3 and each smaller or equal to 999.
	 */
	private int encodeInt(int[] dec) {
		if (dec.length > 3)
			throw new IllegalArgumentException();

		int result = 1000000000; // add 1 billion, otherwise leading 0s will not
									// be visible
		for (int i = 0; i < dec.length; i++) {
			int decInRange = Math.max(Math.min(dec[i], 999), 0);

			result += decInRange * Math.pow(1000, (2 - i));
		}
		return result;
	}

	/**
	 * Encodes the specified integers into one String. The integers must be a
	 * maximum of 15 and each smaller or equal to 999. The Strings are appended.
	 */
	private String encodeString(int[] dec) {
		if (dec.length > 15)
			throw new IllegalArgumentException();

		StringBuffer result = new StringBuffer();
		for (int i = 0; i < dec.length && i < 15; i++) {
			int decInRange = Math.max(Math.min(dec[i], 999), 0);

			// write leading 0
			if (decInRange < 100) {
				result.append("0");
				if (decInRange < 10) {
					result.append("0");
				}
			}

			result.append(decInRange);
		}

		return result.toString();
	}

	/**
	 * Decodes the specified encoded integer into an array of exactly three
	 * integers (each smaller or equal to 999).
	 */
	private int[] decodeInt(int encoded) {
		int[] dec = new int[3];
		dec[0] = encoded / 1000000 % 1000;
		dec[1] = (encoded / 1000) % 1000;
		dec[2] = encoded % 1000;
		return dec;
	}

	/**
	 * Decodes the specified string into an array of exactly 15 integers. Each
	 * integer is smaller or equal to 999.
	 */
	private int[] decodeString(String encoded) {
		int[] dec = new int[15];
		for (int i = 0; i < encoded.length() / 3 && i < 15; i++) {
			try {
				dec[i] = Integer.parseInt(encoded.substring(i * 3, i * 3 + 3));
			} catch (NumberFormatException e) {
				// do nothing --> int in the array will have 0 als value
			}
		}
		return dec;
	}
}