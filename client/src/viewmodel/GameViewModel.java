package viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Card;
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
  private final ObjectProperty<Card> cardProperty;

  public GameViewModel(Model model, ViewModelState viewModelState)
  {
    super(model, viewModelState);

    this.playersProperty = FXCollections.observableArrayList();
    this.errorProperty = new SimpleStringProperty();
    this.cardProperty = new SimpleObjectProperty<>();
  }

  @Override public void reset()
  {
    //loadOtherPlayers
    loadPlayers();
    errorProperty.set(null);
    Room room = (Room) viewModelState.get("room");
    Player player = room.getPlayer((Player) viewModelState.get("player"));
    cardProperty.set(player.getCard());
  }

  public ObservableList<Player> playersProperty()
  {
    return playersProperty;
  }

  public StringProperty errorProperty()
  {
    return errorProperty;
  }

  public ObjectProperty<Card> cardProperty()
  {
    return cardProperty;
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
