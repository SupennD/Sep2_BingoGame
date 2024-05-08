package view;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import viewmodel.LoginViewModel;

/**
 * A controller class responsible for registration and login.
 *
 * @author Octavian
 * @version 1.0.0 - April 2024
 */
public class LoginViewController extends ViewController<LoginViewModel>
{
  @FXML private Text errorText;
  @FXML private TextField userNameField;
  @FXML private PasswordField passwordField;

  public void init(ViewHandler viewHandler, LoginViewModel viewModel, Region root)
  {
    super.init(viewHandler, viewModel, root);

    errorText.textProperty().bind(viewModel.errorProperty());
    userNameField.textProperty().bindBidirectional(viewModel.userNameProperty());
    passwordField.textProperty().bindBidirectional(viewModel.passwordProperty());
  }

  @FXML public void onRegister()
  {
    boolean success = viewModel.register();

    if (success)
    {
      viewHandler.openView(View.MAIN);
    }
  }

  @FXML public void onLogin()
  {
    boolean success = viewModel.login();

    if (success)
    {
      viewHandler.openView(View.MAIN);
    }
  }
}