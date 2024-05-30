package model.card;

/**
 * A class defining the marked state of the cell.
 *
 * @author Lucia Andronic
 * @author Supendra Bogati
 * @version 1.1.0 - May 2024
 */
public class MarkedCellState extends CellState
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
   * A method to highlight the cell, transitioning it to the marked and highlighted state.
   *
   * @param cell The cell to highlight.
   */
  @Override public void highlight(Cell cell)
  {
    cell.setState(new MarkedAndHighlightedCellState());
  }
}