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
  private final StringProperty rulesProperty;

  public RoomViewModel(Model model, ViewModelState viewModelState)
  {
    super(model, viewModelState);

    this.model.addListener(this, "room:join", "room:full");
    this.playersProperty = FXCollections.observableArrayList();
    this.isFullProperty = new SimpleBooleanProperty();
    this.errorProperty = new SimpleStringProperty();
    this.messageProperty = new SimpleStringProperty();
    this.rulesProperty = new SimpleStringProperty();
  }

  @Override public void reset()
  {
    // Initialize 4 null elements to show placeholders in the UI
    playersProperty.setAll(null, null, null, null);
    joinRoom(); // Send a join request for the current player
    isFullProperty.set(false);
    errorProperty.set(null);
    messageProperty.set("Waiting for all players to join...");
    getRules(); // Get room rules
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

  public StringProperty rulesProperty()
  {
    return rulesProperty;
  }

  private void joinRoom()
  {
    errorProperty.set(null);

    try
    {
      Player currentPlayer = (Player) viewModelState.get("currentPlayer");
      int roomId = model.joinRoom(currentPlayer);
      viewModelState.put("roomId", roomId);
    }
    catch (IllegalStateException e)
    {
      errorProperty.set(e.getMessage());
    }
  }

  private void getRules()
  {
    errorProperty.set(null);

    try
    {
      int roomId = (int) viewModelState.get("roomId");
      String rules = model.getRules(roomId);
      rulesProperty.set(rules);
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
      // By setting elements one by one we are replacing the placeholders in the UI with the actual players
      for (int i = 0; i < players.size(); i++)
      {
        playersProperty.set(i, players.get(i));
      }

      viewModelState.put("players", players);

      // Update current player stored in state
      Player currentPlayer = (Player) viewModelState.get("currentPlayer");
      int currentPlayerIndex = players.indexOf(currentPlayer);
      viewModelState.put("currentPlayer", players.get(currentPlayerIndex));
    }
  }

  private void roomFilled(ObserverEvent<Object, Object> observerEvent)
  {
    int roomId = (Integer) observerEvent.getValue2();
    int currentRoomId = (Integer) viewModelState.get("roomId");

    if (currentRoomId == roomId)
    {
      messageProperty.set("All players joined, game will start");
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