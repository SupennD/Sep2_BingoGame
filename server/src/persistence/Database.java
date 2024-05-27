package persistence;

import model.Player;
import model.Score;
import model.User;
import utility.persistence.MyDatabase;
import utils.log.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A class offering PostgreSQL database persistence.
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

  public Database() throws ClassNotFoundException
  {
    this.db = new MyDatabase(DRIVER, URL, USER, PASSWORD);
  }

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
        throw new IllegalStateException("No scores found");
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
        throw new IllegalStateException("No records yet");
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