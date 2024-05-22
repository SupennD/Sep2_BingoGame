package view;

import javafx.scene.layout.Region;
import viewmodel.ViewModel;

/**
 * A general class that defines the base structure of a ViewController and should be extended by concrete view
 * controllers.
 *
 * @param <VM> the concrete {@link ViewModel} to be used in this view controller
 * @author Alexandru Tofan
 * @version 1.1.0 - May 2024
 */
public class ViewController<VM extends ViewModel>
{
  protected VM viewModel;
  protected ViewHandler viewHandler;
  private Region root;

  /**
   * Constructor invoked by FXML on load, should be empty.
   */
  public ViewController()
  {
  }

  /**
   * A method to be used for initialization after the controller was loaded. Can be overriden by subclasses to add other
   * initialization code. In that case the super class {@code init} should be called first.
   *
   * @param viewHandler the application view handler
   * @param viewModel the view model to be associated with this controller
   * @param root the FXML root
   */
  public void init(ViewHandler viewHandler, VM viewModel, Region root)
  {
    this.viewHandler = viewHandler;
    this.viewModel = viewModel;
    this.root = root;
  }

  /**
   * A method called by the {@link ViewHandler} before opening a new view. It delegates to this controllers
   * {@link ViewModel} and can be used for clean-up.
   */
  public final void stop()
  {
    viewModel.stop();
  }

  /**
   * A method used to reset view data by delegating to the {@link ViewModel}.
   */
  public final void reset()
  {
    viewModel.reset();
  }

  /**
   * A getter for the FXML root of this view
   *
   * @return the FXML root {@link Region}
   */
  public final Region getRoot()
  {
    return root;
  }
}