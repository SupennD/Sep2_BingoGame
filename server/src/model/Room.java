package model;

import utils.blocking.BlockingArrayDeque;
import utils.blocking.BlockingDeque;

import java.util.ArrayList;

/**
 * A class that contains and adds some players in a blocking deque
 *
 * @author Supendra Bogati
 * @version 1.0.0 - May 2024
 */
public class Room
{
  private static final int CAPACITY = 4;
  private static int nextId = 1;
  private final int id;
  private final BlockingDeque<Player> players;

  public Room()
  {
    this.id = nextId++;
    this.players = new BlockingArrayDeque<>(true, CAPACITY);
  }

  public int getId()
  {
    return id;
  }

  public ArrayList<Player> getPlayers()
  {
    return players.toArrayList();
  }

  public boolean isAvailable()
  {
    return players.size() < CAPACITY;
  }

  public void addPlayer(Player player)
  {
    players.enqueue(player);
  }

  @Override public boolean equals(Object obj)
  {
    if (obj == null || getClass() != obj.getClass())
    {
      return false;
    }

    Room other = (Room) obj;

    return id == other.id;
  }

  @Override public String toString()
  {
    return "Room{" + id + ", players=" + players + '}';
  }
}
