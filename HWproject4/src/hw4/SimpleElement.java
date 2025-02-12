package hw4;

import java.awt.Rectangle;
import api.AbstractElement;

/**
 * Minimal concrete extension of AbstractElement. The <code>update</code> method
 * in this implementation just increments the frame count.
 * 
 * @author Austin Loomis
 */
public class SimpleElement extends AbstractElement {
	/**
	 * width of an object
	 */
	private int width;
	/**
	 * height of an object
	 */
	private int height;
	/**
	 * x-axis coordinate 
	 */
	private double x;
	/**
	 * y-axis coordinate 
	 */
	private double y;
	/**
	 * Keeping track of when the object is in view
	 */
	private int frameCount;
	/**
	 * Is the object there?
	 */
	private boolean delete;
	/**
	 * Making the object shape
	 */
	private Rectangle rect;

	/**
	 * Constructs a new SimpleElement.
	 * 
	 * @param x      x-coordinate of upper left corner
	 * @param y      y-coordinate of upper left corner
	 * @param width  element's width
	 * @param height element's height
	 */
	public SimpleElement(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rect = new Rectangle(getXInt(), getYInt(), width, height);
		this.frameCount = 0;
		this.delete = false;
	}

	/**
	 * Gets x-axis coordinate
	 */
	public int getXInt() {
		return (int) x;
	}

	/**
	 * Gets y-axis coordinate
	 */
	public int getYInt() {
		return (int) y;
	}

	/**
	 * Gets width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Gets height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets position
	 */
	public void setPosition(double newX, double newY) {
		x = newX;
		y = newY;
		rect.setLocation(getXInt(), getYInt());
	}

	/**
	 * Gets rectangle as an object
	 */
	public Rectangle getRect() {
		return rect;
	}

	/**
	 * Gets real x coordinate
	 */
	@Override
	public double getXReal() {
		return x;
	}

	/**
	 * Gets real y coordinate
	 */
	@Override
	public double getYReal() {
		return y;
	}

	/**
	 * Updates screen using frame count
	 */
	@Override
	public void update() {
		addFrameCount();
	}

	/**
	 * Gets frame count
	 */
	@Override
	public int getFrameCount() {
		return frameCount;
	}

	/**
	 * Gets is an object needs to be deleted
	 */
	@Override
	public boolean isMarked() {
		return delete;
	}

	/**
	 * Marks object for deletion
	 */
	@Override
	public void markForDeletion() {
		delete = true;
	}

	/**
	 * Is the object running into another?
	 */
	@Override
	public boolean collides(AbstractElement other) {
		double x1 = getXReal();
		double y1 = getYReal();
		int w1 = getWidth();
		int h1 = getHeight();

		double x2 = other.getXReal();
		double y2 = other.getYReal();
		int w2 = other.getWidth();
		int h2 = other.getHeight();

		return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
	}

	protected void addFrameCount() {
		frameCount++;
	}

}