package persistence;

import model.Player;
import model.Score;
import model.User;
import utils.log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A proxy class offering a caching layer for database persistence. Implements the {@link Persistence} interface
 *
 * @author Lucia Andronic
 * @author Alexandru Tofan
 * @author Supendra Bogati
 * @version 1.2.0 - May 2024
 */
public class DatabaseCache implements Persistence
{
  private final Map<String, User> users;
  private final Database db;
  private final Log log = Log.getInstance();

  /**
   * Constructs a new DatabaseCache instance.
   *
   * @throws ClassNotFoundException if the database driver class cannot be found
   */
  public DatabaseCache() throws ClassNotFoundException
  {
    // TODO: maybe cap this and remove old items to make room for new ones
    this.users = new HashMap<>();
    this.db = new Database();
  }

  /**
   * Adds a new user to the database and the cache.
   *
   * @param userName the username of the new user
   * @param password the password of the new user
   * @return the newly added user
   */
  @Override public User addUser(String userName, String password)
  {
    // Add to database
    User user = db.addUser(userName, password);
    // Add to cache for later retrievals
    users.put(user.getUserName(), user);

    return user;
  }

  /**
   * Gets a user by their username from the cache if available; otherwise, retrieves from the database.
   *
   * @param userName the username of the user to retrieve
   * @return the user with the specified username
   */
  @Override public User getUser(String userName)
  {
    // Check cache
    if (users.containsKey(userName))
    {
      log.info("Cache hit: getUser, " + userName);
      return users.get(userName);
    }

    // Get from database if not found in cache
    User user = db.getUser(userName);
    // Add to cache for later retrievals
    users.put(user.getUserName(), user);

    return user;
  }

  /**
   * Gets a user by their username and password from the database.
   *
   * @param userName the username of the user to retrieve
   * @param password the password of the user to retrieve
   * @return the user with the specified username and password
   */
  @Override public User getUser(String userName, String password)
  {
    // We want this to always get from database so that the password is checked
    User user = db.getUser(userName, password);
    // Add to cache for later retrievals
    users.put(user.getUserName(), user);

    return user;
  }

  /**
   * Gets all users from the database.
   *
   * @return an ArrayList containing all users
   */
  @Override public ArrayList<User> getAllUsers()
  {
    // This changes frequently, so fresh data is important
    return db.getAllUsers();
  }

  /**
   * Adds a score entry to the database.
   *
   * @param score the score to be added
   */
  @Override public void addScore(Score score)
  {
    db.addScore(score);
  }

  /**
   * Gets scores for a specific player from the database.
   *
   * @param player the player for whom scores are to be retrieved
   * @return the player with the retrieved scores
   */
  @Override public Player getScores(Player player)
  {
    // This changes frequently, so fresh data is important
    return db.getScores(player);
  }

  /**
   * Gets the top players with the highest scores from the database.
   *
   * @return an ArrayList containing the top players
   */
  @Override public ArrayList<Player> getTopPlayers()
  {
    // This changes frequently, so fresh data is important
    return db.getTopPlayers();
  }
}