package model.card;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An interface to be implemented by all variations of a BINGO card.
 *
 * @author Alexandru Tofan
 * @author Lucia Andronic
 * @version 1.2.0 - May 2024
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
  Cell[][] getCells();
  /**
   * A method used to mark a cell on the card. This sets the {@link CellState} of the card to {@link MarkedCellState}.
   *
   * @param cell the cell to mark
   */
  void markCell(Cell cell);
  /**
   * A method used to check if a cell on the card is marked.
   *
   * @param cell the cell to check
   * @return {@code true} if the given cell is marked, {@code false} otherwise
   */
  boolean isMarked(Cell cell);
  /**
   * A method used to check if a cell on the card is highlighted.
   *
   * @param cell the cell to check
   * @return {@code true} if the given cell is highlighted, {@code false} otherwise
   */
  boolean isHighlighted(Cell cell);
  /**
   * A method used to check if a cell on the card is highlighted.
   *
   * @return {@code true} if card has a win combination, {@code false} otherwise
   */
  boolean hasWinCombination();
}