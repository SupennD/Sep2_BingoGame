package model.card;

/**
 * A class defining the initial state of the cell.
 *
 * @author Lucia Andronic
 * @author Supendra Bogati
 * @version 1.1.0 - May 2024
 */
public class InitialCellState extends CellState
{
  /**
   * A method to mark the cell, transitioning it to the marked state.
   *
   * @param cell The cell to mark.
   */
  @Override public void mark(Cell cell)
  {
    cell.setState(new MarkedCellState());
  }
}