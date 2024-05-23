package model;

import model.card.Cell;

import java.io.Serializable;

public interface Game extends Serializable
{
  String getRules();
  void addPlayer(Player player);
  void start(int roomId);
  void makeMove(Player player, Cell cell);
  boolean isFull();
  void callBingo(int roomId, Player player);
  void removePlayer(Player player);
  void stop(int roomId);

}