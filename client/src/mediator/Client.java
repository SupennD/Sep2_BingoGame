package mediator;

import model.Model;
import model.Player;
import model.Room;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.RemoteListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeHandler;
import utility.observer.subject.RemoteSubject;
import utils.log.Log;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * A class that will be exposed as a remote object using RMI, so that the server can call methods on it. This is also a
 * {@link RemoteListener} which allows it to react to changes in a {@link RemoteSubject}, like the server's
 * {@link RemoteModel}. Mostly this class will delegate actual work to the remote model.
 *
 * @author Alexandru Tofan
 * @author Supendra Bogati
 * @version 1.1.0 - April 2024
 */
public class Client implements Model, RemoteListener<Object, Object>
{
  private final int PORT = 1099;
  private final RemoteModel remoteModel;
  private final PropertyChangeHandler<Object, Object> propertyChangeHandler;
  private final Log log = Log.getInstance();

  /**
   * Constructor responsible for initializing variables and starting the RMI server.
   */
  public Client(String host) throws RemoteException, MalformedURLException, NotBoundException
  {
    start(); // Start the RMI server

    this.remoteModel = (RemoteModel) Naming.lookup("rmi://" + host + ":" + PORT + "/RemoteModel");
    this.remoteModel.addListener(this);
    this.propertyChangeHandler = new PropertyChangeHandler<>(this, true);
  }

  /**
   * A method that can be used to start the RMI server
   *
   * @throws RemoteException if something goes wrong starting the server
   */
  private void start() throws RemoteException
  {
    // Export the object and start waiting for calls.
    // We don't need to create a registry or bind the name because
    // the client obj will be passed as an argument to the server.
    UnicastRemoteObject.exportObject(this, 0);
    log.info("Client server started...");
  }

  /**
   * A method that can be used to stop the RMI server
   *
   * @throws RemoteException if something goes wrong stopping the server
   */
  public void stop() throws RemoteException
  {
    // Un-export the object and stop accepting calls
    UnicastRemoteObject.unexportObject(this, false);
    log.info("Client server stopped...");
  }

  @Override public Player register(String userName, String password)
      throws IllegalArgumentException, IllegalStateException
  {
    try
    {
      return remoteModel.register(userName, password);
    }
    catch (RemoteException e)
    {
      throw new IllegalStateException(e);
    }
  }

  @Override public Player login(String userName, String password) throws IllegalArgumentException, IllegalStateException
  {
    try
    {
      return remoteModel.login(userName, password);
    }
    catch (RemoteException e)
    {
      throw new IllegalStateException(e);
    }
  }

  @Override public Room joinRoom(Player player)
  {
    try
    {
      return remoteModel.joinRoom(player);
    }
    catch (RemoteException e)
    {
      throw new IllegalStateException(e);
    }
  }

  /**
   * Add a local listener to this local subject.
   *
   * @see LocalSubject
   */
  @Override public boolean addListener(GeneralListener<Object, Object> listener, String... propertyNames)
  {
    log.debug("Added listener: " + listener);
    return propertyChangeHandler.addListener(listener, propertyNames);
  }

  /**
   * Remove a local listener to this local subject.
   *
   * @see LocalSubject
   */
  @Override public boolean removeListener(GeneralListener<Object, Object> listener, String... propertyNames)
  {
    log.debug("Removed listener: " + listener);
    return propertyChangeHandler.removeListener(listener, propertyNames);
  }

  /**
   * React to changes this listener is subscribed to.
   *
   * @see RemoteListener
   */
  @Override public void propertyChange(ObserverEvent<Object, Object> observerEvent)
  {
    log.debug("Remote event received: " + observerEvent);
    propertyChangeHandler.firePropertyChange(observerEvent);
  }
}