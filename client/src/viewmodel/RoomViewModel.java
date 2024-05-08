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
import model.Room;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

public class RoomViewModel extends ViewModel implements LocalListener<Object, Object>
{
  private final ObservableList<Player> playersProperty;
  private final BooleanProperty isFullProperty;
  private final StringProperty errorProperty;
  private final StringProperty messageProperty;

  public RoomViewModel(Model model, ViewModelState viewModelState)
  {
    super(model, viewModelState);

    this.model.addListener(this, "joinRoom", "fullRoom");
    this.playersProperty = FXCollections.observableArrayList();
    this.isFullProperty = new SimpleBooleanProperty(false);
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
    Room room = (Room) viewModelState.get("room");
    playersProperty.clear();
    playersProperty.addAll(room.getPlayers());
    isFullProperty.set(room.isFull());
    errorProperty.set(null);
    messageProperty.set(room.isFull() ? "The game will now start" : "Waiting for enough players to join");
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

  private void joinRoom(ObserverEvent<Object, Object> observerEvent)
  {
    Room room = (Room) observerEvent.getValue2();
    Room currentRoom = (Room) viewModelState.get("room");

    if (room.equals(currentRoom))
    {
      playersProperty.clear();
      playersProperty.addAll(room.getPlayers());
      isFullProperty.set(room.isFull());
    }
  }

  @Override public void propertyChange(ObserverEvent<Object, Object> observerEvent)
  {
    // Run it on the JavaFX thread
    Platform.runLater(() -> {
      switch (observerEvent.getPropertyName())
      {
        case "joinRoom" -> joinRoom(observerEvent);
      }
    });
  }
}
