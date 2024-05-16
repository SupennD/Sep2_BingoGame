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

import java.util.ArrayList;

/**
 * A view model class responsible for managing game
 *
 * @author Supendra Bogati
 * @author Lucia Andronic
 * @version 1.1.0 - May 2024
 */
public class GameViewModel extends ViewModel
{

  private final ObservableList<Player> playersProperty;
  private final ObjectProperty<Card> cardProperty;
  private final StringProperty errorProperty;

  public GameViewModel(Model model, ViewModelState viewModelState)
  {
    super(model, viewModelState);

    this.playersProperty = FXCollections.observableArrayList();
    this.cardProperty = new SimpleObjectProperty<>();
    this.errorProperty = new SimpleStringProperty();
  }

  @Override public void reset()
  {
    // TODO: look into how to safely cast this
    ArrayList<Player> players = (ArrayList<Player>) viewModelState.get("players");
    Player player = (Player) viewModelState.get("player");
    playersProperty.clear();
    playersProperty.addAll(players);
    cardProperty.set(player.getCard());
    errorProperty.set(null);
  }

  public ObservableList<Player> playersProperty()
  {
    return playersProperty;
  }

  public ObjectProperty<Card> cardProperty()
  {
    return cardProperty;
  }

  public StringProperty errorProperty()
  {
    return errorProperty;
  }
}
