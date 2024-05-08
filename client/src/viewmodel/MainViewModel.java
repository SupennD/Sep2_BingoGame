package viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Model;
import model.Player;
import model.Room;

public class MainViewModel extends ViewModel
{
  private final ObjectProperty<Player> playerProperty;
  private final StringProperty errorProperty;

  public MainViewModel(Model model, ViewModelState viewModelState)
  {
    super(model, viewModelState);

    this.playerProperty = new SimpleObjectProperty<>();
    this.errorProperty = new SimpleStringProperty();
  }

  @Override public void reset()
  {
    Player player = (Player) viewModelState.get("player");
    playerProperty.set(player);
    errorProperty.set(null);
  }

  public ObjectProperty<Player> playerProperty()
  {
    return playerProperty;
  }

  public StringProperty errorProperty()
  {
    return errorProperty;
  }

  public boolean start()
  {
    errorProperty.set(null);

    try
    {
      Room room = model.joinRoom(playerProperty.get());
      viewModelState.put("room", room);

      return true;
    }
    catch (IllegalStateException e)
    {
      errorProperty.set(e.getMessage());
    }

    return false;
  }
}
