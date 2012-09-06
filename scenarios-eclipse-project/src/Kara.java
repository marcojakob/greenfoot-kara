import greenfoot.*;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * This is the superclass for all Karas containing the basic Kara methods.
 * Programs should only be written in subclasses (e.g. MyKara).
 * <p>
 * 
 * <i>Diese Klasse ist die Oberklasse fuer alle Karas und enthaelt die
 * Grundfunktionen von Kara. Programme sollten nur in den Unterklassen wie
 * MyKara geschrieben werden.</i>
 * 
 * @author Marco Jakob (majakob@gmx.ch)
 */
public class Kara extends Actor {
	private static final int DIRECTION_RIGHT = 0;
	private static final int DIRECTION_DOWN = 90;
	private static final int DIRECTION_LEFT = 180;
	private static final int DIRECTION_UP = 270;

	/**
	 * Kara makes a step in the current direction <br>
	 * <i>Kara macht einen Schritt in die aktuelle Richtung</i>
	 */
	public void move() {
		// Check for a tree
		if (treeFront()) {
			showWarning("Kara can't move because of a tree!",
					"Kara kann sich nicht bewegen wegen einem Baum!");
			return;
		}

		// Check for a mushroom
		Mushroom mushroomFront = (Mushroom) getObjectInFront(getRotation(), 1,
				Mushroom.class);
		if (mushroomFront != null) {
			// Check if the mushroom could be pushed to the next field
			if (getObjectInFront(getRotation(), 2, Tree.class) == null
					&& getObjectInFront(getRotation(), 2, Mushroom.class) == null) {
				// Push the mushroom
				moveActor(mushroomFront, getRotation());
                // Check if the mushroom is now on a leaf
                mushroomFront.updateImage();
			} else {
				// Could not push the mushroom
				showWarning(
						"Kara can't move because he can't push the mushroom!",
						"Kara kann sich nicht bewegen, da er den Pilz nicht schieben kann!");
				return;
			}
		}

		// Kara can move
		moveActor(this, getRotation());
		Greenfoot.delay(1);
	}

	/**
	 * Kara turns left by 90 degrees <br>
	 * <i>Kara dreht sich um 90° nach links</i>
	 */
	public void turnLeft() {
		turn(-90);
		Greenfoot.delay(1);
	}

	/**
	 * Kara turns right by 90 degrees <br>
	 * <i>Kara dreht sich um 90° nach rechts</i>
	 */
	public void turnRight() {
		turn(90);
		Greenfoot.delay(1);
	}

	/**
	 * Kara puts down a leaf <br>
	 * <i>Kara legt ein neues Kleeblatt an die Position, auf der er sich
	 * befindet</i>
	 */
	public void putLeaf() {
		if (!onLeaf()) {
			Leaf leaf = new Leaf();
			getWorld().addObject(leaf, getX(), getY());
			Greenfoot.delay(1);
		} else {
			showWarning("Kara can't put a leaf on top of another leaf!",
					"Kara kann kein Kleeblatt auf ein Feld legen, auf dem schon eines ist!");
		}
	}

	/**
	 * Kara picks up a leaf <br>
	 * <i>Kara entfernt ein unter ihm liegendes Kleeblatt</i>
	 */
	public void removeLeaf() {
		Leaf leaf = (Leaf) getOneObjectAtOffset(0, 0, Leaf.class);
		if (leaf != null) {
			getWorld().removeObject(leaf);
			Greenfoot.delay(1);
		} else {
			showWarning("There is no leaf that Kara could remove here!",
					"Kara kann hier kein Blatt auflesen!");
		}
	}

	/**
	 * Kara checks if he stands on a leaf <br>
	 * <i>Kara schaut nach, ob er sich auf einem Kleeblatt befindet</i>
	 * 
	 * @return true if Kara stands on a leaf, false otherwise
	 */
	public boolean onLeaf() {
		return getOneObjectAtOffset(0, 0, Leaf.class) != null;
	}

	/**
	 * Kara checks if there is a tree in front of him <br>
	 * <i>Kara schaut nach, ob sich ein Baum vor ihm befindet</i>
	 * 
	 * @return true if there is a tree in front of Kara, false otherwise
	 */
	public boolean treeFront() {
		return getObjectInFront(getRotation(), 1, Tree.class) != null;
	}

