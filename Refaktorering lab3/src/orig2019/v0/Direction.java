package orig2019.v0;

/**
 * An enum to help movement in four directions.
 *
 * @author Pelle Evensen, evensen@chalmers.se
 *
 */
public enum Direction {
	EAST(1, 0), SOUTHEAST(1, 1), SOUTH(0, 1), SOUTHWEST(-1, 1), WEST(-1, 0), NORTHWEST(-1, -1), NORTH(0, -1),
	NORTHEAST(1, -1), NONE(0, 0);

	private final int xDelta;
	private final int yDelta;

	Direction(final int xDelta, final int yDelta) {
		this.xDelta = xDelta;
		this.yDelta = yDelta;
	}

	public int getXDelta() {
		return this.xDelta;
	}

	public int getYDelta() {
		return this.yDelta;
	}
}
