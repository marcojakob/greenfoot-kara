package kara.greenfoot;

import greenfoot.*;

/**
 * A leaf can be put and removed by Kara.
 * 
 * @author Marco Jakob (http://code.makery.ch)
 */
public class Leaf extends Actor {
	
	/**
	 * This method is called when a new Leaf is added to the world. It checks
	 * whether it is ok to put it there.
	 */
	protected void addedToWorld(World world) {
		if (getOneObjectAtOffset(0, 0, Leaf.class) != null
				|| getOneObjectAtOffset(0, 0, Tree.class) != null) {
			// There is something in the way, remove it again
			world.removeObject(this);
		}
	}

	/**
	 * Overriding setLocation(...) of the Actor class to prevent dragging the
	 * Leaf on a Tree or another Leaf.
	 */
	public void setLocation(int x, int y) {
		if (getWorld().getObjectsAt(x, y, Leaf.class).isEmpty()
				&& getWorld().getObjectsAt(x, y, Tree.class).isEmpty()) {
			// Nothing is in the way, we can set the location
			super.setLocation(x, y);
		}
	}
}
