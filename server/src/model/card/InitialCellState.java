package model.card;

/**
 * A class defining the initial state of the cell.
 *
 * @author Lucia Andronic
 * @version 1.1.0 - May 2024
 */
public class InitialCellState extends CellState
{
  @Override public void mark(Cell cell)
  {
    cell.setState(new MarkedCellState());
  }
}