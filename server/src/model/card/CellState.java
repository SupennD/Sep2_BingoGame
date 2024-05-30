package model.card;

import java.io.Serializable;

/**
 * An interface defining the generic state of a cell.
 *
 * @author Lucia Andronic
 * @author Supendra Bogati
 * @version 1.1.0 - May 2024
 */
public abstract class CellState implements Serializable
{
  /**
   * A method to mark the cell. This method should be overridden in subclasses.
   *
   * @param cell The cell to mark.
   * @throws IllegalStateException if marking the cell is not supported in this state.
   */
  public void mark(Cell cell)
  {
    throw new IllegalStateException("Cannot mark cell");
  }

  /**
   * A method to check if the cell is marked.
   *
   * @return {@code true} if the cell is marked, {@code false} otherwise.
   */
  public boolean isMarked()
  {
    return false;
  }

  /**
   * A method to highlight the cell. This method should be overridden in subclasses.
   *
   * @param cell The cell to highlight.
   * @throws IllegalStateException if highlighting the cell is not supported in this state.
   */
  public void highlight(Cell cell)
  {
    throw new IllegalStateException("Cannot highlight cell");
  }

  /**
   * A method to check if the cell is highlighted.
   *
   * @return {@code true} if the cell is highlighted, {@code false} otherwise.
   */
  public boolean isHighlighted()
  {
    return false;
  }
}