package persistence;

import model.Player;
import model.Score;
import model.User;

import java.util.ArrayList;

/**
 * An interface to be implemented by classes providing persistence functionality.
 *
 * @author Lucia Andronic
 * @author Supendra Bogati
 * @version 1.1.0 - April 2024
 */
public interface Persistence
{
  /**
   * Adds a new user to the persistence layer.
   *
   * @param userName the username of the new user
   * @param password the password of the new user
   * @return the newly added user
   */
  User addUser(String userName, String password);
  /**
   * Gets a user by their username.
   *
   * @param userName the username of the user to retrieve
   * @return the user with the specified username, or null if no such user exists
   */
  User getUser(String userName);
  /**
   * Retrieves a user by their username and password.
   *
   * @param userName the username of the user to retrieve
   * @param password the password of the user to retrieve
   * @return the user with the specified username and password, or null if no such user exists
   */
  User getUser(String userName, String password);
  /**
   * Retrieves all users from the persistence layer.
   *
   * @return an array list of all users
   */
  ArrayList<User> getAllUsers();
  /**
   * Adds a score to the persistence layer.
   *
   * @param score the score to add
   */
  void addScore(Score score);
  /**
   * Gets scores for the specified player.
   *
   * @param player the player whose scores are to be retrieved
   * @return the player's scores
   */
  Player getScores(Player player);
  /**
   * Gets the top players based on scores.
   *
   * @return an ArrayList of top players
   */
  ArrayList<Player> getTopPlayers();
}