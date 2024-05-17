package view;

import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
 * @version 1.1.0 - May 2024
 */
public class GameViewController extends ViewController<GameViewModel>
{
  @FXML private Text playersText;
  @FXML private ListView<Player> playersListView;
  @FXML private Text errorText;
  @FXML private GridPane cardGridPane;
  @FXML private HBox calledNumbers;

  @Override public void init(ViewHandler viewHandler, GameViewModel viewModel, Region root)
  {
    super.init(viewHandler, viewModel, root);

    playersText.textProperty().bind(Bindings.concat("Players ", viewModel.timerProperty()));
    playersListView.setItems(viewModel.playersProperty());
    viewModel.currentPlayerProperty().addListener((o, ov, currentPlayer) -> {
      playersListView.getSelectionModel().clearSelection();
      playersListView.getSelectionModel().select(currentPlayer);
    });
    errorText.textProperty().bind(viewModel.errorProperty());
    viewModel.calledNumbersProperty().addListener((ListChangeListener<Integer>) change -> {
      while (change.next())
      {
        // Convert to a normal list
        List<Integer> numbers = change.getList().stream().map(player -> (Integer) player).toList();
        updateCalledNumbers(numbers);
      }
    });
    viewModel.cardProperty().addListener((o, ov, card) -> {
      updateTitle(card);
      updateItems(card);
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
}
