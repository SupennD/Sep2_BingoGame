package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Player;
import viewmodel.RoomViewModel;

/**
 * A controller class responsible for starting the game when a room is full
 *
 * @author Lucia Andronic
 * @author Alexandru Tofan
 * @version 1.0.0 - May 2024
 */
public class RoomViewController extends ViewController<RoomViewModel>
{
  @FXML private ListView<Player> playersListView;
  @FXML private Text errorText;
  @FXML private Text messageText;

  @Override public void init(ViewHandler viewHandler, RoomViewModel viewModel, Region root)
  {
    super.init(viewHandler, viewModel, root);

    playersListView.setCellFactory(list -> new PlayerListCell());
    playersListView.setItems(viewModel.playersProperty());
    errorText.textProperty().bind(viewModel.errorProperty());
    messageText.textProperty().bind(viewModel.messageProperty());

    // Delay opening the game view for 2s to give players some time
    Timeline openGameViewTimeline = new Timeline(
        new KeyFrame(Duration.seconds(2), e -> viewHandler.openView(View.GAME)));

    // Listen for when the room is full and open the game view by playing the timeline
    viewModel.isFullProperty().addListener((o, ov, isFull) -> {
      if (isFull)
      {
        openGameViewTimeline.play(); // start the game in 2s after the room is full
      }
    });
  }

  @FXML public void onGetRules()
  {
    InformationAlert rulesAlert = new InformationAlert("Rules", "How to play", viewModel.rulesProperty().get());
    rulesAlert.show();
  }
}