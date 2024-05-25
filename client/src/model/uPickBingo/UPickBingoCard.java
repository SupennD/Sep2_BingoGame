package model.uPickBingo;

import model.card.Card;
import model.card.Cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * A class representing a card for the custom "Laso Bingo" variation of the BINGO game.
 *
 * @author Alexandru Tofan
 * @author Lucia Andronic
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

  @Override public ArrayList<String> getTitle()
  {
    return title;
  }

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

  @Override public void markCell(Cell cell)
  {
    getCell(cell).mark();
    highlightMarkedLines(); // After a mark, check if there are any lines to highlight and highlight them
  }

  @Override public boolean isMarked(Cell cell)
  {
    return getCell(cell).isMarked();
  }

  @Override public boolean isHighlighted(Cell cell)
  {
    return getCell(cell).isHighlighted();
  }

  @Override public boolean hasWinCombination()
  {
    return highlightedLines.size() >= MIN_LINES_TO_WIN;
  }

  private ArrayList<String> initTitle()
  {
    return new ArrayList<>(Arrays.asList("B", "I", "N", "G", "O"));
  }

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

  private Cell getCell(Cell cell)
  {
    int index = cells.indexOf(cell);

    if (index == -1)
    {
      throw new IllegalArgumentException("Cell " + cell + " not found");
    }

    return cells.get(index);
  }

  private void highlightCell(Cell cell)
  {
    getCell(cell).highlight();
  }

  private void highlightMarkedLines()
  {
    Cell[][] cells = getCells();
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