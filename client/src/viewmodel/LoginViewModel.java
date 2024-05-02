package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Model;

/**
 * Class responsible for bridging the login model and view
 *
 * @author Lucia Andronic
 * @version 1.0.0 - April 2024
 */
public class LoginViewModel extends ViewModel
{
  private final StringProperty userNameProperty;
  private final StringProperty passwordProperty;
  private final StringProperty errorProperty;

  public LoginViewModel(Model model, ViewModelState viewModelState)
  {
    super(model, viewModelState);

    this.userNameProperty = new SimpleStringProperty();
    this.passwordProperty = new SimpleStringProperty();
    this.errorProperty = new SimpleStringProperty();
  }

  @Override public void reset()
  {
    userNameProperty.set(null);
    passwordProperty.set(null);
    errorProperty.set(null);
  }

  public StringProperty userNameProperty()
  {
    return userNameProperty;
  }

  public StringProperty passwordProperty()
  {
    return passwordProperty;
  }

  public StringProperty errorProperty()
  {
    return errorProperty;
  }

  public boolean register()
  {
    errorProperty.set(null);

    try
    {
      model.register(userNameProperty.get(), passwordProperty.get());
      viewModelState.put("userName", userNameProperty.get());

      return true;
    }
    catch (IllegalArgumentException | IllegalStateException e)
    {
      errorProperty.set(e.getMessage());
    }

    return false;
  }

  public boolean login()
  {
    errorProperty.set(null);

    try
    {
      model.login(userNameProperty.get(), passwordProperty.get());
      viewModelState.put("userName", userNameProperty.get());

      return true;
    }
    catch (IllegalArgumentException | IllegalStateException e)
    {
      errorProperty.set(e.getMessage());
    }

    return false;
  }
}