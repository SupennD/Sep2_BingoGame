package mediator;

import utility.observer.listener.GeneralListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeHandler;

/**
 * A mediator class used to decouple the listeners from the subjects. Should be used for firing game specific events. It
 * will re-route the events to its listeners.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - May 2024
 */
public class GameEvents implements LocalSubject<Object, Object>
{
  private static final Object lock = new Object();
  private static GameEvents instance;
  private final PropertyChangeHandler<Object, Object> propertyChangeHandler;

  /**
   * Private constructor that creates an instance of this class.
   */
  private GameEvents()
  {
    this.propertyChangeHandler = new PropertyChangeHandler<>(this, true);
  }

  /**
   * A method to be used for getting a singleton instance in a thread safe way of this class.
   *
   * @return the singleton instance
   */
  public static GameEvents getInstance()
  {
    if (instance == null)
    {
      synchronized (lock)
      {
        if (instance == null)
        {
          instance = new GameEvents();
        }
      }
    }
    return instance;
  }

  @Override public boolean addListener(GeneralListener<Object, Object> generalListener, String... strings)
  {
    return propertyChangeHandler.addListener(generalListener, strings);
  }

  @Override public boolean removeListener(GeneralListener<Object, Object> generalListener, String... strings)
  {
    return propertyChangeHandler.removeListener(generalListener, strings);
  }

  public void fireEvent(String propertyName, Object oldValue, Object newValue)
  {
    propertyChangeHandler.firePropertyChange(propertyName, oldValue, newValue);
  }
}
