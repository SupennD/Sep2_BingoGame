package persistence;

import model.Player;
import model.Score;
import model.User;
import utility.persistence.MyDatabase;
import utils.log.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A class offering Postgres SQL database persistence. Implements the {@link Persistence} interface
 *
 * @author Lucia Andronic
 * @author Alexandru Tofan
 * @author Supendra Bogati
 * @version 1.2.0 - May 2024
 */
public class Database implements Persistence
{
  private static final String DRIVER = "org.postgresql.Driver";
  private static final String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=bingo";
  private static final String USER = "postgres";
  private static final String PASSWORD = ""; // This should not be done in a production environment
  private final MyDatabase db;
  private final Log log = Log.getInstance();

  /**
   * Constructs a new Database instance.
   *
   * @throws ClassNotFoundException if the Postgres SQL driver class cannot be found
   */
  public Database() throws ClassNotFoundException
  {
    this.db = new MyDatabase(DRIVER, URL, USER, PASSWORD);
  }

  /**
   * Adds a new user to the database.
   *
   * @param userName the username of the new user
   * @param password the password of the new user
   * @return the newly added user
   * @throws IllegalStateException if adding the user fails, such as due to a duplicate username
   */
  @Override public User addUser(String userName, String password)
  {
    try
    {
      User user = new User(userName, password);

      String sql = "INSERT INTO \"user\" (\"userName\", \"password\") VALUES(?, ?);";
      db.update(sql, user.getUserName(), user.getPassword());

      log.info("Database: addUser: " + user);

      return user;
    }
    catch (SQLException e)
    {
      String message = e.getMessage();

      if (message.contains("duplicate"))
      {
        message = "Username taken, choose another one";
      }
      else
      {
        message = "Adding user failed";
      }

      throw new IllegalStateException(message);
    }
  }

  /**
   * Gets a user from the database by their username.
   *
   * @param userName the username of the user to retrieve
   * @return the user with the specified username
   * @throws IllegalStateException if the user cannot be found
   */
  @Override public User getUser(String userName)
  {
    try
    {
      User user = new User(userName);

      String sql = "SELECT * FROM \"user\" WHERE \"userName\" = ?;";
      ArrayList<Object[]> rows = db.query(sql, user.getUserName());

      if (rows.isEmpty())
      {
        throw new IllegalStateException("User not found, register first");
      }

      Object[] row = rows.get(0);
      user.setPassword(row[1].toString()); // Update the user password to the one from database

      log.info("Database: getUser: " + user);

      return user;
    }
    catch (SQLException e)
    {
      throw new IllegalStateException("Getting user failed");
    }
  }

  /**
   * Gets a user from the database by their username and password.
   *
   * @param userName the username of the user to retrieve
   * @param password the password of the user to retrieve
   * @return the user with the specified username and password
   * @throws IllegalArgumentException if the password is empty or incorrect
   * @throws IllegalStateException if the user cannot be found
   */
  @Override public User getUser(String userName, String password)
  {
    User user = new User(userName, password);
    User storedUser = getUser(user.getUserName());

    if (!user.getPassword().equals(storedUser.getPassword()))
    {
      throw new IllegalArgumentException("Wrong password, try again");
    }

    log.info("Database: getUser: " + user);

    return user;
  }

  /**
   * Gets all users from the database.
   *
   * @return an ArrayList containing all users
   * @throws IllegalStateException if retrieving users fails
   */
  @Override public ArrayList<User> getAllUsers()
  {
    try
    {
      ArrayList<User> users = new ArrayList<>();

      String sql = "SELECT * FROM \"user\";";
      ArrayList<Object[]> rows = db.query(sql);

      for (Object[] row : rows)
      {
        users.add(new User(row[0].toString(), row[1].toString()));
      }

      log.info("Database: getAllUsers: " + users.size());

      return users;
    }
    catch (SQLException e)
    {
      throw new IllegalStateException("Getting users failed");
    }
  }

  /**
   * Adds a score entry to the database.
   *
   * @param score the score to be added
   * @throws IllegalStateException if adding the score fails
   */
  @Override public void addScore(Score score)
  {
    try
    {
      String sql = "INSERT INTO \"score\"(\"gameId\", \"userName\", \"score\") VALUES (?, ?, ?);";
      db.update(sql, score.getGameId(), score.getUserName(), score.getScore());

      log.info("Database: addScore: " + score);
    }
    catch (SQLException e)
    {
      throw new IllegalStateException("Adding score failed");
    }
  }

  /**
   * Gets scores for a specific player from the database.
   *
   * @param player the player for whom scores are to be retrieved
   * @return the player with the retrieved scores
   * @throws IllegalStateException if retrieving scores fails
   */
  @Override public Player getScores(Player player)
  {
    try
    {
      String sql = """
          SELECT score."gameId", score."userName", SUM(score."score") AS totalScore
          FROM score
          WHERE score."userName" = ?
          GROUP BY score."gameId", score."userName";
          """;
      ArrayList<Object[]> rows = db.query(sql, player.getUserName());

      if (rows.isEmpty())
      {
        return player;
      }

      for (Object[] row : rows)
      {
        player.addScore(new Score(Long.valueOf(row[0].toString()), row[1].toString(), Long.valueOf(row[2].toString())));
      }

      log.info("Database: getScores: " + player.getScores().size());

      return player;
    }
    catch (SQLException e)
    {
      throw new IllegalStateException("Getting scores failed");
    }
  }

  /**
   * Gets the top players with the highest scores from the database.
   *
   * @return an ArrayList containing the top players
   * @throws IllegalStateException if retrieving top players fails
   */
  @Override public ArrayList<Player> getTopPlayers()
  {
    try
    {
      String sql = """
          SELECT score."userName", SUM(score."score") AS totalScore
          FROM score
          GROUP BY score."userName"
          ORDER BY totalScore DESC
          LIMIT 5;
          """;
      ArrayList<Object[]> rows = db.query(sql);

      if (rows.isEmpty())
      {
        throw new IllegalStateException("No top players yet");
      }

      ArrayList<Player> players = new ArrayList<>();

      for (Object[] row : rows)
      {
        Score score = new Score(row[0].toString(), Long.valueOf(row[1].toString()));
        Player player = new Player(score.getUserName());
        player.addScore(score);

        players.add(player);
      }

      log.info("Database: getTopPlayers: " + players.size());

      return players;
    }
    catch (SQLException e)
    {
      throw new IllegalStateException("Getting top players failed");
    }
  }
}