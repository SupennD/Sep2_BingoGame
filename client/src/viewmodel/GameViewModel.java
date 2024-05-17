package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Card;
import model.Model;
import model.Player;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

import java.util.ArrayList;

/**
 * A view model class responsible for managing game
 *
 * @author Supendra Bogati
 * @author Lucia Andronic
 * @version 1.1.0 - May 2024
 */
public class GameViewModel extends ViewModel implements LocalListener<Object, Object>
{
  private final ObservableList<Player> playersProperty;
  private final ObjectProperty<Player> currentPlayerProperty;
  private final StringProperty timerProperty;
  private final ObjectProperty<Card> cardProperty;
  private final ObservableList<Integer> calledNumbersProperty;
  private final StringProperty errorProperty;

  public GameViewModel(Model model, ViewModelState viewModelState)
  {
    super(model, viewModelState);

    this.model.addListener(this, "game:tick", "game:turn", "game:call");
    this.playersProperty = FXCollections.observableArrayList();
    this.currentPlayerProperty = new SimpleObjectProperty<>();
    this.timerProperty = new SimpleStringProperty();
    this.cardProperty = new SimpleObjectProperty<>();
    this.calledNumbersProperty = FXCollections.observableArrayList();
    this.errorProperty = new SimpleStringProperty();
  }

  @Override public void reset()
  {
    // TODO: look into how to safely cast this
    ArrayList<Player> players = (ArrayList<Player>) viewModelState.get("players");
    Player player = (Player) viewModelState.get("player");
    playersProperty.clear();
    playersProperty.addAll(players);
    currentPlayerProperty.set(null);
    timerProperty.set("00:00");
    cardProperty.set(player.getCard());
    calledNumbersProperty.clear();
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

  public StringProperty timerProperty()
  {
    return timerProperty;
  }

  public ObjectProperty<Card> cardProperty()
  {
    return cardProperty;
  }

  public ObservableList<Integer> calledNumbersProperty()
  {
    return calledNumbersProperty;
  }

  public StringProperty errorProperty()
  {
    return errorProperty;
  }

  public boolean makeMove(int number)
  {
    errorProperty.set(null);

    try
    {
      int roomId = (Integer) viewModelState.get("roomId");
      Player player = (Player) viewModelState.get("player");
      model.makeMove(roomId, player, number);

      return true;
    }
    catch (IllegalStateException e)
    {
      errorProperty.set(e.getMessage());
    }

    return false;
  }

  private void nextPlayer(ObserverEvent<Object, Object> observerEvent)
  {
    int roomId = (Integer) observerEvent.getValue1();
    int currentRoomId = (Integer) viewModelState.get("roomId");

    if (currentRoomId == roomId)
    {
      Player player = (Player) observerEvent.getValue2();
      currentPlayerProperty.set(player);
    }
  }

  private void calledNumber(ObserverEvent<Object, Object> observerEvent)
  {
    int roomId = (Integer) observerEvent.getValue1();
    int currentRoomId = (Integer) viewModelState.get("roomId");

    if (roomId == currentRoomId)
    {
      int calledNumber = (Integer) observerEvent.getValue2();
      calledNumbersProperty.add(calledNumber);
    }
  }

  @Override public void propertyChange(ObserverEvent<Object, Object> observerEvent)
  {
    // Run it on the JavaFX thread
    Platform.runLater(() -> {
      switch (observerEvent.getPropertyName())
      {
        case "game:tick" -> timerProperty.set(observerEvent.getValue2().toString());
        case "game:turn" -> nextPlayer(observerEvent);
        case "game:call" -> calledNumber(observerEvent);
      }
    });
  }
}
