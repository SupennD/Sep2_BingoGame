package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
  private final ObservableList<Player> playersProperty;
  private final ObjectProperty<Player> currentPlayerProperty;
  private final ObjectProperty<Player> winnerPlayerProperty;
  private final ObjectProperty<Card> cardProperty;
  private final ObservableList<Cell> calledCellsProperty;
  private final StringProperty errorProperty;

  public GameViewModel(Model model, ViewModelState viewModelState)
  {
    super(model, viewModelState);

    this.model.addListener(this, "game:turn", "game:call", "game:win");
    this.playersProperty = FXCollections.observableArrayList();
    this.currentPlayerProperty = new SimpleObjectProperty<>();
    this.winnerPlayerProperty = new SimpleObjectProperty<>();
    this.cardProperty = new SimpleObjectProperty<>();
    this.calledCellsProperty = FXCollections.observableArrayList();
    this.errorProperty = new SimpleStringProperty();
  }

  @Override public void reset()
  {
    startGame(); // Start the actual game
    // TODO: look into how to safely cast this
    ArrayList<Player> players = (ArrayList<Player>) viewModelState.get("players");
    Player currentPlayer = (Player) viewModelState.get("currentPlayer");
    playersProperty.clear();
    playersProperty.addAll(players);
    currentPlayerProperty.set(null);
    winnerPlayerProperty.set(null);
    cardProperty.set(currentPlayer.getCard());
    calledCellsProperty.clear();
    errorProperty.set(null);
  }

  public ObservableList<Player> playersProperty()
  {
    return playersProperty;
  }

  public ObjectProperty<Player> currentPlayerProperty()
  {
    return currentPlayerProperty;
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

  public boolean makeMove(Cell cell)
  {
    errorProperty.set(null);

    try
    {
      int roomId = (Integer) viewModelState.get("roomId");
      Player currentPlayer = (Player) viewModelState.get("currentPlayer");
      model.makeMove(roomId, currentPlayer, cell);

      return true;
    }
    catch (IllegalStateException e)
    {
      errorProperty.set(e.getMessage());
    }

    return false;
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

  private void gameTurn(ObserverEvent<Object, Object> observerEvent)
  {
    int roomId = (Integer) observerEvent.getValue1();
    int currentRoomId = (Integer) viewModelState.get("roomId");

    if (currentRoomId == roomId)
    {
      Player player = (Player) observerEvent.getValue2();
      currentPlayerProperty.set(player);
    }
  }

  private void gameCall(ObserverEvent<Object, Object> observerEvent)
  {
    int roomId = (Integer) observerEvent.getValue1();
    int currentRoomId = (Integer) viewModelState.get("roomId");

    if (roomId == currentRoomId)
    {
      Cell calledCell = (Cell) observerEvent.getValue2();
      calledCellsProperty.add(calledCell);
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