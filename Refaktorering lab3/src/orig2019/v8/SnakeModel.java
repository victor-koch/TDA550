package orig2019.v8;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.SplittableRandom;

import orig2019.v0.Constants;
import orig2019.v0.Direction;
import orig2019.v0.GameOverException;


public class SnakeModel implements GameModel {
	/** Starting number of apples. */
	private static final int APPLE_START_AMOUNT = 1;
	
	/** Graphical representation of an apple. */
	private static final GameTile APPLE_TILE = new RoundTile(new Color(105, 0,0), new Color(205, 30, 0), 2.0);
	
	/** Graphical representation of the snake head. */
	private static final GameTile SNAKE_HEAD_TILE = new RoundTile(Color.DARK_GRAY);
	
	/** Graphical representation of snake body. */
	private static final GameTile SNAKE_BODY_TILE = new SquareTile(Color.DARK_GRAY);

	/** Graphical representation of a blank tile. */
	private static final GameTile BLANK_TILE = new BlankTile();
	
	/** A matrix containing the gameboard. */
	private GameTile[][] gameboard; 
	
	/** Update interval of the game. Determines the speed. */
	private int updateInterval = 150;
	
	/** Message to let the GamevView know the view has changed. */
	private final String updated = "Game updated";
	
	/** A list containing the positions of all apples. */
	private final List<Position> apples = new ArrayList<>();
	
	/** The position of the snake head. */
	private Position snakeHeadPos;
	
	/** A list containing the position of the snake. */
	private Deque<Position> snakeBody = new LinkedList<>();
	
	/** The starting direction of the snake. */
	private Direction direction = Direction.NORTH;

	/** The number of apples eaten. */
	private int score;
	
	/** Apple eaten last gameUpdate. */
	private boolean appleEatenLastUpdate = false;

	/** The models pseudo random number generator. */
	private final SplittableRandom rng;
	
	/** Observer object for observers to add. */
	private PropertyChangeSupport obs;

	
	/**
	 * Create a new model for the gold game.
	 */
	public SnakeModel() {
		Dimension size = getGameboardSize();
		
		gameboard = new GameTile[size.width][size.height];
		
		this.rng = new SplittableRandom();
		
		// Blank out the whole gameboard
		for (int i = 0; i < size.width; i++) {
			for (int j = 0; j < size.height; j++) {
				GameUtils.setGameboardState(i, j, BLANK_TILE, gameboard);
			}
		}

		// Insert the snake in the middle of the gameboard.
		this.snakeHeadPos = new Position(size.width / 2, size.height / 2);
		GameUtils.setGameboardState(this.snakeHeadPos, SNAKE_HEAD_TILE, gameboard);

		// Insert apples into the gameboard.
		for (int i = 0; i < APPLE_START_AMOUNT; i++) {
			addApple();
		}
		
		obs = new PropertyChangeSupport(this);
	}
	
	/**
	 * Return whether the specified position is empty.
	 * 
	 * @param pos The position to test.
	 * @return true if position is empty.
	 */
	private boolean isPositionEmpty(final Position pos) {
		return (getGameboardState(pos) == BLANK_TILE);
	}
	
	/**
	 * Update the direction of the snake
	 * according to the user's keypress.
	 */
	private void updateDirection(final int key) {
		switch (key) {
			case KeyEvent.VK_LEFT:
				if(this.direction != Direction.EAST) {
					this.direction = Direction.WEST;
					break;
				}break;
			case KeyEvent.VK_UP:
				if(this.direction != Direction.SOUTH) {
					this.direction = Direction.NORTH;
					break;
				}break;
			case KeyEvent.VK_RIGHT:
				if(this.direction != Direction.WEST) {
					this.direction = Direction.EAST;
					break;
				} break;
			case KeyEvent.VK_DOWN:
				if(this.direction != Direction.NORTH) {
					this.direction = Direction.SOUTH;
					break;
				} break;
			default:
				// Don't change direction if another key is pressed
				break;
		}
	}

