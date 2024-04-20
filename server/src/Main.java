import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import mediator.Server;
import model.Model;
import model.ModelManager;
import view.ViewHandler;
import viewmodel.ViewModelFactory;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * Main class of the Server application and the entry point.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - April 2024
 */
public class Main extends Application
{
  /**
   * The main entry point.
   *
   * @param args optional command line arguments
   */
  public static void main(String[] args)
  {
    launch(args);
  }

  /**
   * The main entry point for the JavaFX application.
   *
   * @param primaryStage the primary stage for this application
   * @throws Exception if something goes wrong
   */
  @Override public void start(Stage primaryStage) throws Exception
  {
    Model model = new ModelManager();
    ViewModelFactory viewModelFactory = new ViewModelFactory(model);
    ViewHandler viewHandler = new ViewHandler(viewModelFactory);

    Server server = new Server(model);

    // Stop the server when the javafx application is closed
    primaryStage.setOnCloseRequest(windowEvent -> {
      try
      {
        server.stop();
      }
      catch (RemoteException | MalformedURLException e)
      {
        Platform.exit();
      }
    });

    viewHandler.start(primaryStage);
  }
}