package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Player;
import viewmodel.RoomViewModel;

import java.util.List;

/**
 * A controller class responsible for starting the game when a room is full
 *
 * @author Lucia Andronic
 * @author Alexandru Tofan
 * @version 1.0.0 - May 2024
 */
public class RoomViewController extends ViewController<RoomViewModel>
{
  @FXML private HBox playersHBox;
  @FXML private Text errorText;
  @FXML private Text messageText;

  @Override public void init(ViewHandler viewHandler, RoomViewModel viewModel, Region root)
  {
    super.init(viewHandler, viewModel, root);

    // Add a listener to the observable list of players and update the view when it changes
    viewModel.playersProperty().addListener((InvalidationListener) o -> {
      updatePlayers(viewModel.playersProperty().stream().toList());
    });
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

  private void updatePlayers(List<Player> players)
  {
    // Loop through the players and replace the placeholders on screen
    for (int i = 0; i < players.size(); i++)
    {
      Player player = players.get(i);
      VBox playerVBox = (VBox) playersHBox.getChildren().get(i);
      // The first children is a VBox container for the image
      VBox imageVbox = (VBox) playerVBox.getChildren().get(0);
      // The second one is a Text node for the username
      Text userNameText = (Text) playerVBox.getChildren().get(1);

      // TODO: maybe add an image later
      imageVbox.getChildren().setAll(new Text("*"));
      userNameText.setText(player.getUserName());
    }
  }

  @FXML public void onGetRules()
  {
    viewHandler.openView(View.RULES);
  }
}