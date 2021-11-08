package geometri;

//import geometri.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.SplittableRandom;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * A small animation of constructing different geometrical shapes, where color
 * and postion are random. Thereafter it is possible to move all shapes to the
 * upper left corner of the drawing area, i.e. moving them pixel by pixel to
 * origo. All shapes are drawn in order of their size, with the biggest shape
 * first and smallest last.
 * <P>
 * <code>Point</code> have been increased to 2 pixels in diameter and leaves a
 * trace on its way to origo. When a <code>Point</code> is choosen, each second
 * time it become black, which will be checked in a <code>HashSet</code>, in
 * order to test the methods of <code>equals</code> and <code>hashCode</code>.
 *
 * @author Bror Bjerner
 * @author Christer Carlsson
 * @author Pelle Evensen, evensen@chalmers.se
 * @version (nov 2019)
 */
public final class MoveToUpperLeftCorner extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final List<ThickPoint> points = new LinkedList<>();
	private final Set<ThickPoint> pointSet = new HashSet<>();
	private final List<TracePoint> tracePoints = new LinkedList<>();
	private final SortedSet<GeometricShape> shapes = new TreeSet<>();
	private final SplittableRandom rng = new SplittableRandom(1);

	// Buttons for each shape that can be choosen, a button to start the moving and
	// a button to restart the whole animation
	private final JButton point = new JButton("Thick point");
	private final JButton line = new JButton("Line");
	private final JButton square = new JButton("Square");
	private final JButton rectangle = new JButton("Rectangle");
	private final JButton circle = new JButton("Circle");
	private final JButton ellipse = new JButton("Ellipse");
	private final JButton startMove = new JButton("Start moving");
	private final JButton restart = new JButton("Restart");

	// Create a new drawing area. Class DrawingArea defined further down in this
	// file. Must be an instance field, since it contains a Timer.
	private final DrawingArea drawingArea = new DrawingArea();

	/**
	 * The whole basic picture is constructed in the constructor.
	 */
	public MoveToUpperLeftCorner() {
		// Make all listeners active.
		this.point.addActionListener(this);
		this.line.addActionListener(this);
		this.square.addActionListener(this);
		this.rectangle.addActionListener(this);
		this.circle.addActionListener(this);
		this.ellipse.addActionListener(this);
		this.startMove.addActionListener(this);
		this.restart.addActionListener(this);

		// Colour the buttons
		this.point.setBackground(Color.YELLOW);
		this.line.setBackground(Color.YELLOW);
		this.square.setBackground(Color.YELLOW);
		this.rectangle.setBackground(Color.YELLOW);
		this.circle.setBackground(Color.YELLOW);
		this.ellipse.setBackground(Color.YELLOW);
		this.startMove.setBackground(Color.MAGENTA);
		this.restart.setBackground(Color.ORANGE);

		// Place the buttons
		final JPanel buttons = new JPanel();
		buttons.add(this.point);
		buttons.add(this.line);
		buttons.add(this.square);
		buttons.add(this.rectangle);
		buttons.add(this.circle);
		buttons.add(this.ellipse);
		buttons.add(this.startMove);
		buttons.add(this.restart);

		// Place buttons and drawingArea into the frame
		final Container content = getContentPane();
		content.add(this.drawingArea, BorderLayout.CENTER);
		content.add(buttons, BorderLayout.SOUTH);
		setSize(760, 780);

		/*
		 * Use this if you want to check vertical and horisontal lines try {
		 * shapes.add(new Line( 10, 10, 10, 60, new Color(50, 75, 200))); shapes.add(new
		 * Line( 20, 20, 60, 20, new Color(50, 200, 100))); } catch
		 * (IllegalPositionException ipe) {
		 * System.out.println("Something is rotten in Denmark."); }
		 */

		// Show the frame at start.
		setVisible(true);
	}

	/**
	 * All actions performed when a button is pressed.
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// Coordinates and colour are chosen at random.
		final int x = this.rng.nextInt(600);
		final int y = this.rng.nextInt(600);
		final Color c = new Color(this.rng.nextInt(100), this.rng.nextInt(100), this.rng.nextInt(100));
		try {
			if (e.getSource() == this.point) {
				addThickPoint(x, y, c);
			} else if (e.getSource() == this.line) {
				addLine(x, y, c);
			} else if (e.getSource() == this.square) {
				addSquare(x, y, c);
			} else if (e.getSource() == this.rectangle) {
				addRectangle(x, y, c);
			} else if (e.getSource() == this.circle) {
				addCircle(x, y, c);
			} else if (e.getSource() == this.ellipse) {
				addEllipse(x, y, c);
			} else if (e.getSource() == this.startMove) {
				startMove();
			} else if (e.getSource() == this.restart) {
				restart();
			}
		} catch (final IllegalPositionException ipe) {
			JOptionPane.showMessageDialog(this, "Illegal position, try again.");
		} catch (final NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "Only positive integers allowed, try again.");
		}
		repaint();
	}

	private void addThickPoint(final int x, final int y, final Color c) throws IllegalPositionException {
		final ThickPoint p = new ThickPoint(x, y, c);
		if (this.pointSet.contains(p)) {
			JOptionPane.showMessageDialog(this, "Point with this color already exists, try again.");
		} else {
			this.pointSet.add(p); // just in order to check hashCode !!
			this.points.add(p);
			JOptionPane.showMessageDialog(this, "A new random thick point is created");
		}
	}

	private void addLine(final int x, final int y, final Color c) throws HeadlessException, IllegalPositionException {
		if (this.shapes.add(new Line(x, y, this.rng.nextInt(720), this.rng.nextInt(720), c))) {
			JOptionPane.showMessageDialog(this, "A new random line is created.");
		} else {
			JOptionPane.showMessageDialog(this, "Line with this size already exists, try again");
		}
	}

	private void addSquare(final int x, final int y, final Color c) throws HeadlessException, IllegalPositionException {
		final int length = Integer.parseInt(JOptionPane.showInputDialog("Choose side length (1-500):"));
		if (!this.shapes.add(new Square(x, y, length, c))) {
			JOptionPane.showMessageDialog(this, "Square with this size already exists, try again");
		}
	}

	private void addRectangle(final int x, final int y, final Color c)
			throws HeadlessException, IllegalPositionException {
		final int width = Integer.parseInt(JOptionPane.showInputDialog("Choose width (1-500)"));
		final int heigth = Integer.parseInt(JOptionPane.showInputDialog("Choose heigth (1-500)"));
		if (!this.shapes.add(new Rectangle(x, y, width, heigth, c))) {
			JOptionPane.showMessageDialog(this, "Rectangle with this size already exists, try again");
		}
	}

	private void addCircle(final int x, final int y, final Color c) throws HeadlessException, IllegalPositionException {
		final int radius = Integer.parseInt(JOptionPane.showInputDialog("Choose diameter (1-500)"));
		System.out.println(c);
		if (!this.shapes.add(new Circle(x, y, radius, c))) {
			JOptionPane.showMessageDialog(this, "Circle with this size already exists, try again");
		}
	}

	private void addEllipse(final int x, final int y, final Color c)
			throws HeadlessException, IllegalPositionException {
		final int width = Integer.parseInt(JOptionPane.showInputDialog("Choose width (1-500)"));
		final int heigth = Integer.parseInt(JOptionPane.showInputDialog("Choose heigth (1-500)"));
		if (!this.shapes.add(new Ellipse(x, y, width, heigth, c))) {
			JOptionPane.showMessageDialog(this, "Ellipse with this size already exists, try again");
		}
	}

	private void startMove() {
		this.drawingArea.start();
	}

	private void restart() {
		this.points.clear();
		this.pointSet.clear();
		this.shapes.clear();
		this.tracePoints.clear();
	}

	// Move the position one pixel closer to the origin. The
	// boolean value returned indicates if the position has changed, i.e. if the
	// position differed from <0,0>.
	private static boolean moveShape(final GeometricShape f) {
		final int x = f.getX();
		final int y = f.getY();
		if (x == 0 && y == 0) {
			return false;
		}
		try {
			f.place(Math.max(x - 1, 0), Math.max(y - 1, 0));
		} catch (final IllegalPositionException ipe) {
			// Can not happen!
		}
		return true;
	}

	// Make a drawing area containg a Timer, which is used to move
	// all shapes in the drawing area step by step to the origin.
	private class DrawingArea extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private boolean notInPlace;
		private final javax.swing.Timer tim = new javax.swing.Timer(20, this);

		public DrawingArea() {
			setSize(700, 700);
			setBackground(Color.white);
		}

		public void start() {
			this.notInPlace = true;
			for (final ThickPoint p : MoveToUpperLeftCorner.this.points) {
				MoveToUpperLeftCorner.this.tracePoints.add(new TracePoint(p, p.getColor()));
			}
			this.tim.start();
		}

		public void stop() {
			MoveToUpperLeftCorner.this.tracePoints.clear();
			this.tim.stop();
		}

		@Override
		public void paint(final Graphics g) {
			super.paint(g);
			for (final GeometricShape f : MoveToUpperLeftCorner.this.shapes) {
				f.fill(g);
			}
			for (final ThickPoint p : MoveToUpperLeftCorner.this.points) {
				p.fill(g);
			}
			for (final TracePoint p : MoveToUpperLeftCorner.this.tracePoints) {
				p.fill(g);
			}
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			if (this.notInPlace) {
				this.notInPlace = false;
				for (final ThickPoint p : MoveToUpperLeftCorner.this.points) {
					if (MoveToUpperLeftCorner.moveShape(p)) {
						this.notInPlace = true;
					}
				}
				for (final ThickPoint p : MoveToUpperLeftCorner.this.points) {
					MoveToUpperLeftCorner.this.tracePoints.add(new TracePoint(p, p.getColor()));
				}
				for (final GeometricShape f : MoveToUpperLeftCorner.this.shapes) {
					if (MoveToUpperLeftCorner.moveShape(f)) {
						this.notInPlace = true;
					}
				}
				repaint();
			} else {
				stop();
			}
		}
	}

	/**
	 * Start the animation.
	 */
	public static void main(final String[] args) {
		final MoveToUpperLeftCorner mainFrame = new MoveToUpperLeftCorner();
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	// In order to make points more visible in the drawing area
	private class ThickPoint extends Point {
		public ThickPoint(final int x, final int y, final Color c) throws IllegalPositionException {
			super(x, y, c);
		}

		public ThickPoint(final Point p, final Color c) {
			super(p, c);
		}

		// Quadruples the thickness of the point.
		@Override
		public void fill(final Graphics g) {
			g.setColor(getColor());
			g.fillOval(getX(), getY(), 4, 4);
		}
	}

	// In order to show the trace of the points in the drawing area
	private class TracePoint extends Point {
		public TracePoint(final int x, final int y, final Color c) throws IllegalPositionException {
			super(x, y, c);
		}

		public TracePoint(final Point p, final Color c) {
			super(p, c);
		}

		// Doubles the thickness of the point.
		@Override
		public void fill(final Graphics g) {
			g.setColor(getColor());
			g.fillOval(getX(), getY(), 2, 2);
		}
	}

}
