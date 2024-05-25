package model.card;

/**
 * A class defining the highlighted state of the cell. This is the final state that a cell can be in.
 *
 * @author Lucia Andronic
 * @version 1.0.0 - May 2024
 */
public class MarkedAndHighlightedCellState extends CellState
{
  @Override public boolean isMarked()
  {
    return true;
  }

  @Override public boolean isHighlighted()
  {
    return true;
  }
}