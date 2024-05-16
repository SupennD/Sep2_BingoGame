package model;

import utils.blocking.BlockingArrayDeque;
import utils.blocking.BlockingDeque;
import utils.log.Log;

/**
 * A class that contains an arraylist of rooms and necessary methods
 *
 * @author Supendra Bogati
 * @version 1.0.0 - May 2024
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

  public String getRules(int roomId)
  {
    // TODO: find a better way to do this
    for (Room room : rooms.toArrayList())
    {
      if (room.getId() == roomId)
      {
        return room.getRules();
      }
    }

    return null;
  }
}
