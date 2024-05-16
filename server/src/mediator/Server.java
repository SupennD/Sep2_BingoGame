package mediator;

import model.Model;
import model.Player;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.PropertyChangeHandler;
import utils.log.Log;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

/**
 * A class that will be exposed as a remote object using RMI, to be used by clients for communicating with the server.
 *
 * @author Alexandru Tofan
 * @version 1.1.0 - April 2024
 */
public class Server implements RemoteModel, LocalListener<Object, Object>
{
  private final Model model;
  private final int PORT = 1099;
  private final Log log = Log.getInstance();
  private final PropertyChangeHandler<Object, Object> propertyChangeHandler;

  /**
   * The default constructor responsible for initializing variables and starting the RMI server.
   *
   * @param model the main application model
   */
  public Server(Model model) throws MalformedURLException, RemoteException
  {
    this.model = model;
    this.model.addListener(this);
    this.propertyChangeHandler = new PropertyChangeHandler<>(this, true);

    start(); // Start the RMI server
  }

  private void start() throws RemoteException, MalformedURLException
  {
    try
    {
      // Start the registry
      LocateRegistry.createRegistry(PORT);
      log.info("Registry started...");
    }
    catch (ExportException e)
    {
      log.warn("Registry already started, " + e.getMessage());
    }

    // Export the object and start waiting for calls
    UnicastRemoteObject.exportObject(this, 0);
    log.info("Server started...");

    // Add the stub to the registry
    Naming.rebind("RemoteModel", this);
    log.info("Stub added to the registry...");
  }

  /**
   * A method that can be used to stop the RMI server
   *
   * @throws RemoteException if something goes wrong stopping the server
   * @throws MalformedURLException if remote name is wrong
   */
  public void stop() throws RemoteException, MalformedURLException
  {
    try
    {
      // Un-export the object and stop accepting calls
      UnicastRemoteObject.unexportObject(this, false);
      log.info("Server stopped...");

      // Remove the stub from the registry
      Naming.unbind("RemoteModel");
      log.info("Stub removed from the registry...");
    }
    catch (NotBoundException e)
    {
      log.warn("The name is not in the registry, " + e.getMessage());
    }
  }

  @Override public Player register(String userName, String password) throws RemoteException
  {
    return model.register(userName, password);
  }

  @Override public Player login(String userName, String password) throws RemoteException
  {
    return model.login(userName, password);
  }

  @Override public int joinRoom(Player player) throws RemoteException
  {
    return model.joinRoom(player);
  }

  @Override public String getRules(int roomId) throws RemoteException
  {
    return model.getRules(roomId);
  }

  /**
   * Add a remote or local listener to this remote subject.
   *
   * @see utility.observer.subject.RemoteSubject
   */
  @Override public boolean addListener(GeneralListener<Object, Object> listener, String... propertyNames)
      throws RemoteException
  {
    log.debug("Adding listener: " + listener);
    return propertyChangeHandler.addListener(listener, propertyNames);
  }

  /**
   * Remove a remote or local listener to this remote subject.
   *
   * @see utility.observer.subject.RemoteSubject
   */
  @Override public boolean removeListener(GeneralListener<Object, Object> listener, String... propertyNames)
      throws RemoteException
  {
    log.debug("Removing listener: " + listener);
    return propertyChangeHandler.removeListener(listener, propertyNames);
  }

  /**
   * React to changes this listener is subscribed to.
   *
   * @see LocalListener
   */
  @Override public void propertyChange(ObserverEvent<Object, Object> observerEvent)
  {
    log.debug("Model event received: " + observerEvent);
    propertyChangeHandler.firePropertyChange(observerEvent.getPropertyName(), observerEvent.getValue1(),
        observerEvent.getValue2());
  }
}