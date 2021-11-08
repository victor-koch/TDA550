package orig2019.v6;

import java.beans.PropertyChangeListener;

public interface Observable {

	void addObserver(PropertyChangeListener observer);
	
	void removeObserver(PropertyChangeListener observer);
	
}
