package viewmodel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import model.Model;
import model.Player;
import model.card.Card;
import model.card.Cell;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

import java.util.ArrayList;

/**
 * A view model class responsible for managing game
 *
 * @author Supendra Bogati
 * @author Lucia Andronic
 * @author Alexandru Tofan
 * @version 1.3.0 - May 2024
 */
public class GameViewModel extends ViewModel implements LocalListener<Object, Object>
{
  public static final int SECONDS_PER_ROUND = 15; // TODO: Should get this from server
  private final ObservableList<Player> playersProperty;
  private final IntegerProperty currentPlayerIndexProperty;
  private final ObjectProperty<Player> winnerPlayerProperty;
  private final ObjectProperty<Card> cardProperty;
  private final ObservableList<Cell> calledCellsProperty;
  private final StringProperty errorProperty;
  private final IntegerProperty remainingSecondsProperty;
  private final StringProperty timerProperty;
  private final Timeline timerTimeline;

  public GameViewModel(Model model, ViewModelState viewModelState)
  {
    super(model, viewModelState);

    this.model.addListener(this, "game:turn", "game:call", "game:win");
    this.playersProperty = FXCollections.observableArrayList();
    this.currentPlayerIndexProperty = new SimpleIntegerProperty();
    this.winnerPlayerProperty = new SimpleObjectProperty<>();
    this.cardProperty = new SimpleObjectProperty<>();
    this.calledCellsProperty = FXCollections.observableArrayList();
    this.errorProperty = new SimpleStringProperty();
    this.remainingSecondsProperty = new SimpleIntegerProperty();
    this.timerProperty = new SimpleStringProperty();
    // Update the timer text based on the remainingSeconds property
    this.remainingSecondsProperty.addListener((o, ov, remainingSeconds) -> {
      timerProperty.set(formatSeconds(remainingSeconds.intValue()));
    });
    // Create a timeline that subtracts 1 from remainingSeconds every second until it gets to 0
    this.timerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
      remainingSecondsProperty.set(remainingSecondsProperty.get() - 1);
    }));
    this.timerTimeline.setCycleCount(SECONDS_PER_ROUND);
  }

  @Override public void reset()
  {
    startGame(); // Start the actual game
    // TODO: look into how to safely cast this
    ArrayList<Player> players = (ArrayList<Player>) viewModelState.get("players");
    Player currentPlayer = (Player) viewModelState.get("currentPlayer");
    playersProperty.clear();
    playersProperty.addAll(players);
    currentPlayerIndexProperty.set(-1);
    winnerPlayerProperty.set(null);
    cardProperty.set(currentPlayer.getCard());
    calledCellsProperty.clear();
    errorProperty.set(null);
    remainingSecondsProperty.set(SECONDS_PER_ROUND);
    timerProperty.set(formatSeconds(remainingSecondsProperty.get()));
  }

  public ObservableList<Player> playersProperty()
  {
    return playersProperty;
  }

  public IntegerProperty currentPlayerIndexProperty()
  {
    return currentPlayerIndexProperty;
  }

  public ObjectProperty<Player> winnerPlayerProperty()
  {
    return winnerPlayerProperty;
  }

  public ObjectProperty<Card> cardProperty()
  {
    return cardProperty;
  }

  public ObservableList<Cell> calledCellsProperty()
  {
    return calledCellsProperty;
  }

  public StringProperty errorProperty()
  {
    return errorProperty;
  }

  public StringProperty timerProperty()
  {
    return timerProperty;
  }

  public void makeMove(Cell cell)
  {
    errorProperty.set(null);

    try
    {
      int roomId = (Integer) viewModelState.get("roomId");
      Player currentPlayer = (Player) viewModelState.get("currentPlayer");
      Card card = model.makeMove(roomId, currentPlayer, cell);
      cardProperty.set(card);

    }
    catch (IllegalStateException e)
    {
      errorProperty.set(e.getMessage());
    }
  }

  public void callBingo()
  {
    errorProperty.set(null);

    try
    {
      int roomId = (Integer) viewModelState.get("roomId");
      Player currentPlayer = (Player) viewModelState.get("currentPlayer");
      model.callBingo(roomId, currentPlayer);
    }
    catch (IllegalStateException e)
    {
      errorProperty.set(e.getMessage());
    }
  }

  private void startGame()
  {
    errorProperty.set(null);

    try
    {
      int roomId = (Integer) viewModelState.get("roomId");
      model.startGame(roomId);
    }
    catch (IllegalStateException e)
    {
      errorProperty.set(e.getMessage());
    }
  }

  private String formatSeconds(int timeSeconds)
  {
    int minutes = timeSeconds / 60;
    int seconds = timeSeconds % 60;
    return String.format("%02d:%02d", minutes, seconds);
  }

  private void gameTurn(ObserverEvent<Object, Object> observerEvent)
  {
    int roomId = (Integer) observerEvent.getValue1();
    int currentRoomId = (Integer) viewModelState.get("roomId");

    if (currentRoomId == roomId)
    {
      Player player = (Player) observerEvent.getValue2();
      currentPlayerIndexProperty.set(playersProperty.indexOf(player));
      remainingSecondsProperty.set(SECONDS_PER_ROUND); // Reset round timer
      timerTimeline.playFromStart(); // Start round timer
    }
  }

  private void gameCall(ObserverEvent<Object, Object> observerEvent)
  {
    int roomId = (Integer) observerEvent.getValue1();
    int currentRoomId = (Integer) viewModelState.get("roomId");

    if (roomId == currentRoomId)
    {
      Cell calledCell = (Cell) observerEvent.getValue2();
      calledCellsProperty.add(0, calledCell);
    }
  }

  private void gameWin(ObserverEvent<Object, Object> observerEvent)
  {
    int roomId = (Integer) observerEvent.getValue1();
    int currentRoomId = (Integer) viewModelState.get("roomId");

    if (roomId == currentRoomId)
    {
      Player winnerPlayer = (Player) observerEvent.getValue2();
      viewModelState.put("winnerPlayer", winnerPlayer);
      winnerPlayerProperty.set(winnerPlayer);
    }
  }

  @Override public void propertyChange(ObserverEvent<Object, Object> observerEvent)
  {
    // Run it on the JavaFX thread
    Platform.runLater(() -> {
      switch (observerEvent.getPropertyName())
      {
        case "game:turn" -> gameTurn(observerEvent);
        case "game:call" -> gameCall(observerEvent);
        case "game:win" -> gameWin(observerEvent);
      }
    });
  }
}