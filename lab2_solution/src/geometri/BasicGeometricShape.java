package geometri;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Objects;

/**
 * This abstract class is a basic implementation of a geometric shape. Each shape 
 * contains of: A rectangle surrounding the shape as narrowly as possible defined
 * by its width, height and the position, the upper left corner of the rectangle, 
 * and a color given as an object of <code>java.awt.Color</code>.
 * <p>
 * Furthermore the shapes may be compared. First the areas of the shapes are
 * compared and if the areas are equal the then the perimeters are compared. The
 * area and the perimeter of a <code>Point</code> are 0. The area of a
 * <code>Line</code> is 0 and its perimeter is the double length of the line.
 * <p>
 * The shapes may be moved, but may not be modified in any other way. Only
 * positive x och y coordinates are allowed for the position. Negative values
 * for the coordinates gives an <code>IllegalPositionException</code>, which is
 * declared in this package.
 * <p>
 * Finally, the shapes may be compared for equality, where all components except
 * the positions of the shapes are compared for equality.
 * 
 * @author Victor
 * @author Mantas
 *
 */
public abstract class BasicGeometricShape implements GeometricShape {
	
	/** X-coordinate of the geometric shape. */
	private int xCord;
	/** Y-coordinate of the geometric shape. */
	private int yCord;
	
	/** Color of the geometric shape. */
	private final Color color;
	
	/**
	 * Creates a Basic geometric shape with the following attributes:
	 * 
	 * @param xCord of the upper left corner of the smallest bounding box, must be a positive integer.
	 * @param yCord of the upper left corner of the smallest bounding box, must be a positive integer.
	 * @param height of the shape.
	 * @param width of the shape.
	 * @param color of the shape.
	 */
	protected BasicGeometricShape(int xCord, int yCord, Color color) throws IllegalPositionException{
		if(xCord < 0 || yCord < 0)
			throw new IllegalPositionException("Position is invalid. Please enter non-negative coordinates!");
		
		this.xCord = xCord;
		this.yCord = yCord;
		this.color = color;
	}
	
	/**
	 * Creates a Basic geometric shape with the following attributes at another shape's location:
	 * 
	 * @param f another geometric shape to pull location from.
	 * @param color of the shape.
	 */
	protected BasicGeometricShape(GeometricShape f, Color color) {
		this.xCord = f.getX();
		this.yCord = f.getY();
		this.color = color;
		
	}
	
	/**
	 * Creates a Basic geometric shape with the following attributes at the location of the minimum x-, y-coordinates:
	 * 
	 * @param f first geometric shape to pull location from.
	 * @param f2 second geometric shape to pull location from.
	 * @param color of the shape.
	 */
	protected BasicGeometricShape(GeometricShape f, GeometricShape f2, Color color) {
		this.xCord = Math.min(f.getX(), f2.getX());
		this.yCord = Math.min(f.getY(), f2.getY());
		this.color = color;
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public abstract int getArea();

	/**
	* {@inheritDoc}
	*/
	@Override
	public int compareTo(GeometricShape f) {
		if(this.getArea() < f.getArea())
			return -1;
		else if(this.getArea() > f.getArea())
			return 1;
		
		// Area must be equal, check perimeter instead.
		else if(this.getPerimeter() < f.getPerimeter())
			return -1;
		else if(this.getPerimeter() > f.getPerimeter())
			return 1;
		return 0;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public abstract void fill(Graphics g);

	/**
	* {@inheritDoc}
	*/
	@Override
	public Color getColor() {
		return color;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public abstract int getWidth();

	/**
	* {@inheritDoc}
	*/
	@Override
	public abstract int getHeight();

	/**
	* {@inheritDoc}
	*/
	@Override
	public int getX() {
		return xCord;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public int getY() {
		return yCord;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public void move(int dx, int dy) throws IllegalPositionException {
		place(this.xCord + dx, this.yCord + dy);
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public abstract int getPerimeter();

	/**
	* {@inheritDoc}
	*/
	@Override
	public void place(int x, int y) throws IllegalPositionException {
		if(x < 0 || y < 0)
			throw new IllegalPositionException("Position is invalid. Please enter non-negative coordinates!");
		this.xCord = x;
		this.yCord = y;
	}
	
	/** 
	 * Checks if two object are equal. Following attributes must be the same:
	 * - color
	 */
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(o == null || this.getClass() != o.getClass()) {
			return false;
		}
		
		BasicGeometricShape other = (BasicGeometricShape) o;
		return this.getColor().equals(other.getColor());
	}
	
	/**
	* Generates a hashcode depending on the color of the shape.
	*/
	@Override
	public int hashCode() {
		return Objects.hash(this.color);
	}

}
