package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Model;
import model.Player;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

import java.util.ArrayList;

public class RoomViewModel extends ViewModel implements LocalListener<Object, Object>
{
  private final ObservableList<Player> playersProperty;
  private final BooleanProperty isFullProperty;
  private final StringProperty errorProperty;
  private final StringProperty messageProperty;

  public RoomViewModel(Model model, ViewModelState viewModelState)
  {
    super(model, viewModelState);

    this.model.addListener(this, "room:join", "room:full");
    this.playersProperty = FXCollections.observableArrayList();
    this.isFullProperty = new SimpleBooleanProperty();
    this.errorProperty = new SimpleStringProperty();
    this.messageProperty = new SimpleStringProperty();

    // Update the message automatically when the room is full
    this.isFullProperty.addListener((o, ov, isFull) -> {
      if (isFull)
      {
        this.messageProperty.set("The game will now start");
      }
    });
  }

  @Override public void reset()
  {
    joinRoom();
    isFullProperty.set(false);
    errorProperty.set(null);
    messageProperty.set("Waiting for enough players to join...");
  }

  public ObservableList<Player> playersProperty()
  {
    return playersProperty;
  }

  public BooleanProperty isFullProperty()
  {
    return isFullProperty;
  }

  public StringProperty errorProperty()
  {
    return errorProperty;
  }

  public StringProperty messageProperty()
  {
    return messageProperty;
  }

  public boolean startGame()
  {
    errorProperty.set(null);

    try
    {
      int roomId = (Integer) viewModelState.get("roomId");
      model.startGame(roomId);

      return true;
    }
    catch (IllegalStateException e)
    {
      errorProperty.set(e.getMessage());
    }

    return false;
  }

  private void joinRoom()
  {
    playersProperty.clear();
    errorProperty.set(null);

    try
    {
      Player player = (Player) viewModelState.get("player");
      int roomId = model.joinRoom(player);
      viewModelState.put("roomId", roomId);
    }
    catch (IllegalStateException e)
    {
      errorProperty.set(e.getMessage());
    }
  }

  private void roomJoined(ObserverEvent<Object, Object> observerEvent)
  {
    // TODO: look into how to safely cast this
    ArrayList<Player> players = (ArrayList<Player>) observerEvent.getValue1();
    int roomId = (Integer) observerEvent.getValue2();
    int currentRoomId = (Integer) viewModelState.get("roomId");

    if (currentRoomId == roomId)
    {
      playersProperty.clear();
      playersProperty.addAll(players);
      viewModelState.put("players", players);

      // Update current player stored in state
      Player player = (Player) viewModelState.get("player");
      int currentPlayerIndex = players.indexOf(player);
      viewModelState.put("player", players.get(currentPlayerIndex));
    }
  }

  private void roomFilled(ObserverEvent<Object, Object> observerEvent)
  {
    int roomId = (Integer) observerEvent.getValue2();
    int currentRoomId = (Integer) viewModelState.get("roomId");

    if (currentRoomId == roomId)
    {
      isFullProperty.set(true);
    }
  }

  @Override public void propertyChange(ObserverEvent<Object, Object> observerEvent)
  {
    // Run it on the JavaFX thread
    Platform.runLater(() -> {
      switch (observerEvent.getPropertyName())
      {
        case "room:join" -> roomJoined(observerEvent);
        case "room:full" -> roomFilled(observerEvent);
      }
    });
  }
}
