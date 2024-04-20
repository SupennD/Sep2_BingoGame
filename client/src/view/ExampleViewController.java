package view;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import viewmodel.ExampleViewModel;

/**
 * Example implementation of a ViewController. Yoy need to pass the concrete ViewModel implementation to the generic
 * ViewController.
 *
 * @deprecated Should be removed when the first UI task is implemented.
 */
public class ExampleViewController extends ViewController<ExampleViewModel>
{
  /**
   * The name should conform to intention + field type
   */
  @FXML private Text exampleText;

  @Override public void init(ViewHandler viewHandler, ExampleViewModel viewModel, Region root)
  {
    // Always call super init first
    super.init(viewHandler, viewModel, root);

    // Do any other initialisation steps
    exampleText.textProperty().bind(viewModel.exampleTextProperty());
  }

  /**
   * Example button action handler
   */
  @FXML public void sayHello()
  {
    viewModel.hello();
  }
}