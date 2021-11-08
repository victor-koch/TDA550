package geometri;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This interface describes drawable geometric shapes. Each shape contains of: A
 * rectangle surrounding the shape as narrowly as possible defined by its width,
 * height and the position, the upper left corner of the rectangle, and a colour
 * given as an object of <code>java.awt.Color</code>.
 * <p>
 * Furthermore the shapes may be compared. First the areas of the shapes are
 * compared and if the areas are equal the then the perimeters are compared. The
 * area and the perimeter of a <code>Point</code> are 0. The area of a
 * <code>Line</code> is 0 and its perimeter is the double length of the line.
 * <p>
 * The shapes may be moved, but may not be modyfied in any other way. Only
 * positive x och y coordinates are allowed for the position. Negative values
 * for the coordinates gives an <code>IllegalPositionException</code>, which is
 * declared in this package.
 * <p>
 * Finally, the shapes may be compared for equality, where all components except
 * the positions of the shapes are compared for equality.
 *
 * @author Bror Bjerner
 * @author Christer Carlsson
 * @author Uno Holmer
 * @author Pelle Evensen, evensen@chalmers.se
 * @version (nov 2019)
 */
public interface GeometricShape extends Comparable<GeometricShape> {

	/**
	 * Get the area of this shape rounded to closest integer.
	 *
	 * @return the computed area.
	 */
	int getArea();

	/**
	 * The method <code>compareTo</code> compares the area and the perimeter of the
	 * shapes to decide if the given geometric shape is less or greater than this
	 * object. If they have the same area, the perimeter decides.
	 *
	 * @param f The geometric shape to compare with.
	 * @return An integer smaller than 0 if this shape is smaller than the shape
	 *         given as argument, 0 if the sizes are equal, and a positive integer
	 *         otherwise.
	 */
	@Override
	int compareTo(GeometricShape f);

	/**
	 * Fill the shape's area at the shape's position with the shape's colour.
	 *
	 * @param g A graphic pen to draw with.
	 */
	void fill(Graphics g);

	/**
	 * Get the colour of this shape.
	 *
	 * @return the colour of this shape.
	 */
	Color getColor();

	/**
	 * Get the width of the surrounding rectangle of this shape.
	 *
	 * @return the width.
	 */
	int getWidth();

	/**
	 * Get the height of the surrounding rectangle of this shape.
	 *
	 * @return the x coordinate.
	 */
	int getHeight();

	/**
	 * Get the x coordinate of this shape.
	 *
	 * @return the x coordinate.
	 */
	int getX();

	/**
	 * Give the y coordinate of this shape.
	 *
	 * @return the y coordinate.
	 */
	int getY();

	/**
	 * Move the shape the given distances. Check if the new coordinates are legal
	 * and if they are, set the position to the new coordinates. Otherwise an
	 * <code>IllegalPositionException</code> is thrown. Origo is (0,0) and only
	 * positive coordinates are legal.
	 *
	 * @param dx move the shape dx points to the right.
	 * @param dy move the shape dy points down.
	 * @throws IllegalPositionException if any coordinate becomes negative.
	 */
	void move(int dx, int dy) throws IllegalPositionException;

	/**
	 * Get the perimeter of this shape rounded to closest integer.
	 *
	 * @return the computed perimeter.
	 */
	int getPerimeter();

	/**
	 * Place the shape on given coordinates. Check if the coordinates are legal.
	 * Origo is (0,0) and only positive coordinates are legal. If they are legal,
	 * update the position with the new coordinates. Otherwise an
	 * <code>IllegalPositionException</code> is thrown.
	 *
	 * @param x Distance right from origo.
	 * @param y Distance down from origo.
	 * @throws <code>IllegalPositionException</code> if any coordinate is negative.
	 */
	void place(int x, int y) throws IllegalPositionException;

}// GeometricalForm
