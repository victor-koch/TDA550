package geometri;

import java.awt.Color;

import javax.swing.WindowConstants;
import geometri.*;

public class Main {

	public static void main(String[] args) {
		
		BasicGeometricShape a;
		BasicGeometricShape b;
		Circle c;
		
		try {
			a = new Circle(10, 0, 10, new Color(255, 0, 0));
			b = new Circle(10, 0, 10, new Color(255, 0, 0));
			c = new Circle(10, 0, 10, new Color(255, 0, 0));
			
			System.out.println("a = a? (true)");
			System.out.println(a.equals(a));
			System.out.println("b = b? (true)");
			System.out.println(b.equals(b));
			System.out.println("a = b? (true)");
			System.out.println(a.equals(b));
			System.out.println("b = a? (true)");
			System.out.println(b.equals(a));
			
			System.out.println("a = c (true)?");
			System.out.println(a.equals(c));
			System.out.println("c = a (true)?");
			System.out.println(c.equals(a));
			
			System.out.println(a.getArea());
			System.out.println(a.getPerimeter());
			
		} catch (IllegalPositionException e) {
			System.out.println(e);
		}
	}

}
