package model;

import model.card.Card;

import java.util.ArrayList;

/**
 * A class containing player specific data and methods.
 *
 * @author Lucia Andronic
 * @author Supendra Bogati
 * @version 1.3.0 - May 2024
 */
public class Player extends User
{
  private Card card;
  private ArrayList<Score> scores;

  /**
   * A constructor to create a new Player object with the specified username and password.
   *
   * @param userName the username of the player
   * @throws IllegalArgumentException if either the username or password is null or empty
   */
  public Player(String userName) throws IllegalArgumentException
  {
    super(userName);
    this.card = null;
    this.scores = new ArrayList<>();
  }

  public ArrayList<Score> getScores()
  {
    return scores;
  }

  public void setScores(ArrayList<Score> scores)
  {
    this.scores = scores;
  }

  public Long getTotalScore()
  {
    Long totalScore = 0L;

    for (Score score : scores)
    {
      totalScore += score.getScore();
    }

    return totalScore;
  }

  public void addScore(Score score)
  {
    scores.add(score);
  }

  /**
   * A method that retrieves the card assigned to the player.
   *
   * @return the card assigned to the player or {@code null} if no card is assigned
   */
  public Card getCard()
  {
    return card;
  }

  /**
   * A method that sets the card assigned to the player.
   *
   * @param card the card to be assigned to the player
   */
  public void setCard(Card card)
  {
    this.card = card;
  }
}