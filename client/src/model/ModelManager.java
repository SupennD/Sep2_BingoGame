package model;

import mediator.Client;
import model.card.Cell;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeHandler;

import java.util.ArrayList;

/**
 * A facade exposing methods to be used by clients of this application. This is also a {@link LocalSubject} in an
 * observer pattern that can be used by {@link LocalListener}s to subscribe to changes and receive updates
 * automatically, as part of the whole MVVM application model.
 *
 * @author Alexandru Tofan
 * @author Supendra Bogati
 * @author Lucia Andronic
 * @version 1.4.0 - May 2024
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

  @Override public Player register(String userName, String password)
      throws IllegalArgumentException, IllegalStateException
  {
    return client.register(userName, password);
  }

  @Override public Player login(String userName, String password) throws IllegalArgumentException, IllegalStateException
  {
    return client.login(userName, password);
  }

  @Override public int joinRoom(Player player) throws IllegalStateException
  {
    return client.joinRoom(player);
  }

  @Override public void leaveRoom(int roomId, Player player) throws IllegalStateException
  {
    client.leaveRoom(roomId, player);
  }

  @Override public String getRules(int roomId) throws IllegalStateException
  {
    return client.getRules(roomId);
  }

  @Override public void startGame(int roomId) throws IllegalStateException
  {
    client.startGame(roomId);
  }

  @Override public void makeMove(int roomId, Player player, Cell cell) throws IllegalStateException
  {
    client.makeMove(roomId, player, cell);
  }

  @Override public void callBingo(int roomId, Player player) throws IllegalStateException
  {
    client.callBingo(roomId, player);
  }

  @Override public Player getScores(Player player) throws IllegalStateException
  {
    return client.getScores(player);
  }

  @Override public ArrayList<Player> getTopPlayers() throws IllegalStateException
  {
    return client.getTopPlayers();
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
    propertyChangeHandler.firePropertyChange(observerEvent);
  }
}