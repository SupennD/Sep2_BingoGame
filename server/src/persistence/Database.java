package persistence;

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
 * @version 1.1.0 - May 2024
 */
public class Database implements Persistence
{
  private static final String DRIVER = "org.postgresql.Driver";
  private static final String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=bingo";
  private static final String USER = "postgres";
  private static final String PASSWORD = "";
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
        message = "Username already taken";
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
      String sql = "SELECT * FROM \"user\" WHERE \"userName\" = ?;";
      ArrayList<Object[]> rows = db.query(sql, userName);

      if (rows.isEmpty())
      {
        throw new IllegalStateException("User not found. Please register first");
      }

      Object[] row = rows.get(0);
      User user = new User(row[0].toString(), row[1].toString());

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
    User user = getUser(userName);

    if (password == null || password.isEmpty())
    {
      throw new IllegalArgumentException("Password cannot be empty");
    }

    if (!user.getPassword().equals(password))
    {
      throw new IllegalArgumentException("Wrong password");
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
}
