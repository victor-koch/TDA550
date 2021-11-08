package geometri;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Objects;

/**
 * This class describes an ellipse defined by it's position, diameter and
 * color given as an object of <code>java.awt.Color</code>.
 * 
 * @author Victor
 * @author Mantas
 *
 */
public class Circle extends BasicGeometricShape {
	
	/** Diameter of the circle. */
	private final int diameter;
	
	/** Creates a circle with defined diameter, color and location
	 * 
	 * @param xCord of the upper left corner of the smallest bounding box.
	 * @param yCord of the upper left corner of the smallest bounding box.
	 * @param diameter of the circle.
	 * @param color of the circle.
	 * @throws IllegalPositionException if any of the coordinates are negative.
	 */
	public Circle(int xCord, int yCord, int diameter, Color color) throws IllegalPositionException {
		super(xCord, yCord, color);
		this.diameter = diameter;
	}
	
	/** Creates a circle at another shape's location.
	 * 
	 * @param f another geometric shape to pull location from.
	 * @param diameter of the circle.
	 * @param color of the circle.
	 */
	public Circle(GeometricShape f, int diameter, Color color) {
		super(f, color);
		this.diameter = diameter;
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public int getWidth() {
		return diameter;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public int getHeight() {
		return diameter;
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public void fill(Graphics g) {
		g.setColor(this.getColor());
		g.fillOval(this.getX(), this.getY(), diameter, diameter);
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public int getArea() {
		double res = Math.PI * Math.pow(diameter / 2.0, 2);
		return (int) res;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public int getPerimeter() {
		double res = Math.PI * (double) diameter;
		return (int) res;
	}
	
	/** 
	 * Checks if two object are equal. Following attributes must be the same:
	 * - color
	 * - diameter
	 */
	@Override
	public boolean equals(Object o) {
		if(!super.equals(o)) {
			return false;
		}
			
		Circle other = (Circle) o;
		return this.diameter == other.getWidth() && this.diameter == other.getHeight();
	}
	
	/**
	* Generates a hashcode depending on the color and diameter of the circle.
	*/
	@Override
	public int hashCode() {
		return Objects.hash(this.getColor(), this.diameter);
	}
}
