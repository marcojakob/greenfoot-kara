package kara.greenfoot;

import greenfoot.*;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;


/**
 * This class creates a world for Kara. It contains settings for height and
 * width of the world and initializes the Actors.
 * 
 * @author Marco Jakob (majakob@gmx.ch)
 */
public class KaraWorld extends World {
	
	/**
	 * The file from which the world setup is loaded <br>
	 * <i>Die Textdatei, von welcher die Welt geladen wird.</i>
	 */
	public static final String WORLD_SETUP_FILE = "WorldSetup.txt";
	
	// Size of one cell
	public static final int CELL_SIZE = 28; 
	
	// background icon
	private static GreenfootImage ICON_BACKGROUND_FIELD;
	
    // all actor icons (this is a trick to get the default image that was chosen for actors)
    public static final GreenfootImage ICON_LEAF = new Leaf().getImage();
    public static final GreenfootImage ICON_MUSHROOM = new Mushroom().getImage();
    public static final GreenfootImage ICON_MUSHROOM_ON_TARGET = findOnTargetImage(ICON_MUSHROOM, "_on_target");
    public static final GreenfootImage ICON_TREE = new Tree().getImage();
    public static final GreenfootImage ICON_KARA = new Kara().getImage();
    public static final GreenfootImage ICON_MY_KARA = new MyKara().getImage();
    
	public static final String WORLD_SETUP_TITLE_KEY = "World:";
	public static final String KARA_DIRECTION_KEY = "Kara:";
	public static final String DIRECTION_RIGHT = "right";
	public static final String DIRECTION_DOWN = "down";
	public static final String DIRECTION_LEFT = "left";
	public static final String DIRECTION_UP = "up";
	
	public static final Class<?>[] PAINT_ORDER = {
			Kara.class, 
			Tree.class, 
			Mushroom.class, 
			Leaf.class};
	
    private static final WorldSetup worldSetup;
    
    static {
    	// This code is executed when the class is loaded, 
    	// BEFORE the constructor is called
		if (WORLD_SETUP_FILE == null) {
			worldSetup = null;
		} else {
			worldSetup = loadWorldSetupFromFile(WORLD_SETUP_FILE);
		}
    }
    
	/**
	 * Creates a world for Kara.
	 */
	public KaraWorld() {
		// Create the new world
		super(worldSetup.getWidth(), worldSetup.getHeight(), CELL_SIZE);
		ICON_BACKGROUND_FIELD = getBackground();

		setPaintOrder(PAINT_ORDER);
		Greenfoot.setSpeed(20);

		// Initialize actors
		prepare();
	}
	
	/**
	 * Creates an empty world for Kara with specified width and height.
	 * 
	 * @param worldWidth
	 *            Number of horizontal cells
	 * @param worldHeight
	 *            Number of vertical cells
	 */
	public KaraWorld(int worldWidth, int worldHeight) {
		// Create the new world
		super(worldWidth, worldHeight, CELL_SIZE);
		ICON_BACKGROUND_FIELD = getBackground();
		
		setPaintOrder(PAINT_ORDER);
		Greenfoot.setSpeed(20);
		
		// Initialize actors
		prepare();
	}
	
