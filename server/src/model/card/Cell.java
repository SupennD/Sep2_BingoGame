package model.card;

import java.io.Serializable;

/**
 * An interface defining the generic structure of a cell.
 *
 * @author Lucia Andronic
 * @version 1.0.0 - May 2024
 */
public interface Cell extends Serializable
{
  void mark();
  void setState(CellState cellState);
  boolean isMarked();
}