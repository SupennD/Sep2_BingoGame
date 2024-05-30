package utils.blocking;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A thread-safe  queue that supports blocking operations for adding and removing elements. This interface defines the
 * basic operations for a blocking deque.
 *
 * @param <T> the type of elements held in this deque
 * @author Supendra Bogati
 * @version 1.0.0 - May 2024
 */
public interface BlockingDeque<T> extends Serializable
{
  /**
   * Pushes an element to the front of the deque, unless the deque is bounded and full. This should be used for stack
   * functionality as it pushes an element to the front (top) of the deque.
   *
   * @param element the element to push
   * @return {@code true} if the element was pushed or {@code false} otherwise
   */
  boolean push(T element);
  /**
   * Retrieves and removes the first element of this deque, or returns {@code null} if this deque is empty. This should
   * be used for stack functionality as it pops the element at the front (top) of the deque.
   *
   * @return the front element or {@code null} if the deque is empty
   */
  T pop();
  /**
   * Inserts the specified element at the end of this deque unless it would violate capacity restrictions.
   *
   * @param element the element to enqueue
   * @return {@code true} if the element was added to this deque, else {@code false}
   */
  boolean enqueue(T element);
  /**
   * Retrieves and removes the first element of this deque, or returns {@code null} if this deque is empty. If
   * {@code circular} was set to {@code true} then this would enqueue the element after dequeue putting it at the tail
   * of this deque thus making it circular.
   *
   * @return the head of this deque, or {@code null} if this deque is empty
   */
  T dequeue();
  /**
   * Retrieves, but does not remove, the first element of this deque, or returns {@code null} if this deque is empty. In
   * case of using this deque as a stack, this returns the top element of the stack.
   *
   * @return the head of this deque, or {@code null} if this deque is empty
   */
  T peek();
  /**
   * Removes the first occurrence of the given element from this deque.
   *
   * @param element the element to remove
   * @return {@code true} if an element has been removed
   */
  boolean remove(T element);
  /**
   * Returns the number of elements in this deque.
   *
   * @return the number of elements in this deque
   */
  int size();

  /**
   * Converts the elements of this deque into an {@link ArrayList}.
   *
   * @return an {@link ArrayList} containing all elements of this deque
   */
  ArrayList<T> toArrayList();
}
