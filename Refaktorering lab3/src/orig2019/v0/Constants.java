package orig2019.v0;

import java.awt.Dimension;

/**
 * Provides a fixed size for various games.
 * 
 * @author Pelle Evensen, evensen@chalmers.se
 */
public enum Constants {
	;
	// Safe Singleton pattern, prevent instantiation.
	/** An immutable Dimension object of constant size. */
	private static final Dimension SIZE = new Dimension(10, 10);

	/** @return an copy of the Dimension constant. */
	public static Dimension getGameSize() {
		// Dimension is a mutable class, copy to prevent mutation.
		return new Dimension(SIZE);
	}
}
