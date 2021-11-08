package orig2019.v8;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import orig2019.v0.*;

/**
 * A somewhat defective implementation of the game Reversi. The purpose of this
 * class is to illustrate shortcomings in the game framework.
 *
 * @author Pelle Evensen, evensen@chalmers.se
 *
 */
public class ReversiModel implements GameModel {
	public enum Turn {
		BLACK, WHITE;

		public static Turn nextTurn(final Turn t) {
			return t == BLACK ? WHITE : BLACK;
		}
	}

	public enum PieceColor {
		BLACK, WHITE, EMPTY;

		public static PieceColor opposite(final PieceColor t) {
			return t == BLACK ? WHITE : BLACK;
		}
	}

	/** Graphical representation of a coin. */
	private static final GameTile blackTile = new RoundTile(Color.BLACK, Color.BLACK, 1.0, 0.8);
	private static final GameTile whiteTile = new RoundTile(Color.BLACK, Color.WHITE, 1.0, 0.8);
	private static final GameTile blankTile = new SquareTile(Color.BLACK, new Color(0, 200, 0), 2.0);
	private static final GameTile whiteGridTile = new CompositeTile(ReversiModel.blankTile, ReversiModel.whiteTile);
	private static final GameTile blackGridTile = new CompositeTile(ReversiModel.blankTile, ReversiModel.blackTile);
	private static final GameTile cursorRedTile = new CrossTile(Color.RED, 2.0);
	private static final GameTile cursorBlackTile = new RoundTile(Color.RED, new Color(0, 50, 0), 2.0, 0.8);
	private static final GameTile cursorWhiteTile = new RoundTile(Color.RED, new Color(210, 255, 210), 2.0, 0.8);

	private Turn turn;
	private Position cursorPos;
	private final PieceColor[][] board;
	private int whiteScore;
	private int blackScore;
	private final int width;
	private final int height;
	private boolean gameOver;

	private PropertyChangeSupport obs;
	private String updated = "Game updated";
	
	private final int updateInterval = 0;
	
