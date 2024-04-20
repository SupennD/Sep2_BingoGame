package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Model;

/**
 * Example implementation of a ViewModel.
 *
 * @deprecated Should be removed when the first actual view model is implemented.
 */
public class ExampleViewModel extends ViewModel
{
  // Usually all instance variable should be java beans (properties)
  private final StringProperty exampleText;

  public ExampleViewModel(Model model, ViewModelState viewModelState)
  {
    // Always call super first
    super(model, viewModelState);

    // Do any other initialisation steps
    exampleText = new SimpleStringProperty();
  }

  @Override public void reset()
  {
    // This is where fields and state can be reset to a default value
    exampleText.set("Example view text.");
  }

  /**
   * Method to get the value of the property.
   *
   * @return the value of the property
   */
  public String getExampleText()
  {
    return exampleText.get();
  }

  /**
   * Method to get the property itself.
   *
   * @return the property bean
   */
  public StringProperty exampleTextProperty()
  {
    return exampleText;
  }

  /**
   * Example of a custom method to be called from the view controller
   */
  public void hello()
  {
    exampleText.set("Hello from javaFX!");
  }
}