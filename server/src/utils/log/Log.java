package utils.log;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * A singleton class that can be used to log messages of different levels to console and file. To get and instance of
 * this class, use the static method {@code Log.getInstance()}. The default log level used is {@code DEBUG}.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - April 2024
 */
public class Log
{
  private static final Object lock = new Object();
  private static Log instance;
  private Level level;

  /**
   * Private constructor that creates an instance of this class with a default log level of {@code DEBUG}.
   */
  private Log()
  {
    this.level = Level.DEBUG;
  }

  /**
   * A method to be used for getting a singleton instance in a thread safe way of this class.
   *
   * @return the singleton instance
   */
  public static Log getInstance()
  {
    if (instance == null)
    {
      synchronized (lock)
      {
        if (instance == null)
        {
          instance = new Log();
        }
      }
    }
    return instance;
  }

  /**
   * A method that can be used to set the log {@link Level} application wide. Setting one level, also enables all levels
   * above it.
   *
   * @param level the level to set
   * @see Level
   */
  public void setLevel(Level level)
  {
    this.level = level;
  }

  /**
   * A method that can be used to log a debug message. This should be used for messages directed to the developer.
   *
   * @param message the debug message to log
   */
  public void debug(String message)
  {
    addLog(Level.DEBUG, message);
  }

  /**
   * A method that can be used to log an info message. This should be used for more general messages that do not require
   * immediate attention.
   *
   * @param message the info message to log
   */
  public void info(String message)
  {
    addLog(Level.INFO, message);
  }

  /**
   * A method that can be used to log a warning message. This should be used in situations where more attention is
   * required or for unexpected behaviours.
   *
   * @param message the warning message to log
   */
  public void warn(String message)
  {
    addLog(Level.WARN, message);
  }

  /**
   * A method that can be used to log a {@link LogLine} to both console and file.
   *
   * @param logLevel the log level of the message
   * @param message the message to log
   */
  private void addLog(Level logLevel, String message)
  {
    LogLine line = new LogLine(logLevel, message);

    // Print all logs to file
    addToFile(line);

    // Print to console only logs that have a value higher or equal to the set log level
    if (level.getValue() <= logLevel.getValue())
    {
      System.out.println(line.toColoredString());
    }
  }

  /**
   * A method that can be used to append a {@link LogLine} to a file.
   *
   * @param logLine the log line to append
   */
  private void addToFile(LogLine logLine)
  {
    if (logLine == null)
    {
      return;
    }

    String filename = "Log-" + logLine.getDateTime().getSortableDate() + ".txt";

    try (BufferedWriter out = new BufferedWriter(new FileWriter(filename, true)))
    {
      out.write(logLine + "\n");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Defines a set of logging levels to be used across the application. The levels are ordered {@code DEBUG},
   * {@code INFO}, {@code WARN}, and setting one level, also enables all levels above it.
   */
  public enum Level
  {
    DEBUG(1, "\u001B[34m"), // ANSI color code for blue
    INFO(2, "\u001B[32m"), // ANSI color code for green
    WARN(3, "\u001B[33m"); // ANSI color code for orange

    private final int value;
    private final String color;

    Level(int value, String color)
    {
      this.value = value;
      this.color = color;
    }

    public int getValue()
    {
      return value;
    }

    public String getColor()
    {
      return color;
    }

    public String resetColor()
    {
      return "\u001B[0m"; // ANSI reset color code
    }
  }
}