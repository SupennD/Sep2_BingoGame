package persistence;

import model.Player;
import model.Score;
import model.User;
import utils.log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A proxy class offering a caching layer for database persistence.
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

  public DatabaseCache() throws ClassNotFoundException
  {
    // TODO: maybe cap this and remove old items to make room for new ones
    this.users = new HashMap<>();
    this.db = new Database();
  }

  @Override public User addUser(String userName, String password)
  {
    // Add to database
    User user = db.addUser(userName, password);
    // Add to cache for later retrievals
    users.put(user.getUserName(), user);

    return user;
  }

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

  @Override public User getUser(String userName, String password)
  {
    // We want this to always get from database so that the password is checked
    User user = db.getUser(userName, password);
    // Add to cache for later retrievals
    users.put(user.getUserName(), user);

    return user;
  }

  @Override public ArrayList<User> getAllUsers()
  {
    // This changes frequently, so fresh data is important
    return db.getAllUsers();
  }

  @Override public void addScore(Score score)
  {
    db.addScore(score);
  }

  @Override public Player getScores(Player player)
  {
    // This changes frequently, so fresh data is important
    return db.getScores(player);
  }

  @Override public ArrayList<Player> getTopPlayers()
  {
    // This changes frequently, so fresh data is important
    return db.getTopPlayers();
  }
}