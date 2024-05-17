package model;

import utility.observer.subject.LocalSubject;

/**
 * A facade interface to be implemented by a class to expose critical methods for interacting with the application. It
 * also acts as a subject in the observer pattern as part of the MVVM application.
 *
 * @author Alexandru Tofan
 * @author Lucia Andronic
 * @version 1.1.0 - April 2024
 */
public interface Model extends LocalSubject<Object, Object>
{
  Player register(String userName, String password) throws IllegalArgumentException, IllegalStateException;
  Player login(String userName, String password) throws IllegalArgumentException, IllegalStateException;
  int joinRoom(Player player);
  String getRules(int roomId) throws IllegalStateException;
  void startGame(int roomId) throws IllegalStateException;
  void makeMove(int roomId, Player player, int number) throws IllegalStateException;
}