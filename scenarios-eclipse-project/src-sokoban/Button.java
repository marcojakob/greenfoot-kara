import greenfoot.*;
import java.awt.Color;
import java.awt.Font;

/**
 * A simple button.
 * 
 * @author Marco Jakob (Button inspired by Taylor Borne)
 */
public class Button extends Label {
	private boolean hover = false;
	private boolean pressed = false;
	private boolean clicked = false;
	private boolean enabled = true;

	/**
	 * Constructor for a button.
	 */
	public Button(String text, int width, int height, Font font) {
		super(text, width, height, font);
	}

	/**
	 * Constructor for a button with only an icon.
	 */
	public Button(int width, int height, GreenfootImage icon) {
		super(width, height, icon);
	}

	/**
	 * This method is called by Greenfoot. Handles mouse events.
	 */
	public void act() {
		if (enabled) {
			boolean last = hover;
			boolean lastP = pressed;
			if (Greenfoot.mouseMoved(this)) {
				hover = true;
			} else if (Greenfoot.mouseMoved(null)) {
				hover = false;
			}
			if (Greenfoot.mousePressed(this)) {
				pressed = true;
			}
			if (Greenfoot.mouseClicked(this) /* && hover */) {
				clicked = true;
			}
			if (Greenfoot.mouseClicked(null) || Greenfoot.mouseDragEnded(null)) {
				pressed = false;
			}
			if (last != hover || lastP != pressed) {
				// state changed
				createImage();
			}
		}
	}

	/**
	 * Override the getter of the superclass to support reaction to mouse
	 * events.
	 */
	public Color getTextColor() {
		if (!enabled) {
			return new Color(102, 102, 102);
		}

		return super.getTextColor();
	}

	/**
	 * Override the getter of the superclass to support reaction to mouse
	 * events.
	 */
	public Color getBorderColor() {
		if (pressed) {
			if (super.getBorderColor() != null) {
				if (isBrightColor(super.getBorderColor())) {
					return Color.DARK_GRAY;
				} else {
					return Color.LIGHT_GRAY;
				}
			} else {
				return Color.GRAY;
			}
		}

		return super.getBorderColor();
	}

	/**
	 * Override the getter of the superclass to support reaction to mouse
	 * events.
	 */
	public Color getBackgroundColor() {
		if (hover) {
			if (isBrightColor(super.getBackgroundColor())) {
				return super.getBackgroundColor().darker();
			} else {
				return super.getBackgroundColor().brighter();
			}
		}

		return super.getBackgroundColor();
	}

	/**
	 * Returns whether the Button was clicked.
	 */
	public boolean wasClicked() {
		boolean c = clicked;
		clicked = false;
		return c;
	}

	/**
	 * This method is called by Greenfoot when this Button was added to the
	 * world.
	 */
	public void addedToWorld(World world) {
		hover = false;
		createImage();
	}

	/**
	 * Set whether the Button is enabled.
	 */
	public void setEnable(boolean enabled) {
		this.enabled = enabled;
		createImage();
	}

	/**
	 * Returns whether the specified color is a bright color.
	 */
	private boolean isBrightColor(Color color) {
		return (color.getRed() + color.getGreen() + color.getBlue()) > 384;
	}
}