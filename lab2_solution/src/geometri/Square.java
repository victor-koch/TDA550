package geometri;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Objects;

/**
 * This class describes a square defined by it's position, side and
 * color given as an object of <code>java.awt.Color</code>.
 * 
 * @author Victor
 * @author Mantas
 *
 */
public class Square extends BasicGeometricShape {

	/** Side of the square */
	private final int side;
	
	/** Creates a square with defined size, color and location.
	 * 
	 * @param xCord of the upper left corner.
	 * @param yCord of the upper left corner.
	 * @param side of the square.
	 * @param color of the square.
	 * @throws IllegalPositionException if any of the coordinates are negative.
	 */
	public Square(int xCord, int yCord, int side, Color color) throws IllegalPositionException {
		super(xCord, yCord, color);
		this.side = side;
	}
	
	/** Creates a square at another shape's location.
	 * 
	 * @param f another geometric shape to pull location from.
	 * @param side of the square.
	 * @param color of the square.
	 * */
	public Square(GeometricShape f, int side, Color color) {
		super(f, color);
		this.side = side;
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public int getWidth() {
		return side;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public int getHeight() {
		return side;
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public void fill(Graphics g) {
		g.setColor(this.getColor());
		g.fillRect(this.getX(), this.getY(), side, side);
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public int getArea() {
		return side * side;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public int getPerimeter() {
		return 4 * side;
	}
	
	/** 
	 * Checks if two object are equal. Following attributes must be the same:
	 * - color
	 * - side
	 */
	@Override
	public boolean equals(Object o) {
		if(!super.equals(o)) {
			return false;
		}
			
		Square other = (Square) o;
		return this.side == other.getWidth() && this.side == other.getHeight();
	}
	
	/**
	* Generates a hashcode depending on the color and side of the square.
	*/
	@Override
	public int hashCode() {
		return Objects.hash(this.getColor(), this.side);
	}
}