	/**
	 * Loads the world setup from the specified file. If it cannot be found, a
	 * warning message is displayed and a default world setup is loaded.
	 * 
	 * @param worldFile
	 *            The filename of the world setup file, relative to the class,
	 *            relative to the package root or relative to the project root.
	 *            Wildcards (? or *) may be used.
	 * @return
	 */
	private static WorldSetup loadWorldSetupFromFile(String worldFile) {
		WorldSetup[] worldSetups = WorldSetup.parseFromFile(worldFile, MyKara.class, WORLD_SETUP_TITLE_KEY, 
				KARA_DIRECTION_KEY);
		WorldSetup result = null;
		
		if (worldSetups.length == 1) {
			result = worldSetups[0];
		} else if (worldSetups.length > 1) {
			// User must choose from a list of world setups
		    result = (WorldSetup) JOptionPane.showInputDialog(null, 
		    		"<html>Please Choose a World: <p><i>Bitte waehle eine Welt:</i>", 
		    		"Choose World",
		    		JOptionPane.QUESTION_MESSAGE, null,
		    		worldSetups, // Array of choices
		    		worldSetups[0]); // Initial choice
		} else {
			String message = "<html>" + "Could not load world setup from file: <p><i>" 
					+ "Konnte keine Welt laden von der Datei: "
					+ "</i><p><p>" + worldFile 
					+ "<p><p>(A world-file must start with \"" + WORLD_SETUP_TITLE_KEY 
					+ "\")</html>";
	
			JOptionPane.showMessageDialog(null, message, "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
		
		if (result != null) {
			return result;
		} else {
			// return a default world setup
			return new WorldSetup.Builder(WORLD_SETUP_TITLE_KEY)
			.setWidth(10)
			.setHeight(10)
			.setTitle("Kara Default")
			.build();
		}
	}

	/**
	 * Initializes the background tiles with the field icon.
	 */
	protected void createFieldBackground() {
		setBackground(ICON_BACKGROUND_FIELD);
	}
	
	/**
	 * Removes the background tiles.
	 */
	protected void clearFieldBackground() {
		setBackground((GreenfootImage) null);
	}

	/**
	 * Prepares the world, i.e. creates all initial actors. If a world setup is
	 * available, it is loaded.
	 * <p>
	 * This method may be overridden by subclasses to provide their own means to
	 * initialize actors, e.g. by calling {@link #addObject()}.
	 */
	protected void prepare() {
		if (worldSetup != null) {
			initActorsFromWorldSetup(worldSetup);
		}
	}
	
	/**
	 * Initializes the actors based on actor information in the specified
	 * {@link WorldSetup}.
	 */
	protected void initActorsFromWorldSetup(WorldSetup worldSetup) {
		for (int y = 0; y < worldSetup.getHeight(); y++) {
			for (int x = 0; x < worldSetup.getWidth(); x++) {
				switch (worldSetup.getActorTypeAt(x, y)) {
				case WorldSetup.KARA:
					addObject(new MyKara(), x, y);
					break;
				case WorldSetup.TREE:
					addObject(new Tree(), x, y);
					break;
				case WorldSetup.LEAF:
					addObject(new Leaf(), x, y);
					break;
				case WorldSetup.MUSHROOM:
					addObject(new Mushroom(), x, y);
					break;
				case WorldSetup.MUSHROOM_LEAF:
					addObject(new Mushroom(true), x, y);
					addObject(new Leaf(), x, y);
					break;
				case WorldSetup.KARA_LEAF:
					addObject(new MyKara(), x, y);
					addObject(new Leaf(), x, y);
					break;
				}
			}
		}
		
		String karaDirection = worldSetup.getAttribute(KARA_DIRECTION_KEY);
		if (karaDirection != null) {
			
			if (karaDirection.equalsIgnoreCase(DIRECTION_DOWN)) {
				for (Object actor : getObjects(Kara.class)) {
					Kara kara = (Kara) actor;
					kara.turnRight();
				}
			} else if (karaDirection.equalsIgnoreCase(DIRECTION_LEFT)) {
				for (Object actor : getObjects(Kara.class)) {
					Kara kara = (Kara) actor;
					kara.turnRight();
					kara.turnRight();
				}
			} else if (karaDirection.equalsIgnoreCase(DIRECTION_UP)) {
				for (Object actor : getObjects(Kara.class)) {
					Kara kara = (Kara) actor;
					kara.turnLeft();
				}
			}
			// DIRECTION_RIGHT is the original direction - do nothing
		}
	}
	
	/**
	 * Prints the world setup to the console.
	 */
	public void printWorldSetupToConsole() {
		System.out.println(";-------------------------- START --------------------------");
		System.out.println(toASCIIText());
		System.out.println(";--------------------------- END ---------------------------\n");
	}
	
	/**
	 * Saves the world setup to a file that the user can choose.
	 */
	public void saveWorldSetupToFile() {
		try {
			WorldSetup.FileUtils.saveToFileWithDialog(toASCIIText());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Creates an ASCII-representation of all the actors in the world.
	 * 
	 * @return the world as ASCII text
	 */
	@SuppressWarnings("unchecked")
	protected String toASCIIText() {
		return WorldSetup.createFromActors(getObjects(null), getWidth(), getHeight(),
				WORLD_SETUP_TITLE_KEY).toASCIIText(true);
	}
	
	/**
	 * Retrieves the filename of the default image and adds the on-target
	 * postfix to the filename to load the on-target-image. If the on target
	 * image cannot be found, the default image is returned.
	 */
	private static GreenfootImage findOnTargetImage(
			GreenfootImage defaultImage, String onTargetPostfix) {
		// Since a GreenfootImage only has a toString method we must parse it to
		// get the file name
		Pattern pattern = Pattern.compile("Image file name: (.+)(\\.+\\S{3,4})\\s+Image url");
		Matcher matcher = pattern.matcher(defaultImage.toString());

		if (matcher.find()) {
			String fileName = matcher.group(1) + onTargetPostfix
					+ matcher.group(2);
			try {
				return new GreenfootImage(fileName);
			} catch (Exception e) {
				System.out.println("Could not find the file: " + fileName
						+ " (the default file is used)");
				return defaultImage;
			}
		} else {
			// Could not find the on target image --> use default
			return defaultImage;
		}
	}
}
