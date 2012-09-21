package kara.greenfoot.sokoban;

import greenfoot.*;
import java.awt.Font;

/**
 * A Label that can be animated to move around.
 * 
 * @author Marco Jakob (Label inspired by Taylor Borne)
 */
public class AnimatedLabel extends Label {
	public static final int LEFT_TO_RIGHT = 0;
	public static final int RIGHT_TO_LEFT = 1;
	private float xPos;
	private float speed = 0;
	private float initialSpeed = 17.5f;
	private int time = 0;
	private GreenfootImage originalImage;
	private int worldWidthInPixels;
	private boolean stopInMiddle = false;

	private static final int STATE_NONE = 0;
	private static final int STATE_RUNNING = 1;
	private static final int STATE_STOPPED = 2;
	private static final int STATE_WAITING_FOR_CONTINUE = 3;
	private static final int STATE_CONTINUED = 4;
	private static final int STATE_DONE = 5;

	private int state = STATE_NONE;

	private int direction;

	/**
	 * Constructor for an animated Label.
	 */
	public AnimatedLabel(String text, int width, int height, Font font,
			int direction) {
		super(text, width, height, font);
		this.direction = direction;
	}

	/**
	 * Constructor for an animated Label with only an icon.
	 */
	public AnimatedLabel(int width, int height, GreenfootImage icon,
			int direction) {
		super(width, height, icon);
		this.direction = direction;
	}

	/**
	 * Set the initial speed of the animation.
	 */
	public void setInitialSpeed(float initialSpeed) {
		this.initialSpeed = initialSpeed;
	}

	/**
	 * Returns true if the animation is finished.
	 */
	public boolean isDone() {
		return state == STATE_DONE;
	}

	/**
	 * Returns true if the animation was stopped.
	 */
	public boolean wasStopped() {
		if (state == STATE_STOPPED) {
			state = STATE_WAITING_FOR_CONTINUE;
			return true;
		}
		return false;
	}

	/**
	 * If set to true, the animation stops in the middle.
	 */
	public void setStopInMiddle(boolean stopInMiddle) {
		this.stopInMiddle = stopInMiddle;
	}

	/**
	 * Continues the animation after it has been stopped.
	 */
	public void continueAnimation() {
		time = 130;
		state = STATE_CONTINUED;
	}

	public void addedToWorld(World world) {
		worldWidthInPixels = world.getWidth() * world.getCellSize();
		switch (direction) {
		case LEFT_TO_RIGHT:
			xPos = -width;
			break;

		case RIGHT_TO_LEFT:
			xPos = worldWidthInPixels;
			break;
		}
		state = STATE_RUNNING;
	}

	public void act() {
		if (stopInMiddle && state == STATE_RUNNING) {
			switch (direction) {
			case LEFT_TO_RIGHT:
				if (xPos >= worldWidthInPixels / 2 - width / 2) {
					state = STATE_STOPPED;
				}
				break;

			case RIGHT_TO_LEFT:
				if (xPos <= worldWidthInPixels / 2 - width / 2) {
					state = STATE_STOPPED;
				}
				break;
			}
		}

		if (state == STATE_RUNNING || state == STATE_CONTINUED) {
			animate();

			if (xPos > worldWidthInPixels || xPos + width < 0) {
				state = STATE_DONE;
			}
		}
	}

	/**
	 * Override this method to save the original image and to prevent the Label
	 * to set the image too early.
	 */
	public void createImage() {
		this.originalImage = super.paintImage();
	}

	private void animate() {
		GreenfootImage back = new GreenfootImage(worldWidthInPixels, height);

		switch (direction) {
		case LEFT_TO_RIGHT:
			xPos += speed;
			break;

		case RIGHT_TO_LEFT:
			xPos -= speed;
			break;
		}

		back.drawImage(originalImage, Math.round(xPos), 0);
		setImage(back);
		time++;
		if (time == 12) {
			speed = initialSpeed;
		} else if (time > 12 && time < 130) {
			if (speed > .6f) {
				speed -= .5f;
			} else if (speed > .1f) {
				speed -= .01f;
			}
		} else if (time > 129) {
			if (speed < .5f) {
				speed += .03f;
			} else {
				speed += .5f;
			}
		}
	}
}