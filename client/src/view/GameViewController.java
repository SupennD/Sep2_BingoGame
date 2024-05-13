package view;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import model.Card;
import model.Player;
import viewmodel.GameViewModel;

import java.util.ArrayList;

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
  @FXML GridPane cardGridPane;

  @Override public void init(ViewHandler viewHandler, GameViewModel viewModel, Region root)
  {
    super.init(viewHandler, viewModel, root);

    playersListView.setItems(viewModel.playersProperty());
    errorText.textProperty().bind(viewModel.errorProperty());
    viewModel.cardProperty().addListener((o, ov, card) -> {
      updateTitle(card);
      updateItems(card);
    });
  }

  private void updateTitle(Card card)
  {
    ArrayList<String> cardTitle = card.getTitle();

    for (int columnIndex = 0; columnIndex < cardTitle.size(); columnIndex++)
    {
      String cellText = cardTitle.get(columnIndex);
      Text text = new Text(cellText);
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
        button.setPrefWidth(300);
        button.setPrefHeight(300);
        cardGridPane.add(button, columnIndex, rowIndex + 1);
      }
    }
  }
}
