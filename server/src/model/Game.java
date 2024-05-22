package model;

import java.io.Serializable;

public interface Game extends Serializable
{
  String getRules();
  void addPlayer(Player player);
  void start(int roomId);
  void makeMove(Player player, int number);
  boolean isFull();
  void callBingo(int roomId, Player player);
}