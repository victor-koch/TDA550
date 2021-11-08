package orig2019.v5;


/**
 * Factory class for available games.
 */
public class ReversiFactory implements GameFactory {

	/**
	 * Returns an array with names of games this factory can create. Used by GUI
	 * list available games.
	 */
	@Override
	public String[] getGameNames() {
		return new String[] { "Gold", "Othello" };
	}

	/**
	 * Returns a new model object for the game corresponding to its Name.
	 *
	 * @param gameName The name of the game as given by getGameNames()
	 * @throws IllegalArgumentException if no such game
	 */
	@Override
	public GameModel createGame(final String gameName) {
		if (gameName.equals("Gold")) {
			return new GoldModel();
		}
		if (gameName.equals("Othello")) {
			return new ReversiModel();
		}

		throw new IllegalArgumentException("No such game: " + gameName);
	}
}
