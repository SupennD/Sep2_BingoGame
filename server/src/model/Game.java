package model;

import model.card.Cell;

import java.io.Serializable;

public interface Game extends Serializable
{
  void addPlayer(Player player);
  void removePlayer(Player player);
  void start(int roomId);
  void stop(int roomId);
  void makeMove(Player player, Cell cell);
  void callBingo(int roomId, Player player);
  boolean isFull();
  String getRules();
}