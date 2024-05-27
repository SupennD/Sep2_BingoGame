package model;

import java.io.Serializable;

/**
 * A class that contains a scores of the player
 *
 * @author Supendra Bogati
 * @version 1.0.0 - May 2024
 */
public class Score implements Serializable
{
  private final Long gameId;
  private final String userName;
  private final Long score;

  public Score(Long gameId, String userName, Long score)
  {
    this.gameId = gameId;
    this.userName = userName;
    this.score = score;
  }

  public Score(String userName, Long score)
  {
    this(null, userName, score);
  }

  public Long getGameId()
  {
    return gameId;
  }

  public String getUserName()
  {
    return userName;
  }

  public Long getScore()
  {
    return score;
  }

  @Override public String toString()
  {
    return String.valueOf(score);
  }
}