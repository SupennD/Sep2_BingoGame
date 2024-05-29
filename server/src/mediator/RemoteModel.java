package mediator;

import model.Player;
import model.card.Card;
import model.card.Cell;
import utility.observer.subject.RemoteSubject;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * An interface intended to be implemented by model classes that want to expose themselves as remote objects. This
 * should be the same on client and server. If you make updates to one, make sure to update the other one as well.
 *
 * @author Alexandru Tofan
 * @author Supendra Bogati
 * @author Lucia Andronic
 * @version 1.4.0 - May 2024
 */
public interface RemoteModel extends RemoteSubject<Object, Object>
{
  Player register(String userName, String password) throws RemoteException;
  Player login(String userName, String password) throws RemoteException;
  int joinRoom(Player player) throws RemoteException;
  void leaveRoom(int roomId, Player player) throws RemoteException;
  String getRules(int roomId) throws RemoteException;
  void startGame(int roomId) throws RemoteException;
  Card makeMove(int roomId, Player player, Cell cell) throws RemoteException;
  void callBingo(int roomId, Player player) throws RemoteException;
  Player getScores(Player player) throws RemoteException;
  ArrayList<Player> getTopPlayers() throws RemoteException;
}