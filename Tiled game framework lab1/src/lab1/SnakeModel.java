package lab1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.SplittableRandom;

public class SnakeModel extends GameModel {
	/** Starting number of apples. */
	private static final int APPLE_START_AMOUNT = 1;
	
	/** Graphical representation of an apple. */
	private static final GameTile APPLE_TILE = new RoundTile(new Color(105, 0,0), new Color(205, 30, 0), 2.0);
	
	/** Graphical representation of the snake head. */
	private static final GameTile SNAKE_HEAD_TILE = new RoundTile(Color.DARK_GRAY);
	
	/** Graphical representation of snake body. */
	private static final GameTile SNAKE_BODY_TILE = new RectangularTile(Color.DARK_GRAY);

	/** Graphical representation of a blank tile. */
	private static final GameTile BLANK_TILE = new GameTile();
	
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

	
	/**
	 * Create a new model for the gold game.
	 */
	public SnakeModel() {
		Dimension size = getGameboardSize();
		
		this.rng = new SplittableRandom();
		
		// Blank out the whole gameboard
		for (int i = 0; i < size.width; i++) {
			for (int j = 0; j < size.height; j++) {
				setGameboardState(i, j, BLANK_TILE);
			}
		}

		// Insert the snake in the middle of the gameboard.
		this.snakeHeadPos = new Position(size.width / 2, size.height / 2);
		setGameboardState(this.snakeHeadPos, SNAKE_HEAD_TILE);

		// Insert apples into the gameboard.
		for (int i = 0; i < APPLE_START_AMOUNT; i++) {
			addApple();
		}
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
			setGameboardState(snakeBody.pollLast(), BLANK_TILE);
	}
	
	/**
	 * Draws the first segment of the tail.
	 */
	private void drawTail() {
		setGameboardState(snakeBody.peekFirst(), SNAKE_BODY_TILE);
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
		setGameboardState(this.snakeHeadPos, BLANK_TILE);
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
		setGameboardState(this.snakeHeadPos, SNAKE_HEAD_TILE);
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
		setGameboardState(newApplePos, APPLE_TILE);
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
}
