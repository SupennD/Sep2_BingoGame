package view;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import viewmodel.WinViewModel;

/**
 * Win view controller
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - May 2024
 * @see ViewController
 */
public class WinViewController extends ViewController<WinViewModel>
{
  @FXML private Text messageText;

  @Override public void init(ViewHandler viewHandler, WinViewModel viewModel, Region root)
  {
    super.init(viewHandler, viewModel, root);

    messageText.textProperty().bind(viewModel.messageProperty());
  }

  @FXML public void onReturn()
  {
    viewHandler.openView(View.MAIN);
  }
}