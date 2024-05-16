package model;

import utils.blocking.BlockingArrayDeque;
import utils.blocking.BlockingDeque;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class that contains and adds some players in a blocking deque
 *
 * @author Supendra Bogati
 * @author Lucia Andronic
 * @version 1.1.0 - May 2024
 */
public class Room implements Serializable
{
  private static final int CAPACITY = 4;
  private static int nextId = 1;
  private final int id;
  private final BlockingDeque<Player> players;
  private final Game game;

  public Room(Game game)
  {
    this.id = nextId++;
    this.players = new BlockingArrayDeque<>(true, CAPACITY);
    this.game = game;
  }

  public int getId()
  {
    return id;
  }

  public Player getPlayer(Player player)
  {
    ArrayList<Player> players = getPlayers();

    for (Player roomPlayer : players)
    {
      if (roomPlayer.equals(player))
      {
        return roomPlayer;
      }
    }

    return null;
  }

  public ArrayList<Player> getPlayers()
  {
    return players.toArrayList();
  }

  public boolean isFull()
  {
    return players.size() == CAPACITY;
  }

  public void addPlayer(Player player)
  {
    player.setCard(game.getCard()); // TODO: think of a better place for this
    players.enqueue(player);
    game.addPlayer(player);
  }

  public String getRules()
  {
    return game.getRules();
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
