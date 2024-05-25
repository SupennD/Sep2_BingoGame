package model.uPickBingo;

import model.card.Cell;
import model.card.CellState;
import model.card.InitialCellState;

/**
 * A class defining a UPick Bingo cell that can be used with a card of the same game.
 *
 * @author Lucia Andronic
 * @version 1.1.0 - May 2024
 */
public class UPickBingoCell implements Cell
{
  private final int value;
  private CellState cellState;

  public UPickBingoCell(int value)
  {
    this.value = value;
    this.cellState = new InitialCellState();
  }

  @Override public void setState(CellState cellState)
  {
    this.cellState = cellState;
  }

  @Override public void mark()
  {
    cellState.mark(this);
  }

  @Override public boolean isMarked()
  {
    return cellState.isMarked();
  }

  @Override public void highlight()
  {
    cellState.highlight(this);
  }

  @Override public boolean isHighlighted()
  {
    return cellState.isHighlighted();
  }

  @Override public boolean equals(Object obj)
  {
    if (obj == null || obj.getClass() != getClass())
      return false;

    UPickBingoCell other = (UPickBingoCell) obj;
    return this.value == other.value;
  }

  @Override public String toString()
  {
    return String.valueOf(value);
  }
}