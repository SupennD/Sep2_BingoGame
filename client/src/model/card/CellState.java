package model.card;

import java.io.Serializable;

/**
 * An interface defining the generic state of a cell.
 *
 * @author Lucia Andronic
 * @version 1.0.0 - May 2024
 */
public interface CellState extends Serializable
{
  void mark(Cell cell);
  boolean isMarked();
}