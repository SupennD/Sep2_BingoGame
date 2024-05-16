package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Model;

public class RulesViewModel extends ViewModel
{
  private final StringProperty rulesProperty;
  private final StringProperty errorPropery;

  public RulesViewModel(Model model, ViewModelState viewModelState)
  {
    super(model, viewModelState);

    this.rulesProperty = new SimpleStringProperty();
    this.errorPropery = new SimpleStringProperty();
  }

  @Override public void reset()
  {
    getRules();
    errorPropery.set(null);
  }

  public StringProperty rulesProperty()
  {
    return rulesProperty;
  }

  public StringProperty errorPropery()
  {
    return errorPropery;
  }

  private void getRules()
  {
    errorPropery.set(null);

    try
    {
      String rules = model.getRules(1); // TODO: fix this
      rulesProperty.set(rules);
    }
    catch (IllegalStateException e)
    {
      errorPropery.set(e.getMessage());
    }
  }
}

