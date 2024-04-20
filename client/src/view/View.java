package view;

/**
 * An enum to hold the names of all the views used in the application and providing a way to get their FXML file path.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - April 2024
 */
public enum View
{
  // TODO: add values for views to be used and remove the EXAMPLE view
  // TODO: corresponds to the FXML file name in resources/view/ExampleView.fxml
  EXAMPLE("ExampleView");
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