	/**
	 * Kara checks if there is a tree on his left side <br>
	 * <i>Kara schaut nach, ob sich ein Baum links von ihm befindet</i>
	 * 
	 * @return true if Kara has a tree on his left, false otherwise
	 */
	public boolean treeLeft() {
		return getObjectInFront(modulo(getRotation() - 90, 360), 1, Tree.class) != null;
	}

	/**
	 * Kara checks if there is a tree on his right side <br>
	 * <i>Kara schaut nach, ob sich ein Baum rechts von ihm befindet</i>
	 * 
	 * @return true if Kara has a tree on his right, false otherwise
	 */
	public boolean treeRight() {
		return getObjectInFront(modulo(getRotation() + 90, 360), 1, Tree.class) != null;
	}

	/**
	 * Kara checks if there is a mushroom in front of him <br>
	 * <i>Kara schaut nach, ob er einen Pilz vor sich hat</i>
	 * 
	 * @return true if a mushroom is in front of a Kara, false otherwise
	 */
	public boolean mushroomFront() {
		return getObjectInFront(getRotation(), 1, Mushroom.class) != null;
	}

	/*----- END OF STANDARD KARA METHODS! BELOW ARE JUST SOME HELPER METHODS ----- */

	/**
	 * Shows a popup with a warning message containing both the english or
	 * german message.
	 */
	void showWarning(String englishMessage, String germanMessage) {
		String message = "<html>" + englishMessage + "<p><i>" + germanMessage
				+ "</i></html>";

		Object[] options = { "OK", "Exit Program" };
		int choice = JOptionPane.showOptionDialog(null, message, "Warning",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[0]);

		if (choice == 1) {
			// Emergency stop. Greenfoot should restart after this.
			System.exit(0);
		} else {
			// Stop. This will still finish the act()-method.
			Greenfoot.stop();
			// Repaint, otherwise the world might keep displaying a dialog in
			// some cases
			getWorld().repaint();
		}
	}
	
	/**
	 * Finds an object in the specified direction.
	 * 
	 * @param direction
	 *            the direction in which to look for the object
	 * @param steps
	 *            number of cells to look ahead (1 means the next field, etc.)
	 * @param clazz
	 *            the (actor) class to look for
	 * @return the object that was found or null if none was found
	 */
	Object getObjectInFront(int direction, int steps, Class<?> clazz) {
		int x = getX();
		int y = getY();

		// Change x und y depending on the direction
		switch (direction) {
		case DIRECTION_RIGHT:
			x = modulo((x + steps), getWorld().getWidth());
			break;

		case DIRECTION_DOWN:
			y = modulo((y + steps), getWorld().getHeight());
			break;

		case DIRECTION_LEFT:
			x = modulo((x - steps), getWorld().getWidth());
			break;

		case DIRECTION_UP:
			y = modulo((y - steps), getWorld().getHeight());
			break;

		default: // Not a valid direction
			return null;
		}

		List<?> objects = getWorld().getObjectsAt(x, y, clazz);

		if (objects != null && objects.size() > 0) {
			return objects.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Moves the actor one step in the specified direction.
	 * 
	 * @param actor
	 *            the actor to be moved
	 * @param direction
	 *            the direction to move
	 */
	private void moveActor(Actor actor, int direction) {
		switch (direction) {
		case DIRECTION_RIGHT:
			actor.setLocation(
					modulo((actor.getX() + 1), getWorld().getWidth()),
					actor.getY());
			break;

		case DIRECTION_DOWN:
			actor.setLocation(actor.getX(),
					modulo((actor.getY() + 1), getWorld().getHeight()));
			break;

		case DIRECTION_LEFT:
			actor.setLocation(
					modulo((actor.getX() - 1), getWorld().getWidth()),
					actor.getY());
			break;

		case DIRECTION_UP:
			actor.setLocation(actor.getX(),
					modulo((actor.getY() - 1), getWorld().getHeight()));
			break;

		default: // Not a valid direction
			break;
		}
	}

	/**
	 * A special modulo operation that never returns a negative number. This is
	 * necessary to always stay inside the grid of the world.
	 * <p>
	 * The Java modulo operation would return -1 for something like -1%10, but
	 * we would need 9.
	 * <p>
	 * Note: Depending on the programming language, the modulo operation for
	 * negative numbers is defined differently.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return the result of the modulo operation, always positive
	 */
	private int modulo(int a, int b) {
		return (a % b + b) % b;
	}
}
