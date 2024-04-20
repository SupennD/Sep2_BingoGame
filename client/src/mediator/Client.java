package mediator;

import model.Model;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.RemoteListener;

import java.rmi.RemoteException;

/**
 * A class that will be exposed as a remote object using RMI, so that the server can call methods on it. This is also a
 * {@link RemoteListener} which allows it to react to changes in a {@link utility.observer.subject.RemoteSubject}, like
 * the server's {@link RemoteModel}. Mostly this class will delegate actual work to the {@link Model}.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - April 2024
 */
public class Client implements Model, RemoteListener<Object, Object>
{
  /**
   * Constructor responsible for initializing variables and starting the RMI server.
   *
   * @param host the RMI server host
   * @param port the RMI server port
   */
  public Client(String host, int port)
  {
    // TODO: implement
  }

  /**
   * A method that can be used to stop the RMI server
   *
   * @throws RemoteException if something goes wrong stopping the server
   */
  public void stop() throws RemoteException
  {
    // TODO: implement
  }

  /**
   * React to changes this listener is subscribed to.
   *
   * @see RemoteListener
   */
  @Override public void propertyChange(ObserverEvent<Object, Object> observerEvent)
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