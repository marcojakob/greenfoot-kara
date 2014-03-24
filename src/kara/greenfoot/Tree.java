package kara.greenfoot;

import greenfoot.*;

/**
 * A tree is a barrier for Kara. Kara can neither move through nor push trees.
 * 
 * @author Marco Jakob (http://code.makery.ch)
 */
public class Tree extends Actor {
	
	/**
	 * This method is called when a new Tree is added to the world. It checks
	 * whether it is ok to put it there.
	 */
	protected void addedToWorld(World world) {
		if (getOneObjectAtOffset(0, 0, Kara.class) != null
				|| getOneObjectAtOffset(0, 0, Mushroom.class) != null
				|| getOneObjectAtOffset(0, 0, Leaf.class) != null
				|| getOneObjectAtOffset(0, 0, Tree.class) != null) {
			// There is something in the way, remove it again
			world.removeObject(this);
		}
	}

	/**
	 * Overriding setLocation(...) of the Actor class to prevent dragging the
	 * Tree on a Kara, Mushroom, Leaf, or another Tree.
	 */
	public void setLocation(int x, int y) {
		if (getWorld().getObjectsAt(x, y, Kara.class).isEmpty()
				&& getWorld().getObjectsAt(x, y, Mushroom.class).isEmpty()
				&& getWorld().getObjectsAt(x, y, Leaf.class).isEmpty()
				&& getWorld().getObjectsAt(x, y, Tree.class).isEmpty()) {
			// Nothing is in the way, we can set the location
			super.setLocation(x, y);
		}
	}
}
