package model.card;

/**
 * A class defining the marked state of the cell.
 *
 * @author Lucia Andronic
 * @version 1.1.0 - May 2024
 */
public class MarkedCellState extends CellState
{
  @Override public boolean isMarked()
  {
    return true;
  }

  @Override public void highlight(Cell cell)
  {
    cell.setState(new MarkedAndHighlightedCellState());
  }
}