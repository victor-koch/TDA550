package orig2019.v0;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * This class creates an AWT window which will contain the game.
 */
public class Main {
	public static void main(final String[] args) {
		// Create a new frame (a window)
		final JFrame frame = new JFrame();

		final GUIView guiView = new GUIView(new BaseGameFactory());

		frame.setTitle("Games 2.0");

		// Add gui to window
		frame.add(guiView);

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// pack() will do the layout of the window so it gets the correct size
		frame.pack();

		// Open the window
		frame.setVisible(true);
		frame.requestFocus();
	}
}
