import greenfoot.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * This is a Label.
 * 
 * @author Marco Jakob (Label inspired by Taylor Borne)
 */
public class Label extends Actor {
	public static final int ALIGN_LEFT = 0;
	public static final int ALIGN_CENTER = 1;
	public static final int ALIGN_RIGHT = 2;

	protected int width;
	protected int height;
	private Font font = GameScreen.FONT_S;
	private Color backgroundColor = Color.WHITE;
	private int backgroundTransparency = 255;
	private Color textColor = Color.BLACK;
	private Color borderColor = Color.BLACK;
	private GreenfootImage icon = new GreenfootImage(1, 1);
	private boolean iconVisible = false;
	private boolean visible = true;
	private int align = ALIGN_CENTER;
	private int inset = 8;

	private String text = "";

	/**
	 * Constructor for a label.
	 */
	public Label(String text, int width, int height, Font font) {
		this.text = text;
		this.width = width;
		this.height = height;
		this.font = font;

		createImage();
	}

	/**
	 * Constructor for a label with only an icon.
	 */
	public Label(int width, int height, GreenfootImage icon) {
		this.width = width;
		this.height = height;

		setIcon(icon);

		createImage();
	}

	/**
	 * Sets whether this label should be visible or not.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
		createImage();
	}

	/**
	 * Returns true if this label is visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Sets the alignement of the text and icon. ALIGN_LEFT, ALIGN_CENTER or
	 * ALIGN_RIGHT.
	 */
	public void setAlign(int align) {
		this.align = align;
		createImage();
	}

	/**
	 * Sets the inset to the border and between text and icon.
	 */
	public void setInset(int inset) {
		this.inset = inset;
		createImage();
	}

	/**
	 * Sets the icon.
	 */
	public void setIcon(GreenfootImage icon) {
		this.icon = icon;
		if (icon != null) {
			iconVisible = true;
		} else {
			iconVisible = false;
		}
		createImage();
	}

	/**
	 * Set if the icon should be visible.
	 */
	public void setIconVisible(boolean visible) {
		this.iconVisible = visible;
		createImage();
	}

	/**
	 * Returns whether the icon is visible.
	 */
	public boolean isIconVisible() {
		return iconVisible;
	}

	/**
	 * Returns the label text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set the text for the label.
	 */
	public void setText(String text) {
		this.text = text;
		createImage();
	}

	/**
	 * Set the font.
	 */
	public void setFont(Font font) {
		this.font = font;
		createImage();
	}

	/**
	 * Returns the font.
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * Set the background color.
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		createImage();
	}

	/**
	 * Returns the background color.
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Set the background transparancy (between 0 and 255).
	 */
	public void setBackgroundTransparency(int backgroundTransparency) {
		this.backgroundTransparency = backgroundTransparency;
		createImage();
	}

	/**
	 * Returns the background transparency.
	 */
	public int getBackgroundTransparency() {
		return backgroundTransparency;
	}

	/**
	 * Set the color of the text.
	 */
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
		createImage();
	}

	/**
	 * Returns the text color.
	 */
	public Color getTextColor() {
		return textColor;
	}

	/**
	 * Set the color of the border. If set to null, no border is created.
	 */
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
		createImage();
	}

	/**
	 * Returns the border color.
	 */
	public Color getBorderColor() {
		return borderColor;
	}

	/**
	 * Paint the background. Is returned as a GreenfootImage.
	 */
	protected GreenfootImage paintBackground() {
		GreenfootImage back = new GreenfootImage(width, height);
		back.setColor(getBackgroundColor());
		back.fill();
		back.setTransparency(getBackgroundTransparency());
		return back;
	}

	/**
	 * Paint the border. Is returned as a GreenfootImage.
	 */
	protected GreenfootImage paintBorder() {
		GreenfootImage pic = new GreenfootImage(width, height);
		if (getBorderColor() != null) {
			pic.setColor(getBorderColor());
			pic.drawRect(0, 0, width - 1, height - 1);
		}
		return pic;
	}

	/**
	 * Paint the text. Is returned as a GreenfootImage.
	 */
	protected GreenfootImage paintTextAndIcon() {
		GreenfootImage pic = new GreenfootImage(width, height);
		Graphics2D graphics = (new GreenfootImage(1, 1)).getAwtImage()
				.createGraphics();
		graphics.setFont(getFont());
		FontMetrics fm = graphics.getFontMetrics();
		pic.setColor(getTextColor());
		pic.setFont(getFont());

		// calculate x positions of text and icon
		int textX = 0;
		int iconX = 0;
		switch (align) {
		case ALIGN_LEFT:
			textX = inset;
			if (iconVisible && icon != null) {
				textX += icon.getWidth() + inset;
				iconX = inset;
			}
			break;

		case ALIGN_CENTER:
			textX = (width - fm.charsWidth((getText()).toCharArray(), 0,
					(getText()).length())) / 2;
			if (iconVisible && icon != null) {
				iconX = textX - (icon.getWidth() / 2) - (inset / 2);
				textX += icon.getWidth() / 2 + (inset / 2);
			}
			break;

		case ALIGN_RIGHT:
			textX = (width - fm.charsWidth((getText()).toCharArray(), 0,
					(getText()).length())) - inset;
			if (iconVisible && icon != null) {
				iconX = textX - icon.getWidth() - inset;
			}
			break;
		}

		// calculate y positions for text and icon
		int textY = (height / 2 - 1) - ((fm.getAscent() + fm.getDescent()) / 2)
				+ fm.getAscent();
		int iconY = 0;
		if (iconVisible && icon != null) {
			iconY = height / 2 - icon.getHeight() / 2 + 1;
			pic.drawImage(icon, iconX, iconY);
		}

		if (!getText().isEmpty()) {
			pic.drawString(getText(), textX, textY);
		}

		graphics.dispose();
		return pic;
	}

	/**
	 * Paint and return the Image.
	 */
	protected GreenfootImage paintImage() {
		GreenfootImage pic = new GreenfootImage(width, height);

		pic.drawImage(paintBackground(), 0, 0);
		pic.drawImage(paintTextAndIcon(), 0, 0);
		pic.drawImage(paintBorder(), 0, 0);

		return pic;
	}

	/**
	 * Create the image.
	 */
	public void createImage() {
		if (visible) {
			setImage(paintImage());
		} else {
			setImage((GreenfootImage) null);
		}
	}
}
