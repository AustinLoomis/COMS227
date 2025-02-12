package hw1;
/**
 * @author Austin Loomis
 * <p>
 * This code simulates how a hot air balloon acts with certain variables such as wind, 
 * temperature and fuel. 
 */

public class Balloon {
	private double airTemp; /** air temperature as it updates */
	private double origAirTemp; /** original air temperature */
	private double windDirection; /** direction of the wind */
	private double oldFuel = 0; /** fuel of the balloon before updating */
	private double burnRate; /**burn rate of fuel */ 
	private double balloonMass; /** Balloon Mass */
	private double balloonTemp; /** temperature of the balloon */
	private double velocity = 0; /** original velocity of the balloon */
	private double altitude = 0; /** original altitude of the balloon */
	private double tether; /**length of the tether holding the balloon to the ground*/
	private long timePassed; /** the amount of time passed during the simulation*/

	final private double HEAT_LOSS = 0.1; /** constant heat loss*/
	final private double BALLOON_VOLUME = 61234; /** constant volume of the balloon*/
	final private double ACCELERATION_GRAVITY = 9.81; /** constant gravity*/
	final private double GAS = 287.05; /** constant gas in balloon*/
	final private double PRESSURE = 1013.25; /** constant pressure*/
	final private double KELVIN_AT_0C = 273.15; /** constant Kelvin at 0 degrees Celsius*/

	/**
	 * Initializes a new Balloon object. This constructor updates the air temperature, 
	 * wind direction, outside air temperature,
	 * and balloon temperature.
	 * @param airTemp The air temperature.
	 * @param windDirection The wind direction.
	 */	
	public Balloon(double airTemp, double windDirection) {
		this.airTemp = airTemp;
		this.windDirection = windDirection;
		origAirTemp = airTemp;
		balloonTemp = airTemp;
	}	
	/** 
	 * Gets the fuel remaining in the balloon.
	 * @return The Original fuel.
	 */
	public double getFuelRemaining() { 
		return oldFuel;
	}
	/**
	 * Sets the fuel remaining in the balloon.
	 * @param fuel The original fuel.
	 */
	public void setFuelRemaning(double fuel) {
		oldFuel = fuel;
	}
	/**
	 * Gets the balloon mass.
	 * @return The balloon mass.
	 */
	public double getBalloonMass() {
		return balloonMass;
	}
	/**
	 * Sets the balloon mass.
	 * @param fuel The fuel in the balloon.
	 */
	public void setBalloonMass(double fuel) {
		balloonMass = fuel;
	}
	/**
	 * Gets the outside air temperature.
	 * @return The air temperature.
	 */
	public double getOutsideAirTemp() {
		return airTemp;
	}
	/**
	 * Sets the outside air temperature.
	 * @param temp The air temperature
	 */
	public void setOutsideAirTemp(double temp) {
		airTemp = temp;
	}
	/**
	 * Gets the fuel burn rate.
	 * @return The burn rate of fuel.
	 */
	public double getFuelBurnRate() {
		return burnRate;
	}
	/**
	 * Sets the fuel burn rate.
	 * @param rate The fuel burn rate.
	 */
	public void setFuelBurnRate(double rate) {
		burnRate = rate;
	}
	/**
	 * Gets the balloon temperature.
	 * @return The temperature of the balloon.
	 */
	public double getBalloonTemp() {
		return balloonTemp;
	}
	/**
	 * Sets the balloon temperature.
	 * @param temp The temperature of the air.
	 */
	public void setBalloonTemp(double temp) {
		balloonTemp = temp;
	}
	/**
	 * Gets the velocity of the balloon.
	 * @return The velocity of the balloon.
	 */
	public double getVelocity() {
		return velocity;
	}
	/**
	 * Gets the altitude of the balloon.
	 * @return The altitude of the balloon.
	 */
	public double getAltitude() {
		return altitude;
	}
	/**
	 * Gets the tether length.
	 * @return Tether length.
	 */
	public double getTetherLength() {
		return tether;
	}
	/**
	 * Gets the amount of tether length remaining when the balloon is in the air.
	 * @return Tether length remaining.
	 */
	public double getTetherRemaining() {
		return tether - altitude;
	}

	/**
	 * Sets the tether length.
	 * @param length The final length of the tether.
	 */
	public void setTetherLength(double length) {
		tether = length;
	}
	/**
	 * Gets the wind direction.
	 * @return The direction of the wind.
	 */
	public double getWindDirection() {
		return windDirection;
	}

	/**
	 * Sets the wind direction.
	 * @param deg The direction of the wind.
	 */
	public void changeWindDirection(double deg) {
		windDirection = (windDirection + deg) % 360;
		windDirection = (windDirection + 360) % 360;
	}
	/**
	 * Gets how many minutes have passed.
	 * @return how many minutes have passed.
	 */
	public long getMinutes() {
		return timePassed/60;
	}
	/**
	 * Gets how many seconds have passed.
	 * @return How many seconds have passed.
	 */
	public long getSeconds() {
		return timePassed%60;
	}
	/**
	 * Updates the balloon's statistics as time passes.
	 */
	public void update() {
		timePassed++;
		double fuelUsed = Math.min(burnRate, oldFuel); /** The amount of fuel used in the current time */
		oldFuel -= fuelUsed;
		double deltaT = fuelUsed + (airTemp - balloonTemp) * HEAT_LOSS; /** The change in temperature of the balloon */
		balloonTemp += deltaT;
		double airPressure = PRESSURE / (GAS * (airTemp + KELVIN_AT_0C)); /** The pressure of the air */
		double balloonPressure = PRESSURE / (GAS * (balloonTemp + KELVIN_AT_0C)); /** The pressure inside the balloon */
		double liftForce = BALLOON_VOLUME * (airPressure - balloonPressure) * ACCELERATION_GRAVITY; /** The force lifting the balloon */
		double gravityForce = balloonMass * ACCELERATION_GRAVITY; /** The force of gravity acting on the balloon */
		double upNetForce = liftForce - gravityForce; /** The net upward force on the balloon */
		double upNetAccel = upNetForce / balloonMass; /** The net upward acceleration of the balloon */
		velocity += upNetAccel;
		altitude += velocity;
		altitude = Math.min(tether, Math.max(altitude, 0));
	}
	/**
	 * Resets all values to their original state.
	 */
	public void reset() {
		oldFuel = 0;
		burnRate = 0;
		balloonMass = 0;
		altitude = 0;
		velocity = 0;
		airTemp = origAirTemp;
		balloonTemp = airTemp;
	}	
}