package viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.AudioClip;
import model.Model;
import model.Player;

public class MainViewModel extends ViewModel
{
  private final ObjectProperty<Player> playerProperty;
  private final ObservableList<Player> topPlayersProperty;
  private final StringProperty errorProperty;
  private final AudioClip backgroundMusic;

  public MainViewModel(Model model, ViewModelState viewModelState)
  {
    super(model, viewModelState);

    this.playerProperty = new SimpleObjectProperty<>();
    this.topPlayersProperty = FXCollections.observableArrayList();
    this.errorProperty = new SimpleStringProperty();
    this.backgroundMusic = new AudioClip(getClass().getResource("/sounds/background.mp3").toExternalForm());
    this.backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
    this.backgroundMusic.setVolume(0.01);
  }

  @Override public void reset()
  {
    errorProperty.set(null);
    getPlayerWithScores();
    getTopPlayers();

    if (!backgroundMusic.isPlaying())
    {
      backgroundMusic.play();
    }
  }

  public ObjectProperty<Player> playerProperty()
  {
    return playerProperty;
  }

  public ObservableList<Player> topPlayersProperty()
  {
    return topPlayersProperty;
  }

  public StringProperty errorProperty()
  {
    return errorProperty;
  }

  private void getPlayerWithScores()
  {
    errorProperty.set(null);

    try
    {
      Player currentPlayer = (Player) viewModelState.get("currentPlayer");
      Player playerWithScores = model.getScores(currentPlayer);

      // Update the view model state with player score
      viewModelState.put("currentPlayer", playerWithScores);
      // Update the player property
      playerProperty.set(playerWithScores);
    }
    catch (IllegalStateException e)
    {
      errorProperty.set(e.getMessage());
    }
  }

  private void getTopPlayers()
  {
    errorProperty.set(null);

    try
    {
      topPlayersProperty.setAll(model.getTopPlayers());
    }
    catch (IllegalStateException e)
    {
      errorProperty.set(e.getMessage());
    }
  }
}