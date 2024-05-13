package model;

import java.io.Serializable;

/**
 * A class containing user specific data and methods.
 *
 * @author Lucia Andronic
 * @version 1.0.0 - April 2024
 */
public class User implements Serializable
{
  private final String userName;
  private final String password;

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

  public String getUserName()
  {
    return userName;
  }

  public String getPassword()
  {
    return password;
  }

  @Override public boolean equals(Object obj)
  {
    if (obj == null || getClass() != obj.getClass())
      return false;

    User other = (User) obj;
    return userName.equals(other.userName);
  }

  @Override public String toString()
  {
    return userName;
  }
}