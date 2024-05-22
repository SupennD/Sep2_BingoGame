package model;

import model.card.Card;

/**
 * A class containing player specific data and methods.
 *
 * @author Lucia Andronic
 * @version 1.1.0 - May 2024
 */
public class Player extends User
{
  private Card card;

  public Player(String userName, String password) throws IllegalArgumentException
  {
    super(userName, password);
    this.card = null;
  }

  public Card getCard()
  {
    return card;
  }

  public void setCard(Card card)
  {
    this.card = card;
  }
}