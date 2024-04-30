package utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A class to be used application wide where date and/or time is needed. This class implements {@link Serializable} so
 * it's safe to use across network calls.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - April 2024
 */
public class DateTime implements Serializable
{
  private final Date date;

  /**
   * A constructor that initializes the date to the current time.
   */
  public DateTime()
  {
    date = Calendar.getInstance().getTime();
  }

  /**
   * A method that can be used to get the time in the format {@code dd/MM/yyyy HH:mm:ss}.
   *
   * @return the timestamp string
   */
  public String getTimestamp()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    return sdf.format(date);
  }

  /**
   * A method that can be used to get the time in the format {@code yyyy-MM-dd-HH-mm-ss}. This works better for when you
   * need to for ex. sort by time.
   *
   * @return the sortable formatted time string
   */
  public String getSortableTime()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    return sdf.format(date);
  }

  /**
   * A method that can be used to get the date in the format {@code yyyy-MM-dd}.
   *
   * @return the sortable formatted date string
   */
  public String getSortableDate()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(date);
  }

  /**
   * A method that can be used to get the time in the format {@code dd/MM/yyyy HH:mm:ss}.
   *
   * @return the timestamp string
   */
  @Override public String toString()
  {
    return getTimestamp();
  }
}