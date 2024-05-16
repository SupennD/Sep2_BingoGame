package view;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import viewmodel.MainViewModel;

public class MainViewController extends ViewController<MainViewModel>
{
  @FXML private Text userNameText;

  @Override public void init(ViewHandler viewHandler, MainViewModel viewModel, Region root)
  {
    super.init(viewHandler, viewModel, root);

    viewModel.playerProperty().addListener((o, ov, player) -> {
      userNameText.setText(player.getUserName());
    });
  }

  @FXML public void onJoinRoom()
  {
    viewHandler.openView(View.ROOM);
  }
}