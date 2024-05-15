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

  @Override public String getRules()
  {
    return """
         When you start a game, you will be assigned to a public room with available spaces where
         you will wait there for the other three players to join as the game only starts when there
        are 4 players. But you can always go back to the main screen while waiting for other players.
                
         Once the game starts the following rules apply:
                
         1. Each player will have a 5 by 5 bingo card with random numbers between 1 and 25.
                
         2. The players take turns to call a number from their bingo card.
                
         3. The called number can be marked by all the players.
                
         4. The steps 2 and 3 continue until a player hits “BINGO”
                
         5. When 5 numbers are marked, either horizontally, vertically or diagonally, the line will be
         highlighted.
                
         6. When a line is highlighted, a letter in “BINGO” also gets highlighted.
                
         7. When all the letters in “BINGO” are highlighted the player can hit “BINGO” and win the
         game.
                
         8. Whoever clicks the “BINGO” first will be the winner.
        """;
  }
}