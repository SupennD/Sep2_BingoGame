package model;

import java.io.Serializable;

/**
 * A class that contains a scores of the player This class extends {@link Serializable}, allowing score instances to be
 * serialized.
 *
 * @author Supendra Bogati
 * @version 1.0.0 - May 2024
 */
public class Score implements Serializable
{
  private final Long gameId;
  private final String userName;
  private final Long score;

  /**
   * Constructs a Score object with the specified game ID, username, and score.
   *
   * @param gameId the ID of the game
   * @param userName the username of the player
   * @param score the score of the player
   */
  public Score(Long gameId, String userName, Long score)
  {
    this.gameId = gameId;
    this.userName = userName;
    this.score = score;
  }

  /**
   * Constructs a Score object with the specified username and score. This constructor is used when the game ID is not
   * provided.
   *
   * @param userName the username of the player
   * @param score the score of the player
   */
  public Score(String userName, Long score)
  {
    this(null, userName, score);
  }

  /**
   * Gets the ID of the game associated with this score.
   *
   * @return the game ID
   */
  public Long getGameId()
  {
    return gameId;
  }

  /**
   * Gets the username of the player.
   *
   * @return the username
   */
  public String getUserName()
  {
    return userName;
  }

  /**
   * Gets the score of the player.
   *
   * @return the score
   */
  public Long getScore()
  {
    return score;
  }

  /**
   * Returns the string representation of the score.
   *
   * @return the score as a string
   */
  @Override public String toString()
  {
    return String.valueOf(score);
  }
}