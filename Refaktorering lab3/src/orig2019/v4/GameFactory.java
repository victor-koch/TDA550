package orig2019.v4;

/**
 * Factory interface for available games.
 */
public interface GameFactory {
	/**
	 * Returns an array with names of games this factory can create. Used by GUI
	 * list availible games.
	 */
	public String[] getGameNames();

	/**
	 * Returns a new model object for the game corresponding to its Name.
	 * 
	 * @param gameName
	 *            The name of the game as given by getGameNames()
	 * @throws IllegalArgumentException
	 *             if no such game
	 */
	public GameModel createGame(final String gameName);
}
