package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LasoCardTest
{
  private LasoCard lasoCard;

  @BeforeEach public void beforeEach()
  {
    lasoCard = new LasoCard();
  }

  @Test public void theTitleIsBINGO()
  {
    ArrayList<String> title = lasoCard.getTitle();
    ArrayList<String> expected = new ArrayList<>(Arrays.asList("B", "I", "N", "G", "O"));
    assertEquals(expected, title, "The title should be \"BINGO\"");
  }

  @RepeatedTest(value = 5, name = "card {currentRepetition}") public void theItemsAreUnique()
  {
    int[][] itemsArray = lasoCard.getItems();
    int itemsInArray = 0;
    // Sets don't allow duplicate values, so if the length of the set differs it means we have duplicates
    HashSet<Integer> uniqueItems = new HashSet<>();

    for (int[] row : itemsArray)
    {
      for (int item : row)
      {
        uniqueItems.add(item);
        itemsInArray++;
      }
    }

    assertEquals(itemsInArray, uniqueItems.size());
  }
}
