package utils.blocking;

import java.io.Serializable;
import java.util.ArrayList;

public interface BlockingDeque<T> extends Serializable
{
  boolean push(T element);
  T pop();
  boolean enqueue(T element);
  T dequeue();
  T peek();
  boolean remove(T element);
  int size();
  ArrayList<T> toArrayList();
}
