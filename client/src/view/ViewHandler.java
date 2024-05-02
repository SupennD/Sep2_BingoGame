package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import utils.log.Log;
import viewmodel.ViewModelFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * A class responsible for handling view transitions, loading and showing views based on their name. Also, it
 * initializes {@link ViewController}s with the necessary {@link viewmodel.ViewModel} and a
 * {@link viewmodel.ViewModelState} for state persistence.
 *
 * @author Alexandru Tofan
 * @author Octavian
 * @version 1.0.1 - April 2024
 */
public class ViewHandler
{
  private final ViewModelFactory viewModelFactory;
  private final Map<View, ViewController> viewControllers;
  private final Log log = Log.getInstance();
  private Stage primaryStage;
  private Scene currentScene;

  /**
   * Constructor initializing the simple view model factory and a hash map to be used for view controller.
   *
   * @param viewModelFactory simple view model factory
   */
  public ViewHandler(ViewModelFactory viewModelFactory)
  {
    this.viewModelFactory = viewModelFactory;
    this.viewControllers = new HashMap<>();
  }

  /**
   * A method to be used for starting the application the first time.
   *
   * @param primaryStage the stage from the default start method of the JavaFX Application
   */
  public void start(Stage primaryStage)
  {
    this.primaryStage = primaryStage;
    this.currentScene = new Scene(new Region());
    openView(View.LOGIN);
  }

  /**
   * A method to be used by {@link ViewController}s to switch between different views.
   *
   * @param view the name of the {@link View} to open
   */
  public void openView(View view)
  {
    Region root = loadView(view);

    currentScene.setRoot(root);

    String title = "";
    if (root.getUserData() != null)
    {
      title += root.getUserData();
    }
    primaryStage.setTitle(title);
    primaryStage.setScene(currentScene);
    primaryStage.setWidth(root.getPrefWidth());
    primaryStage.setHeight(root.getPrefHeight());
    primaryStage.show();
  }

  /**
   * A method used to load a view from an FXML file based on a {@link View} name and to initialize the corresponding
   * {@link ViewController} with this {@link ViewHandler} it's {@link viewmodel.ViewModel} and the root of the FXML
   * view.
   *
   * @param view the name of the {@link View} to load
   * @return the root of the FXML view
   */
  private Region loadView(View view)
  {
    ViewController viewController = viewControllers.get(view);

    // Load view if it's not already loaded into the map
    if (viewController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(view.getFxml()));
        Region root = loader.load();
        viewController = loader.getController();
        viewControllers.put(view, viewController); // Add it to the map
        viewController.init(this, viewModelFactory.createViewModel(view), root);
      }
      catch (Exception e)
      {
        log.warn("Could not load view: " + e.getMessage());
      }
    }

    viewController.reset();

    return viewController.getRoot();
  }
}