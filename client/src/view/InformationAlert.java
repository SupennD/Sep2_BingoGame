package view;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;

public class InformationAlert extends Alert
{
  public InformationAlert(String title, String header, String content)
  {
    super(AlertType.INFORMATION);

    TextArea textArea = new TextArea(content);
    textArea.setEditable(false);
    textArea.setWrapText(true);
    textArea.setBorder(Border.EMPTY);
    textArea.setPadding(Insets.EMPTY);

    this.getDialogPane().getStylesheets().add("/css/styles.css");
    this.setResizable(true);
    this.setTitle(title);
    this.setHeaderText(header);
    this.getDialogPane().setContent(textArea);
    this.getButtonTypes().setAll(ButtonType.CLOSE);
  }
}