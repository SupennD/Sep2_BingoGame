package model;

import model.card.Card;

import java.util.ArrayList;

/**
 * A class containing player specific data and methods.
 *
 * @author Lucia Andronic
 * @version 1.3.0 - May 2024
 */
public class Player extends User
{
  private Card card;
  private ArrayList<Score> scores;

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

  public Card getCard()
  {
    return card;
  }

  public void setCard(Card card)
  {
    this.card = card;
  }
}