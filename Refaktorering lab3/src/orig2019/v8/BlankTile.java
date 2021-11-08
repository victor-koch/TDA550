package orig2019.v8;

import java.awt.Dimension;
import java.awt.Graphics;

public class BlankTile implements GameTile {

	@Override
	public void draw(Graphics g, int x, int y, Dimension d) {
		// The default GameTile is transparent,
		// therefore no drawing is performed.
	}

}
