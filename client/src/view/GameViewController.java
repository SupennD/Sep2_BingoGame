package view;

import javafx.scene.layout.Region;
import viewmodel.GameViewModel;

/**
 * A controller class responsible for managing game
 *
 * @author Supendra Bogati
 * @version 1.0.0 - April 2024
 */
public class GameViewController extends ViewController<GameViewModel>
{
  @Override public void init(ViewHandler viewHandler, GameViewModel viewModel, Region root)
  {
    super.init(viewHandler, viewModel, root);
  }
}
