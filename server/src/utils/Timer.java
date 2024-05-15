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
 * @version 1.0.0 - May 2024
 */
public class Timer implements Runnable, LocalSubject<Integer, String>
{
  private final PropertyChangeHandler<Integer, String> propertyChangeHandler;
  private int timerSeconds;

  public Timer(int timerSeconds)
  {
    this.timerSeconds = timerSeconds;
    propertyChangeHandler = new PropertyChangeHandler<>(this, true);
  }

  public int getSeconds()
  {
    return timerSeconds;
  }

  @Override public void run()
  {
    LocalTime time = LocalTime.ofSecondOfDay(timerSeconds);
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("mm:ss");

    while (timerSeconds >= 0)
    {
      try
      {
        propertyChangeHandler.firePropertyChange("time", timerSeconds, time.format(timeFormatter));
        Thread.sleep(1000);
        time = time.minusSeconds(1);
        timerSeconds--;
      }
      catch (InterruptedException e)
      {
        // do nothing
      }
    }

    propertyChangeHandler.firePropertyChange("end", 0, null);
  }

  @Override public boolean addListener(GeneralListener<Integer, String> generalListener, String... strings)
  {
    return propertyChangeHandler.addListener(generalListener, strings);
  }

  @Override public boolean removeListener(GeneralListener<Integer, String> generalListener, String... strings)
  {
    return propertyChangeHandler.removeListener(generalListener, strings);
  }
}
