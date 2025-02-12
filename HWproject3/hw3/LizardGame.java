package hw3;

import java.util.ArrayList;
import api.*;

/*
 * @author Austin Loomis
 */

/**
 * Class that models the Lizard game.
 */
public class LizardGame {
	private ShowDialogListener dialogListener;
	private ScoreUpdateListener scoreListener;
	private int width;
	private int height;
	private ArrayList<Lizard> lizards;
	private Cell[][] grid;

	/**
	 * Constructs a new Lizard Game object with given grid dimensions.
	 *
	 * @param width  number of columns
	 * @param height number of rows
	 */
	public LizardGame(int width, int height) {
		// Implementation
		this.width = width;
		this.height = height;
		this.grid = new Cell[width][height];
		this.lizards = new ArrayList<Lizard>();
		Cell tempCell;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tempCell = new Cell(i, j);
				grid[i][j] = tempCell;
			}
		}
	}

	/**
	 * Get the grid's width.
	 *
	 * @return width of the grid
	 */
	public int getWidth() {
		// Implementation
		return width;
	}

	/**
	 * Get the grid's height.
	 *
	 * @return height of the grid
	 */
	public int getHeight() {
		// Implementation
		return height;
	}

	/**
	 * Adds a wall to the grid.
	 *
	 * @param wall to add
	 */
	public void addWall(Wall wall) {
		// Implementation
		wall.getCell().placeWall(wall);
	}

	/**
	 * Adds an exit to the grid.
	 *
	 * @param exit to add
	 */
	public void addExit(Exit exit) {
		exit.getCell().placeExit(exit);
		// Implementation
	}

	/**
	 * Gets a list of all lizards on the grid. Does not include lizards that have
	 * exited.
	 *
	 * @return lizards list of lizards
	 */
	public ArrayList<Lizard> getLizards() {
		// Implementation
		return lizards;
	}

	/**
	 * Adds the given lizard to the grid.
	 *
	 * @param lizard to add
	 */
	public void addLizard(Lizard lizard) {
		// Implementation
		lizards.add(lizard);
		scoreListener.updateScore(lizards.size());
	}

	/**
	 * Removes the given lizard from the grid. Be aware that each cell object knows
	 * about a lizard that is placed on top of it. It is expected that this method
	 * updates all cells that the lizard used to be on, so that they now have no
	 * lizard placed on them.
	 *
	 * @param lizard to remove
	 */
	public void removeLizard(Lizard lizard) {
		// Remove the lizard from the list of lizards
		lizards.remove(lizard);
		// Iterate over the grid to find and remove the lizard from cells
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Cell cell = grid[i][j];
				// Check if the cell contains the lizard to be removed
				if (cell.getLizard() != null && cell.getLizard().equals(lizard)) {
					// Remove the lizard from the cell
					cell.placeLizard(null);
				}
			}
		}
		// Update the score listener with the number of lizards left
		scoreListener.updateScore(lizards.size());
	}

	/**
	 * Gets the cell for the given column and row.
	 *
	 * @param col column of the cell
	 * @param row of the cell
	 * @return the cell or null
	 */
	public Cell getCell(int col, int row) {
		// Implementation
		if (col < 0 || col >= getWidth() || row < 0 || row >= getHeight()) {
			return null;
		}
		return grid[col][row];
	}

	/**
	 * Gets the cell that is adjacent to (one over from) the given column and row,
	 * when moving in the given direction.
	 *
	 * @param col the given column
	 * @param row the given row
	 * @param dir the direction from the given column and row to the adjacent cell
	 * @return the adjacent cell or null
	 */
	public Cell getAdjacentCell(int col, int row, Direction dir) {
		// Implementation
		// Calculate the coordinates of the adjacent cell based on the direction
		int adjacentCol = col;
		int adjacentRow = row;
		// switch case to tell direction of lizard
		switch (dir) {
		case UP:
			adjacentRow--;
			break;
		case DOWN:
			adjacentRow++;
			break;
		case LEFT:
			adjacentCol--;
			break;
		case RIGHT:
			adjacentCol++;
			break;
		}
		// Check if the adjacent cell is within the boundaries of the grid
		return getCell(adjacentCol, adjacentRow);
	}

	/**
	 * Resets the grid. After calling this method the game should have a grid of
	 * size width x height containing all empty cells. Empty means cells with no
	 * walls, exits, etc.
	 *
	 * @param width  number of columns of the resized grid
	 * @param height number of rows of the resized grid
	 */
	public void resetGrid(int width, int height) {
		// Implementation
		this.width = width;
		this.height = height;
		this.grid = new Cell[width][height];
		this.lizards = new ArrayList<Lizard>();
		Cell tempCell;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tempCell = new Cell(i, j);
				grid[i][j] = tempCell;
			}
		}
	}

	/**
	 * Returns true if a given cell location (col, row) is available for a lizard to
	 * move into.
	 *
	 * @param row of the cell being tested
	 * @param col of the cell being tested
	 * @return true if the cell is available, false otherwise
	 */
	public boolean isAvailable(int col, int row) {
		// Implementation
		// Get the cell at the given coordinates
		Cell cell = getCell(col, row);
		// Check if the cell is not null and does not contain a wall or a lizard
		return cell != null && cell.getWall() == null && cell.getLizard() == null;
	}

	/**
	 * Moves the lizard in the specified direction starting from the given position.
	 *
	 * @param col the column index of the starting position
	 * @param row the row index of the starting position
	 * @param dir the direction in which to move the lizard
	 */
	public void move(int col, int row, Direction dir) {
		Cell selectedCell = getCell(col, row);
		// can't move nothing
		if (selectedCell == null || selectedCell.getLizard() == null) {
			return;
		}
		Lizard lizard = selectedCell.getLizard();
		ArrayList<BodySegment> segments = lizard.getSegments();
		boolean MovingForward = (dir == lizard.getHeadDirection());
		Cell nextHeadCell = null;
		Cell nextTailCell = null;
		if (MovingForward) {
			nextHeadCell = getAdjacentCell(lizard.getHeadSegment().getCell().getCol(),
					lizard.getHeadSegment().getCell().getRow(), dir);
			// Moving forwards
			if (nextHeadCell != null
					&& (isAvailable(nextHeadCell.getCol(), nextHeadCell.getRow()) || nextHeadCell.getExit() != null)) {
				segments.add(new BodySegment(lizard, nextHeadCell));
				Cell oldTailCell = segments.remove(0).getCell();
				oldTailCell.removeLizard();
			}
		} else {
			nextTailCell = getAdjacentCell(lizard.getTailSegment().getCell().getCol(),
					lizard.getTailSegment().getCell().getRow(), dir);
			// moving backwards
			if (nextTailCell != null
					&& (isAvailable(nextTailCell.getCol(), nextTailCell.getRow()) || nextTailCell.getExit() != null)) {
				segments.add(0, new BodySegment(lizard, nextTailCell));
				Cell oldHeadCell = segments.remove(segments.size() - 1).getCell();
				oldHeadCell.removeLizard();
			}
		}
		// update lizard positions
		for (BodySegment segment : segments) {
			segment.getCell().placeLizard(lizard);
		}
		// Remove lizard if you hit the exit
		if ((MovingForward && nextHeadCell != null && nextHeadCell.getExit() != null)
				|| (!MovingForward && nextTailCell != null && nextTailCell.getExit() != null)) {
			removeLizard(lizard);
		}
		// Did you win?
		if (getLizards().isEmpty() && dialogListener != null) {
			dialogListener.showDialog("You win! 🎉");
		}
	}

	/**
	 * Sets callback listeners for game events.
	 *
	 * @param dialogListener listener for creating a user dialog
	 * @param scoreListener  listener for updating the player's score
	 */
	public void setListeners(ShowDialogListener dialogListener, ScoreUpdateListener scoreListener) {
		this.dialogListener = dialogListener;
		this.scoreListener = scoreListener;
	}

	/**
	 * Load the game from the given file path
	 *
	 * @param filePath location of file to load
	 */
	public void load(String filePath) {
		GameFileUtil.load(filePath, this);
	}

	/**
	 * Returns a string representation of the grid and lizards.
	 *
	 * @return string representation of the grid and lizards
	 */
	@Override
	public String toString() {
		String str = "---------- GRID ----------\n";
		str += "Dimensions:\n";
		str += getWidth() + " " + getHeight() + "\n";
		str += "Layout:\n";
		for (int y = 0; y < getHeight(); y++) {
			if (y > 0) {
				str += "\n";
			}
			for (int x = 0; x < getWidth(); x++) {
				str += getCell(x, y);
			}
		}
		str += "\nLizards:\n";
		for (Lizard l : getLizards()) {
			str += l;
		}
		str += "\n--------------------------\n";
		return str;
	}
}
