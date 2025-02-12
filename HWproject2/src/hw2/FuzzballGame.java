package hw2;

/**
 * Represents a simplified baseball-like game called Fuzzball.
 * This class models the game mechanics and scoring system.
 * 
 * @author Austin Loomis
 */
public class FuzzballGame {
    /**
     * Maximum number of strikes that result in a player being declared out.
     */
	
    public static final int MAX_STRIKES = 2;
    /**
     * Maximum number of balls that allow a player to walk to the next base.
     */
    
    public static final int MAX_BALLS = 5;
    /**
     * Number of outs before the teams switch.
     */
    
    public static final int MAX_OUTS = 3;
    
    private int currentStrikes; // Current count of strikes for the current batter.
    private int currentBalls; // Current count of balls for the current batter.
    private int currentOuts; // Current count of outs for the current half inning.
    private int team0Score; // Current score for Team 0.
    private int team1Score; // Current score for Team 1.
    private int round; // Current round or inning in the game.
    private int maxInnings; // Maximum number of innings for the game.
    private boolean gameEnded; // Indicates whether the game has ended.
    private boolean[] base = new boolean[3]; // array that keeps track of who is on which base.

    /**
     * Constructs a Fuzzball game with the given number of innings.
     * @param givenInnings The number of innings for the game.
     */
    public FuzzballGame(int givenInnings) {
        // Initialize game state variables
        currentStrikes = 0;
        currentBalls = 0;
        currentOuts = 0;
        team0Score = 0;
        team1Score = 0;
        round = 0;
        // Initialize bases to be empty
        base[0] = false;
        base[1] = false;
        base[2] = false;
        // Set the maximum number of innings for the game
        maxInnings = givenInnings;
        // Set gameEnded to false as the game starts
        gameEnded = false;
    }

    /**
     * Resets the current batter's strike and ball counters.
     */
    private void resetBatter() {
        currentStrikes = 0;
        currentBalls = 0;
    }

    /**
     * Switches the teams, resets the outs, and increments the round.
     * If the game reaches the maximum innings, sets the game as ended.
     */
    private void switchTeam() {
        currentOuts = 0;
        resetBatter();
        round++;
        if (round / 2 == maxInnings) {
            gameEnded = true;
        }
        for (int i = 0; i < base.length; i++) {
            base[i] = false;
        }
    }

    /**
     * Gives a point to the team that is batting.
     */
    private void giveTeamPoint() {
        if (round % 2 == 0) {
            team0Score++;
        } else {
            team1Score++;
        }
    }

    /**
     * Moves the runners on bases based on the number of bases taken.
     * @param bases The number of bases the batter advances.
     */
    private void advanceRunners(int bases) {
        for (int i = base.length; i > 0; i--) {
            if (base[i - 1]) {
                if (i + bases >= 4) {
                    giveTeamPoint();
                    base[i - 1] = false;
                } else {
                    base[i - 1] = false;
                    base[i - 1 + bases] = true;
                }
            }
        }
        if (bases < 4) {
            base[bases - 1] = true;
        } else {
            giveTeamPoint();
        }
    }

    /**
     * Handles the case when the batter walks. If all bases are occupied, gives a point to the team.
     */
    private void walk() {
        resetBatter();
        for (int i = 0; i < base.length; i++) {
            if (!base[i]) {
                base[i] = true;
                return;
            }
        }
        giveTeamPoint();
    }

    /**
     * Handles the case when the batter goes out. Resets the batter and increments the outs.
     * If the maximum outs are reached, switches teams.
     */
    private void out() {
        resetBatter();
        currentOuts++;
        if (currentOuts >= MAX_OUTS) {
            switchTeam();
        }
    }

    /**
     * Indicates a ball pitch. Adds 1 to the batter's count of balls, possibly resulting in a walk. 
     * Does nothing if the game has ended.
     */
    public void ball() {
        if (gameEnded) {
            return;
        }
        currentBalls++;
        if (currentBalls >= MAX_BALLS) {
            walk();
        }
    }

    /**
     * Indicates a caught fly. Does nothing if the game has ended.
     */
    public void caughtFly() {
        if (gameEnded) {
            return;
        }
        out();
    }

    /**
     * Indicates that the batter hit the ball. Handles different situations based on the hit distance.
     * @param distance The distance the ball travels.
     */
    public void hit(int distance) {
        if (gameEnded) {
            return;
        }
        if (distance >= 15) {
            resetBatter();
        }
        if (distance < 15) {
            out();
        } else if (distance < 150) {
            advanceRunners(1);
        } else if (distance < 200) {
            advanceRunners(2);
        } else if (distance < 250) {
            advanceRunners(3);
        } else {
            advanceRunners(4);
        }
    }

    /**
     * Indicates a strike for the current batter. If swung is true, the batter is immediately out.
     * Otherwise, adds 1 to the batter's current count of called strikes, possibly resulting in the batter being out.
     * Does nothing if the game has ended.
     * @param swung True if the batter swung at the pitch, false if it's a "called" strike.
     */
    public void strike(boolean swung) {
        if (gameEnded) {
            return;
        }
        if (swung) {
            out();
        } else {
            currentStrikes++;
            if (currentStrikes >= MAX_STRIKES) {
                out();
            }
        }
    }

    /**
     * Checks if the game has ended.
     * @return True if the game is over, false otherwise.
     */
    public boolean gameEnded() {
        return gameEnded;
    }

    /**
     * Checks if it's the first half of the inning (team 0 is at bat).
     * @return True if it's the first half of the inning, false otherwise.
     */
    public boolean isTopOfInning() {
        return (round % 2 == 0);
    }

    /**
     * Checks if there is a runner on the indicated base.
     * @param which The base number to check.
     * @return True if there is a runner on the indicated base, false otherwise.
     */
    public boolean runnerOnBase(int which) {
        return base[which - 1];
    }

    /**
     * Gets the current number of balls for the current batter.
     * @return The current number of balls.
     */
    public int getBallCount() {
        return currentBalls;
    }

    /**
     * Gets the current number of called strikes for the current batter.
     * @return The current number of called strikes.
     */
    public int getCalledStrikes() {
        return currentStrikes;
    }

    /**
     * Gets the current number of outs for the current half inning.
     * @return The current number of outs.
     */
    public int getCurrentOuts() {
        return currentOuts;
    }

    /**
     * Gets the score for Team 0.
     * @return The score for Team 0.
     */
    public int getTeam0Score() {
        return team0Score;
    }

    /**
     * Gets the score for Team 1.
     * @return The score for Team 1.
     */
    public int getTeam1Score() {
        return team1Score;
    }

    /**
     * Gets the current inning number.
     * @return The current inning number.
     */
    public int whichInning() {
        return round / 2 + 1;
    }

    /**
     * Gets a string representation of the current base occupancy.
     * @return A string indicating the occupancy of bases (X for occupied, o for empty).
     */
    public String getBases() {
        return (runnerOnBase(1) ? "X" : "o") + (runnerOnBase(2) ? "X" : "o") + (runnerOnBase(3) ? "X" : "o");
    }

    /**
     * Gets a string representation of the current state of the game.
     * @return A formatted string with information about the current game state.
     */
    public String toString() {
        String bases = getBases();
        String topOrBottom = (isTopOfInning() ? "T" : "B");
        return String.format("Bases:%s Inning:%d (%s) Score:%d-%d Balls:%d Strikes:%d Outs:%d",
                bases, whichInning(), topOrBottom, getTeam0Score(), getTeam1Score(),
                getBallCount(), getCalledStrikes(), getCurrentOuts());
    }
}
