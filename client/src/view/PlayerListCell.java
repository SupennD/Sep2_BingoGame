package view;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Player;

/**
 * A class extending {@link ListCell} that can be used a custom cell factory in {@link javafx.scene.control.ListView}
 * and as an independent graphic by using the static method {@code getGraphic}.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - May 2024
 */
class PlayerListCell extends ListCell<Player>
{
  /**
   * The base size to use for the UI elements
   */
  public static final int SIZE = 64;
  /**
   * A string property used to show a timer in the player container
   */
  private final StringProperty timer;

  /**
   * A constructor that accepts an {@link StringProperty} and can be used to show for example a timer.
   *
   * @param timer the string property to bind the timer text to
   */
  public PlayerListCell(StringProperty timer)
  {
    this.timer = timer;
  }

  /**
   * An empty constructor that can be used when there is no need for a timer text.
   */
  public PlayerListCell()
  {
    this(null);
  }

  /**
   * A shorthand method useful in situations when you just want to use the graphic for a single player instead of a
   * list.
   *
   * @param player the player to show the info for
   * @return an {@link HBox} node with all the player info
   */
  public static HBox getGraphic(Player player)
  {
    return getGraphic(player, null, null, true);
  }

  /**
   * A static method that can be used to get the graphic node representing one player container with avatar, name, score
   * and timer.
   *
   * @param player the player to show the info for
   * @param rank the player rank, it can be shown instead of the avatar
   * @param timer the string property to use for the timer text
   * @param isSelected if the container should be highlighted
   * @return an {@link HBox} node with all the player info
   */
  private static HBox getGraphic(Player player, Integer rank, StringProperty timer, boolean isSelected)
  {
    // The nodes were created in Java because otherwise some artefacts could be observed when using it with ListView
    // The container in which all the other nodes will be placed
    HBox containerHBox = new HBox(12);
    containerHBox.setPadding(new Insets(8));
    containerHBox.setAlignment(Pos.CENTER_LEFT);
    containerHBox.setBackground(new Background(
        new BackgroundFill(Color.web(isSelected ? "#ff6f16" : "#c1edff"), new CornerRadii(12), Insets.EMPTY)));
    containerHBox.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.web("#092e5c0d"), 8, 0, 1, 3));

    // Stack pane for holding the avatar, rank or progress depending on the state
    StackPane avatarStackPane = new StackPane();
    avatarStackPane.setPrefWidth(SIZE);
    avatarStackPane.setPrefHeight(SIZE);
    avatarStackPane.setBorder(new Border(
        new BorderStroke(Color.web("#ffffff50"), BorderStrokeStyle.SOLID, new CornerRadii(8), new BorderWidths(4))));
    avatarStackPane.setBackground(
        new Background(new BackgroundFill(Color.web("#ffffff50"), new CornerRadii(12), Insets.EMPTY)));

    // Rank text that show a number text representing a rank
    if (rank != null)
    {
      Text rankText = new Text(String.valueOf(rank));
      rankText.getStyleClass().add("h1");
      avatarStackPane.getChildren().setAll(rankText);
    }
    // Progress indicator shown if this is marked as a placeholder
    else if (player == null)
    {
      ProgressIndicator progressIndicator = new ProgressIndicator();
      progressIndicator.setMaxSize(32, 32);
      avatarStackPane.getChildren().setAll(progressIndicator);
    }
    // Image avatar of the player
    else
    {
      // TODO: add avatar here once implemented
      ImageView avatarImageView = new ImageView();
      avatarImageView.setFitWidth(SIZE - 8);
      avatarImageView.setFitHeight(SIZE - 8);
      avatarStackPane.getChildren().setAll(avatarImageView);
    }

    // A container that holds the username, timer and score if there is one
    VBox detailsVBox = new VBox(4);
    // The username of the player
    Text userNameText = new Text(player == null ? "Connecting..." : player.getUserName());
    userNameText.setFill(Color.web(isSelected ? "#ffffff" : "#000000"));
    detailsVBox.getChildren().add(userNameText);

    if (timer != null)
    {
      addTimer(detailsVBox, timer, isSelected);
    }
    else if (player != null && player.getTotalScore() != null)
    {
      addScore(detailsVBox, player, isSelected);
    }

    containerHBox.getChildren().addAll(avatarStackPane, detailsVBox);

    return containerHBox;
  }

  /**
   * A method use to add the score info in the player container.
   *
   * @param details the parent node to add the score {@link Text} node to
   * @param player the player for which the score is added
   */
  private static void addScore(VBox details, Player player, boolean isSelected)
  {
    Text score = new Text("Score: " + player.getTotalScore().toString());
    score.setFill(Color.web(isSelected ? "#ffffff" : "#ef6e11"));
    details.getChildren().add(score);
  }

  /**
   * A method use to add the score info in the player container.
   *
   * @param details the parent node to add the timer {@link Text} node to
   * @param timer the string property representing the timer
   * @param isSelected used in a list, this denotes if the item is selected in the list
   */
  private static void addTimer(VBox details, StringProperty timer, boolean isSelected)
  {
    Text time = new Text("00:00");
    time.setFill(Color.web(isSelected ? "#ffffff" : "#ef6e11"));
    details.getChildren().add(time);

    TranslateTransition translateTransition = new TranslateTransition(Duration.millis(250), time);
    FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), time);
    ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);

    if (isSelected)
    {
      time.textProperty().bind(timer);

      translateTransition.setFromY(12);
      translateTransition.setToY(0);
      fadeTransition.setFromValue(0);
      fadeTransition.setToValue(1);
    }
    else
    {
      translateTransition.setFromY(0);
      translateTransition.setToY(12);
      fadeTransition.setFromValue(1);
      fadeTransition.setToValue(0);
    }

    parallelTransition.play();
  }

  /**
   * A method used by the {@link javafx.scene.control.ListView} to update the cell, part of the cell factory.
   *
   * @param player The new item for the cell.
   * @param empty whether this cell represents data from the list. If it is empty, then it does not represent any domain
   * data, but is a cell being used to render an "empty" row.
   */
  @Override protected void updateItem(Player player, boolean empty)
  {
    super.updateItem(player, empty);

    // Don't allow clicking on the list items
    setMouseTransparent(true);
    setFocusTraversable(false);
    // Add some padding for space between items
    setPadding(new Insets(6, 0, 6, 0));

    // If the list cell is empty remove the graphic
    if (empty)
    {
      setGraphic(null);
    }
    else if (player == null)
    {
      setGraphic(getGraphic(null, null, null, false));
    }
    else
    {
      setGraphic(getGraphic(player, getIndex() + 1, timer, isSelected()));
    }
  }
}