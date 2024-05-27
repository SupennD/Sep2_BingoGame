package viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Model;
import model.Player;

public class MainViewModel extends ViewModel
{
  private final ObjectProperty<Player> playerProperty;
  private final ObservableList<Player> topPlayersProperty;
  private final StringProperty errorProperty;

  public MainViewModel(Model model, ViewModelState viewModelState)
  {
    super(model, viewModelState);

    this.playerProperty = new SimpleObjectProperty<>();
    this.topPlayersProperty = FXCollections.observableArrayList();
    this.errorProperty = new SimpleStringProperty();
  }

  @Override public void reset()
  {
    errorProperty.set(null);
    Player currentPlayer = (Player) viewModelState.get("currentPlayer");
    playerProperty.set(currentPlayer);
    topPlayersProperty.clear();
    getTopPlayers();
  }

  public ObjectProperty<Player> playerProperty()
  {
    return playerProperty;
  }

  public ObservableList<Player> topPlayersProperty()
  {
    return topPlayersProperty;
  }

  private void getTopPlayers()
  {
    errorProperty.set(null);

    try
    {
      topPlayersProperty.addAll(model.getTopPlayers());
    }
    catch (IllegalStateException e)
    {
      errorProperty.set(e.getMessage());
    }
  }
}