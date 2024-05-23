package model;

import model.card.Cell;
import model.uPickBingo.UPickBingoGame;
import utils.blocking.BlockingArrayDeque;
import utils.blocking.BlockingDeque;
import utils.log.Log;

/**
 * A class that contains an arraylist of rooms and necessary methods
 *
 * @author Supendra Bogati
 * @author Alexandru Tofan
 * @version 1.2.0 - May 2024
 */
public class RoomList
{
  private final BlockingDeque<Room> rooms;
  private final Log log = Log.getInstance();

  public RoomList()
  {
    this.rooms = new BlockingArrayDeque<>(false);
  }

  public Room joinRoom(Player player)
  {
    Room room = getAvailableRoom();
    room.addPlayer(player);

    return room;
  }

  public String getRules(int roomId)
  {
    Room room = getRoomById(roomId);
    return room.getRules();
  }

  public void makeMove(int roomId, Player player, Cell cell)
  {
    Room room = getRoomById(roomId);
    room.makeMove(player, cell);
  }

  public void startGame(int roomId)
  {
    Room room = getRoomById(roomId);
    room.startGame();
  }

  public void callBingo(int roomId, Player player)
  {
    Room room = getRoomById(roomId);
    room.callBingo(player);
    rooms.remove(room);
  }

  public void leaveRoom(int roomId, Player player)
  {
    Room room = getRoomById(roomId);
    room.leaveRoom(player);
    if (room.isEmpty())
    {
      rooms.remove(room);
    }
  }

  private Room getAvailableRoom()
  {
    Room room = rooms.peek();

    if (room == null || room.isFull())
    {
      log.info("No available room found. Creating a new one.");
      room = new Room(new UPickBingoGame());
      rooms.push(room);
    }

    log.info("Available room: " + room);

    return room;
  }

  private Room getRoomById(int roomId)
  {
    for (Room room : rooms.toArrayList())
    {
      if (room.getId() == roomId)
      {
        return room;
      }
    }

    throw new IllegalStateException("No room found with id: " + roomId);
  }
}