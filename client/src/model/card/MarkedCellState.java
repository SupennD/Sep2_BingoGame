package model.card;

/**
 * A class defining the marked state of the cell. This is the final state that a cell can be in.
 *
 * @author Lucia Andronic
 * @version 1.0.0 - May 2024
 */
public class MarkedCellState implements CellState
{
  @Override public void mark(Cell cell)
  {
    throw new UnsupportedOperationException("Cell already marked.");
  }

  @Override public boolean isMarked()
  {
    return true;
  }
}