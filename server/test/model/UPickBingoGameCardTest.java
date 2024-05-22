package model;

import model.card.Card;
import model.card.Cell;
import model.uPickBingo.UPickBingoCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

  @RepeatedTest(value = 5, name = "card {currentRepetition}") public void theCellsAreUnique()
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
}