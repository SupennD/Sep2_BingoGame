package model.card;

import java.io.Serializable;

/**
 * An interface defining the generic state of a cell.
 *
 * @author Lucia Andronic
 * @version 1.1.0 - May 2024
 */
public abstract class CellState implements Serializable
{
  public void mark(Cell cell)
  {
    throw new IllegalStateException("Cannot mark cell");
  }

  public boolean isMarked()
  {
    return false;
  }

  public void highlight(Cell cell)
  {
    throw new IllegalStateException("Cannot highlight cell");
  }

  public boolean isHighlighted()
  {
    return false;
  }
}