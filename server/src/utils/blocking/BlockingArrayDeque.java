package utils.blocking;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * A blocking {@link ArrayDeque} that can be used as both a stack and queue, and can be optionally circular and/or
 * bounded. For using it as a stack methods {@code push()} and {@code pop()} are recommended. For queue functionality
 * methods {@code enqueue()} and {@code dequeue()} are recommended.
 *
 * @param <T> the type of elements to store in the deque
 * @author Alexandru Tofan
 * @version 1.0.0 - May 2024
 */
public class BlockingArrayDeque<T> implements BlockingDeque<T>
{
  private final Deque<T> deque;
  private final boolean circular;
  private final int capacity;

  /**
   * A constructor that can be used to create a bounded deque
   *
   * @param circular if the deque should be circular
   * @param capacity maximum capacity of the deque
   */
  public BlockingArrayDeque(boolean circular, int capacity)
  {
    this.deque = new ArrayDeque<>();
    this.circular = circular;
    this.capacity = capacity;
  }

  /**
   * A constructor that can be used to create an unbounded deque
   *
   * @param circular if the deque should be circular
   */
  public BlockingArrayDeque(boolean circular)
  {
    this(circular, -1);
  }

  /**
   * Pushes an element to the front of the deque, unless the deque is bounded and full. This should be used for stack
   * functionality as it pushes an element to the front (top) of the deque.
   *
   * @param element the element to push
   * @return {@code true} if the element was pushed or {@code false} otherwise
   */
  public synchronized boolean push(T element)
  {
    if (capacity > 0 && size() == capacity)
    {
      return false;
    }

    return deque.offerFirst(element);
  }

  /**
   * Retrieves and removes the first element of this deque, or returns {@code null} if this deque is empty. This should
   * be used for stack functionality as it pops the element at the front (top) of the deque.
   *
   * @return the front element or {@code null} if the deque is empty
   */
  public synchronized T pop()
  {
    return deque.pollFirst();
  }

  /**
   * Inserts the specified element at the end of this deque unless it would violate capacity restrictions.
   *
   * @param element the element to enqueue
   * @return {@code true} if the element was added to this deque, else {@code false}
   */
  public synchronized boolean enqueue(T element)
  {
    if (capacity > 0 && size() == capacity)
    {
      return false;
    }

    return deque.offerLast(element);
  }

  /**
   * Retrieves and removes the first element of this deque, or returns {@code null} if this deque is empty. If
   * {@code circular} was set to {@code true} then this would enqueue the element after dequeue putting it at the tail
   * of this deque thus making it circular.
   *
   * @return the head of this deque, or {@code null} if this deque is empty
   */
  public synchronized T dequeue()
  {
    T element = deque.pollFirst();

    if (circular && element != null)
    {
      enqueue(element);
    }

    return element;
  }

  /**
   * Retrieves, but does not remove, the first element of this deque, or returns {@code null} if this deque is empty. In
   * case of using this deque as a stack, this returns the top element of the stack.
   *
   * @return the head of this deque, or {@code null} if this deque is empty
   */
  public synchronized T peek()
  {
    return deque.peekFirst();
  }

  /**
   * Removes the first occurrence of the given element from this deque.
   *
   * @param element the element to remove
   * @return {@code true} if an element has been removed
   */
  public synchronized boolean remove(T element)
  {
    return deque.remove(element);
  }

  /**
   * Returns the number of elements in this deque.
   *
   * @return the number of elements in this deque
   */
  public synchronized int size()
  {
    return deque.size();
  }

  /**
   * Converts the elements of this deque into an {@link ArrayList}.
   *
   * @return an {@link ArrayList} containing all elements of this deque
   */
  public synchronized ArrayList<T> toArrayList()
  {
    ArrayList<T> list = new ArrayList<>(size());

    for (T element : deque)
    {
      list.add(element);
    }

    return list;
  }

  /**
   * A method to get a string representation of this deque.
   *
   * @return a string in the format {@code BlockingArrayDeque{element1, element2, ...}}
   */
  @Override public synchronized String toString()
  {
    String string = "BlockingArrayDeque{";

    for (T element : deque)
    {
      string += element.toString() + ",";
    }

    return string.substring(0, string.length() - 1) + "}";
  }
}