package model;

import utils.blocking.BlockingArrayDeque;
import utils.blocking.BlockingDeque;

/**
 * A class that contains an arraylist of rooms and necessary methods
 *
 * @author Supendra Bogati
 * @version 1.0.0 - May 2024
 */
public class RoomList
{
  private final BlockingDeque<Room> rooms;

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

    if (room != null && room.isAvailable())
    {
      return room;
    }

    return new Room();
  }
}
