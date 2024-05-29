package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import model.Player;
import model.card.Card;
import model.card.Cell;
import viewmodel.GameViewModel;

import java.util.ArrayList;

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
  @FXML private ListView<Player> playersListView;
  @FXML private ListView<Cell> calledCellsListView;
  @FXML private GridPane cardGridPane;
  @FXML private Text errorText;

  @Override public void init(ViewHandler viewHandler, GameViewModel viewModel, Region root)
  {
    super.init(viewHandler, viewModel, root);

    playersListView.setCellFactory(list -> new PlayerListCell(viewModel.timerProperty()));
    playersListView.setItems(viewModel.playersProperty());
    viewModel.currentPlayerIndexProperty().addListener((o, ov, currentPlayerIndex) -> {
      playersListView.getSelectionModel().select(currentPlayerIndex.intValue());
    });
    viewModel.winnerPlayerProperty().addListener((o, ov, winnerPlayer) -> {
      viewHandler.openView(View.WIN);
    });
    calledCellsListView.setCellFactory(list -> new ListCell<>()
    {
      @Override protected void updateItem(Cell item, boolean empty)
      {
        super.updateItem(item, empty);

        if (!empty && item != null)
        {
          setText(item.toString());
          setGraphic(new Circle(22));

          if (getIndex() == 0)
          {
            updateSelected(true);
          }
        }
        else
        {
          setGraphic(null);
        }
      }
    });
    calledCellsListView.setItems(viewModel.calledCellsProperty());
    viewModel.cardProperty().addListener((o, ov, card) -> {
      updateTitle(card);
      updateCells(card);
    });
    errorText.textProperty().bind(viewModel.errorProperty());
  }

  private void updateTitle(Card card)
  {
    ArrayList<String> cardTitle = card.getTitle();

    for (int columnIndex = 0; columnIndex < cardTitle.size(); columnIndex++)
    {
      String cellText = cardTitle.get(columnIndex);
      Text text = new Text(cellText);
      text.setBoundsType(TextBoundsType.VISUAL);
      StackPane stackPane = new StackPane(text);
      cardGridPane.add(stackPane, columnIndex, 0);
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

        Button button = (Button) cardGridPane.lookup("#" + cell);

        if (button == null)
        {
          button = new Button(cell.toString());
          button.setGraphic(new Circle(22));
          button.setUserData(cell);
          button.setId(cell.toString());
          button.setOnAction(this::makeMove);

          cardGridPane.add(button, columnIndex, rowIndex + 1);
        }

        if (cell.isMarked())
        {
          button.setDisable(true);
        }

        if (cell.isHighlighted())
        {
          button.getStyleClass().add("highlighted");
        }
      }
    }
  }

  private void makeMove(ActionEvent event)
  {
    Button button = (Button) event.getSource();
    Cell cell = (Cell) button.getUserData();
    viewModel.makeMove(cell);
  }

  @FXML public void callBingo()
  {
    viewModel.callBingo();
  }
}