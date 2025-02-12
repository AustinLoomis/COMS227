package hw4;

/**
 * An element that does not move. Instead, it is intended to appear on the
 * screen for a fixed number of frames.
 * 
 * @author Austin Loomis
 */
public class VanishingElement extends SimpleElement {
	/**
	 * How many frames the object should be in view
	 */
	private int inView;

	/**
	 * Constructs a new VanishingElement.
	 * 
	 * @param x           x-coordinate of upper left corner
	 * @param y           y-coordinate of upper left corner
	 * @param width       element's width
	 * @param height      element's height
	 * @param initialLife the number of frames until this element marks itself for
	 *                    deletion
	 */
	public VanishingElement(double x, double y, int width, int height, int initialLife) {
		super(x, y, width, height);
		this.inView = initialLife;
	}

	/**
	 * Updates the position adds frame rate, keeps track if object is in view
	 */
	@Override
	public void update() {
		addFrameCount();
		inView--;
		if (inView < 1) {
			this.markForDeletion();
		}
	}

}