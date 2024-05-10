package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Model;
import model.Player;
import model.Room;

/**
 * A view model class responsible for managing game
 *
 * @author Supendra Bogati
 * @version 1.0.1 - April 2024
 */
public class GameViewModel extends ViewModel
{

  private final ObservableList<Player> playersProperty;
  private final StringProperty errorProperty;

  public GameViewModel(Model model, ViewModelState viewModelState)
  {
    super(model, viewModelState);

    this.playersProperty = FXCollections.observableArrayList();
    this.errorProperty = new SimpleStringProperty();
  }

  @Override public void reset()
  {
    //loadOtherPlayers
    loadPlayers();
    errorProperty.set(null);
  }

  public ObservableList<Player> playersProperty()
  {
    return playersProperty;
  }

  public StringProperty errorProperty()
  {
    return errorProperty;
  }

  private void loadPlayers()
  {
    playersProperty.clear();

    Room room = (Room) viewModelState.get("room");
    if (room != null && room.getPlayers() != null)
    {
      playersProperty.addAll(room.getPlayers());
    }
  }
}
