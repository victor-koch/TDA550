package orig2019.v8;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ReversiScoreView implements PropertyChangeListener {
	
	private int score;
	
	private ReversiModel game;
	
	public ReversiScoreView(ReversiModel game) {
		if(game != null) {
			this.game = game;
			game.addObserver(this);
		}
		score = game.getBlackScore();
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getSource().getClass() == ReversiModel.class && score != game.getBlackScore()) {
			System.out.println("White: " + game.getWhiteScore() + " Black: " + game.getBlackScore() + " Now it is " + game.getTurnColor() + "'s turn!");
			score = game.getBlackScore();
		}
	}

}
