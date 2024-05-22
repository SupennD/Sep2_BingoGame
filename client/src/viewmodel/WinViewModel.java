package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Model;
import model.Player;

/**
 * Win view model
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - May 2024
 * @see ViewModel
 */
public class WinViewModel extends ViewModel
{
  private final StringProperty messageProperty;

  public WinViewModel(Model model, ViewModelState viewModelState)
  {
    super(model, viewModelState);

    this.messageProperty = new SimpleStringProperty();
  }

  public StringProperty messageProperty()
  {
    return messageProperty;
  }

  @Override public void reset()
  {
    Player winnerPlayer = (Player) viewModelState.get("winnerPlayer");
    Player currentPlayer = (Player) viewModelState.get("currentPlayer");
    boolean currentPlayerWon = winnerPlayer.equals(currentPlayer);

    messageProperty.set(currentPlayerWon ? "You won!" : "You lost!");
  }
}