	/**
	 * Get next position of the snake head.
	 */
	private Position getNextSnakePosition() {
		return this.snakeHeadPos.move(this.direction);
	}
	
	/**
	 * Updates and erases the last position of the tail.
	 * Does not paint the last position blank if an apple has been eaten.
	 * Behavior is dependent on (boolean) appleEatenLastUpdate
	 */
	private void updateTailPosition() {
		snakeBody.addFirst(snakeHeadPos);
		if(appleEatenLastUpdate)
			snakeBody.removeLast();
		else
			GameUtils.setGameboardState(snakeBody.pollLast(), BLANK_TILE, gameboard);
	}
	
	/**
	 * Draws the first segment of the tail.
	 */
	private void drawTail() {
		GameUtils.setGameboardState(snakeBody.peekFirst(), SNAKE_BODY_TILE, gameboard);
	}

	/**
	 * This method is called repeatedly so that the
	 * game can update its state.
	 * 
	 * @param lastKey
	 *            The most recent keystroke.
	 */
	@Override
	public void gameUpdate(final int lastKey) throws GameOverException {
		updateDirection(lastKey);
		
		// Erase the previous position.
		GameUtils.setGameboardState(this.snakeHeadPos, BLANK_TILE, gameboard);
		updateTailPosition();
		
		
		// Update head to next position.
		this.snakeHeadPos = getNextSnakePosition();
		
		// Check if snake has passed through a wall.
		isOutOfBounds(snakeHeadPos);
		
		// Check if snake has crashed into its tail.
		if(snakeBody.contains(snakeHeadPos)) {
		 	throw new GameOverException(this.score);
		}
		
		// Draw snake at new position.
		GameUtils.setGameboardState(this.snakeHeadPos, SNAKE_HEAD_TILE, gameboard);
		if(score > 0) {
			drawTail();
		}
		
		// Remove the apple at the new snake position (if any), and adds a new apple.
		// Checks if the game is finished and the snake takes up the entire game.
		if (this.apples.remove(this.snakeHeadPos)) {
			this.score++;
			if(score +1 == getGameboardSize().width * getGameboardSize().height)
				throw new GameOverException(this.score);
			snakeBody.add(snakeHeadPos);
			addApple();
			appleEatenLastUpdate = true;
		} else
			appleEatenLastUpdate = false;
		
		obs.firePropertyChange(updated, false, true);
	}
	
	/**
	 * Insert another apple into the gameboard.
	 */
	private void addApple() {
		Position newApplePos;
		Dimension size = getGameboardSize();
		// Loop until a blank position is found and ...
		do {
			int x = this.rng.nextInt(size.width);
			int y = this.rng.nextInt(size.height);
			newApplePos = new Position(x, y);
		} while (!isPositionEmpty(newApplePos));

		// ... add a new apple to the empty tile.
		GameUtils.setGameboardState(newApplePos, APPLE_TILE, gameboard);
		this.apples.add(newApplePos);
	}
	
	/**
	 * Check if snake has passed through wall, and if so move the head to the other side of the gameboard.
	 * @param pos The position to test.
	 */
	private void isOutOfBounds(Position pos) {
		if(pos.getX() < 0) {
			this.snakeHeadPos = new Position(getGameboardSize().width -1, pos.getY());
		}
		if(pos.getX() >= getGameboardSize().width) {
			this.snakeHeadPos = new Position(0, pos.getY());
		}
		if(pos.getY() < 0) {
			this.snakeHeadPos = new Position(pos.getX(), getGameboardSize().height -1);
		}
		if(pos.getY() >= getGameboardSize().height) {
			this.snakeHeadPos = new Position(pos.getX(), 0);
		}
	}

	@Override
	public void addObserver(PropertyChangeListener observer) {
		obs.addPropertyChangeListener(observer);		
	}

	@Override
	public void removeObserver(PropertyChangeListener observer) {
		obs.removePropertyChangeListener(observer);
		
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
		return Constants.getGameSize();
	}

	@Override
	public int getUpdateSpeed() {
		return updateInterval;
	}
}
