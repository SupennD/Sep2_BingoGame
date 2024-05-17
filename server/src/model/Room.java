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
  private static int nextId = 1;
  private final int id;
  private final BlockingDeque<Player> players;
  private final Game game;

  public Room(Game game)
  {
    this.id = nextId++;
    this.players = new BlockingArrayDeque<>(true);
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
    return game.isFull();
  }

  public void addPlayer(Player player)
  {
    if (isFull())
    {
      throw new IllegalStateException("The room is already full");
    }

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

  public void startGame()
  {
    game.start(getId());
  }

  public void makeMove(Player player, int number)
  {
    game.makeMove(player, number);
  }
}
