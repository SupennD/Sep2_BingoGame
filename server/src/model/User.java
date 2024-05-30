package model;

import java.io.Serializable;

/**
 * A class containing user specific data and methods.
 *
 * @author Lucia Andronic
 * @author Supendra Bogati
 * @version 1.1.0 - May 2024
 * @version 1.2.0 - May 2024
 */
public class User implements Serializable
{
  private final String userName;
  private String password;

  /**
   * A constructor to create a new User object with the specified username and password.
   *
   * @param userName the username of the user
   * @param password the password of the user
   * @throws IllegalArgumentException if either the username or password is null or empty
   */
  public User(String userName, String password) throws IllegalArgumentException
  {
    if (userName == null || userName.isEmpty())
    {
      throw new IllegalArgumentException("Please enter an username");
    }

    if (password == null || password.isEmpty())
    {
      throw new IllegalArgumentException("Please enter a password");
    }

    this.userName = userName;
    this.password = password;
  }

  /**
   * A method that can be used to get the username of the user.
   *
   * @return the username of the user
   */
  public User(String userName) throws IllegalArgumentException
  {
    if (userName == null || userName.isEmpty())
    {
      throw new IllegalArgumentException("Please enter an username");
    }

    this.userName = userName;
  }

  public String getUserName()
  {
    return userName;
  }

  /**
   * A method that can be used to get the password of the user.
   *
   * @return the password of the user
   */
  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }
  /**
   * A method for checking if this User object is equal to another object.
   *
   * @param obj the object to compare with
   * @return true if the objects are equal, false otherwise
   */

  @Override public boolean equals(Object obj)
  {
    if (obj == null || getClass() != obj.getClass())
      return false;

    User other = (User) obj;
    return userName.equals(other.userName);
  }

  /**
   * A method to get the string representation of the user.
   *
   * @return the string representation of the user
   */
  @Override public String toString()
  {
    return userName;
  }
}