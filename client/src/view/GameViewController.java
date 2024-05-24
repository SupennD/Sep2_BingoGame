package view;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
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
import model.Player;
import model.card.Card;
import model.card.Cell;
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
  @FXML private HBox calledCells;
  @FXML private GridPane cardGridPane;
  @FXML private Text errorText;

  @Override public void init(ViewHandler viewHandler, GameViewModel viewModel, Region root)
  {
    super.init(viewHandler, viewModel, root);

    viewModel.playersProperty().addListener((InvalidationListener) o -> {
      updatePlayers(viewModel.playersProperty());
    });
    viewModel.currentPlayerIndexProperty().addListener((o, lastPlayerIndex, currentPlayerIndex) -> {
      updatePlayer(lastPlayerIndex.intValue(), false);
      updatePlayer(currentPlayerIndex.intValue(), true);
    });
    viewModel.winnerPlayerProperty().addListener((o, ov, winnerPlayer) -> {
      viewHandler.openView(View.WIN);
    });
    viewModel.calledCellsProperty().addListener((InvalidationListener) o -> {
      updateCalledCells(viewModel.calledCellsProperty());
    });
    viewModel.cardProperty().addListener((o, ov, card) -> {
      updateTitle(card);
      updateCells(card);
    });
    errorText.textProperty().bind(viewModel.errorProperty());
  }

  private void updatePlayers(List<Player> players)
  {
    ObservableList<Node> playerHBoxNodes = playersVBox.getChildren();

    for (int i = 0; i < playerHBoxNodes.size(); i++)
    {
      HBox playerHBox = (HBox) playerHBoxNodes.get(i);
      // TODO: update avatar once we add image support
      // StackPane avatarStackPane = (StackPane) playerHBox.getChildren().get(0);
      VBox detailsVBox = (VBox) playerHBox.getChildren().get(1);
      Text userNameText = (Text) detailsVBox.getChildren().get(0);
      Text timerText = (Text) detailsVBox.getChildren().get(1);

      Player player = players.get(i);
      userNameText.setText(player.getUserName());
      timerText.setOpacity(0);
    }
  }

  private void updatePlayer(int playerIndex, boolean current)
  {
    if (playerIndex != -1)
    {
      HBox playerHBox = (HBox) playersVBox.getChildren().get(playerIndex);
      VBox detailsVBox = (VBox) playerHBox.getChildren().get(1);
      Text timerText = (Text) detailsVBox.getChildren().get(1);

      if (current)
      {
        timerText.textProperty().bind(viewModel.timerProperty());
        playerHBox.getStyleClass().add("selected");
      }
      else
      {
        timerText.textProperty().unbind();
        playerHBox.getStyleClass().remove("selected");
      }

      TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), timerText);
      FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), timerText);
      ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);

      translateTransition.setToY(current ? 0 : 5);
      fadeTransition.setToValue(current ? 1 : 0);
      parallelTransition.playFromStart();
    }
  }

  private void updateCalledCells(List<Cell> cells)
  {
    calledCells.getChildren().clear();

    for (int i = cells.size() - 1; i >= 0; i--)
    {
      Text text = new Text(cells.get(i).toString());
      calledCells.getChildren().add(text);
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

  private void updateCells(Card card)
  {
    Cell[][] cells = card.getCells();

    for (int rowIndex = 0; rowIndex < cells.length; rowIndex++)
    {
      for (int columnIndex = 0; columnIndex < cells[rowIndex].length; columnIndex++)
      {
        Cell cell = cells[rowIndex][columnIndex];
        Button button = new Button(cell.toString());
        button.setPrefWidth(64);
        button.setPrefHeight(64);
        button.setUserData(cell);
        button.setOnAction(this::makeMove);
        cardGridPane.add(button, columnIndex, rowIndex + 1);
      }
    }
  }

  private void makeMove(ActionEvent event)
  {
    Button button = (Button) event.getSource();
    Cell cell = (Cell) button.getUserData();
    boolean success = viewModel.makeMove(cell);

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