package view;

/**
 * An enum to hold the names of all the views used in the application and providing a way to get their FXML file path.
 *
 * @author Alexandru Tofan
 * @author Octavian
 * @version 1.0.1 - April 2024
 */
public enum View
{
  LOGIN("LoginView"),//
  MAIN("MainView"),//
  ROOM("RoomView"),//
  GAME("GameView"),//
  RULES("RulesView"),//
  WIN("WinView");

  private final String name;

  /**
   * Constructor used to set the name to be used for constructing the FXML path.
   *
   * @param name of the FXML file
   */
  View(String name)
  {
    this.name = name;
  }

  /**
   * A method to get the path to a FXML file based on a view name
   *
   * @return FXML file path
   */
  public String getFxml()
  {
    return name + ".fxml";
  }
}