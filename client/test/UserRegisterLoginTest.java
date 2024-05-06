import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mediator.Client;
import model.Model;
import model.ModelManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import view.View;
import viewmodel.LoginViewModel;
import viewmodel.ViewModelFactory;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test for user register/login
 *
 * @author Supendra Bogati
 * @version 1.0.0 - April 2024
 */
class UserRegisterLoginTest
{
  private final String userName = "testUser";
  private final String password = "testPassword";
  private Client client;
  private LoginViewModel loginViewModel;

  @BeforeEach public void beforeEach()
  {
    try
    {
      client = new Client("localhost");
      Model model = new ModelManager(client);
      ViewModelFactory viewModelFactory = new ViewModelFactory(model);
      loginViewModel = (LoginViewModel) viewModelFactory.createViewModel(View.LOGIN);
    }
    catch (Exception e)
    {
      throw new RuntimeException("Could not connect to server. Start the server application first.", e);
    }
  }

  @AfterEach public void afterEach()
  {
    try
    {
      loginViewModel = null;
      client.stop();
    }
    catch (RemoteException e)
    {
      // Ignore it
    }
  }

  @Order(1) @Test public void userCanRegister()
  {
    // Simulate the JavaFX view properties
    StringProperty errorProperty = new SimpleStringProperty();
    StringProperty userNameProperty = new SimpleStringProperty();
    StringProperty passwordProperty = new SimpleStringProperty();

    loginViewModel.reset();

    errorProperty.bind(loginViewModel.errorProperty());
    userNameProperty.bindBidirectional(loginViewModel.userNameProperty());
    passwordProperty.bindBidirectional(loginViewModel.passwordProperty());

    // Simulate entering a username and password
    userNameProperty.set(userName);
    passwordProperty.set(password);

    boolean success = loginViewModel.register();

    assertNull(errorProperty.get());
    assertTrue(success);
  }

  @Order(2) @Test public void userCanLogin()
  {
    // Simulate the JavaFX view properties
    StringProperty errorProperty = new SimpleStringProperty();
    StringProperty userNameProperty = new SimpleStringProperty();
    StringProperty passwordProperty = new SimpleStringProperty();

    loginViewModel.reset();

    errorProperty.bind(loginViewModel.errorProperty());
    userNameProperty.bindBidirectional(loginViewModel.userNameProperty());
    passwordProperty.bindBidirectional(loginViewModel.passwordProperty());

    // Simulate entering a username and password
    userNameProperty.set(userName);
    passwordProperty.set(password);

    boolean success = loginViewModel.login();

    assertNull(errorProperty.get());
    assertTrue(success);
  }
}