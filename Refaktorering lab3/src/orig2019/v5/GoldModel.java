package orig2019.v5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import orig2019.v0.*;

/**
 * Sample game for illustration. Intentionally stupid; more interesting games to
 * be provided by students.
 * <p>
 * Initially 20 gold coins are randomly placed in the matrix. The red gold
 * collector aims to collect these coins which disappear after collection. Each
 * coin is randomly moved to a new position every n moves, where n is the number
 * of remaining coins. The game is won when all coins are collected and lost
 * when collector leaves game board.
 *
 *
 */
public class GoldModel implements GameModel {
	private static final int COIN_START_AMOUNT = 20;

	/*
	 * The following GameTile objects are used only to describe how to paint the
	 * specified item.
	 *
	 * This means that they should only be used in conjunction with the
	 * get/setGameboardState() methods.
	 */

	private PropertyChangeSupport obs;
	
	private String updated = "Game updated";
	
	/** The game board represented by a matrix. */
	private GameTile[][] gameboard;
	
	/** The size of the gameboard. */
	private final Dimension size = Constants.getGameSize();
	
	/** Graphical representation of a coin. */
	private static final GameTile COIN_TILE = new RoundTile(new Color(255, 215, 0), new Color(255, 255, 0), 2.0);

	/** Graphical representation of the collector */
	private static final GameTile COLLECTOR_TILE = new RoundTile(Color.BLACK, Color.RED, 2.0);

	/** Graphical representation of a blank tile. */
	private static final GameTile BLANK_TILE = new BlankTile();

	/** A list containing the positions of all coins. */
	private final List<Position> coins = new ArrayList<>();

	/** The position of the collector. */
	private Position collectorPos;

	/** The direction of the collector. */
	private Direction direction = Direction.NORTH;

	/** The number of coins found. */
	private int score;

	/**
	 * Create a new model for the gold game.
	 */
	public GoldModel() {
		//size = getGameboardSize();
		
		obs = new PropertyChangeSupport(this);
		
		gameboard = new GameTile[size.width][size.height];
		
		// Blank out the whole gameboard
		for (int i = 0; i < size.width; i++) {
			for (int j = 0; j < size.height; j++) {
				GameUtils.setGameboardState(i, j, GoldModel.BLANK_TILE, gameboard);
			}
		}

		// Insert the collector in the middle of the gameboard.
		this.collectorPos = new Position(size.width / 2, size.height / 2);
		GameUtils.setGameboardState(this.collectorPos, GoldModel.COLLECTOR_TILE, gameboard);

		// Insert coins into the gameboard.
		for (int i = 0; i < GoldModel.COIN_START_AMOUNT; i++) {
			addCoin();
		}
	}

	/**
	 * Insert another coin into the gameboard.
	 */
	private void addCoin() {
		Position newCoinPos;
		final Dimension size = getGameboardSize();
		// Loop until a blank position is found and ...
		do {
			newCoinPos = new Position((int) (Math.random() * size.width), (int) (Math.random() * size.height));
		} while (!isPositionEmpty(newCoinPos));

		// ... add a new coin to the empty tile.
		GameUtils.setGameboardState(newCoinPos, GoldModel.COIN_TILE, gameboard);
		this.coins.add(newCoinPos);
	}

	/**
	 * Return whether the specified position is empty.
	 *
	 * @param pos The position to test.
	 * @return true if position is empty.
	 */
	private boolean isPositionEmpty(final Position pos) {
		return getGameboardState(pos) == GoldModel.BLANK_TILE;
	}

	/**
	 * Update the direction of the collector according to the user's keypress.
	 */
	private void updateDirection(final int key) {
		switch (key) {
			case KeyEvent.VK_LEFT:
				this.direction = Direction.WEST;
				break;
			case KeyEvent.VK_UP:
				this.direction = Direction.NORTH;
				break;
			case KeyEvent.VK_RIGHT:
				this.direction = Direction.EAST;
				break;
			case KeyEvent.VK_DOWN:
				this.direction = Direction.SOUTH;
				break;
			default:
				// Don't change direction if another key is pressed
				break;
		}
	}

	/**
	 * Get next position of the collector.
	 */
	private Position getNextCollectorPos() {
		return new Position(this.collectorPos.getX() + this.direction.getXDelta(),
				this.collectorPos.getY() + this.direction.getYDelta());
	}

	/**
	 * This method is called repeatedly so that the game can update its state.
	 *
	 * @param lastKey The most recent keystroke.
	 */
	@Override
	public void gameUpdate(final int lastKey) throws GameOverException {
		updateDirection(lastKey);

		// Erase the previous position.
		GameUtils.setGameboardState(this.collectorPos, GoldModel.BLANK_TILE, gameboard);
		// Change collector position.
		this.collectorPos = getNextCollectorPos();

		if (isOutOfBounds(this.collectorPos)) {
			throw new GameOverException(this.score);
		}
		// Draw collector at new position.
		GameUtils.setGameboardState(this.collectorPos, GoldModel.COLLECTOR_TILE, gameboard);

		// Remove the coin at the new collector position (if any)
		if (this.coins.remove(this.collectorPos)) {
			this.score++;
		}

		// Check if all coins are found
		if (this.coins.isEmpty()) {
			throw new GameOverException(this.score + 5);
		}

		// Remove one of the coins
		final Position oldCoinPos = this.coins.get(0);
		this.coins.remove(0);
		GameUtils.setGameboardState(oldCoinPos, GoldModel.BLANK_TILE, gameboard);

		// Add a new coin (simulating moving one coin)
		addCoin();

		obs.firePropertyChange(updated, false, true);
	}

	/**
	 *
	 * @param pos The position to test.
	 * @return <code>false</code> if the position is outside the playing field,
	 *         <code>true</code> otherwise.
	 */
	private boolean isOutOfBounds(final Position pos) {
		return pos.getX() < 0 || pos.getX() >= getGameboardSize().width || pos.getY() < 0
				|| pos.getY() >= getGameboardSize().height;
	}

	@Override
	public GameTile getGameboardState(Position pos) {
		return getGameboardState(pos.getX(), pos.getY());
	}

	@Override
	public GameTile getGameboardState(int x, int y) {
		return gameboard[x][y];
	}

	@Override
	public Dimension getGameboardSize() {
		return size;
	}

	@Override
	public void addObserver(PropertyChangeListener observer) {
		obs.addPropertyChangeListener(observer);
	}

	@Override
	public void removeObserver(PropertyChangeListener observer) {
		obs.removePropertyChangeListener(observer);
		
	}

}
