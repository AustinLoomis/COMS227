package hw4;

/**
 * An element in which the <code>update</code> method updates the position each
 * frame according to a <em>velocity</em> vector (deltaX, deltaY). The units are
 * assumed to be "pixels per frame".
 * 
 * @author Austin Loomis
 */
public class MovingElement extends SimpleElement {
	/**
	 * Change in x-axis
	 */
	private double deltaX;
	/**
	 * Change in the y-axis
	 */
	private double deltaY;

	/**
	 * Constructs a MovingElement with a default velocity of zero in both
	 * directions.
	 * 
	 * @param x      x-coordinate of upper left corner
	 * @param y      y-coordinate of upper left corner
	 * @param width  object's width
	 * @param height object's height
	 */
	public MovingElement(double x, double y, int width, int height) {
		super(x, y, width, height);
	}

	/**
	 * Gets Change in x-axis
	 * 
	 * @return deltaX
	 */
	public double getDeltaX() {
		return deltaX;
	}

	/**
	 * Gets Change in y-axis
	 * 
	 * @return deltaY
	 */
	public double getDeltaY() {
		return deltaY;
	}

	/**
	 * Sets change in x-axis and y-axis
	 * 
	 * @param deltaX
	 * @param deltaY
	 */
	public void setVelocity(double deltaX, double deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	/**
	 * Updates the position adds frame rate
	 */
	@Override
	public void update() {
		addFrameCount();
		this.setPosition(this.getXReal() + getDeltaX(), this.getYReal() + getDeltaY());
	}

}