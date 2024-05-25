package model.card;

import java.io.Serializable;

/**
 * An interface defining the generic structure of a cell.
 *
 * @author Lucia Andronic
 * @version 1.1.0 - May 2024
 */
public interface Cell extends Serializable
{
  void setState(CellState cellState);
  void mark();
  boolean isMarked();
  void highlight();
  boolean isHighlighted();
}