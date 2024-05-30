package utils;

import utility.observer.listener.GeneralListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeHandler;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * A class that can be used where you need to keep track of time passed. Since this is a subject, you can add a listener
 * to it to listen for {@code time} and {@code end} events.
 *
 * @author Lucia Andronic
 * @author Supendra Bogati
 * @version 1.1.0 - May 2024
 */
public class Timer implements Runnable, LocalSubject<Integer, String>
{
  private final PropertyChangeHandler<Integer, String> propertyChangeHandler;
  private final int timerSeconds;
  private final boolean infinite;
  private final Thread timerThread;
  private int elapsedSeconds;
  private boolean running;

  /**
   * A constructor setting a timer with the specified duration in seconds and the option to run infinitely.
   *
   * @param timerSeconds the duration of the timer in seconds
   * @param infinite true if the timer should run infinitely otherwise false
   */
  public Timer(int timerSeconds, boolean infinite)
  {
    this.propertyChangeHandler = new PropertyChangeHandler<>(this, true);
    this.timerSeconds = timerSeconds;
    this.elapsedSeconds = timerSeconds;
    this.infinite = infinite;
    this.running = true;
    this.timerThread = new Thread(this);
    this.timerThread.setDaemon(true);
  }

  /**
   * A constructor setting a timer with the specified duration in seconds and setting the option to run infinitely to
   * false.
   *
   * @param timerSeconds the duration of the timer in seconds
   */
  public Timer(int timerSeconds)
  {
    this(timerSeconds, false);
  }

  /**
   * A method that can be used to get the total duration of the timer in seconds.
   *
   * @return the total duration of the timer in seconds
   */
  public int getSeconds()
  {
    return timerSeconds;
  }

  /**
   * Starts the timer.
   */
  public void start()
  {
    timerThread.start();
  }

  /**
   * Resets the timer to its initial duration.
   */
  public void reset()
  {
    elapsedSeconds = timerSeconds;
  }

  /**
   * Stops the timer.
   */
  public void stop()
  {
    running = false;
    propertyChangeHandler.firePropertyChange("timer:stop", elapsedSeconds, null);
  }

  /**
   * Runs the timer thread and continuously updates the elapsed time and resets the timer to its initial duration.
   * {@code elapsedSeconds = timerSeconds} .
   */
  @Override public void run()
  {
    try
    {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss");

      while (elapsedSeconds >= 0 && running)
      {
        LocalTime time = LocalTime.ofSecondOfDay(elapsedSeconds);
        propertyChangeHandler.firePropertyChange("timer:tick", elapsedSeconds, time.format(formatter));

        Thread.sleep(1000);

        if (elapsedSeconds == 0)
        {
          propertyChangeHandler.firePropertyChange("timer:done", elapsedSeconds, time.format(formatter));

          if (infinite)
          {
            propertyChangeHandler.firePropertyChange("timer:reset", elapsedSeconds, time.format(formatter));
            reset();
          }
        }
        else
        {
          elapsedSeconds--;
        }
      }
    }
    catch (InterruptedException e)
    {
      // do nothing
    }
  }

  /**
   * Adds a general listener to the timer, allowing it to listen for specific events.
   *
   * @param generalListener the listener to be added
   * @param strings the event(s) to listen for
   * @return true if the listener was successfully added, false otherwise
   */
  @Override public boolean addListener(GeneralListener<Integer, String> generalListener, String... strings)
  {
    return propertyChangeHandler.addListener(generalListener, strings);
  }

  /**
   * Removes a general listener from the timer.
   *
   * @param generalListener the listener to be removed
   * @param strings the event(s) to stop listening for
   * @return true if the listener was successfully removed, false otherwise
   */
  @Override public boolean removeListener(GeneralListener<Integer, String> generalListener, String... strings)
  {
    return propertyChangeHandler.removeListener(generalListener, strings);
  }
}
