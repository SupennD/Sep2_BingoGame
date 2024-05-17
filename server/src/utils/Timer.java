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
 * @version 1.1.0 - May 2024
 */
public class Timer implements Runnable, LocalSubject<Integer, String>
{
  private final PropertyChangeHandler<Integer, String> propertyChangeHandler;
  private final int timerSeconds;
  private final boolean infinite;
  private int elapsedSeconds;

  public Timer(int timerSeconds, boolean infinite)
  {
    this.propertyChangeHandler = new PropertyChangeHandler<>(this, true);
    this.timerSeconds = timerSeconds;
    this.elapsedSeconds = timerSeconds;
    this.infinite = infinite;
  }

  public Timer(int timerSeconds)
  {
    this(timerSeconds, false);
  }

  public int getSeconds()
  {
    return timerSeconds;
  }

  public void reset()
  {
    elapsedSeconds = timerSeconds;
  }

  @Override public void run()
  {
    try
    {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss");

      while (elapsedSeconds >= 0)
      {
        LocalTime time = LocalTime.ofSecondOfDay(elapsedSeconds);
        propertyChangeHandler.firePropertyChange("timer:tick", elapsedSeconds, time.format(formatter));

        if (elapsedSeconds == 0)
        {
          propertyChangeHandler.firePropertyChange("timer:done", elapsedSeconds, time.format(formatter));

          if (infinite)
          {
            propertyChangeHandler.firePropertyChange("timer:reset", elapsedSeconds, time.format(formatter));
            elapsedSeconds = timerSeconds + 1;
          }
        }

        Thread.sleep(1000);

        elapsedSeconds--;
      }
    }
    catch (InterruptedException e)
    {
      // do nothing
    }
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
