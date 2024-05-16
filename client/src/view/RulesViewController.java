package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import viewmodel.RulesViewModel;

public class RulesViewController extends ViewController<RulesViewModel>
{
  @FXML private TextArea rulesTextArea;
  @FXML private Text errorText;

  @Override public void init(ViewHandler viewHandler, RulesViewModel viewModel, Region root)
  {
    super.init(viewHandler, viewModel, root);

    rulesTextArea.textProperty().bind(viewModel.rulesProperty());
    errorText.textProperty().bind(viewModel.errorPropery());
  }

  @FXML public void onReturn()
  {
    viewHandler.openView(View.ROOM);
  }
}
