package hw3;

import api.BodySegment;
import api.Cell;
import api.Direction;
import java.util.ArrayList;	

/*
 * @author Austin Loomis
 */

/**
 * Represents a Lizard as a collection of body segments.
 */
public class Lizard {
    private ArrayList<BodySegment> segments;

    /**
     * Constructs a new Lizard object.
     */
    public Lizard() {
        segments = new ArrayList<>();
    }

    /**
     * Sets the segments of the lizard. Segments should be ordered from tail to
     * head.
     *
     * @param segments List of segments ordered from tail to head
     */
    public void setSegments(ArrayList<BodySegment> segments) {
        this.segments = segments;
    }

    /**
     * Gets the segments of the lizard. Segments are ordered from tail to head.
     *
     * @return A list of segments ordered from tail to head
     */
    public ArrayList<BodySegment> getSegments() {
        return segments;
    }

    /**
     * Gets the head segment of the lizard.
     *
     * @return The head segment of the lizard, or null if there are no segments
     */
    public BodySegment getHeadSegment() {
        if (segments.isEmpty()) {
            return null;
        }
        return segments.get(segments.size() - 1);
    }

    /**
     * Gets the tail segment of the lizard.
     *
     * @return The tail segment of the lizard, or null if there are no segments
     */
    public BodySegment getTailSegment() {
        if (segments.isEmpty()) {
            return null;
        }
        return segments.get(0);
    }

    /**
     * Gets the segment that is located at a given cell.
     *
     * @param cell The cell to look for the lizard
     * @return The segment that is on the cell, or null if there is none
     */
    public BodySegment getSegmentAt(Cell cell) {
        for (BodySegment segment : segments) {
            if (segment.getCell().equals(cell)) {
                return segment;
            }
        }
        return null;
    }

    /**
     * Private helper method to determine the direction between two cells.
     *
     * @param fromCell The starting cell
     * @param toCell The destination cell
     * @return The direction from the starting cell to the destination cell
     */
    private Direction directionTo(Cell fromCell, Cell toCell) {
        int rowDiff = toCell.getRow() - fromCell.getRow();
        int colDiff = toCell.getCol() - fromCell.getCol();
        if (rowDiff == -1 && colDiff == 0) {
            return Direction.UP;
        } else if (rowDiff == 1 && colDiff == 0) {
            return Direction.DOWN;
        } else if (rowDiff == 0 && colDiff == -1) {
            return Direction.LEFT;
        } else if (rowDiff == 0 && colDiff == 1) {
            return Direction.RIGHT;
        } else {
            return null; // No direction
        }
    }

    /**
     * Gets the segment that is in front of (closer to the head segment than) the
     * given segment.
     *
     * @param segment The starting segment
     * @return The segment in front of the given segment, or null if there is no segment ahead
     */
    public BodySegment getSegmentAhead(BodySegment segment) {
        int index = segments.indexOf(segment);
        if (index == -1 || index == segments.size() - 1) {
            return null;
        }
        return segments.get(index + 1);
    }

    /**
     * Gets the segment that is behind (closer to the tail segment than) the given
     * segment.
     *
     * @param segment The starting segment
     * @return The segment behind the given segment, or null if there is no segment behind
     */
    public BodySegment getSegmentBehind(BodySegment segment) {
        int index = segments.indexOf(segment);
        if (index == -1 || index == 0) {
            return null;
        }
        return segments.get(index - 1);
    }

    /**
     * Gets the direction from the perspective of the given segment pointing to the
     * segment ahead (in front of) of it.
     *
     * @param segment The starting segment
     * @return The direction to the segment ahead of the given segment, or null if there is no segment ahead
     */
    public Direction getDirectionToSegmentAhead(BodySegment segment) {
        BodySegment aheadSegment = getSegmentAhead(segment);
        if (aheadSegment == null) {
            return null;
        }
        Cell currentCell = segment.getCell();
        Cell aheadCell = aheadSegment.getCell();
        return directionTo(currentCell, aheadCell);
    }

    /**
     * Gets the direction from the perspective of the given segment pointing to the
     * segment behind it.
     *
     * @param segment The starting segment
     * @return The direction to the segment behind the given segment, or null if there is no segment behind
     */
    public Direction getDirectionToSegmentBehind(BodySegment segment) {
        BodySegment behindSegment = getSegmentBehind(segment);
        if (behindSegment == null) {
            return null;
        }
        Cell currentCell = segment.getCell();
        Cell behindCell = behindSegment.getCell();
        return directionTo(currentCell, behindCell);
    }

    /**
     * Gets the direction in which the head segment is pointing.
     *
     * @return The direction in which the head segment is pointing, or null if there is no defined head direction
     */
    public Direction getHeadDirection() {
        if (segments.size() <= 1) {
            return null;
        }

        BodySegment headSegment = getHeadSegment();
        BodySegment behindHeadSegment = getSegmentBehind(headSegment);
        if (behindHeadSegment == null) {
            return null;
        }
        return directionTo(behindHeadSegment.getCell(), headSegment.getCell());
    }

    /**
     * Gets the direction in which the tail segment is pointing.
     *
     * @return The direction in which the tail segment is pointing, or null if there is no defined tail direction
     */
    public Direction getTailDirection() {
        if (segments.size() <= 1) {
            return null;
        }

        BodySegment tailSegment = getTailSegment();
        BodySegment aheadTailSegment = getSegmentAhead(tailSegment);
        if (aheadTailSegment == null) {
            return null;
        }
        return directionTo(aheadTailSegment.getCell(), tailSegment.getCell());
    }

    /**
     * Returns a string representation of the lizard.
     *
     * @return A string representation of the lizard
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (BodySegment seg : segments) {
            result.append(seg).append(" ");
        }
        return result.toString();
    }
}
