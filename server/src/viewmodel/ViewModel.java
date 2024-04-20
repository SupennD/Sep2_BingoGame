package viewmodel;

import model.Model;

/**
 * An abstract class to be extended by all ViewModel's in the Application.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - April 2024
 */
public abstract class ViewModel
{
  /**
   * Main application model
   */
  protected final Model model;
  /**
   * A {@link ViewModelState} instance to be used for state management
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
   * A method to be implemented by subclasses and used to reset model fields/properties.
   */
  public abstract void reset();
}