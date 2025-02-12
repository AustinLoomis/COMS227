package hw3;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import api.BodySegment;
import api.*;

/*
 * @author Austin Loomis
 */

/**
 * Utility class with static methods for loading game files.
 */
public class GameFileUtil {

	/**
	 * Loads the file at the given file path into the given game object. When the
	 * method returns, the game object has been modified to represent the loaded
	 * game.
	 *
	 * @param filePath the path of the file to load
	 * @param game the game to modify
	 */
	public static void load(String filePath, LizardGame game) {
		// Initialize scanner and file
		File file = new File(filePath);
		Scanner s = null;

		try {
			s = new Scanner(file);
		} catch (Exception e) {
			// If an exception occurs while opening the file, return without modifying the game
			return;
		}

		// Read dimensions of the game grid from the first line of the file
		String[] dimen = s.nextLine().split("x");
		int width = Integer.parseInt(dimen[0]);
		int height = Integer.parseInt(dimen[1]);
		game.resetGrid(width, height);

		// Populate the game grid with walls and exits
		for (int i = 0; i < height; i++) {
			String currLine = s.nextLine();
			for (int j = 0; j < width; j++) {
				char currChar = currLine.charAt(j);
				Cell cell = game.getCell(j, i);
				if (currChar == 'W') {
					game.addWall(new Wall(cell));
				}
				if (currChar == 'E') {
					game.addExit(new Exit(cell));
				}
			}
		}

		// Process lizard information and add lizards to the game
		while (s.hasNextLine()) {
			String line = s.nextLine();
			if (line.startsWith("L")) {
				String[] individual = line.substring(2).split(" ");
				Lizard liz = new Lizard();
				ArrayList<BodySegment> segments = new ArrayList<>();
				for (String part : individual) {
					String[] grid = part.split(",");
					int grid1 = Integer.parseInt(grid[0]);
					int grid2 = Integer.parseInt(grid[1]);
					Cell cell = game.getCell(grid1, grid2);
					BodySegment seg = new BodySegment(liz, cell);
					segments.add(seg);
				}
				liz.setSegments(segments);
				game.addLizard(liz);
			}
		}
		// Close scanner after processing the file
		s.close();
	}
}
