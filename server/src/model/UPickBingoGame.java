package model;

import mediator.GameEvents;
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
 * @version 1.1.0 - May 2024
 */
public class UPickBingoGame implements Game, LocalListener<Integer, String>
{
  private static final int MAX_PLAYERS = 4;
  private static final int SECONDS_PER_TURN = 15;
  private final ArrayList<Player> players;
  private final ArrayList<Integer> calledNumbers;
  private final Timer timer;
  private final Log log = Log.getInstance();
  private final GameEvents gameEvents = GameEvents.getInstance();
  private boolean isStarted;
  private int currentPlayer;
  private int roomId;

  public UPickBingoGame()
  {
    this.timer = new Timer(SECONDS_PER_TURN, true);
    timer.addListener(this, "timer:tick", "timer:done");
    this.players = new ArrayList<>();
    this.currentPlayer = 0;
    this.calledNumbers = new ArrayList<>();
    this.isStarted = false;
  }

  private synchronized boolean isCurrentPlayer(Player player)
  {
    return players.get(currentPlayer).equals(player);
  }

  private synchronized void nextPlayer()
  {
    timer.reset();
    currentPlayer = (currentPlayer + 1) % players.size();
    Player nextPlayer = players.get(currentPlayer);
    gameEvents.fireEvent("game:turn", roomId, nextPlayer);
    log.info("Next player " + nextPlayer);
  }

  private synchronized boolean wasNumberCalled(int number)
  {
    for (Integer calledNumber : calledNumbers)
    {
      if (calledNumber == number)
      {
        return true;
      }
    }

    return false;
  }

  private synchronized void callNumber(int number)
  {
    if (!wasNumberCalled(number))
    {
      calledNumbers.add(number);
      gameEvents.fireEvent("game:call", roomId, number);
      log.info("Called number " + number);
      nextPlayer();
    }
  }

  private synchronized void markNumber(int number)
  {
    if (wasNumberCalled(number))
    {
      log.info("Marked number " + number);
    }
    else
    {
      throw new IllegalStateException("Cannot mark number " + number + " as it was not called.");
    }
  }

  @Override public void propertyChange(ObserverEvent<Integer, String> observerEvent)
  {
    switch (observerEvent.getPropertyName())
    {
      case "timer:tick" -> gameEvents.fireEvent("game:tick", null, observerEvent.getValue2());
      case "timer:done" -> nextPlayer();
    }
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

    // Set the roomId to the id of the room where it's played
    this.roomId = roomId;
    // Start a timer that will be used to manage turns
    Thread timerThread = new Thread(timer);
    timerThread.setDaemon(true);
    timerThread.start();
    isStarted = true;
    log.info("Game started");
  }

  @Override public synchronized void makeMove(Player player, int number)
  {
    log.info("Make move, player: " + player + ", number: " + number + ", currentPlayer: " + isCurrentPlayer(player));

    if (isCurrentPlayer(player))
    {
      callNumber(number);
    }

    markNumber(number);
  }

  @Override public synchronized boolean isFull()
  {
    return players.size() == MAX_PLAYERS;
  }
}