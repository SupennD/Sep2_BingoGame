package model.uPickBingo;

import model.card.Card;
import model.card.Cell;
import model.card.CellState;
import model.card.MarkedCellState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * A class representing a card for the custom "Laso Bingo" variation of the BINGO game.
 *
 * @author Alexandru Tofan
 * @author Lucia Andronic
 * @author Supendra Bogati
 * @version 1.3.0 - May 2024
 */
public class UPickBingoCard implements Card
{
  public static final int SIZE = 5;
  public static final int MIN_LINES_TO_WIN = 5;

  private final ArrayList<String> title;
  private final ArrayList<Cell> cells;
  private final ArrayList<ArrayList<Cell>> highlightedLines;

  /**
   * The default constructor initializing the card items to auto-generated numbers and the title to "BINGO".
   */
  public UPickBingoCard()
  {
    this.title = initTitle();
    this.cells = initCells();
    this.highlightedLines = new ArrayList<>();
  }

  /**
   * A method used to get the title row of a card.
   *
   * @return a list of items representing the title
   */
  @Override public ArrayList<String> getTitle()
  {
    return title;
  }

  /**
   * A method used to get the items of a card as a 2-dimensional array.
   *
   * @return the 2-dimensional array representing the items
   */
  @Override public Cell[][] getCells()
  {
    Cell[][] cellsArray = new UPickBingoCell[SIZE][SIZE];

    for (int i = 0; i < cells.size(); i++)
    {
      int row = i / SIZE;
      int col = i % SIZE;
      cellsArray[row][col] = cells.get(i);
    }

    return cellsArray;
  }

  /**
   * A method used to mark a cell on the card. This sets the {@link CellState} of the card to {@link MarkedCellState}.
   *
   * @param cell the cell to mark
   */
  @Override public void markCell(Cell cell)
  {
    getCell(cell).mark();
    highlightMarkedLines(); // After a mark, check if there are any lines to highlight and highlight them
  }

  /**
   * A method used to check if a cell on the card is marked.
   *
   * @param cell the cell to check
   * @return {@code true} if the given cell is marked, {@code false} otherwise
   */
  @Override public boolean isMarked(Cell cell)
  {
    return getCell(cell).isMarked();
  }

  /**
   * A method used to check if a cell on the card is highlighted.
   *
   * @param cell the cell to check
   * @return {@code true} if the given cell is highlighted, {@code false} otherwise
   */
  @Override public boolean isHighlighted(Cell cell)
  {
    return getCell(cell).isHighlighted();
  }

  /**
   * A method used to check if a cell on the card is highlighted.
   *
   * @return {@code true} if card has a win combination, {@code false} otherwise
   */
  @Override public boolean hasWinCombination()
  {
    return highlightedLines.size() >= MIN_LINES_TO_WIN;
  }

  /**
   * Initializes the title row of the bingo card.
   *
   * @return An ArrayList containing the title row items.
   */
  private ArrayList<String> initTitle()
  {
    return new ArrayList<>(Arrays.asList("B", "I", "N", "G", "O"));
  }

  /**
   * Initializes the cells of the bingo card with unique numbers.
   *
   * @return An ArrayList containing the initialized cells.
   */
  private ArrayList<Cell> initCells()
  {
    Random random = new Random();
    ArrayList<Cell> uniqueCells = new ArrayList<>();

    while (uniqueCells.size() < SIZE * SIZE)
    {
      int number = random.nextInt(1, SIZE * SIZE + 1);
      Cell cell = new UPickBingoCell(number);

      // Make sure all the numbers are unique
      if (!uniqueCells.contains(cell))
      {
        uniqueCells.add(cell);
      }
    }

    return uniqueCells;
  }

  /**
   * A method to get the cell from the bingo card based on the provided cell object.
   *
   * @param cell The cell object to retrieve.
   * @return The cell from the bingo card.
   * @throws IllegalArgumentException if the provided cell is not found in the card.
   */
  private Cell getCell(Cell cell)
  {
    int index = cells.indexOf(cell);

    if (index == -1)
    {
      throw new IllegalArgumentException("Cell " + cell + " not found");
    }

    return cells.get(index);
  }

  /**
   * A method to highlight a specified cell on the bingo card.
   *
   * @param cell The cell to highlight.
   */
  private void highlightCell(Cell cell)
  {
    getCell(cell).highlight();
  }

  /**
   * A method to highlight the lines on the bingo card that contain marked cells. It checks for horizontal, vertical,
   * diagonal, and anti-diagonal lines with all cells marked, and adds them to the list of highlighted lines.
   */
  private void highlightMarkedLines()
  {
    Cell[][] cells = getCells();
    highlightedLines.clear();
    ArrayList<Cell> diagonal = new ArrayList<>();
    ArrayList<Cell> antiDiagonal = new ArrayList<>();

    // Find lines/cells to highlight
    for (int row = 0; row < cells.length; row++)
    {
      ArrayList<Cell> horizontal = new ArrayList<>();
      ArrayList<Cell> vertical = new ArrayList<>();

      for (int col = 0; col < cells[row].length; col++)
      {
        // Add marked cells to the horizontal line
        if (cells[row][col].isMarked())
        {
          horizontal.add(cells[row][col]);
        }

        // Add marked cells to the vertical line
        if (cells[col][row].isMarked())
        {
          vertical.add(cells[col][row]);
        }
      }

      // Add marked cells to the diagonal line
      if (cells[row][row].isMarked())
      {
        diagonal.add(cells[row][row]);
      }

      // Add marked cells to the anti-diagonal line
      if (cells[row][cells.length - row - 1].isMarked())
      {
        antiDiagonal.add(cells[row][cells.length - 1 - row]);
      }

      // If all 5 cells in the horizontal line are marked add it to the highlighted lines
      if (horizontal.size() == SIZE)
      {
        highlightedLines.add(horizontal);
      }

      // If all 5 cells in the vertical line are marked add it to the highlighted lines
      if (vertical.size() == SIZE)
      {
        highlightedLines.add(vertical);
      }
    }

    // If all 5 cells in the diagonal line are marked add it to the highlighted lines
    if (diagonal.size() == SIZE)
    {
      highlightedLines.add(diagonal);
    }

    // If all 5 cells in the anti-diagonal line are marked add it to the highlighted lines
    if (antiDiagonal.size() == SIZE)
    {
      highlightedLines.add(antiDiagonal);
    }

    // Go through all the found lines/cells and highlight them
    for (ArrayList<Cell> highlightedLine : highlightedLines)
    {
      for (Cell cellToHighlight : highlightedLine)
      {
        if (!cellToHighlight.isHighlighted())
        {
          highlightCell(cellToHighlight);
        }
      }
    }
  }
}