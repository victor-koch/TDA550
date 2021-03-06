package orig2019.v2;

import orig2019.v0.*;

/**
 * Factory class for available games.
 */
public class BaseGameFactory implements GameFactory {

	/**
	 * Returns an array with names of games this factory can create. Used by GUI
	 * list available games.
	 */
	@Override
	public String[] getGameNames() {
		return new String[] { "Gold" };
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

		throw new IllegalArgumentException("No such game: " + gameName);
	}
}