	public ReversiModel() {
		this.width = Constants.getGameSize().width;
		this.height = Constants.getGameSize().height;
		this.board = new PieceColor[this.width][this.height];
		
		// Blank out the whole gameboard...
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				this.board[i][j] = PieceColor.EMPTY;
			}
		}

		this.turn = Turn.BLACK;

		// Insert the four starting bricks.
		final int midX = this.width / 2 - 1;
		final int midY = this.height / 2 - 1;
		this.board[midX][midY] = PieceColor.WHITE;
		this.board[midX + 1][midY + 1] = PieceColor.WHITE;
		this.board[midX + 1][midY] = PieceColor.BLACK;
		this.board[midX][midY + 1] = PieceColor.BLACK;

		// Set the initial score.
		this.whiteScore = 2;
		this.blackScore = 2;

		this.gameOver = false;

		// Insert the collector in the middle of the gameboard.
		this.cursorPos = new Position(midX, midY);
		
		obs = new PropertyChangeSupport(this);
	}

	/**
	 * Return whether the specified position is empty. If it only consists of a
	 * blank tile, it is considered empty.
	 *
	 * @param pos The position to test.
	 * @return true if position is empty, false otherwise.
	 */
	private boolean isPositionEmpty(final Position pos) {
		return this.board[pos.getX()][pos.getY()] == PieceColor.EMPTY;
	}

	/**
	 * Update the direction of the collector according to the users keypress.
	 *
	 * @throws GameOverException
	 */
	private Direction updateDirection(final int key) {
		switch (key) {
			case KeyEvent.VK_LEFT:
				return Direction.WEST;
			case KeyEvent.VK_UP:
				return Direction.NORTH;
			case KeyEvent.VK_RIGHT:
				return Direction.EAST;
			case KeyEvent.VK_DOWN:
				return Direction.SOUTH;
			case KeyEvent.VK_SPACE:
				tryPlay();
				return Direction.NONE;
			default:
				// Do nothing if another key is pressed
				return Direction.NONE;
		}
	}

	private void tryPlay() {
		if (isPositionEmpty(this.cursorPos)) {
			GameTile t;
			if (this.turn == Turn.BLACK) {
				t = ReversiModel.blackGridTile;
			} else {
				t = ReversiModel.whiteGridTile;
			}
			if (isLegalMove(this.turn, this.cursorPos)) {
				turnOver(this.turn, this.cursorPos);
				this.board[this.cursorPos.getX()][this.cursorPos.getY()] = this.turn == Turn.BLACK ? PieceColor.BLACK
						: PieceColor.WHITE;
				this.turn = Turn.nextTurn(this.turn);
			}
			if (!hasPossibleMove(this.turn)) {
				if (!hasPossibleMove(Turn.nextTurn(this.turn))) {
					this.gameOver = true;
					return;
				}

				this.turn = Turn.nextTurn(this.turn);
			}
		}

	}

	private void turnOver(@SuppressWarnings("hiding") final Turn turn,
			@SuppressWarnings("hiding") final Position cursorPos) {
		if (isPositionEmpty(cursorPos)) {
			final PieceColor myColor = turn == Turn.BLACK ? PieceColor.BLACK : PieceColor.WHITE;
			final PieceColor opponentColor = PieceColor.opposite(myColor);
			final int blackResult = turn == Turn.BLACK ? 1 : -1;
			final int whiteResult = -blackResult;

			this.blackScore += Math.max(0, blackResult);
			this.whiteScore += Math.max(0, whiteResult);

			for (int i = 0; i < 8; i++) {
				final Direction d = Direction.values()[i];
				final int xDelta = d.getXDelta();
				final int yDelta = d.getYDelta();
				int x = cursorPos.getX() + xDelta;
				int y = cursorPos.getY() + yDelta;
				boolean canTurn = false;
				while (x >= 0 && x < this.width && y >= 0 && y < this.height) {
					if (this.board[x][y] == opponentColor) {
						canTurn = true;
					} else if (this.board[x][y] == myColor && canTurn) {
						// Move backwards to the cursor, flipping bricks
						// as we go.
						x -= xDelta;
						y -= yDelta;
						while (!(x == cursorPos.getX() && y == cursorPos.getY())) {
							this.board[x][y] = myColor;
							x -= xDelta;
							y -= yDelta;
							this.blackScore += blackResult;
							this.whiteScore += whiteResult;
						}
						break;
					} else {
						break;
					}
					x += xDelta;
					y += yDelta;
				}
			}
		}
	}

	private boolean hasPossibleMove(final Turn turn) {
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				if (isLegalMove(turn, new Position(x, y))) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isLegalMove(final Turn turn, final Position cursorPos) {
		if (isPositionEmpty(cursorPos)) {
			final PieceColor myColor = turn == Turn.BLACK ? PieceColor.BLACK : PieceColor.WHITE;
			final PieceColor opponentColor = PieceColor.opposite(myColor);
			for (int i = 0; i < 8; i++) {
				final Direction d = Direction.values()[i];
				final int xDelta = d.getXDelta();
				final int yDelta = d.getYDelta();
				int x = cursorPos.getX() + xDelta;
				int y = cursorPos.getY() + yDelta;
				boolean canTurn = false;
				while (x >= 0 && x < this.width && y >= 0 && y < this.height) {
					if (this.board[x][y] == opponentColor) {
						canTurn = true;
					} else if (this.board[x][y] == myColor && canTurn) {
						return true;
					} else {
						break;
					}
					x += xDelta;
					y += yDelta;
				}
			}
		}
		return false;
	}

	/**
	 * Get the current player's color
	 */
	public Turn getTurnColor() {
		return this.turn;
	}

	/**
	 * Accessor to black's current score.
	 *
	 * @return black's score
	 */
	public int getBlackScore() {
		return this.blackScore;
	}

	/**
	 * Accessor to white's current score.
	 *
	 * @return white's score
	 */
	public int getWhiteScore() {
		return this.whiteScore;
	}

	/**
	 * Get next position of the collector.
	 */
	private Position getNextCursorPos(final Direction dir) {
		return new Position(this.cursorPos.getX() + dir.getXDelta(), this.cursorPos.getY() + dir.getYDelta());
	}

	/**
	 * This method is called repeatedly so that the game can update its state.
	 *
	 * @param lastKey The most recent keystroke.
	 */
	@Override
	public void gameUpdate(final int lastKey) throws GameOverException {
		if (!this.gameOver) {
			Position nextCursorPos = getNextCursorPos(updateDirection(lastKey));
			final Dimension boardSize = getGameboardSize();
			final int nextX = Math.max(0, Math.min(nextCursorPos.getX(), boardSize.width - 1));
			final int nextY = Math.max(0, Math.min(nextCursorPos.getY(), boardSize.height - 1));
			nextCursorPos = new Position(nextX, nextY);
			this.cursorPos = nextCursorPos;
		} else {
			throw new GameOverException(this.blackScore - this.whiteScore);
		}
		
		obs.firePropertyChange(updated, false, true);
	}

	@Override
	public GameTile getGameboardState(Position pos) {
		return getGameboardState(pos.getX(), pos.getY());
	}

	@Override
	public GameTile getGameboardState(int x, int y) {
		
		if(board[x][y] == PieceColor.BLACK) {
			if(cursorPos.getX() == x && cursorPos.getY() == y) {
				return new CompositeTile(ReversiModel.blackGridTile, ReversiModel.cursorRedTile);
			}
			return ReversiModel.blackGridTile;
		}
		else if(board[x][y] == PieceColor.WHITE) {
			if(cursorPos.getX() == x && cursorPos.getY() == y) {
				return new CompositeTile(ReversiModel.whiteGridTile, ReversiModel.cursorRedTile);
			}
			return ReversiModel.whiteGridTile;
		}
		else {
			if(isLegalMove(this.turn, this.cursorPos)) {
				if(turn == Turn.BLACK) {
					if(cursorPos.getX() == x && cursorPos.getY() == y) {
					return new CompositeTile(ReversiModel.blankTile, ReversiModel.cursorBlackTile);
					}
					return ReversiModel.blankTile;
				}
				if(cursorPos.getX() == x && cursorPos.getY() == y) {
					return new CompositeTile(ReversiModel.blankTile, ReversiModel.cursorWhiteTile);
				}
				return ReversiModel.blankTile;
			}
			else if(!isLegalMove(this.turn, this.cursorPos)) {
				if(cursorPos.getX() == x && cursorPos.getY() == y) {
					return new CompositeTile(ReversiModel.blankTile, ReversiModel.cursorRedTile);
				}
				return ReversiModel.blankTile;
			}
			else {
				return ReversiModel.blankTile;
			}
		}
	}

	@Override
	public Dimension getGameboardSize() {
		return Constants.getGameSize();
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
	public int getUpdateSpeed() {
		return updateInterval;
	}

}
