package geometri;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Objects;

/**
 * This class describes a rectangle defined by it's position, height, width and
 * color given as an object of <code>java.awt.Color</code>.
 * 
 * @author Victor
 * @author Mantas
 *
 */
public class Rectangle extends BasicGeometricShape {
	
	/** Height of the geometric shape. */
	private final int height;
	/** Width of the geometric shape. */
	private final int width;
	
	/** Creates a rectangle with defined size, color and location.
	 * 
	 * @param xCord of the upper left corner.
	 * @param yCord of the upper left corner.
	 * @param width of the rectangle.
	 * @param height of the rectangle.
	 * @param color of the rectangle.
	 * @throws IllegalPositionException if any of the coordinates are negative.
	 */
	public Rectangle(int xCord, int yCord, int width, int height, Color color) throws IllegalPositionException {
		super(xCord, yCord, color);
		this.width = width;
		this.height = height;
		}
	
	/** Creates a rectangle at another shape's location.
	 * 
	 * @param f another geometric shape to pull location from.
	 * @param width of the rectangle.
	 * @param height of the rectangle.
	 * @param color of the rectangle.
	 * */
	public Rectangle(GeometricShape f, int width, int height, Color color) {
		super(f, color);
		this.width = width;
		this.height = height;
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public int getWidth() {
		return width;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public int getHeight() {
		return height;
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public void fill(Graphics g) {
		g.setColor(this.getColor());
		g.fillRect(this.getX(), this.getY(), width, height);
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public int getArea() {
		return width * height;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public int getPerimeter() {
		return 2 * width + 2 * height;
	}
	
	/** 
	 * Checks if two object are equal. Following attributes must be the same:
	 * - color
	 * - width
	 * - height
	 */
	@Override
	public boolean equals(Object o) {
		if(!super.equals(o)) {
			return false;
		}
			
		Rectangle other = (Rectangle) o;
		return this.width == other.getWidth() && this.height == other.getHeight();
	}
	
	/**
	* Generates a hashcode depending on the color, height and width of the rectangle.
	*/
	@Override
	public int hashCode() {
		return Objects.hash(this.getColor(), this.width, this.height);
	}
}
