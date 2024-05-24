package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
    playersHBox.getChildren().clear();

    for (int i = 0; i < 4; i++)
    {
      Text userNameText = new Text("Connecting...");
      ProgressIndicator progressIndicator = new ProgressIndicator();
      VBox avatarVBox = new VBox(progressIndicator);
      VBox playerVBox = new VBox(avatarVBox, userNameText);
      playerVBox.getStyleClass().add("player-placeholder");
      HBox.setHgrow(playerVBox, Priority.ALWAYS);

      try
      {
        Player player = players.get(i);
        userNameText.setText(player.getUserName());
        // TODO: update avatar once we add image support
        ImageView avatarImageView = new ImageView("images/user-placeholder.png");
        avatarImageView.setFitWidth(48);
        avatarImageView.setFitHeight(48);
        avatarVBox.getChildren().setAll(avatarImageView);
        playersHBox.getChildren().add(playerVBox);
      }
      catch (IndexOutOfBoundsException e) // If player not present at position, show default placeholder
      {
        playersHBox.getChildren().add(playerVBox);
      }
    }
  }

  @FXML public void onGetRules()
  {
    viewHandler.openView(View.RULES);
  }
}