package viewmodel;

import model.Model;

/**
 * An abstract class to be extended by all ViewModel's in the Application.
 *
 * @author Alexandru Tofan
 * @version 1.1.0 - May 2024
 */
public abstract class ViewModel
{
  /**
   * Main application model, using protected for easier access without a getter.
   */
  protected final Model model;
  /**
   * A {@link ViewModelState} instance to be used for state management, using protected for easier access without a
   * getter.
   */
  protected final ViewModelState viewModelState;

  /**
   * Constructor initializing the instance variables with the passed in parameters.
   *
   * @param model the main application model
   * @param viewModelState the state instance
   */
  public ViewModel(Model model, ViewModelState viewModelState)
  {
    this.model = model;
    this.viewModelState = viewModelState;
  }

  /**
   * A method to be implemented by subclasses that need to reset view model properties.
   */
  public void reset()
  {
    // do nothing by default
  }

  /**
   * A method to be implemented by subclasses that need to do any clean-up before another view is opened.
   */
  public void stop()
  {
    // do nothing by default
  }
}