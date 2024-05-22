package model.uPickBingo;

import mediator.GameEvent;
import model.Game;
import model.Player;
import model.card.Cell;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;
import utils.Timer;
import utils.log.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class that is responsible for handling all the actions in the game It checks for many state and do the things that
 * can be supposedly done in each state
 *
 * @author Supendra Bogati
 * @author Alexandru Tofan
 * @version 1.3.0 - May 2024
 */
public class UPickBingoGame implements Game, LocalListener<Integer, String>
{
  private static final int MAX_PLAYERS = 4;
  private static final int SECONDS_PER_TURN = 15;
  private final ArrayList<Player> players;
  private final ArrayList<Cell> calledCells;
  private final Timer timer;
  private final Log log = Log.getInstance();
  private final GameEvent gameEvent = GameEvent.getInstance();
  private boolean isStarted;
  private int currentPlayer;
  private int roomId;

  public UPickBingoGame()
  {
    this.timer = new Timer(SECONDS_PER_TURN, true);
    timer.addListener(this, "timer:done");
    this.players = new ArrayList<>();
    this.currentPlayer = 0;
    this.calledCells = new ArrayList<>();
    this.isStarted = false;
  }

  private synchronized boolean isCurrentPlayer(Player player)
  {
    return players.get(currentPlayer).equals(player);
  }

  private synchronized void nextPlayer()
  {
    timer.reset();
    currentPlayer = (currentPlayer + 1) % players.size(); // TODO: check this in relation to the data-structure
    Player nextPlayer = players.get(currentPlayer);
    gameEvent.fireEvent("game:turn", roomId, nextPlayer);
    log.info("Next player " + nextPlayer);
  }

  private synchronized boolean wasCellCalled(Cell cell)
  {
    for (Cell calledCell : calledCells)
    {
      if (calledCell.equals(cell))
      {
        return true;
      }
    }

    return false;
  }

  private synchronized void callCell(Cell cell)
  {
    if (!wasCellCalled(cell))
    {
      calledCells.add(cell);
      gameEvent.fireEvent("game:call", roomId, cell);
      log.info("Called cell " + cell);
      nextPlayer();
    }
  }

  private synchronized void markCell(Cell cell)
  {
    if (wasCellCalled(cell))
    {
      log.info("Marked cell " + cell);
    }
    else
    {
      throw new IllegalStateException("Cannot mark cell " + cell + " as it was not called.");
    }
  }

  @Override public void propertyChange(ObserverEvent<Integer, String> observerEvent)
  {
    nextPlayer();
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
        5. When 5 numbers are marked, either horizontally, vertically or diagonally, the line will be highlighted.
        6. When a line is highlighted, a letter in “BINGO” also gets highlighted.
        7. When all the letters in “BINGO” are highlighted the player can hit “BINGO” and win the game.
        8. Whoever clicks the “BINGO” first will be the winner.
        """;
  }

  @Override public synchronized void addPlayer(Player player)
  {
    player.setCard(new UPickBingoCard());
    players.add(player);
    log.info("Added player " + player);
  }

  @Override public synchronized void start(int roomId)
  {
    if (isStarted) // If it's already started do nothing
    {
      log.info("Game already started");
      return;
    }

    if (!isFull()) // This variation of BINGO works with 4 players only
    {
      throw new IllegalStateException("Not enough players to start a game.");
    }

    // Set isStarted to true so that future calls can be ignored
    isStarted = true;
    // Set the roomId to the id of the room where it's played
    this.roomId = roomId;
    // Start a timer that will be used to manage turns
    Thread timerThread = new Thread(timer);
    timerThread.setDaemon(true);
    timerThread.start();
    // Start the first turn
    Player nextPlayer = players.get(currentPlayer);
    gameEvent.fireEvent("game:turn", roomId, nextPlayer);

    log.info("U Pick BINGO game started in room " + roomId);
  }

  @Override public synchronized void makeMove(Player player, Cell cell)
  {
    log.info("Make move, player: " + player + ", cell: " + cell + ", currentPlayer: " + isCurrentPlayer(player));

    if (isCurrentPlayer(player))
    {
      callCell(cell);
    }

    markCell(cell);
  }

  @Override public synchronized boolean isFull()
  {
    return players.size() == MAX_PLAYERS;
  }

  @Override public synchronized void callBingo(int roomId, Player player)
  {
    // TODO: check win once the card is updated to use objects for numbers
    boolean won = new Random().nextBoolean();

    if (won)
    {
      gameEvent.fireEvent("game:win", roomId, player);
      log.info("Player " + player + " won in room " + roomId);
    }
    else
    {
      throw new IllegalStateException("Oops! I think you're trying to cheat");
    }
  }
}