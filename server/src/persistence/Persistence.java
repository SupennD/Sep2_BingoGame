package persistence;

import model.User;

import java.util.ArrayList;

/**
 * An interface to be implemented by classes providing persistence functionality.
 *
 * @author Lucia Andronic
 * @version 1.0.0 - April 2024
 */
public interface Persistence
{
  User addUser(String userName, String password);
  User getUser(String userName);
  User getUser(String userName, String password);
  ArrayList<User> getAllUsers();
}
