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
 * @version 1.1.0 - May 2024
 */
public class UPickBingoCard implements Card
{
  public static final int ROWS = 5;
  public static final int COLUMNS = 5;
  public static final int SIZE = ROWS * COLUMNS;

  private final ArrayList<String> title;
  private final ArrayList<Cell> cells;

  /**
   * The default constructor initializing the card items to auto-generated numbers and the title to "BINGO".
   */
  public UPickBingoCard()
  {
    this.title = initTitle();
    this.cells = initCells();
  }

  @Override public ArrayList<String> getTitle()
  {
    return title;
  }

  @Override public Cell[][] getCells()
  {
    Cell[][] cellsArray = new UPickBingoCell[ROWS][COLUMNS];

    int index = 0;

    for (int i = 0; i < ROWS; i++)
    {
      for (int j = 0; j < COLUMNS; j++)
      {
        cellsArray[i][j] = cells.get(index);
        index++;
      }
    }

    return cellsArray;
  }

  private ArrayList<String> initTitle()
  {
    return new ArrayList<>(Arrays.asList("B", "I", "N", "G", "O"));
  }

  private ArrayList<Cell> initCells()
  {
    Random random = new Random();
    ArrayList<Cell> uniqueCells = new ArrayList<>();

    while (uniqueCells.size() < SIZE)
    {
      int number = random.nextInt(1, SIZE + 1);
      Cell cell = new UPickBingoCell(number);

      // Make sure all the numbers are unique
      if (!uniqueCells.contains(cell))
      {
        uniqueCells.add(cell);
      }
    }

    return uniqueCells;
  }
}