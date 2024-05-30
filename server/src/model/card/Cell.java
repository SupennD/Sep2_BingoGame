package model.card;

import java.io.Serializable;

/**
 * An interface defining the generic structure of a cell.It extends the {@link Serializable} interface, allowing cell
 * instances to be serialized. Implementations of this interface are expected to manage the state of the cell whether it
 * is marked or highlighted.
 *
 * @author Lucia Andronic
 * @author Supendra Bogati
 * @version 1.1.0 - May 2024
 */
public interface Cell extends Serializable
{
  /**
   * A method to set the state of the cell.
   *
   * @param cellState the new state of the cell
   */
  void setState(CellState cellState);
  /**
   * A method to mark the cell.
   */
  void mark();
  /**
   * A method to check if the cell is marked.
   *
   * @return true if the cell is marked, false otherwise
   */
  boolean isMarked();

  /**
   * Highlights the cell. This typically changes the visual representation of the cell to indicate that it is
   * highlighted.
   */
  void highlight();
  /**
   * Checks if the cell is highlighted.
   *
   * @return true if the cell is highlighted, false otherwise
   */
  boolean isHighlighted();
}