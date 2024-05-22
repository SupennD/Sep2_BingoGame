package view;

import javafx.animation.*;
import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Card;
import model.Player;
import viewmodel.GameViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A controller class responsible for managing game
 *
 * @author Supendra Bogati
 * @author Lucia Andronic
 * @author Alexandru Tofan
 * @version 1.2.1 - May 2024
 */
public class GameViewController extends ViewController<GameViewModel>
{
  @FXML private VBox playersVBox;
  @FXML private HBox calledNumbers;
  @FXML private GridPane cardGridPane;
  @FXML private Text errorText;

  @Override public void init(ViewHandler viewHandler, GameViewModel viewModel, Region root)
  {
    super.init(viewHandler, viewModel, root);

    viewModel.playersProperty().addListener((InvalidationListener) o -> {
      updatePlayers(viewModel.playersProperty());
    });
    viewModel.currentPlayerProperty().addListener((o, ov, currentPlayer) -> {
      updateCurrentPlayer(currentPlayer);
    });
    viewModel.winnerPlayerProperty().addListener((o, ov, winnerPlayer) -> {
      viewHandler.openView(View.WIN);
    });
    viewModel.calledNumbersProperty().addListener((InvalidationListener) o -> {
      updateCalledNumbers(viewModel.calledNumbersProperty());
    });
    viewModel.cardProperty().addListener((o, ov, card) -> {
      updateTitle(card);
      updateItems(card);
    });
    errorText.textProperty().bind(viewModel.errorProperty());
  }

  private void updatePlayers(List<Player> players)
  {
    ObservableList<Node> playerNodes = playersVBox.getChildren();

    for (int i = 0; i < playerNodes.size(); i++)
    {
      HBox container = (HBox) playerNodes.get(i);
      // TODO: update avatar once we add image support
      // StackPane avatar = (StackPane) container.getChildren().get(0);
      VBox details = (VBox) container.getChildren().get(1);
      Text userName = (Text) details.getChildren().get(0);

      Player player = players.get(i);
      container.setUserData(player.getUserName());
      userName.setText(player.getUserName());
    }
  }

  private void updateCurrentPlayer(Player currentPlayer)
  {
    playersVBox.getChildren().forEach(node -> {
      String userName = node.getUserData().toString();
      HBox container = (HBox) node;
      Text timer = (Text) ((VBox) container.getChildren().get(1)).getChildren().get(1);

      TranslateTransition translateTransition = new TranslateTransition(Duration.millis(400), timer);
      FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), timer);
      ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);

      if (currentPlayer.getUserName().equals(userName))
      {
        container.getStyleClass().add("selected");
        timer.setText("00:15");
        timer.setUserData(15);

        translateTransition.setToY(0);
        fadeTransition.setToValue(1);
        parallelTransition.play();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
          int remainingSeconds = Integer.parseInt(timer.getUserData().toString());
          int minutes = remainingSeconds / 60;
          int seconds = remainingSeconds % 60;
          timer.setUserData(remainingSeconds - 1);
          timer.setText(String.format("%02d:%02d", minutes, seconds));
        }));
        timeline.setCycleCount(15);
        timeline.playFromStart();
      }
      else
      {
        container.getStyleClass().remove("selected");
        translateTransition.setToY(3);
        fadeTransition.setToValue(0);
        parallelTransition.playFromStart();
      }
    });
  }

  private void updateCalledNumbers(List<Integer> numbers)
  {
    if (!numbers.isEmpty())
    {
      calledNumbers.getChildren().clear();
      int start = numbers.size() > 5 ? numbers.size() - 5 : 0;
      for (int i = start; i < numbers.size(); i++)
      {
        Text text = new Text(String.valueOf(numbers.get(i)));
        text.setFont(Font.font("Arial Bold", 24));
        calledNumbers.getChildren().add(text);
      }
    }
  }

  private void updateTitle(Card card)
  {
    ArrayList<String> cardTitle = card.getTitle();

    for (int columnIndex = 0; columnIndex < cardTitle.size(); columnIndex++)
    {
      String cellText = cardTitle.get(columnIndex);
      Text text = new Text(cellText);
      text.setFont(Font.font("Arial Bold", 24));
      GridPane.setValignment(text, VPos.CENTER);
      GridPane.setHalignment(text, HPos.CENTER);
      cardGridPane.add(text, columnIndex, 0);
    }
  }

  private void updateItems(Card card)
  {
    int[][] cardNumbers = card.getItems();

    for (int rowIndex = 0; rowIndex < cardNumbers.length; rowIndex++)
    {
      for (int columnIndex = 0; columnIndex < cardNumbers[rowIndex].length; columnIndex++)
      {
        String cellNumber = cardNumbers[rowIndex][columnIndex] + "";
        Button button = new Button(cellNumber);
        button.setPrefWidth(64);
        button.setPrefHeight(64);
        button.setOnAction(this::makeMove);
        cardGridPane.add(button, columnIndex, rowIndex + 1);
      }
    }
  }

  private void makeMove(ActionEvent event)
  {
    Button button = (Button) event.getSource();
    int number = Integer.parseInt(button.getText());
    boolean success = viewModel.makeMove(number);

    if (success)
    {
      button.setStyle("-fx-text-fill: white; -fx-background-color: green;");
      button.setDisable(true);
    }
  }

  @FXML public void callBingo()
  {
    viewModel.callBingo();
  }
}