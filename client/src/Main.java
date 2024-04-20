import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import mediator.Client;
import model.Model;
import model.ModelManager;
import view.ViewHandler;
import viewmodel.ViewModelFactory;

import java.rmi.RemoteException;
import java.util.Map;

/**
 * Main class of the Client application and the entry point.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - April 2024
 */
public class Main extends Application
{
  private final String HOST = "localhost";
  private final String PORT = "1099"; // Using a String to match the command line parameters type

  /**
   * The main entry point.
   *
   * @param args optional command line arguments, --host and --port supported by this application
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
    // Allow connecting to different servers using command line parameters
    // Ex... --host=127.0.0.1 --port=3444
    Map<String, String> namedParameters = getParameters().getNamed();
    String host = namedParameters.getOrDefault("host", HOST);
    int port = Integer.parseInt(namedParameters.getOrDefault("port", PORT));

    Client client = new Client(host, port);

    // Stop the server when the javafx application is closed
    primaryStage.setOnCloseRequest(windowEvent -> {
      try
      {
        client.stop();
      }
      catch (RemoteException e)
      {
        Platform.exit();
      }
    });

    Model model = new ModelManager(client);
    ViewModelFactory viewModelFactory = new ViewModelFactory(model);
    ViewHandler viewHandler = new ViewHandler(viewModelFactory);

    viewHandler.start(primaryStage);
  }
}