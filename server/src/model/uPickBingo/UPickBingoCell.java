package model.uPickBingo;

import model.card.Cell;
import model.card.CellState;
import model.card.InitialCellState;

/**
 * A class defining a UPick Bingo cell that can be used with a card of the same game. Implements the {@link Cell}
 * interface, providing the necessary functionality for a UPick Bingo cell, including state management, marking, and
 * highlighting.
 *
 * @author Lucia Andronic
 * @version 1.1.0 - May 2024
 */
public class UPickBingoCell implements Cell
{
  private final int value;
  private CellState cellState;

  /**
   * A constructor for a UPickBingoCell with a specified value. The initial state of the cell is set to
   * {@link InitialCellState}.
   *
   * @param value the value of the cell
   */
  public UPickBingoCell(int value)
  {
    this.value = value;
    this.cellState = new InitialCellState();
  }

  /**
   * A method to set the state of the cell.
   *
   * @param cellState the new state of the cell
   */
  @Override public void setState(CellState cellState)
  {
    this.cellState = cellState;
  }

  /**
   * A method to mark the cell. The specific behavior of marking is defined by the current state.
   */
  @Override public void mark()
  {
    cellState.mark(this);
  }

  /**
   * A method to check if the cell is marked. The specific behavior of highlighting is defined by the current state
   *
   * @return true if the cell is marked, false otherwise
   */
  @Override public boolean isMarked()
  {
    return cellState.isMarked();
  }

  /**
   * Highlights the cell. The specific behavior of highlighting is defined by the current state.
   */
  @Override public void highlight()
  {
    cellState.highlight(this);
  }

  /**
   * Checks if the cell is highlighted. The specific behavior of highlighting is defined by the current state
   *
   * @return true if the cell is highlighted, false otherwise
   */
  @Override public boolean isHighlighted()
  {
    return cellState.isHighlighted();
  }

  /**
   * A method to compare this cell to the specified object. The result is true if and only if the argument is not null
   * and is a UPickBingoCell object that has the same value as this cell.
   *
   * @param obj the object to compare this UPickBingoCell against
   * @return true if the given object represents a UPickBingoCell equivalent to this cell, false otherwise
   */
  @Override public boolean equals(Object obj)
  {
    if (obj == null || obj.getClass() != getClass())
      return false;

    UPickBingoCell other = (UPickBingoCell) obj;
    return this.value == other.value;
  }

  /**
   * A method to return a string representation of the cell's value.
   *
   * @return the string representation of the cell's value
   */
  @Override public String toString()
  {
    return String.valueOf(value);
  }
}