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
 * @version 1.3.0 - May 2024
 */
public class ModelManager implements Model
{
  private final PropertyChangeHandler<Object, Object> propertyChangeHandler;
  private final Persistence persistence;
  private final RoomList roomList;

  /**
   * Constructor that returns the facade object
   */
  public ModelManager() throws ClassNotFoundException
  {
    this.propertyChangeHandler = new PropertyChangeHandler<>(this, true);
    this.persistence = new DatabaseCache();
    this.roomList = new RoomList();
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

  @Override public int joinRoom(Player player)
  {
    Room room = roomList.joinRoom(player);
    propertyChangeHandler.firePropertyChange("room:join", room.getPlayers(), room.getId());

    if (room.isFull())
    {
      propertyChangeHandler.firePropertyChange("room:full", null, room.getId());
    }

    return room.getId();
  }

  @Override public String getRules(int roomId) throws IllegalStateException
  {
    return roomList.getRules(roomId);
  }

  @Override public void startGame(int roomId) throws IllegalStateException
  {
    roomList.startGame(roomId);
  }

  @Override public void makeMove(int roomId, Player player, int number) throws IllegalStateException
  {
    roomList.makeMove(roomId, player, number);
  }

  @Override public void callBingo(int roomId, Player player) throws IllegalStateException
  {
    roomList.callBingo(roomId, player);
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