package model.uPickBingo;

import mediator.GameEvent;
import model.Game;
import model.Player;
import model.Score;
import model.card.Card;
import model.card.Cell;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;
import utils.Timer;
import utils.log.Log;

import java.util.ArrayList;

/**
 * A class that is responsible for handling all the actions in the game It checks for many state and do the things that
 * can be supposedly done in each state
 *
 * @author Supendra Bogati
 * @author Alexandru Tofan
 * @author Lucia Andronic
 * @version 1.3.1 - May 2024
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
  private int currentPlayerIndex;
  private int roomId;

  public UPickBingoGame()
  {
    this.timer = new Timer(SECONDS_PER_TURN, true);
    timer.addListener(this, "timer:done");
    this.players = new ArrayList<>();
    this.currentPlayerIndex = -1; // If the game didn't start yet, nobody's current player
    this.calledCells = new ArrayList<>();
    this.isStarted = false;
  }

  private synchronized void nextPlayer()
  {
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

    Player currentPlayer = players.get(currentPlayerIndex);
    log.info("Current player " + currentPlayer);
    gameEvent.fireEvent("game:turn", roomId, currentPlayer);
    timer.reset();
  }

  private synchronized boolean isCurrentPlayer(Player player)
  {
    return players.get(currentPlayerIndex).equals(player);
  }

  private synchronized boolean isCalled(Cell cell)
  {
    return calledCells.contains(cell);
  }

  private synchronized boolean callCell(Cell cell)
  {
    if (isCalled(cell))
    {
      log.info("Number " + cell + " was already called");
      return false;
    }

    calledCells.add(cell);
    gameEvent.fireEvent("game:call", roomId, cell);
    log.info("Called number " + cell);
    return true;
  }

  private synchronized Card markCell(int playerIndex, Cell cell)
  {
    if (!isCalled(cell))
    {
      throw new IllegalStateException("Number " + cell + " was not called");
    }

    Player player = players.get(playerIndex);
    Card card = player.getCard();
    card.markCell(cell);

    log.info("Player " + player + " marked number " + cell);

    return card;
  }

  @Override public synchronized void addPlayer(Player player)
  {
    player.setCard(new UPickBingoCard());
    players.add(player);
    log.info("Added player " + player);
  }

  @Override public synchronized void removePlayer(Player player)
  {
    if (isCurrentPlayer(player))
    {
      nextPlayer(); // if the current player leaves, move to the next one
    }

    players.remove(player);
    log.info("Removed player " + player);
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
    timer.start();
    // Start the first turn
    nextPlayer();

    log.info("U Pick BINGO game started in room " + roomId);
  }

  @Override public synchronized void stop(int roomId)
  {
    timer.stop();
    log.info("The game ended in room " + roomId);
  }

  @Override public synchronized Card makeMove(Player player, Cell cell)
  {
    log.info("Make move, player: " + player + ", number: " + cell + ", currentPlayer: " + isCurrentPlayer(player));

    int playerIndex = players.indexOf(player);

    if (isCurrentPlayer(player))
    {
      boolean success = callCell(cell);
      Card card = markCell(playerIndex, cell);

      if (success)
      {
        nextPlayer();
      }

      return card;
    }

    return markCell(playerIndex, cell);
  }

  @Override public synchronized Score callBingo(int roomId, Player player)
  {
    Player caller = players.get(players.indexOf(player));
    Card card = caller.getCard();

    if (card.hasWinCombination())
    {
      // TODO: get the details from the db
      Score score = new Score(1L, "todo", 100L);
      caller.addScore(score); // Add the score to the player who won
      gameEvent.fireEvent("game:win", roomId, caller);
      log.info("Player " + caller + " won in room " + roomId);
      stop(roomId);

      return score;
    }
    else
    {
      throw new IllegalStateException("Woah! I think you're trying to cheat");
    }
  }

  @Override public synchronized boolean isFull()
  {
    return players.size() == MAX_PLAYERS;
  }

  @Override public String getRules()
  {
    return """
        UPick Bingo, is a BINGO variation that has turns and players act as callers in their turn.
        When your turn comes just call a number! In the meantime you can mark already called numbers on your card and try to form 5 lines.
        When you have 5 lines highlighted just hit "BINGO" to win the game.

        Once the game starts the following rules apply:
        1. Each player will have a 5 by 5 bingo card with random numbers between 1 and 25.
        2. The players take turns to call a number from their bingo card.
        3. The called number can be marked by all the players, and you can mark called numbers even if it's not your turn.
        5. When 5 numbers are marked, either horizontally, vertically or diagonally, the line will be highlighted.
        6. When 5 lines are highlighted you can hit "BINGO" to win the game.
        7. The game continues until one of the players hits "BINGO".
        """;
  }

  @Override public synchronized void propertyChange(ObserverEvent<Integer, String> observerEvent)
  {
    nextPlayer();
  }
}