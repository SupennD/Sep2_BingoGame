package mediator;

import utility.observer.subject.RemoteSubject;

import java.rmi.RemoteException;

/**
 * An interface intended to be implemented by model classes that want to expose themselves as remote objects. This
 * should be the same on client and server. If you make updates to one, make sure to update the other one as well.
 *
 * @author Alexandru Tofan
 * @author Supendra Bogati
 * @version 1.1.0 - April 2024
 */
public interface RemoteModel extends RemoteSubject<Object, Object>
{
  void register(String userName, String password) throws RemoteException;
  void login(String userName, String password) throws RemoteException;
}