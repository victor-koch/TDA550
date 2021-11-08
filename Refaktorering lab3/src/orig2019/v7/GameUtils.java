package orig2019.v7;

import orig2019.v0.*;

/**
 * Common superclass for all game model classes.
 * 
 * Constructors of subclasses should initiate matrix elements and additional,
 * game-dependent fields.
 */
public class GameUtils {

	/**
	 * Set the tile on a specified position in the gameboard.
	 * 
	 * @param pos
	 *            The position in the gameboard matrix.
	 * @param tile
	 *            The type of tile to paint in specified position
	 */
	protected static void setGameboardState(final Position pos, final GameTile tile, final GameTile[][] board) {
		setGameboardState(pos.getX(), pos.getY(), tile, board);
	}

	/**
	 * Set the tile on a specified position in the gameboard.
	 * 
	 * @param x
	 *            Coordinate in the gameboard matrix.
	 * @param y
	 *            Coordinate in the gameboard matrix.
	 * @param tile
	 *            The type of tile to paint in specified position
	 */
	protected static void setGameboardState(final int x, final int y, final GameTile tile, final GameTile[][] board) {
		board[x][y] = tile;
	}
	
}
