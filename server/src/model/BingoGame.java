package model;

/**
 * A class that is responsible for handling all the actions in the game It checks for many state and do the things that
 * can be supposedly done in each state
 *
 * @author Supendra Bogati
 * @version 1.0.0 - May 2024
 */

public class BingoGame implements Game
{
  private final RoomList roomList;

  public BingoGame()
  {
    roomList = new RoomList();
  }

  @Override public Room joinRoom(Player player)
  {
    player.setCard(new LasoCard());
    return roomList.joinRoom(player);
  }
}