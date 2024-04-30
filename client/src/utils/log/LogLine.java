package utils.log;

import utils.DateTime;

/**
 * A class that can be used to construct a formatted log line.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - April 2024
 */
public class LogLine
{
  private final Log.Level level;
  private final String text;
  private final DateTime dateTime;

  /**
   * A constructor that initializes instance variables based on parameters and the {@code dateTime} to the current time
   * of when this log line is created.
   *
   * @param level log level
   * @param text text of the log
   */
  public LogLine(Log.Level level, String text)
  {
    this.level = level;
    this.text = text;
    this.dateTime = new DateTime();
  }

  /**
   * A method to get the log message as plain text.
   *
   * @return the plain text message
   */
  public String getText()
  {
    return text;
  }

  /**
   * A method to get the date time of when the log was created.
   *
   * @return date time of log creation
   */
  public DateTime getDateTime()
  {
    return dateTime;
  }

  /**
   * A method to get a colored and formatted string based on level.
   *
   * @return a colored and formatted string of the log
   */
  public String toColoredString()
  {
    return level.getColor() + this + level.resetColor();
  }

  /**
   * A method to get a formatted string with the date and log level.
   *
   * @return a formatted string of the log
   */
  @Override public String toString()
  {
    return dateTime.getTimestamp() + " [" + level + "] " + text;
  }
}