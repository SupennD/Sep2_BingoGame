package view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Player;
import viewmodel.MainViewModel;

public class MainViewController extends ViewController<MainViewModel>
{
  @FXML private VBox userVBox;
  @FXML private ListView<Player> topPlayerListView;
  @FXML private Text errorText;

  @Override public void init(ViewHandler viewHandler, MainViewModel viewModel, Region root)
  {
    super.init(viewHandler, viewModel, root);

    viewModel.playerProperty().addListener((o, ov, player) -> {
      userVBox.getChildren().setAll(PlayerListCell.getGraphic(player));
    });
    // Use a custom cell factory
    topPlayerListView.setCellFactory(list -> new PlayerListCell());
    topPlayerListView.setItems(viewModel.topPlayersProperty());
    errorText.textProperty().bind(viewModel.errorProperty());
  }

  @FXML public void onJoinRoom()
  {
    viewHandler.openView(View.ROOM);
  }
}