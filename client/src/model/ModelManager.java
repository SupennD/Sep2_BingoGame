package model;

import mediator.Client;
import utility.observer.listener.GeneralListener;

/**
 * A facade exposing methods to be used by clients of this application. This is also a
 * {@link utility.observer.subject.LocalSubject} in an observer pattern that can be used by
 * {@link utility.observer.listener.LocalListener}s to subscribe to changes and receive updates automatically, as part
 * of the whole MVVM application model.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - April 2024
 */
public class ModelManager implements Model
{
  /**
   * Constructor that returns the facade object and has access to the RMI client.
   *
   * @param client the RMI client
   */
  public ModelManager(Client client)
  {
    // TODO: implement
  }

  /**
   * Add a local listener to this local subject.
   *
   * @see utility.observer.subject.LocalSubject
   */
  @Override public boolean addListener(GeneralListener<Object, Object> listener, String... propertyNames)
  {
    return false; // TODO: implement
  }

  /**
   * Remove a local listener to this local subject.
   *
   * @see utility.observer.subject.LocalSubject
   */
  @Override public boolean removeListener(GeneralListener<Object, Object> listener, String... propertyNames)
  {
    return false; // TODO: implement
  }
}