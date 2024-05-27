package model;

import model.card.Cell;
import utility.observer.subject.LocalSubject;

import java.util.ArrayList;

/**
 * A facade interface to be implemented by a class to expose critical methods for interacting with the application. It
 * also acts as a subject in the observer pattern as part of the MVVM application.
 *
 * @author Alexandru Tofan
 * @author Supendra Bogati
 * @author Lucia Andronic
 * @version 1.4.0 - May 2024
 */
public interface Model extends LocalSubject<Object, Object>
{
  Player register(String userName, String password) throws IllegalArgumentException, IllegalStateException;
  Player login(String userName, String password) throws IllegalArgumentException, IllegalStateException;
  int joinRoom(Player player) throws IllegalStateException;
  void leaveRoom(int roomId, Player player) throws IllegalStateException;
  String getRules(int roomId) throws IllegalStateException;
  void startGame(int roomId) throws IllegalStateException;
  void makeMove(int roomId, Player player, Cell cell) throws IllegalStateException;
  void callBingo(int roomId, Player player) throws IllegalStateException;
  Player getScores(Player player) throws IllegalStateException;
  ArrayList<Player> getTopPlayers() throws IllegalStateException;
}