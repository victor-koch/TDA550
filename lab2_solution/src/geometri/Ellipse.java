package geometri;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Objects;

/**
 * This class describes an ellipse defined by it's position, height, width and
 * color given as an object of <code>java.awt.Color</code>.
 * 
 * @author Victor
 * @author Mantas
 *
 */
public class Ellipse extends BasicGeometricShape {
	
	/** Height of the geometric shape. */
	private final int height;
	/** Width of the geometric shape. */
	private final int width;

	/** Creates an ellipse with defined size, color and location
	 * 
	 * @param xCord of the upper left corner of the smallest bounding box.
	 * @param yCord of the upper left corner of the smallest bounding box.
	 * @param width of the ellipse.
	 * @param height of the ellipse
	 * @param color of the ellipse.
	 * @throws IllegalPositionException if any of the coordinates are negative.
	 */
	public Ellipse(int xCord, int yCord, int width, int height, Color color) throws IllegalPositionException {
		super(xCord, yCord, color);
		this.width = width;
		this.height = height;
	}
	
	/** Creates an ellipse at another shape's location.
	 * 
	 * @param f another geometric shape to pull location from.
	 * @param width of the ellipse.
	 * @param height of the ellipse.
	 * @param color of the ellipse.
	 * */
	public Ellipse(GeometricShape f, int width, int height, Color color) {
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
		g.fillOval(this.getX(), this.getY(), width, height);
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public int getArea() {
		double a = this.height / 2.0;
		double b = this.width / 2.0;
		double res = Math.PI * a * b;
		return (int) res;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public int getPerimeter() {
		double a = this.height / 2.0;
		double b = this.width / 2.0;
		double res = Math.PI * 2 * Math.sqrt((Math.pow(a, 2) + Math.pow(b, 2)) / 2.0);
		return (int) res;
	}
	
	/** 
	 * Checks if two object are equal. Following attributes must be the same:
	 * - color
	 * - height
	 * - width
	 */
	@Override
	public boolean equals(Object o) {
		if(!super.equals(o)) {
			return false;
		}
			
		Ellipse other = (Ellipse) o;
		return this.width == other.getWidth() && this.height == other.getHeight();
	}
	
	/**
	* Generates a hashcode depending on the color, width end height of the ellipse.
	*/
	@Override
	public int hashCode() {
		return Objects.hash(this.getColor(), this.width, this.height);
	}
}
