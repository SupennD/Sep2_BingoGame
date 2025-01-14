package viewmodel;

import model.Model;
import view.View;

/**
 * A simple parameterized factory that creates concrete view models based on a {@link View} parameter.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - April 2024
 */
public class ViewModelFactory
{
  /**
   * Main application model
   */
  private final Model model;
  /**
   * A {@link ViewModelState} instance to be passed to the created {@link ViewModel}'s
   */
  private final ViewModelState viewModelState;

  /**
   * Constructor initializing the instance variables, and creating a new {@link ViewModelState} instance to be used when
   * creating new {@link ViewModel}'s.
   *
   * @param model the main application model
   */
  public ViewModelFactory(Model model)
  {
    this.model = model;
    this.viewModelState = new ViewModelState();
  }

  /**
   * A simple parameterized factory method that creates {@link ViewModel}'s based on a {@link View} parameter.
   *
   * @param view the {@link View} for which to create a view model
   * @return a {@link ViewModel} based on the provided {@link View}
   */
  public ViewModel createViewModel(View view)
  {
    return switch (view)
    {
      // TODO: add cases for needed view types and remove the EXAMPLE
      case EXAMPLE -> new ExampleViewModel(model, viewModelState);
    };
  }
}