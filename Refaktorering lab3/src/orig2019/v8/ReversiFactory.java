package orig2019.v8;


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
		return new String[] { "Gold", "Othello", "Snake" };
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
			ReversiModel reversiGame = new ReversiModel();
			new ReversiScoreView(reversiGame);
			
			return reversiGame;
		}
		if (gameName.equals("Snake")) {
			return new SnakeModel();
		}

		throw new IllegalArgumentException("No such game: " + gameName);
	}
}
