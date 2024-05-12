package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An interface to be implemented by all variations of a BINGO card.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - May 2024
 */
public interface Card extends Serializable
{
  /**
   * A method used to get the title row of a card.
   *
   * @return a list of items representing the title
   */
  ArrayList<String> getTitle();
  /**
   * A method used to get the items of a card as a 2-dimensional array.
   *
   * @return the 2-dimensional array representing the items
   */
  int[][] getItems(); // TODO: maybe this should be generic
}
