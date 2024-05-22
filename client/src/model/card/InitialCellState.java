package model.card;

/**
 * A class defining the initial state of the cell.
 *
 * @author Lucia Andronic
 * @version 1.0.0 - May 2024
 */
public class InitialCellState implements CellState
{
  @Override public void mark(Cell cell)
  {
    cell.setState(new MarkedCellState());
  }

  @Override public boolean isMarked()
  {
    return false;
  }
}