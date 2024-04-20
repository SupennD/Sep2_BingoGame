package viewmodel;

import java.util.HashMap;
import java.util.Map;

/**
 * A class to be used for passing state between different views. This is intended to be used in a view model factory,
 * where a single instance can be passed to all view models that need it.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - April 2024
 */
public class ViewModelState
{
  /**
   * Map to store state objects
   */
  private final Map<String, Object> data;

  /**
   * Constructor initializing the map
   */
  public ViewModelState()
  {
    data = new HashMap<>();
  }

  /**
   * Clears the state by removing all stored objects.
   */
  public void clear()
  {
    data.clear();
  }

  /**
   * Adds a new object to the state at the specified key or, replaces the existing one.
   *
   * @param key the key to store the object under
   * @param value the object to store under the provided key
   */
  public void put(String key, Object value)
  {
    data.put(key, value);
  }

  /**
   * Returns the object stored under the specified key or {@code null}.
   *
   * @param key the key for which to return the object
   * @return the object based on the specified key or {@code null}
   */
  public Object get(String key)
  {
    return data.get(key);
  }

  /**
   * Removes specific object from state based on key.
   *
   * @param key the key for the object to be removed
   */
  public void remove(String key)
  {
    data.remove(key);
  }
}