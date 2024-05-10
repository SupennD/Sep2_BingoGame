package view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import model.Player;
import viewmodel.GameViewModel;

/**
 * A controller class responsible for managing game
 *
 * @author Supendra Bogati
 * @version 1.0.1 - April 2024
 */
public class GameViewController extends ViewController<GameViewModel>
{
  @FXML ListView<Player> playersListView;
  @FXML Text errorText;

  @Override public void init(ViewHandler viewHandler, GameViewModel viewModel, Region root)
  {
    super.init(viewHandler, viewModel, root);

    playersListView.setItems(viewModel.playersProperty());
    errorText.textProperty().bind(viewModel.errorProperty());
  }

}
