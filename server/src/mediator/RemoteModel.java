package mediator;

import model.Player;
import model.Room;
import utility.observer.subject.RemoteSubject;

import java.rmi.RemoteException;

/**
 * An interface intended to be implemented by model classes that want to expose themselves as remote objects. This
 * should be the same on client and server. If you make updates to one, make sure to update the other one as well.
 *
 * @author Alexandru Tofan
 * @author Supendra Bogati
 * @author Lucia Andronic
 * @version 1.2.1 - May 2024
 */
public interface RemoteModel extends RemoteSubject<Object, Object>
{
  Player register(String userName, String password) throws RemoteException;
  Player login(String userName, String password) throws RemoteException;
  Room joinRoom(Player player) throws RemoteException;
  String getRules() throws RemoteException;
}