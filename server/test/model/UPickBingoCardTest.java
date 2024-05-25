package model;

import model.card.Card;
import model.card.Cell;
import model.uPickBingo.UPickBingoCard;
import model.uPickBingo.UPickBingoCell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UPickBingoCardTest
{
  private Card card;

  @BeforeEach public void beforeEach()
  {
    card = new UPickBingoCard();
  }

  @Test public void theTitleIsBINGO()
  {
    ArrayList<String> title = card.getTitle();
    ArrayList<String> expected = new ArrayList<>(Arrays.asList("B", "I", "N", "G", "O"));
    assertEquals(expected, title, "The title should be \"BINGO\"");
  }

  @RepeatedTest(value = 5, name = "Card {currentRepetition} is unique") //
  public void theCellsAreUnique()
  {
    Cell[][] cellsArray = card.getCells();
    int cellsInArray = 0;
    // Sets don't allow duplicate values, so if the length of the set differs it means we have duplicates
    HashSet<Cell> uniqueCells = new HashSet<>();

    for (Cell[] row : cellsArray)
    {
      for (Cell cell : row)
      {
        uniqueCells.add(cell);
        cellsInArray++;
      }
    }

    assertEquals(cellsInArray, uniqueCells.size());
  }

  @ParameterizedTest(name = "Cell {arguments} marked") //
  @ValueSource(ints = {1, 2, 3, 4, 5}) // cell values
  public void validCellsCanBeMarked(int number)
  {
    Cell cellToMark = new UPickBingoCell(number);

    assertDoesNotThrow(() -> card.markCell(cellToMark));
    assertTrue(card.isMarked(cellToMark));
  }

  @ParameterizedTest(name = "Cell {arguments} already marked") //
  @ValueSource(ints = {1, 2, 3, 4, 5}) // cell values
  public void markedCellsCanNotBeMarked(int number)
  {
    Cell cellToMark = new UPickBingoCell(number);

    assertDoesNotThrow(() -> card.markCell(cellToMark)); // mark the cell the first time
    assertThrows(IllegalStateException.class, () -> card.markCell(cellToMark)); // try to mark it a second time
  }

  @ParameterizedTest(name = "Cannot mark cell {arguments}") //
  @ValueSource(ints = {-1, 99, 33, 45, 120}) // cell values
  public void invalidCellsCanNotBeMarked(int number)
  {
    Cell cellToMark = new UPickBingoCell(number);

    assertThrows(IllegalArgumentException.class, () -> card.markCell(cellToMark));
  }

  @Test public void hasWinCombination()
  {
    Cell[][] cells = card.getCells();

    for (int i = 0; i < cells.length; i++)
    {
      for (int j = 0; j < cells[i].length; j++)
      {
        if (!card.isMarked(cells[i][j]) && i < 2) // Add only 2 rows
        {
          card.markCell(card.getCells()[i][j]);
        }

        if (!card.isMarked(cells[j][i]) && i < 1) // Add only one column
        {
          card.markCell(card.getCells()[j][i]);
        }

        if (!card.isMarked(cells[i][i]))
        {
          card.markCell(card.getCells()[i][i]);
        }

        if (!card.isMarked(cells[i][cells.length - 1 - i]))
        {
          card.markCell(card.getCells()[i][cells.length - 1 - i]);
        }
      }
    }

    assertTrue(card.hasWinCombination());
  }
}