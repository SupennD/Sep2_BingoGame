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
public class GameEvent implements LocalSubject<Object, Object>
{
  private static final Object lock = new Object();
  private static GameEvent instance;
  private final PropertyChangeHandler<Object, Object> propertyChangeHandler;

  /**
   * Private constructor that creates an instance of this class.
   */
  private GameEvent()
  {
    this.propertyChangeHandler = new PropertyChangeHandler<>(this, true);
  }

  /**
   * A method to be used for getting a singleton instance in a thread safe way of this class.
   *
   * @return the singleton instance
   */
  public static GameEvent getInstance()
  {
    if (instance == null)
    {
      synchronized (lock)
      {
        if (instance == null)
        {
          instance = new GameEvent();
        }
      }
    }
    return instance;
  }

  /**
   * Adds a general listener to this mediator.
   *
   * @param generalListener the listener to add
   * @param strings the property names to listen for
   * @return true if the listener is added successfully, false otherwise
   */
  @Override public boolean addListener(GeneralListener<Object, Object> generalListener, String... strings)
  {
    return propertyChangeHandler.addListener(generalListener, strings);
  }

  /**
   * Removes a general listener from this mediator.
   *
   * @param generalListener the listener to remove
   * @param strings the property names to stop listening for
   * @return true if the listener is removed successfully, false otherwise
   */
  @Override public boolean removeListener(GeneralListener<Object, Object> generalListener, String... strings)
  {
    return propertyChangeHandler.removeListener(generalListener, strings);
  }

  /**
   * Fires a property change event with the specified property name, old value, and new value.
   *
   * @param propertyName the name of the property that changed
   * @param oldValue the old value of the property
   * @param newValue the new value of the property
   */
  public void fireEvent(String propertyName, Object oldValue, Object newValue)
  {
    propertyChangeHandler.firePropertyChange(propertyName, oldValue, newValue);
  }
}
