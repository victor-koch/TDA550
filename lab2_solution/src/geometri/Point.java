package geometri;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This class describes a point defined by it's position, and
 * color given as an object of <code>java.awt.Color</code>.
 * 
 * @author Victor
 * @author Mantas
 *
 */
public class Point extends BasicGeometricShape {

	/** Creates a point with defined color and location
	 * 
	 * @param xCord of the point.
	 * @param yCord of the point.
	 * @param color of the point.
	 * @throws IllegalPositionException if any of the coordinates are negative.
	 */
	public Point(int xCord, int yCord, Color color) throws IllegalPositionException {
		super(xCord, yCord, color);
	}
	
	/** Creates a point at another shape's location.
	 * 
	 * @param f another geometric shape to pull location from.
	 * @param color of the point.
	 * */
	public Point(GeometricShape f, Color color) {
		super(f, color);
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public int getWidth() {
		return 0;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public int getHeight() {
		return 0;
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public void fill(Graphics g) {
		g.setColor(this.getColor());
		g.fillOval(this.getX(), this.getY(), 0, 0);
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public int getArea() {
		return 0;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public int getPerimeter() {
		return 0;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}
	
	/**
	* Generates a hashcode depending on the color of the point.
	*/
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
