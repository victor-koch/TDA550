package orig2019.v4;

import java.beans.PropertyChangeListener;

public interface Observable {

	void addObserver(PropertyChangeListener observer);
	
	void removeObserver(PropertyChangeListener observer);
	
}
