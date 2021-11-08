package lab1;

/**
 * Factory class for available games.
 */
public class GameFactory implements IGameFactory {
	private static final String GAME_NAME_GOLD = "Gold";
	private static final String GAME_NAME_SNAKE = "Snake";
	
	/**
	 * Returns an array with names of games this factory can create. Used by GUI
	 * list availible games.
	 */
	@Override
	public String[] getGameNames() {
		return new String[] { GAME_NAME_SNAKE, GAME_NAME_GOLD };
	}

	/**
	 * Returns a new model object for the game corresponding to its Name.
	 * 
	 * @param gameName
	 *            The name of the game as given by getGameNames()
	 * @throws IllegalArgumentException
	 *             if no such game
	 */
	@Override
	public GameModel createGame(final String gameName) {
		if (gameName.equals(GAME_NAME_GOLD)) {
			return new GoldModel();
		} else if(gameName.equals(GAME_NAME_SNAKE))
			return new SnakeModel();

		throw new IllegalArgumentException("No such game: " + gameName);
	}
}
