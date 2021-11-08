package geometri;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Objects;

/**
 * This class describes a line defined by it's position, height, width, angle and
 * color given as an object of <code>java.awt.Color</code>.
 * 
 * @author Victor
 * @author Mantas
 *
 */
public class Line extends BasicGeometricShape {
	
	/** Height of the geometric shape. */
	private final int height;
	/** Width of the geometric shape. */
	private final int width;
	/** Angle of the line. */
	private final int angle;
	
	/** Creates a line with defined starting point, end point and color.
	 * 
	 * @param xCord of the starting point.
	 * @param yCord of the starting point.
	 * @param xCordSecond of the end point
	 * @param yCordSecond of the end point.
	 * @param color of the line.
	 * @throws IllegalPositionException if any of the coordinates are negative.
	 */
	public Line(int xCord, int yCord, int xCordSecond, int yCordSecond, Color color) throws IllegalPositionException{
		super(Math.min(xCord, xCordSecond), Math.min(yCord, yCordSecond), color);
		
		angle = (yCordSecond - yCord) / (xCordSecond - xCord);

		this.width = Math.abs(xCordSecond - xCord);
		this.height = Math.abs(yCordSecond - yCord);
	}
	
	/** Creates a line betweeen the locations of two other shapes.
	 * 
	 * @param f first geometric shape to pull location from.
	 * @param f2 second geometric shape to pull location from.
	 * @param color of the line.
	 */
	public Line(GeometricShape f, GeometricShape f2, Color color) {
		//Since the constructor takes two GeometricShapes, we know that their coordinates must be valid and thus, 
		//the super-constructor cannot cast an exception here.
		super(f, f2, color);
		
		angle = (f2.getY() - f.getY()) / (f2.getX() - f.getX());

		this.width = Math.abs(f2.getX() - f.getX());
		this.height = Math.abs(f2.getY() - f.getY());
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
		
		if(angle > 0)
			g.drawLine(this.getX(), this.getY() + height, this.getX() + width, this.getY());
		else {
			g.drawLine(this.getX(), this.getY(), this.getX() + width, this.getY() + height);
		}
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
		double res = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));
		return (int) res;
	}
	
	/** 
	 * Checks if two object are equal. Following attributes must be the same:
	 * - color
	 * - width
	 * - height
	 * - angle
	 */
	@Override
	public boolean equals(Object o) {
		if(!super.equals(o)) {
			return false;
		}
			
		Line other = (Line) o;
		return this.width == other.getWidth() && this.height == other.getHeight() && this.angle == other.angle;
	}
	
	/**
	* Generates a hashcode depending on the color of the shape, height, width and angle of the line.
	*/
	@Override
	public int hashCode() {
		return Objects.hash(this.getColor(), this.width, this.height, this.angle);
	}
}
