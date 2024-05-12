package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * A class representing a card for the custom "Laso Bingo" variation of the BINGO game.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - May 2024
 */
public class LasoCard implements Card
{
  public static final int ROWS = 5;
  public static final int COLUMNS = 5;
  public static final int SIZE = ROWS * COLUMNS;

  private final ArrayList<String> title;
  private final ArrayList<Integer> numbers;

  /**
   * The default constructor initializing the card items to auto-generated numbers and the title to "BINGO".
   */
  public LasoCard()
  {
    this.title = initTitle();
    this.numbers = initNumbers();
  }

  @Override public ArrayList<String> getTitle()
  {
    return title;
  }

  @Override public int[][] getItems()
  {
    int[][] numbersArray = new int[ROWS][COLUMNS];

    int index = 0;

    for (int i = 0; i < ROWS; i++)
    {
      for (int j = 0; j < COLUMNS; j++)
      {
        numbersArray[i][j] = numbers.get(index);
        index++;
      }
    }

    return numbersArray;
  }

  private ArrayList<String> initTitle()
  {
    return new ArrayList<>(Arrays.asList("B", "I", "N", "G", "O"));
  }

  private ArrayList<Integer> initNumbers()
  {
    Random random = new Random();
    ArrayList<Integer> randomNumbers = new ArrayList<>();

    while (randomNumbers.size() < SIZE)
    {
      int number = random.nextInt(1, SIZE + 1);

      // Make sure all the numbers are unique
      if (randomNumbers.contains(number))
      {
        continue;
      }

      randomNumbers.add(number);
    }

    return randomNumbers;
  }
}
