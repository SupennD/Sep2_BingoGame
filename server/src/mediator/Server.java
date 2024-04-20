package mediator;

import model.Model;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * A class that will be exposed as a remote object using RMI, to be used by clients for communicating with the server.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - April 2024
 */
public class Server implements RemoteModel, LocalListener<Object, Object>
{
  /**
   * The default constructor responsible for initializing variables and starting the RMI server.
   *
   * @param model the main application model
   */
  public Server(Model model)
  {
    // TODO: implement
  }

  /**
   * A method that can be used to stop the RMI server
   *
   * @throws RemoteException if something goes wrong stopping the server
   * @throws MalformedURLException if remote name is wrong
   */
  public void stop() throws RemoteException, MalformedURLException
  {
    // TODO: implement
  }

  /**
   * React to changes this listener is subscribed to.
   *
   * @see LocalListener
   */
  @Override public void propertyChange(ObserverEvent<Object, Object> observerEvent)
  {
    // TODO: implement
  }

  /**
   * Add a remote or local listener to this remote subject.
   *
   * @see utility.observer.subject.RemoteSubject
   */
  @Override public boolean addListener(GeneralListener<Object, Object> listener, String... propertyNames)
      throws RemoteException
  {
    return false; // TODO: implement
  }

  /**
   * Remove a remote or local listener to this remote subject.
   *
   * @see utility.observer.subject.RemoteSubject
   */
  @Override public boolean removeListener(GeneralListener<Object, Object> listener, String... propertyNames)
      throws RemoteException
  {
    return false; // TODO: implement
  }
}