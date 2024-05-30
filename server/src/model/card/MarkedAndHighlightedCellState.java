package model.card;

/**
 * A class defining the highlighted state of the cell. This is the final state that a cell can be in.
 *
 * @author Lucia Andronic
 * @author Supendra Bogati
 * @version 1.1.0 - May 2024
 */
public class MarkedAndHighlightedCellState extends CellState
{
  /**
   * A method to check if the cell is marked.
   *
   * @return {@code true} indicating that the cell is marked.
   */
  @Override public boolean isMarked()
  {
    return true;
  }

  /**
   * A method to check if the cell is highlighted.
   *
   * @return {@code true} indicating that the cell is highlighted.
   */
  @Override public boolean isHighlighted()
  {
    return true;
  }
}