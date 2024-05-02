package model;

import mediator.Client;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeHandler;

/**
 * A facade exposing methods to be used by clients of this application. This is also a {@link LocalSubject} in an
 * observer pattern that can be used by {@link LocalListener}s to subscribe to changes and receive updates
 * automatically, as part of the whole MVVM application model.
 *
 * @author Alexandru Tofan
 * @author Supendra Bogati
 * @version 1.1.0 - April 2024
 */
public class ModelManager implements Model, LocalListener<Object, Object>
{
  private final Client client;
  private final PropertyChangeHandler<Object, Object> propertyChangeHandler;

  /**
   * Constructor that returns the facade object and has access to the RMI client.
   */
  public ModelManager(Client client)
  {
    this.client = client;
    this.client.addListener(this);
    this.propertyChangeHandler = new PropertyChangeHandler<>(this, true);
  }

  @Override public synchronized void register(String userName, String password)
      throws IllegalArgumentException, IllegalStateException
  {
    client.register(userName, password);
  }

  @Override public synchronized void login(String userName, String password)
      throws IllegalArgumentException, IllegalStateException
  {
    client.login(userName, password);
  }

  /**
   * Add a local listener to this local subject.
   *
   * @see LocalSubject
   */
  @Override public boolean addListener(GeneralListener<Object, Object> listener, String... propertyNames)
  {
    return propertyChangeHandler.addListener(listener, propertyNames);
  }

  /**
   * Remove a local listener to this local subject.
   *
   * @see LocalSubject
   */
  @Override public boolean removeListener(GeneralListener<Object, Object> listener, String... propertyNames)
  {
    return propertyChangeHandler.removeListener(listener, propertyNames);
  }

  @Override public void propertyChange(ObserverEvent<Object, Object> observerEvent)
  {
    // TODO: implement logic for "broadcasting" events
  }
}