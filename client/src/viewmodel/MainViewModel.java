package viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Model;
import model.Player;

public class MainViewModel extends ViewModel
{
  private final ObjectProperty<Player> playerProperty;

  public MainViewModel(Model model, ViewModelState viewModelState)
  {
    super(model, viewModelState);

    this.playerProperty = new SimpleObjectProperty<>();
  }

  @Override public void reset()
  {
    Player currentPlayer = (Player) viewModelState.get("currentPlayer");
    playerProperty.set(currentPlayer);
  }

  public ObjectProperty<Player> playerProperty()
  {
    return playerProperty;
  }
}