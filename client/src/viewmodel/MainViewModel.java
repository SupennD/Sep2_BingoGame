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
    Player player = (Player) viewModelState.get("player");
    playerProperty.set(player);
  }

  public ObjectProperty<Player> playerProperty()
  {
    return playerProperty;
  }
}
