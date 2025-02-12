package hw4;

/**
 * Moving element in which the vertical velocity is adjusted each frame by a
 * gravitational constant to simulate gravity. The element can be set to
 * "grounded", meaning gravity will no longer influence its velocity.
 * 
 * @author Austin Loomis
 */
public class FlyingElement extends MovingElement {
	/**
	 * Is it on the ground?
	 */
	private boolean grounded;
	/**
	 * Gravity variable
	 */
	private double gravity;

	/**
	 * Constructs a new FlyingElement. By default it should be grounded, meaning
	 * gravity does not influence its velocity.
	 * 
	 * @param x      x-coordinate of upper left corner
	 * @param y      y-coordinate of upper left corner
	 * @param width  element's width
	 * @param height element's height
	 */
	public FlyingElement(double x, double y, int width, int height) {
		super(x, y, width, height);
		this.grounded = true;
		this.gravity = 0;
	}

	/**
	 * Is it on the ground?
	 * 
	 * @return is it grounded
	 */
	public boolean isGrounded() {
		return grounded;
	}

	/**
	 * If it's on the ground it is on the ground
	 * 
	 * @param grounded
	 */
	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}

	/**
	 * Sets gravity
	 * 
	 * @param gravity
	 */
	public void setGravity(double gravity) {
		this.gravity = gravity;
	}

	/**
	 * Updates position and adds frame rate
	 */
	@Override
	public void update() {
		addFrameCount();
		if (grounded) {
			this.setPosition(this.getXReal() + getDeltaX(), this.getYReal() + getDeltaY());
		} else {
			this.setPosition(this.getXReal() + getDeltaX(), this.getYReal() + getDeltaY());
			this.setVelocity(getDeltaX(), getDeltaY() + gravity);
		}
	}
}