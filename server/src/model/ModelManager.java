package model;

import persistence.DatabaseCache;
import persistence.Persistence;
import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeHandler;

/**
 * A facade exposing methods to be used by clients of this application. This is also a
 * {@link utility.observer.subject.LocalSubject} in an observer pattern that can be used by
 * {@link utility.observer.listener.LocalListener}s to subscribe to changes and receive updates automatically, as part
 * of the whole MVVM application model.
 *
 * @author Alexandru Tofan
 * @author Lucia Andronic
 * @version 1.1.0 - April 2024
 */
public class ModelManager implements Model
{
  private final PropertyChangeHandler<Object, Object> propertyChangeHandler;
  private final Persistence persistence;
  private final Game game;

  /**
   * Constructor that returns the facade object
   */
  public ModelManager(Game game) throws ClassNotFoundException
  {
    this.propertyChangeHandler = new PropertyChangeHandler<>(this, true);
    this.persistence = new DatabaseCache();
    this.game = game;
  }

  @Override public synchronized Player register(String userName, String password)
      throws IllegalArgumentException, IllegalStateException
  {
    User user = persistence.addUser(userName, password);
    Player player = new Player(userName, password);
    propertyChangeHandler.firePropertyChange("register", user, player);

    return player;
  }

  @Override public synchronized Player login(String userName, String password)
      throws IllegalArgumentException, IllegalStateException
  {
    User user = persistence.getUser(userName, password);
    Player player = new Player(userName, password);
    propertyChangeHandler.firePropertyChange("login", user, player);

    return player;
  }

  @Override public Room joinRoom(Player player)
  {
    Room room = game.joinRoom(player);
    propertyChangeHandler.firePropertyChange("joinRoom", null, room);

    return room;
  }

  @Override public String getRules() throws IllegalStateException
  {
    return game.getRules();
  }

  /**
   * Add a local listener to this local subject.
   *
   * @see utility.observer.subject.LocalSubject
   */
  @Override public boolean addListener(GeneralListener<Object, Object> listener, String... propertyNames)
  {
    return propertyChangeHandler.addListener(listener, propertyNames);
  }

  /**
   * Remove a local listener to this local subject.
   *
   * @see utility.observer.subject.LocalSubject
   */
  @Override public boolean removeListener(GeneralListener<Object, Object> listener, String... propertyNames)
  {
    return propertyChangeHandler.removeListener(listener, propertyNames);
  }
}