package model;

import java.io.Serializable;

public interface Game extends Serializable
{
  Card getCard();
  String getRules();
  void addPlayer(Player player);
}
